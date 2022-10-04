package main.java.me.avankziar.sale.spigot.assistance;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.objects.ItemHologram;
import main.java.me.avankziar.sale.spigot.objects.PlayerData;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class BackgroundTask
{
	private static SaLE plugin;
	
	public BackgroundTask(SaLE plugin)
	{
		BackgroundTask.plugin = plugin;
		//initBackgroundTask();
	}
	
	public boolean initBackgroundTask()
	{
		cleanUpPlayerData(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.Player.Active", false));
		cleanUpSignShopLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShopLog.Active", false));
		cleanUpSignShopDailyLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShopDailyLog.Active", false));
		cleanUpShoppingLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShoppingLog.Active", false));
		cleanUpShoppingDailyLog(plugin.getYamlHandler().getConfig().getBoolean("CleanUpTask.ShoppingDailyLog.Active", false));
		removeShopItemHologram();
		//TODO Steuern pro Woche pro Shop
		//TODO Timer für 5 min um kurz nachricht zu geben für einen kauf pro spieler+Hover was gekauft wurde pro item
		//TODO Timer für 15 min um alle Shoplog für den moneylog zusammenzufassen.
		
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
				-1000L*60*60*24*plugin.getYamlHandler().getConfig().getInt("CleanUpTask.ShopLog.DeleteAfterXDays", 365); //TODO Fehler?
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				int signShopLogCount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SIGNSHOPLOG,
						"`id` ASC", "`date_time` < ?", olderThanAtLeast);
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
						"`id` ASC", "`dates` < ?", olderThanAtLeast);
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
						"`id` ASC", "`date_time` < ?", olderThanAtLeast);
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
						"`id` ASC", "`dates` < ?", olderThanAtLeast);
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
		final long runEveryXSeconds = plugin.getYamlHandler().getConfig().getInt("ShopItemHover.ShoppingDailyLog.DeleteAfterXDays");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				long now = System.currentTimeMillis();
				ArrayList<Long> toDelete = new ArrayList<>();
				for(Entry<Long, ItemHologram> e : ItemHologramHandler.taskMap.entrySet())
				{
					if(e.getKey() < now)
					{
						e.getValue().despawn();
					}
					toDelete.add(e.getKey());
				}
				for(Long l : toDelete)
				{
					ItemHologramHandler.taskMap.remove(l);
				}
			}
		}.runTaskTimer(plugin, 20L*5, runEveryXSeconds*20L);
	}
}
