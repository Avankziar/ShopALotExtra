package main.java.me.avankziar.sale.spigot.assistance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.handler.Base64Handler;
import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.listener.ShopPostTransactionListener;
import main.java.me.avankziar.sale.spigot.objects.ItemHologram;
import main.java.me.avankziar.sale.spigot.objects.PlayerData;
import main.java.me.avankziar.sale.spigot.objects.ShopLogVar;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.objects.SignShopDailyLog;
import main.java.me.avankziar.sale.spigot.objects.SignShopLog;
import main.java.me.avankziar.sale.spigot.objects.SignShopLog.WayType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class BackgroundTask
{
	private static SaLE plugin;
	
	public BackgroundTask(SaLE plugin)
	{
		BackgroundTask.plugin = plugin;
		initBackgroundTask();
	}
	
	public boolean initBackgroundTask()
	{
		cleanUpPlayerData(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.Player.Active", false));
		cleanUpSignShopLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShopLog.Active", false));
		cleanUpSignShopDailyLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShopDailyLog.Active", false));
		cleanUpShoppingLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShoppingLog.Active", false));
		cleanUpShoppingDailyLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShoppingDailyLog.Active", false));
		removeShopItemHologram();
		msgTransactionMessageToShopOwnerTimer();
		transactionShopLogTimer();
		return true;
	}
	
	public void cleanUpPlayerData(boolean active)
	{
		if(!active)
		{
			return;
		}
		final long offlineSinceAtLeast = System.currentTimeMillis()
				-1000L*60*60*24*plugin.getYamlHandler().getConfig().getInt("CleanUpTask.Player.DeleteAfterXDaysOffline");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int playerCount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.PLAYERDATA, "`last_login` < ?", offlineSinceAtLeast);
				if(playerCount <= 0)
				{
					return;
				}
				int signShopCount = 0;
				long itemLost = 0;
				int shoppingLogCount = 0;
				int shoppingDailyLogCount = 0;
				int signShopLogCount = 0;
				int signShopDailyLogCount = 0;
				int subscribedShop = 0;
				ArrayList<UUID> uuidlist = new ArrayList<>();
				ArrayList<Integer> ssIdList = new ArrayList<>();
				ArrayList<SignShop> ssList = new ArrayList<>();
				for(PlayerData pd : PlayerData.convert(plugin.getMysqlHandler().getFullList(MysqlHandler.Type.PLAYERDATA,
						"`id` ASC",	"`last_login` < ?", offlineSinceAtLeast)))
				{
					if(pd == null)
					{
						continue;
					}
					UUID owner = pd.getUUID();
					uuidlist.add(owner);
					ArrayList<SignShop> list = SignShop.convert(plugin.getMysqlHandler().getFullList(MysqlHandler.Type.SIGNSHOP,
							"`id` ASC", "`player_uuid` = ?", owner.toString()));
					ssList.addAll(list);
				}
				signShopCount = ssList.size();
				for(SignShop ss : ssList)
				{
					itemLost += ss.getItemStorageCurrent();
					ssIdList.add(ss.getId());
				}
				plugin.getMysqlHandler().deleteData(MysqlHandler.Type.PLAYERDATA,
						"`id` ASC",	"`last_login` < ?", offlineSinceAtLeast);
				for(UUID uuid : uuidlist)
				{
					plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOP,
							"`id` ASC", "`player_uuid` = ?", uuid.toString());
					subscribedShop += plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SUBSCRIBEDSHOP,
							"`id` ASC", "`player_uuid` = ?", uuid.toString());
					shoppingLogCount += plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SHOPPINGLOG,
							"`id` ASC", "`player_uuid` = ?", uuid.toString());
					shoppingDailyLogCount += plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SHOPPINGDAILYLOG,
							"`id` ASC", "`player_uuid` = ?", uuid.toString());
				}
				for(int ssid : ssIdList)
				{
					subscribedShop += plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SUBSCRIBEDSHOP,
							"`id` ASC", "`sign_shop_id` = ?", ssid);
					signShopLogCount += plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOPLOG,
							"`id` ASC", "`sign_shop_id` = ?", ssid);
					signShopDailyLogCount += plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOPDAILYLOG,
							"`id` ASC", "`sign_shop_id` = ?", ssid);
					plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOP,
							"`id` ASC", "`id` = ?", ssid);
				}
				plugin.getLogger().info("==========SaLE Database DeleteTask==========");
				plugin.getLogger().info("Deleted PlayerData: "+playerCount);
				plugin.getLogger().info("Deleted ShoppingLog: "+shoppingLogCount);
				plugin.getLogger().info("Deleted ShoppingDailyLog: "+shoppingDailyLogCount);
				plugin.getLogger().info("Deleted SubscribedStore: "+subscribedShop);
				plugin.getLogger().info("Deleted SignShop: "+signShopCount);
				plugin.getLogger().info("Lost ItemAmount: "+itemLost);
				plugin.getLogger().info("Deleted SignShopLog: "+signShopLogCount);
				plugin.getLogger().info("Deleted SignShopDailyLog: "+signShopDailyLogCount);
				plugin.getLogger().info("===========================================");
			}
		}.runTaskLaterAsynchronously(plugin, 20L*5);
	}
	
	public void cleanUpSignShopLog(boolean active)
	{
		if(!active)
		{
			return;
		}
		final long olderThanAtLeast = System.currentTimeMillis()
				-1000L*60*60*24*plugin.getYamlHandler().getConfig().getInt("CleanUpTask.ShopLog.DeleteAfterXDays", 365);
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int signShopLogCount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SIGNSHOPLOG,
						"`date_time` < ?", olderThanAtLeast);
				if(signShopLogCount <= 0)
				{
					return;
				}
				plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOPLOG,
						"`id` ASC", "`date_time` < ?", olderThanAtLeast);
				plugin.getLogger().info("==========SaLE Database DeleteTask==========");
				plugin.getLogger().info("Deleted SignShopLog: "+signShopLogCount);
				plugin.getLogger().info("===========================================");
			}
		}.runTaskLaterAsynchronously(plugin, 20L*6);
	}
	
	public void cleanUpSignShopDailyLog(boolean active)
	{
		if(!active)
		{
			return;
		}
		final long olderThanAtLeast = System.currentTimeMillis()
				-1000L*60*60*24*plugin.getYamlHandler().getConfig().getInt("CleanUpTask.ShopDailyLog.DeleteAfterXDays");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int signShopDailyLogCount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SIGNSHOPDAILYLOG,
						"`dates` < ?", olderThanAtLeast);
				if(signShopDailyLogCount <= 0)
				{
					return;
				}
				plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOPDAILYLOG,
						"`id` ASC", "`dates` < ?", olderThanAtLeast);
				plugin.getLogger().info("==========SaLE Database DeleteTask==========");
				plugin.getLogger().info("Deleted SignShopDailyLog: "+signShopDailyLogCount);
				plugin.getLogger().info("===========================================");
			}
		}.runTaskLaterAsynchronously(plugin, 20L*7);
	}
	
	public void cleanUpShoppingLog(boolean active)
	{
		if(!active)
		{
			return;
		}
		final long olderThanAtLeast = System.currentTimeMillis()
				-1000L*60*60*24*plugin.getYamlHandler().getConfig().getInt("CleanUpTask.ShoppingLog.DeleteAfterXDays");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int shoppingLogCount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SHOPPINGLOG,
						"`date_time` < ?", olderThanAtLeast);
				if(shoppingLogCount <= 0)
				{
					return;
				}
				plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SHOPPINGLOG,
						"`id` ASC", "`date_time` < ?", olderThanAtLeast);
				plugin.getLogger().info("==========SaLE Database DeleteTask==========");
				plugin.getLogger().info("Deleted ShoppingLog: "+shoppingLogCount);
				plugin.getLogger().info("===========================================");
			}
		}.runTaskLaterAsynchronously(plugin, 20L*8);
	}
	
	public void cleanUpShoppingDailyLog(boolean active)
	{
		if(!active)
		{
			return;
		}
		final long olderThanAtLeast = System.currentTimeMillis()
				-1000L*60*60*24*plugin.getYamlHandler().getConfig().getInt("CleanUpTask.ShoppingDailyLog.DeleteAfterXDays");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int shoppingDailyLogCount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SHOPPINGDAILYLOG,
						"`dates` < ?", olderThanAtLeast);
				if(shoppingDailyLogCount <= 0)
				{
					return;
				}
				plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SHOPPINGDAILYLOG,
						"`id` ASC", "`dates` < ?", olderThanAtLeast);
				plugin.getLogger().info("==========SaLE Database DeleteTask==========");
				plugin.getLogger().info("Deleted ShoppingDailyLog: "+shoppingDailyLogCount);
				plugin.getLogger().info("===========================================");
			}
		}.runTaskLaterAsynchronously(plugin, 20L*9);
	}
	
	public void removeShopItemHologram()
	{
		long runEveryXSeconds = plugin.getYamlHandler().getConfig().getInt("SignShop.ItemHologram.RunTimerInSeconds", 5);
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				doRemoveShopItemHologram();
			}
		}.runTaskTimer(plugin, 0L, runEveryXSeconds*20L);
	}
	
	public void doRemoveShopItemHologram()
	{
		long now = System.currentTimeMillis();
		ArrayList<String> toDelete = new ArrayList<>();
		for(Entry<String, ItemHologram> e : ItemHologramHandler.taskMap.entrySet())
		{
			if(Long.parseLong(e.getKey()) < now)
			{
				e.getValue().despawn();
				toDelete.add(e.getKey());
			}
		}
		for(String l : toDelete)
		{
			ItemHologramHandler.taskMap.remove(l);
		}
	}
	
	public void msgTransactionMessageToShopOwnerTimer()
	{
		long runEveryXMin = plugin.getYamlHandler().getConfig().getInt("SignShop.TransactionSummary.MessageToShopOwner.RunTimerInMinutes", 5)*60;
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				ArrayList<UUID> del = new ArrayList<>();
				for(UUID shopOwner : ShopPostTransactionListener.maping.keySet())
				{
					LinkedHashMap<UUID, LinkedHashMap<String, ShopLogVar>> subB = ShopPostTransactionListener.maping.get(shopOwner);
					LinkedHashMap<UUID, List<String>> lhm = new LinkedHashMap<>(); //client, hover
					for(UUID client : subB.keySet())
					{
						LinkedHashMap<String, ShopLogVar> sub2 = subB.get(client);
						List<String> hov = new ArrayList<>();
						for(Entry<String, ShopLogVar> var : sub2.entrySet())
						{
							ItemStack is = new Base64Handler(var.getKey()).fromBase64();
							String shopname = var.getValue().shopname;
							String currency = var.getValue().currency;
							long bsamo = var.getValue().itemAmountBuy;
							double bcostTotal = var.getValue().costTotalBuy;
							long ssamo = var.getValue().itemAmountSell;
							double scostTotal = var.getValue().costTotalSell;
							if(bsamo > 0)
							{
								hov.add(plugin.getYamlHandler().getLang().getString("ShopLog.MsgTimer.Buy")
										.replace("%amount%", String.valueOf(bsamo))
										.replace("%item%", is.getItemMeta().hasDisplayName() 
												? is.getItemMeta().getDisplayName() 
												: SaLE.getPlugin().getEnumTl().getLocalization(is.getType()))
										.replace("%shop%", shopname)
										.replace("%format%", plugin.getIFHEco().format(bcostTotal,
												SaLE.getPlugin().getIFHEco().getCurrency(currency))));
							}
							if(ssamo > 0)
							{
								hov.add(plugin.getYamlHandler().getLang().getString("ShopLog.MsgTimer.Sell")
										.replace("%amount%", String.valueOf(ssamo))
										.replace("%item%", is.getItemMeta().hasDisplayName() 
												? is.getItemMeta().getDisplayName() 
												: SaLE.getPlugin().getEnumTl().getLocalization(is.getType()))
										.replace("%shop%", shopname)
										.replace("%format%", plugin.getIFHEco().format(scostTotal,
												SaLE.getPlugin().getIFHEco().getCurrency(currency))));
							}
						}
						if(lhm.containsKey(client))
						{
							List<String> ls = lhm.get(client);
							ls.addAll(hov);
							lhm.put(client, ls);
						} else
						{
							lhm.put(client, hov);
						}
					}
					for(Entry<UUID, List<String>> li : lhm.entrySet())
					{
						StringBuilder sb = new StringBuilder();
						for(int i = 0; i < li.getValue().size(); i++)
						{
							sb.append(li.getValue().get(i));
							if(i+1 < li.getValue().size())
							{
								sb.append("~!~");
							}
						}
						String pn = Utility.convertUUIDToName(li.getKey().toString());
						TextComponent tc = ChatApi.hoverEvent(
								plugin.getYamlHandler().getLang().getString("ShopLog.MsgTimer.Msg").replace("%player%", pn),
								HoverEvent.Action.SHOW_TEXT, sb.toString());
						
						if(Bukkit.getPlayer(shopOwner) != null)
						{
							Bukkit.getPlayer(shopOwner).spigot().sendMessage(tc);
						} else
						{
							ArrayList<BaseComponent> list = new ArrayList<>();
							list.add(tc);
							ArrayList<ArrayList<BaseComponent>> listInList = new ArrayList<>();
							listInList.add(list);
							SaLE.getPlugin().getBctB().sendMessage(shopOwner, listInList);
						}
					}
					del.add(shopOwner);
				}
				for(UUID uuid : del)
				{
					ShopPostTransactionListener.maping.remove(uuid);
				}
			}
		}.runTaskTimer(plugin, 0L, runEveryXMin*20L);
	}
	
	public void transactionShopLogTimer()
	{
		long runEveryXMin = plugin.getYamlHandler().getConfig().getInt("SignShop.TransactionSummary.ShopLop.RunTimerInMinutes", 15)*60;
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				doShopLog();
			}
		}.runTaskTimer(plugin, 0L, runEveryXMin*20L);
	}
	
	public void doShopLog() //TODO fehler bei der erstellung des shoplogs
	{
		ArrayList<UUID> del = new ArrayList<>();
		for(UUID shopOwner : ShopPostTransactionListener.maping2.keySet())
		{
			LinkedHashMap<UUID, LinkedHashMap<String, ShopLogVar>> subB = ShopPostTransactionListener.maping2.get(shopOwner);
			for(UUID client : subB.keySet())
			{
				LinkedHashMap<String, ShopLogVar> sub2 = subB.get(client);
				for(Entry<String, ShopLogVar> var : sub2.entrySet())
				{
					ItemStack is = new Base64Handler(var.getKey()).fromBase64();
					int shopID = var.getValue().shopID;
					long bsamo = var.getValue().itemAmountBuy;
					double bcostTotal = var.getValue().costTotalBuy;
					long ssamo = var.getValue().itemAmountSell;
					double scostTotal = var.getValue().costTotalSell;
					long date = TimeHandler.getDate(TimeHandler.getDate(System.currentTimeMillis()));
					if(bsamo > 0)
					{
						SignShopLog ssl = new SignShopLog(0, shopID, System.currentTimeMillis(),
								is, is.getItemMeta().hasDisplayName() 
								? is.getItemMeta().getDisplayName() 
								: SaLE.getPlugin().getEnumTl().getLocalization(is.getType()),
							is.getType(), WayType.BUY, bcostTotal, (int) bsamo,
							client, shopOwner);
						plugin.getMysqlHandler().create(MysqlHandler.Type.SIGNSHOPLOG, ssl);
					}
					if(ssamo > 0)
					{
						SignShopLog ssl = new SignShopLog(0, shopID, System.currentTimeMillis(),
								is, is.getItemMeta().hasDisplayName() 
								? is.getItemMeta().getDisplayName() 
								: SaLE.getPlugin().getEnumTl().getLocalization(is.getType()),
							is.getType(), WayType.SELL, scostTotal, (int) ssamo,
							client, shopOwner);
						plugin.getMysqlHandler().create(MysqlHandler.Type.SIGNSHOPLOG, ssl);
					}
					SignShopDailyLog ssdl = (SignShopDailyLog) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOPDAILYLOG,
							"`sign_shop_id` = ? AND `dates` = ?", shopID, date);
					if(ssdl == null)
					{
						ssdl = new SignShopDailyLog(0, shopID, date, bcostTotal, scostTotal, (int) bsamo, (int) ssamo, shopOwner);
						plugin.getMysqlHandler().create(MysqlHandler.Type.SIGNSHOPDAILYLOG, ssdl);
					} else
					{
						ssdl.setBuyItemAmount(ssdl.getBuyItemAmount()+(int)bsamo);
						ssdl.setBuyAmount(ssdl.getBuyAmount()+bcostTotal);
						ssdl.setSellItemAmount(ssdl.getSellItemAmount()+(int)ssamo);
						ssdl.setSellAmount(ssdl.getSellAmount()+scostTotal);
						plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOPDAILYLOG, ssdl, 
								"`sign_shop_id` = ? AND `dates` = ?", shopID,
								date);
					}
				}
			}
			del.add(shopOwner);
		}
		for(UUID uuid : del)
		{
			ShopPostTransactionListener.maping2.remove(uuid);
		}
	}
}