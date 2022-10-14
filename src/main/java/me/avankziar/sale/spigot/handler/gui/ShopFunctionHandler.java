package main.java.me.avankziar.sale.spigot.handler.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.ifh.general.economy.account.AccountCategory;
import main.java.me.avankziar.ifh.general.economy.action.EconomyAction;
import main.java.me.avankziar.ifh.general.economy.action.OrdererType;
import main.java.me.avankziar.ifh.spigot.economy.account.Account;
import main.java.me.avankziar.ifh.spigot.economy.currency.EconomyCurrency;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.event.ShopPostTransactionEvent;
import main.java.me.avankziar.sale.spigot.event.ShopPreTransactionEvent;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.MessageHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.objects.SubscribedShop;

public class ShopFunctionHandler
{
	private static SaLE plugin = SaLE.getPlugin();
	
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
		for(int i = 0; i < player.getInventory().getStorageContents().length; i++)
		{
			ItemStack is = player.getInventory().getStorageContents()[i];
			if(is == null || is.getType() == Material.AIR)
			{
				continue;
			}
			ItemStack c = is.clone();
			c.setAmount(1);
			if(!ssh.getItemStack().toString().equals(c.toString()))
			{
				continue;
			}
			if(quantity == 0)
			{
				break;
			}
			//TODO Fehler
			if(quantity > is.getAmount())
			{
				postc = postc + is.getAmount();
				quantity = quantity - is.getAmount();
				ItemStack cc = is.clone();
				islist.add(cc);
				is.setAmount(0);
			} else if(quantity <= is.getAmount())
			{
				postc = postc + quantity;
				ItemStack cc = is.clone();
				cc.setAmount((int) quantity);
				islist.add(cc);
				is.setAmount(is.getAmount() - (int) quantity);
				quantity = 0;
				break;
			}
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
}