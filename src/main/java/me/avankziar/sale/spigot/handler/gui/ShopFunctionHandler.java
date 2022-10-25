package main.java.me.avankziar.sale.spigot.handler.gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.ifh.general.economy.account.AccountCategory;
import main.java.me.avankziar.ifh.general.economy.action.EconomyAction;
import main.java.me.avankziar.ifh.general.economy.action.OrdererType;
import main.java.me.avankziar.ifh.spigot.economy.account.Account;
import main.java.me.avankziar.ifh.spigot.economy.currency.EconomyCurrency;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.event.ShopPostTransactionEvent;
import main.java.me.avankziar.sale.spigot.event.ShopPreTransactionEvent;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.MessageHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.objects.ShoppingDailyLog;
import main.java.me.avankziar.sale.spigot.objects.ShoppingLog;
import main.java.me.avankziar.sale.spigot.objects.ShoppingLog.WayType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.objects.SubscribedShop;

public class ShopFunctionHandler
{
	private static SaLE plugin = SaLE.getPlugin();
	private static Enchantment[] enchs = Enchantment.values();
	private static PotionEffectType[] poefty = PotionEffectType.values();
	
	public static void doClickFunktion(GuiType guiType, ClickFunctionType cft, Player player, SignShop ssh,
			Inventory openInv, SettingsLevel settingsLevel)
	{
		switch(cft)
		{
		default: return;
		case SHOP_BUY_1: buy(player, ssh, 1, openInv, settingsLevel); break;
		case SHOP_BUY_8: buy(player, ssh, 8, openInv, settingsLevel); break;
		case SHOP_BUY_16: buy(player, ssh, 16, openInv, settingsLevel); break;
		case SHOP_BUY_32: buy(player, ssh, 32, openInv, settingsLevel); break;
		case SHOP_BUY_64: buy(player, ssh, 64, openInv, settingsLevel); break;
		case SHOP_BUY_576: buy(player, ssh, 576, openInv, settingsLevel); break;
		case SHOP_BUY_1728: buy(player, ssh, 1728, openInv, settingsLevel); break;
		case SHOP_BUY_2304: buy(player, ssh, 2304, openInv, settingsLevel); break;
		case SHOP_SELL_1: sell(player, ssh, 1, openInv, settingsLevel); break;
		case SHOP_SELL_8: sell(player, ssh, 8, openInv, settingsLevel); break;
		case SHOP_SELL_16: sell(player, ssh, 16, openInv, settingsLevel); break;
		case SHOP_SELL_32: sell(player, ssh, 32, openInv, settingsLevel); break;
		case SHOP_SELL_64: sell(player, ssh, 64, openInv, settingsLevel); break;
		case SHOP_SELL_576: sell(player, ssh, 576, openInv, settingsLevel); break;
		case SHOP_SELL_1728: sell(player, ssh, 1728, openInv, settingsLevel); break;
		case SHOP_SELL_2304: sell(player, ssh, 2304, openInv, settingsLevel); break;
		case SHOP_TOGGLE_SUBSCRIBE: subscribe(player, ssh, openInv, settingsLevel); break;
		}
		SignHandler.updateSign(ssh);
	}
	
	private static boolean isDiscount(SignShop ssh, long now)
	{
		return now >= ssh.getDiscountStart() && now < ssh.getDiscountEnd();
	}
	
	private static int emtpySlots(Player player)
	{
		int es = 0;
		for(int i = 0; i < player.getInventory().getStorageContents().length; i++)
		{
			ItemStack is = player.getInventory().getStorageContents()[i];
			if(is == null || is.getType() == Material.AIR)
			{
				es++;
			}
		}
		return es;
	}
	
	private static boolean doTransaction(Player player, Account from, Account to, double amount, EconomyCurrency ec,
			String category, String comment, Double taxation)
	{
		Account tax = plugin.getIFHEco().getDefaultAccount(from.getOwner().getUUID(), AccountCategory.TAX, ec);
		EconomyAction ea = null;
		if(taxation == null && category != null)
		{
			ea = plugin.getIFHEco().transaction(from, to, amount, OrdererType.PLAYER, player.getUniqueId().toString(), category, comment);
		} else
		{
			boolean taxAreExclusive = false;
			ea = plugin.getIFHEco().transaction(from, to, amount, taxation, taxAreExclusive, tax, 
					OrdererType.PLAYER, player.getUniqueId().toString(), category, comment);
		}
		if(!ea.isSuccess())
		{
			player.sendMessage(ChatApi.tl(ea.getDefaultErrorMessage()));
			return false;
		}
		ArrayList<String> list = new ArrayList<>();
		String wformat = plugin.getIFHEco().format(ea.getWithDrawAmount(), from.getCurrency());
		String dformat = plugin.getIFHEco().format(ea.getDepositAmount(), from.getCurrency());
		String tformat = plugin.getIFHEco().format(ea.getTaxAmount(), from.getCurrency());
		for(String s : plugin.getYamlHandler().getLang().getStringList("ShopFunctionHandler.Transaction"))
		{
			String a = s.replace("%fromaccount%", from.getAccountName())
			.replace("%toaccount%", to.getAccountName())
			.replace("%formatwithdraw%", wformat)
			.replace("%formatdeposit%", dformat)
			.replace("%formattax%", tformat)
			.replace("%category%", category != null ? category : "/")
			.replace("%comment%", comment != null ? comment : "/");
			list.add(a);
		}
		for(String s : list)
		{
			player.sendMessage(ChatApi.tl(s));
		}
		return true;
	}
	
	private static void buy(Player player, SignShop ssh, long amount, Inventory inv, SettingsLevel settingsLevel)
	{
		if(!ssh.canBuy())
		{
			return;
		}
		if(ssh.getItemStorageCurrent() == 0 && !ssh.isUnlimitedBuy())
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.NoGoodsInStock")));
			if(plugin.getPCS() != null && ssh.getStorageID() != 0)
			{
				plugin.getPCS().getOutOfStorageToShop(ssh.getId(), ssh.getStorageID(),
						ssh.getItemStack(), (long) (ssh.getItemStorageTotal()*0.5));
			} else
			{
				String msg = plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.NoGoodsInStockII")
						.replace("%shopname%", ssh.getSignShopName());
				new MessageHandler().sendMessageToOwnerAndMember(ssh, msg);
			}
			return;
		}
		Double d = 0.0;
		if(isDiscount(ssh, System.currentTimeMillis()))
		{
			d = ssh.getDiscountBuyAmount();
			if((d == null && ssh.getBuyAmount() == null) || (d < 0.0 && ssh.getBuyAmount() < 0.0))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.NotInit")));
				return;
			} else
			{
				d = ssh.getBuyAmount();
			}
			if(ssh.getDiscountPossibleBuy() == 0)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.PossibleIsZero")));
				return;
			}
		} else
		{
			d = ssh.getBuyAmount();
			if(d == null || d < 0.0)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.NotInit")));
				return;
			}
			if(ssh.getPossibleBuy() == 0)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.PossibleIsZero")));
				return;
			}
		}
		Account to = plugin.getIFHEco().getAccount(ssh.getAccountId());
		if(to == null)
		{
			player.sendMessage(ChatApi.tl(
					plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.ShopHaveNotAccountReady")));
			return;
		}
		Account from = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.MAIN, to.getCurrency());
		if(from == null)
		{
			player.sendMessage(ChatApi.tl(
					plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Buy.YouDontHaveAccountToWithdraw")));
			return;
		}
		ArrayList<ItemStack> islist = new ArrayList<>();
		int emptySlot = emtpySlots(player);
		long postc = ssh.getItemStorageCurrent();
		long quantity = amount;
		if(quantity > ssh.getItemStorageCurrent())
		{
			if(!ssh.isUnlimitedBuy())
			{
				quantity = ssh.getItemStorageCurrent();
			}
		}
		long samo = quantity;
		while(emptySlot > 0)
		{
			ItemStack is = ssh.getItemStack().clone();
			if(quantity > is.getMaxStackSize())
			{
				is.setAmount(is.getMaxStackSize());
				islist.add(is);
				postc = postc - is.getMaxStackSize();
				quantity =  quantity - is.getMaxStackSize();
			} else if(is.getMaxStackSize() >= quantity)
			{
				is.setAmount((int) quantity);
				islist.add(is);
				postc = postc - quantity;
				quantity = 0;
				break;
			}
			emptySlot--;
		}
		if(quantity != 0)
		{
			samo = samo - quantity;
		}
		Double taxation = plugin.getYamlHandler().getConfig().get("SignShop.Tax.BuyInPercent") != null 
				? plugin.getYamlHandler().getConfig().getDouble("SignShop.Tax.BuyInPercent") : null;
		
		/*if(plugin.getBonusMalus() != null) //TODO
		{
			taxation = plugin.getBonusMalus().getResult(player.getUniqueId(), taxation, BonusMalus.SHOP_BUYING_TAX.getBonusMalus());
		}*/
		ShopPreTransactionEvent sprte = new ShopPreTransactionEvent(ssh, samo, d.doubleValue(), taxation, true, player);
		Bukkit.getPluginManager().callEvent(sprte);
		if(sprte.isCancelled())
		{
			return;
		}
		String category = plugin.getYamlHandler().getLang().getString("Economy.Buy.Category");
		String comment = plugin.getYamlHandler().getLang().getString("Economy.Buy.Comment")
				.replace("%amount%", String.valueOf(samo))
				.replace("%item%", ssh.getItemStack().getItemMeta().hasDisplayName() 
						? ssh.getItemStack().getItemMeta().getDisplayName() 
						: SaLE.getPlugin().getEnumTl().getLocalization(ssh.getItemStack().getType()))
				.replace("%shop%", ssh.getSignShopName());
		if(!doTransaction(player, from, to, samo*d, to.getCurrency(), category, comment, taxation))
		{
			return;
		}
		comment = comment + plugin.getYamlHandler().getLang().getString("Economy.CommentAddition")
				.replace("%format%", plugin.getIFHEco().format(samo*d, from.getCurrency()));
		long date = TimeHandler.getDate(TimeHandler.getDate(System.currentTimeMillis()));
		ShoppingLog sl = new ShoppingLog(0, player.getUniqueId(), System.currentTimeMillis(),
				ssh.getItemStack(), ssh.getDisplayName(), ssh.getMaterial(), WayType.BUY, samo*d, (int) samo,
				ssh.getId());
		plugin.getMysqlHandler().create(MysqlHandler.Type.SHOPPINGLOG, sl);
		ShoppingDailyLog sdl = (ShoppingDailyLog) plugin.getMysqlHandler().getData(MysqlHandler.Type.SHOPPINGDAILYLOG,
				"`player_uuid` = ? AND `dates` = ?", player.getUniqueId().toString(), date);
		if(sdl == null)
		{
			sdl = new ShoppingDailyLog(0, player.getUniqueId(), date, samo*d, 0, (int) samo, 0);
			plugin.getMysqlHandler().create(MysqlHandler.Type.SHOPPINGDAILYLOG, sdl);
		} else
		{
			sdl.setBuyAmount(sdl.getBuyAmount()+samo*d);
			sdl.setBuyItemAmount(sdl.getBuyItemAmount()+(int) samo);
			plugin.getMysqlHandler().updateData(MysqlHandler.Type.SHOPPINGDAILYLOG, sdl, "`id` = ?", sdl.getId());
		}
		ShopPostTransactionEvent spote = new ShopPostTransactionEvent(ssh, samo, d.doubleValue(), true, player, category, comment);
		Bukkit.getPluginManager().callEvent(spote);
		if(!ssh.isUnlimitedBuy())
		{
			ssh.setItemStorageCurrent(postc);
			plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		}
		for(ItemStack is : islist)
		{
			player.getInventory().addItem(is);
		}
		if(!ssh.isUnlimitedBuy())
		{
			if(ssh.getItemStorageCurrent() < ssh.getItemStorageTotal()*0.1)
			{
				if(plugin.getPCS() != null && ssh.getStorageID() != 0)
				{
					plugin.getPCS().getOutOfStorageToShop(ssh.getId(), ssh.getStorageID(),
							ssh.getItemStack(), (long)( ssh.getItemStorageTotal()*0.5));
				}
			}
		}
		GuiHandler.openShop(ssh, player, settingsLevel, inv, false);
	}
	
	private static void sell(Player player, SignShop ssh, long amount, Inventory inv, SettingsLevel settingsLevel)
	{
		if(!ssh.canSell())
		{
			return;
		}
		if(ssh.getItemStorageCurrent() == ssh.getItemStorageTotal() && !ssh.isUnlimitedBuy())
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.ShopIsFull")));
			if(plugin.getPCS() != null && ssh.getStorageID() != 0)
			{
				long removed = (long) (ssh.getItemStorageTotal()*0.5);
				ssh.setItemStorageCurrent(ssh.getItemStorageCurrent()-removed);
				plugin.getPCS().putIntoStorageFromShop(ssh.getId(), ssh.getStorageID(), ssh.getItemStack(), removed);
				plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
			} else
			{
				String msg = plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.ShopIsFullII")
						.replace("%shopname%", ssh.getSignShopName());
				new MessageHandler().sendMessageToOwnerAndMember(ssh, msg);
			}
			return;
		}
		Double d = 0.0;
		if(isDiscount(ssh, System.currentTimeMillis()))
		{
			d = ssh.getDiscountSellAmount();
			if((d == null && ssh.getSellAmount() == null) || (d < 0.0 && ssh.getSellAmount() < 0.0))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.NotInit")));
				return;
			} else
			{
				d = ssh.getSellAmount();
			}
			if(ssh.getDiscountPossibleSell() == 0)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.PossibleIsZero")));
				return;
			}
		} else
		{
			d = ssh.getSellAmount();
			if(d == null || d < 0.0)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.NotInit")));
				return;
			}
			if(ssh.getPossibleSell() == 0)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.PossibleIsZero")));
				return;
			}
		}
		Account from = plugin.getIFHEco().getAccount(ssh.getAccountId());
		if(from == null)
		{
			player.sendMessage(ChatApi.tl(
					plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.ShopHaveNotAccountReady")));
			return;
		}
		Account to = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.MAIN, from.getCurrency());
		if(to == null)
		{
			player.sendMessage(ChatApi.tl(
					plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.YouDontHaveAccountToWithdraw")));
			return;
		}
		ArrayList<ItemStack> islist = new ArrayList<>();
		long postc = ssh.getItemStorageCurrent();
		long quantity = amount;
		if(quantity > ssh.getItemStorageTotal() - ssh.getItemStorageCurrent())
		{
			if(!ssh.isUnlimitedSell())
			{
				quantity = ssh.getItemStorageTotal() - ssh.getItemStorageCurrent();
			}
		}
		long samo = quantity;
		long count = 0;
		for(int i = 0; i < player.getInventory().getStorageContents().length; i++)
		{
			ItemStack is = player.getInventory().getStorageContents()[i];
			if(is == null || is.getType() == Material.AIR)
			{
				continue;
			}
			ItemStack c = is.clone();
			c.setAmount(1);
			if(!isSimilar(ssh.getItemStack(), c))
			{
				continue;
			}
			if(quantity == 0)
			{
				break;
			}
			if(quantity > is.getAmount())
			{
				count += is.getAmount();
				postc = postc + is.getAmount();
				quantity = quantity - is.getAmount();
				ItemStack cc = is.clone();
				islist.add(cc);
				is.setAmount(0);
			} else if(quantity <= is.getAmount())
			{
				count += quantity;
				postc = postc + quantity;
				ItemStack cc = is.clone();
				cc.setAmount((int) quantity);
				islist.add(cc);
				is.setAmount(is.getAmount() - (int) quantity);
				quantity = 0;
				break;
			}
		}
		if(count == 0)
		{
			player.sendMessage(ChatApi.tl(
					plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Sell.NoItemInInventory")));
			return;
		}
		if(quantity != 0)
		{
			samo = samo - quantity;
		}
		Double taxation = plugin.getYamlHandler().getConfig().get("SignShop.Tax.SellInPercent") != null 
				? plugin.getYamlHandler().getConfig().getDouble("SignShop.Tax.SellInPercent") : null;
		/*if(plugin.getBonusMalus() != null)
		{
			taxation = plugin.getBonusMalus().getResult(player.getUniqueId(), taxation, BonusMalus.SHOP_SELLING_TAX.getBonusMalus());
		}*/
		ShopPreTransactionEvent sprte = new ShopPreTransactionEvent(ssh, samo, d.doubleValue(), taxation, false, player);
		Bukkit.getPluginManager().callEvent(sprte);
		if(sprte.isCancelled())
		{
			return;
		}
		String category = plugin.getYamlHandler().getLang().getString("Economy.Sell.Category");
		String comment = plugin.getYamlHandler().getLang().getString("Economy.Sell.Comment")
				.replace("%amount%", String.valueOf(samo))
				.replace("%item%", ssh.getItemStack().getItemMeta().hasDisplayName() 
						? ssh.getItemStack().getItemMeta().getDisplayName() 
						: SaLE.getPlugin().getEnumTl().getLocalization(ssh.getItemStack().getType()))
				.replace("%shop%", ssh.getSignShopName());
		if(!doTransaction(player, from, to, d*samo, to.getCurrency(), category, comment, taxation))
		{
			for(ItemStack is : islist)
			{
				player.getInventory().addItem(is);
			}
			return;
		}
		comment = comment + plugin.getYamlHandler().getLang().getString("Economy.CommentAddition")
				.replace("%format%", plugin.getIFHEco().format(samo*d, from.getCurrency()));
		long date = TimeHandler.getDate(TimeHandler.getDate(System.currentTimeMillis()));
		ShoppingLog sl = new ShoppingLog(0, player.getUniqueId(), System.currentTimeMillis(),
				ssh.getItemStack(), ssh.getDisplayName(), ssh.getMaterial(), WayType.SELL, samo*d, (int) samo,
				ssh.getId());
		plugin.getMysqlHandler().create(MysqlHandler.Type.SHOPPINGLOG, sl);
		ShoppingDailyLog sdl = (ShoppingDailyLog) plugin.getMysqlHandler().getData(MysqlHandler.Type.SHOPPINGDAILYLOG,
				"`player_uuid` = ? AND `dates` = ?", player.getUniqueId().toString(), date);
		if(sdl == null)
		{
			sdl = new ShoppingDailyLog(0, player.getUniqueId(), date, 0, samo*d, 0, (int) samo);
			plugin.getMysqlHandler().create(MysqlHandler.Type.SHOPPINGDAILYLOG, sdl);
		} else
		{
			sdl.setSellAmount(sdl.getSellAmount()+samo*d);
			sdl.setSellItemAmount(sdl.getSellItemAmount()+(int) samo);
			plugin.getMysqlHandler().updateData(MysqlHandler.Type.SHOPPINGDAILYLOG, sdl, "`id` = ?", sdl.getId());
		}
		ShopPostTransactionEvent spote = new ShopPostTransactionEvent(ssh, samo, d.doubleValue(), false, player, category, comment);
		Bukkit.getPluginManager().callEvent(spote);
		if(!ssh.isUnlimitedSell())
		{
			ssh.setItemStorageCurrent(postc);
			if(ssh.getItemStorageCurrent() > ssh.getItemStorageTotal()/100*90)
			{
				if(plugin.getPCS() != null && ssh.getStorageID() != 0)
				{
					long removed = (long) (ssh.getItemStorageTotal()*0.5);
					ssh.setItemStorageCurrent(ssh.getItemStorageCurrent()-removed);
					plugin.getPCS().putIntoStorageFromShop(ssh.getId(), ssh.getStorageID(), ssh.getItemStack(), removed);
				}
			}
		}
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		GuiHandler.openShop(ssh, player, settingsLevel, inv, false);
	}
	
	private static void subscribe(Player player, SignShop ssh, Inventory inv, SettingsLevel settingsLevel)
	{
		SubscribedShop subs = (SubscribedShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SUBSCRIBEDSHOP, 
				"`player_uuid` = ? AND `sign_shop_id` = ?", player.getUniqueId().toString(), ssh.getId());
		if(subs != null)
		{
			plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SUBSCRIBEDSHOP, "`id` = ?", subs.getId());
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Unsubscribe")
					.replace("%shop%", ssh.getSignShopName())));
		} else
		{
			subs = new SubscribedShop(0, player.getUniqueId(), ssh.getId(), System.currentTimeMillis());
			plugin.getMysqlHandler().create(MysqlHandler.Type.SUBSCRIBEDSHOP, subs);
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("ShopFunctionHandler.Subscribe")
					.replace("%shop%", ssh.getSignShopName())));
		}
		GuiHandler.openShop(ssh, player, settingsLevel, inv, false);
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isSimilar(ItemStack item, ItemStack filter)
	{
		if (item == null || filter == null) 
        {
            return false;
        }
        final ItemStack i = item.clone();
        final ItemStack f = filter.clone();
        i.setAmount(1);
        f.setAmount(1);
        if(i.getType() != f.getType())
        {
        	return false;
        }
        if(i.hasItemMeta() == true && f.hasItemMeta() == true)
        {
        	ItemMeta im = i.getItemMeta();
        	ItemMeta fm = f.getItemMeta();
        	if(im.hasCustomModelData())
        	{
        		if(!fm.hasCustomModelData())
        		{
        			return false;
        		}
        		if(im.getCustomModelData() != fm.getCustomModelData())
        		{
        			return false;
        		}
        	}
        	if(im.hasDisplayName())
        	{
        		if(!fm.hasDisplayName())
        		{
        			return false;
        		}
        		if(!im.getDisplayName().equals(fm.getDisplayName()))
        		{
        			return false;
        		}
        	}
        	if(!im.getItemFlags().isEmpty())
        	{
        		if(fm.getItemFlags().isEmpty()
        				|| im.getItemFlags().size() != fm.getItemFlags().size())
        		{
        			return false;
        		}
        		for(ItemFlag iifs : im.getItemFlags())
        		{
        			if(!fm.hasItemFlag(iifs))
        			{
        				return false;
        			}
        		}
        	}
        	if(im.hasLore())
        	{
        		if(!fm.hasLore())
        		{
        			return false;
        		}
        		for(int j = 0; j < im.getLore().size(); j++)
        		{
        			if(j >= fm.getLore().size())
        			{
        				return false;
        			}
        			if(!im.getLore().get(j).equals(fm.getLore().get(j)))
        			{
        				return false;
        			}
        		}
        	}
        	if(im instanceof EnchantmentStorageMeta)
        	{
        		if(!(fm instanceof EnchantmentStorageMeta))
        		{
        			return false;
        		}
        		EnchantmentStorageMeta iesm = (EnchantmentStorageMeta) im;
        		EnchantmentStorageMeta fesm = (EnchantmentStorageMeta) fm;
        		i.setItemMeta(orderStorageEnchantments(iesm));
        		f.setItemMeta(orderStorageEnchantments(fesm));
        		im = i.getItemMeta();
            	fm = f.getItemMeta();
        		for(Entry<Enchantment, Integer> e : iesm.getStoredEnchants().entrySet())
        		{
        			boolean bo = false;
        			for(Entry<Enchantment, Integer> ee : fesm.getStoredEnchants().entrySet())
        			{
        				if(e.getKey().getName().equals(ee.getKey().getName())
        						&& e.getValue() == ee.getValue())
        				{
        					bo = true;
        					break;
        				}
        			}
        			if(!bo)
        			{
        				return false;
        			}
        		}
        	}
        	
        	if(im.hasEnchants() && i.getType() != Material.ENCHANTED_BOOK)
        	{
        		if(!fm.hasEnchants() && f.getType() != Material.ENCHANTED_BOOK)
        		{
        			return false;
        		}
        		i.setItemMeta(orderEnchantments(im));
        		f.setItemMeta(orderEnchantments(fm));
        		im = i.getItemMeta();
            	fm = f.getItemMeta();
        		for(Entry<Enchantment, Integer> e : im.getEnchants().entrySet())
        		{
        			boolean bo = false;
        			for(Entry<Enchantment, Integer> ee : fm.getEnchants().entrySet())
        			{
        				if(e.getKey().getName().equals(ee.getKey().getName())
        						&& e.getValue() == ee.getValue())
        				{
        					bo = true;
        					break;
        				}
        			}
        			if(!bo)
        			{
        				return false;
        			}
        		}
        	}
        	if(im instanceof BlockStateMeta)
			{
        		if(!(fm instanceof BlockStateMeta))
        		{
        			return false;
        		}
				BlockStateMeta ibsm = (BlockStateMeta) im;
				BlockStateMeta fbsm = (BlockStateMeta) im;
				if(ibsm.getBlockState() instanceof ShulkerBox)
				{
					if(!(ibsm.getBlockState() instanceof ShulkerBox))
					{
						return false;
					}
					ShulkerBox ish = (ShulkerBox) ibsm.getBlockState();
					ShulkerBox fsh = (ShulkerBox) fbsm.getBlockState();
					if(ish.getColor() != fsh.getColor())
					{
						return false;
					}
					for(int j = 0; j < ish.getSnapshotInventory().getStorageContents().length; j++)
					{
						ItemStack its = ish.getSnapshotInventory().getStorageContents()[j];
						ItemStack fts = fsh.getSnapshotInventory().getStorageContents()[j];
						if(!isSimilar(its, fts))
						{
							return false;
						}
					}
				}
				return true; //Short exist only shulker
			}
        	if(im instanceof BannerMeta)
			{
        		if(!(fm instanceof BannerMeta))
        		{
        			return false;
        		}
				BannerMeta bim = (BannerMeta) im;
				BannerMeta bfm = (BannerMeta) fm;
				for(int j = 0; j < bim.getPatterns().size(); j++)
				{
					if(j >= bfm.getPatterns().size())
					{
						return false;
					}
					if((bim.getPattern(j).getPattern() != bfm.getPattern(j).getPattern())
							|| (bim.getPattern(j).getColor() != bfm.getPattern(j).getColor()))
					{
						return false;
					}
				}
			}
        	if(im instanceof PotionMeta)
			{
        		if(!(fm instanceof PotionMeta))
        		{
        			return false;
        		}
				PotionMeta pim = (PotionMeta) im;
				PotionMeta pfm = (PotionMeta) fm;
				if(pim.hasCustomEffects())
				{
					i.setItemMeta(orderCustomEffects(pim));
					f.setItemMeta(orderCustomEffects(pfm));
					im = i.getItemMeta();
					fm = f.getItemMeta();
					for(int j = 0; j < pim.getCustomEffects().size(); j++)
					{
						PotionEffect pei = pim.getCustomEffects().get(j);
						if(j >= pfm.getCustomEffects().size())
						{
							return false;
						}
						PotionEffect pef = pfm.getCustomEffects().get(j);
						if(pei.getAmplifier() != pef.getAmplifier()
								|| pei.getDuration() != pef.getDuration())
						{
							return false;
						}
					}
				} else
				{
					int pv = 0;
					if(i.getType() == Material.POTION) {pv = 1;}
					else if(i.getType() == Material.SPLASH_POTION) {pv = 2;}
					else if(i.getType() == Material.LINGERING_POTION) {pv = 3;}
					List<PotionEffect> peil = GuiHandler.getBasePotion(pim.getBasePotionData(), pv);
					List<PotionEffect> pefl = GuiHandler.getBasePotion(pfm.getBasePotionData(), pv);
					for(int j = 0; j < peil.size(); j++)
					{
						PotionEffect pei = peil.get(j);
						if(j >= pefl.size())
						{
							return false;
						}
						PotionEffect pef = pefl.get(j);
						if(pei.getAmplifier() != pef.getAmplifier()
								|| pei.getDuration() != pef.getDuration())
						{
							return false;
						}
					}
				}
			}
        	return i.toString().equals(f.toString());
        } else
    	{
        	return i.toString().equals(f.toString());
    	}
	}
	
	public static ItemMeta orderEnchantments(ItemMeta i)
	{
		ItemMeta ri = i.clone();
		for(Enchantment enchan : i.getEnchants().keySet())
		{
			ri.removeEnchant(enchan);
		}
		for(Enchantment enchan : enchs)
		{
			if(i.hasEnchant(enchan))
			{
				ri.addEnchant(enchan, i.getEnchantLevel(enchan), true);
			}
		}
		return ri;
	}
	
	public static EnchantmentStorageMeta orderStorageEnchantments(EnchantmentStorageMeta esm)
	{
		EnchantmentStorageMeta resm = esm.clone();
		for(Enchantment enchan : esm.getStoredEnchants().keySet())
		{
			resm.removeStoredEnchant(enchan);
		}
		for(Enchantment enchan : enchs)
		{
			if(esm.hasStoredEnchant(enchan))
			{
				resm.addStoredEnchant(enchan, esm.getStoredEnchantLevel(enchan), true);
			}
		}
		return resm;
	}
	
	public static PotionMeta orderCustomEffects(PotionMeta p)
	{
		PotionMeta pm = p.clone();
		LinkedHashMap<PotionEffectType, PotionEffect> pel = new LinkedHashMap<>();
		for(PotionEffect pe : p.getCustomEffects())
		{
			pel.put(pe.getType(), pe);
			pm.removeCustomEffect(pe.getType());
		}
		for(PotionEffectType pet : poefty)
		{
			if(pel.containsKey(pet))
			{
				pm.addCustomEffect(pel.get(pet), true);
			}
		}
		return pm;
	}
}