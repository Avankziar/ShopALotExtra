package main.java.me.avankziar.sale.spigot.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.ifh.general.economy.account.AccountCategory;
import main.java.me.avankziar.ifh.general.economy.account.AccountManagementType;
import main.java.me.avankziar.ifh.general.economy.action.EconomyAction;
import main.java.me.avankziar.ifh.general.economy.action.OrdererType;
import main.java.me.avankziar.ifh.general.economy.currency.CurrencyType;
import main.java.me.avankziar.ifh.spigot.economy.account.Account;
import main.java.me.avankziar.ifh.spigot.economy.currency.EconomyCurrency;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandExecuteType;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandSuggest;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.permission.BonusMalusPermission;
import main.java.me.avankziar.sale.spigot.permission.Bypass;

public class AdminstrationFunctionHandler
{
	private static SaLE plugin = SaLE.getPlugin();
	
	public static void doClickFunktion(GuiType guiType, ClickFunctionType cft, Player player, SignShop ssh,
			Inventory openInv, SettingsLevel settingsLevel)
	{
		switch(cft)
		{
		default: return;
		case ADMINISTRATION_ADDSTORAGE_1: addStorage(player, ssh, 1, openInv, settingsLevel); break;
		case ADMINISTRATION_ADDSTORAGE_16: addStorage(player, ssh, 16, openInv, settingsLevel); break;
		case ADMINISTRATION_ADDSTORAGE_64: addStorage(player, ssh, 64, openInv, settingsLevel); break;
		case ADMINISTRATION_ADDSTORAGE_576: addStorage(player, ssh, 576, openInv, settingsLevel); break;
		case ADMINISTRATION_DELETE_ALL: deleteAll(player, ssh); break;
		case ADMINISTRATION_ITEM_CLEAR: clearItem(player, ssh); break;
		case ADMINISTRATION_OPEN_SHOPLOG: openShopLog(player, ssh); break;
		case ADMINISTRATION_NUMPAD_0: numpad(player, ssh, "0", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_1: numpad(player, ssh, "1", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_2: numpad(player, ssh, "2", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_3: numpad(player, ssh, "3", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_4: numpad(player, ssh, "4", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_5: numpad(player, ssh, "5", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_6: numpad(player, ssh, "6", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_7: numpad(player, ssh, "7", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_8: numpad(player, ssh, "8", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_9: numpad(player, ssh, "9", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_COLON: numpad(player, ssh, ":", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_DECIMAL: numpad(player, ssh, ".", guiType, openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_CLEAR: setClear(player, ssh, "NUMTEXT", openInv, settingsLevel); break;
		case ADMINISTRATION_NUMPAD_CANCEL: cancelNumpad(player, ssh, openInv, settingsLevel);
		case ADMINISTRATION_SETACCOUNT_DEFAULT: setAccountDefault(player, ssh, openInv, settingsLevel); break;
		case ADMINISTRATION_SETACCOUNT_OPEN_NUMPAD: openNumpad(player, ssh, "ACCOUNT", openInv, settingsLevel); break;
		case ADMINISTRATION_SETACCOUNT_TAKEOVER: takeOver(player, ssh, "ACCOUNT", openInv, settingsLevel); break;
		case ADMINISTRATION_SETASH_CLEAR: setClearASH(player, ssh, openInv, settingsLevel); break;
		case ADMINISTRATION_SETASH_OPEN_NUMPAD: openNumpad(player, ssh, "ASH", openInv, settingsLevel); break;
		case ADMINISTRATION_SETASH_TAKEOVER: takeOver(player, ssh, "ASH", openInv, settingsLevel); break;
		case ADMINISTRATION_SETBUY_CLEAR: setClear(player, ssh, "BUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETBUY_OPEN_NUMPAD: openNumpad(player, ssh, "BUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETBUY_TAKEOVER: takeOver(player, ssh, "BUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETSELL_CLEAR: setClear(player, ssh, "SELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETSELL_OPEN_NUMPAD: openNumpad(player, ssh, "SELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETSELL_TAKEOVER: takeOver(player, ssh, "SELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETPOSSIBLE_BUY_CLEAR: setClear(player, ssh, "POSSIBLEBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETPOSSIBLE_BUY_OPEN_NUMPAD: openNumpad(player, ssh, "POSSIBLEBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETPOSSIBLE_BUY_TAKEOVER: takeOver(player, ssh, "POSSIBLEBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETPOSSIBLE_SELL_CLEAR: setClear(player, ssh, "POSSIBLESELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETPOSSIBLE_SELL_OPEN_NUMPAD: openNumpad(player, ssh, "POSSIBLESELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETPOSSIBLE_SELL_TAKEOVER: takeOver(player, ssh, "POSSIBLESELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNT_CLEAR: setClear(player, ssh, "DISCOUNT", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNT_START_OPEN_NUMPAD: openNumpad(player, ssh, "DISCOUNT_START", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNT_START_TAKEOVER: takeOver(player, ssh, "DISCOUNT_START", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNT_END_OPEN_NUMPAD: openNumpad(player, ssh, "DISCOUNT_END", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNT_END_TAKEOVER: takeOver(player, ssh, "DISCOUNT_END", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTBUY_CLEAR: setClear(player, ssh, "DISCOUNTBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTBUY_OPEN_NUMPAD: openNumpad(player, ssh, "DISCOUNTBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTBUY_TAKEOVER: takeOver(player, ssh, "DISCOUNTBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTSELL_CLEAR: setClear(player, ssh, "DISCOUNTSELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTSELL_OPEN_NUMPAD: openNumpad(player, ssh, "DISCOUNTSELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTSELL_TAKEOVER: takeOver(player, ssh, "DISCOUNTSELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_CLEAR: setClear(player, ssh, "DISCOUNTPOSSIBLEBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_OPEN_NUMPAD: openNumpad(player, ssh, "DISCOUNTPOSSIBLEBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_TAKEOVER: takeOver(player, ssh, "DISCOUNTPOSSIBLEBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_CLEAR: setClear(player, ssh, "DISCOUNTPOSSIBLESELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_OPEN_NUMPAD: openNumpad(player, ssh, "DISCOUNTPOSSIBLESELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_TAKEOVER: takeOver(player, ssh, "DISCOUNTPOSSIBLESELL", openInv, settingsLevel); break;
		case ADMINISTRATION_SETTINGSLEVEL_SETTO_ADVANCED: switchSettingsLevel(player, ssh, null, openInv, SettingsLevel.ADVANCED); break;
		case ADMINISTRATION_SETTINGSLEVEL_SETTO_BASE: switchSettingsLevel(player, ssh, null, openInv, SettingsLevel.BASE); break;
		case ADMINISTRATION_SETTINGSLEVEL_SETTO_EXPERT: switchSettingsLevel(player, ssh, null, openInv, SettingsLevel.EXPERT); break;
		case ADMINISTRATION_SETTINGSLEVEL_SETTO_MASTER: switchSettingsLevel(player, ssh, null, openInv, SettingsLevel.MASTER); break;
		case ADMINISTRATION_TOGGLE_BUY: setToggle(player, ssh, "BUY", openInv, settingsLevel); break;
		case ADMINISTRATION_TOGGLE_SELL: setToggle(player, ssh, "SELL", openInv, settingsLevel); break;
		case ADMINISTRATION_UNLIMITED_TOGGLE_BUY: setToggle(player, ssh, "UBUY", openInv, settingsLevel); break;
		case ADMINISTRATION_UNLIMITED_TOGGLE_SELL: setToggle(player, ssh, "USELL", openInv, settingsLevel); break;
		}
	}
	
	private static boolean isTooMuchShop(Player player, SignShop ssh)
	{
		if(!player.getUniqueId().equals(ssh.getOwner()))
		{
			return false;
		}
		int signShopAmount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SIGNSHOP, "`player_uuid` = ?", player.getUniqueId().toString());
		int maxSignShopAmount = BonusMalusPermission.getPermissionCount(player, Bypass.CountPermission.SHOP_CREATION_AMOUNT_);
		if(signShopAmount > maxSignShopAmount)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignChangeListener.AlreadyHaveMaximalSignShop")
					.replace("%actual%", String.valueOf(signShopAmount))
					.replace("%max%", String.valueOf(maxSignShopAmount))
					));
			return true;
		}
		return false;
	}
	
	private static void addStorage(Player player, SignShop ssh, long amount, Inventory inv, SettingsLevel settingsLevel)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		List<String> costPerOne = plugin.getYamlHandler().getConfig().getStringList("SignShop.CostToAdd1Storage");
		long maxStorage = ssh.getItemStorageTotal();
		long maxPossibleStorage = (long) BonusMalusPermission.getPermissionCount(player, Bypass.CountPermission.SHOP_ITEMSTORAGE_AMOUNT_);
		if(maxStorage >= maxPossibleStorage)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("")));
			return;
		}
		long ca = amount;
		if(maxPossibleStorage-maxStorage > ca)
		{
			ca = maxPossibleStorage - maxStorage;
		}
		LinkedHashMap<EconomyCurrency, Double> moneymap = new LinkedHashMap<>();
		for(String t : costPerOne)
		{
			String[] split = t.split(";");
			if(split.length != 2)
			{
				return;
			}
			EconomyCurrency ec = plugin.getIFHEco().getCurrency(split[1]);
			if(ec == null)
			{
				return;
			}
			if(!MatchApi.isDouble(split[2]))
			{
				return;
			}
			double d = Double.parseDouble(split[2]);
			moneymap.put(ec, d);
		}
		String category = plugin.getYamlHandler().getLang().getString("Economy.AddStorage.Category");
		String comment = plugin.getYamlHandler().getLang().getString("Economy.AddStorage.Comment");
		for(Entry<EconomyCurrency, Double> e : moneymap.entrySet())
		{
			EconomyCurrency ec = e.getKey();
			double d = e.getValue();
			Account from = plugin.getIFHEco().getAccount(ssh.getAccountId());
			if(from == null)
			{
				player.sendMessage(ChatApi.tl(
						plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.AddStorage.YouDontHaveAccountToWithdraw")));
				return;
			}
			Account to = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.VOID, ec);
			EconomyAction ea = plugin.getIFHEco().transaction(from, to, d, OrdererType.PLAYER, player.getUniqueId().toString(), category, comment);
			ArrayList<String> list = new ArrayList<>();
			if(to != null)
			{
				ea = plugin.getIFHEco().transaction(from, to, d, OrdererType.PLAYER, player.getUniqueId().toString(), category, comment);
				String wformat = plugin.getIFHEco().format(ea.getWithDrawAmount(), from.getCurrency());
				for(String s : plugin.getYamlHandler().getLang().getStringList("AdminstrationFunctionHandler.AddStorage.Transaction"))
				{
					String a = s.replace("%fromaccount%", from.getAccountName())
					.replace("%toaccount%", to.getAccountName())
					.replace("%formatwithdraw%", wformat)
					.replace("%category%", category != null ? category : "/")
					.replace("%comment%", comment != null ? comment : "/");
					list.add(a);
				}
			} else
			{
				ea = plugin.getIFHEco().withdraw(from, d, OrdererType.PLAYER, player.getUniqueId().toString(), category, comment);
				String wformat = plugin.getIFHEco().format(ea.getWithDrawAmount(), from.getCurrency());
				for(String s : plugin.getYamlHandler().getLang().getStringList("AdminstrationFunctionHandler.AddStorage.Withdraw"))
				{
					String a = s.replace("%fromaccount%", from.getAccountName())
					.replace("%formatwithdraw%", wformat)
					.replace("%category%", category != null ? category : "/")
					.replace("%comment%", comment != null ? comment : "/");
					list.add(a);
				}
			}
			if(!ea.isSuccess())
			{
				player.sendMessage(ChatApi.tl(ea.getDefaultErrorMessage()));
				return;
			}
			for(String s : list)
			{
				player.sendMessage(ChatApi.tl(s));
			}
		}
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, false);
	}
	
	private static void clearItem(Player player, SignShop ssh)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		if(ssh.getItemStorageCurrent() > 0)
		{
			return;
		}
		player.closeInventory();
		ssh.setItemStack(null);
		ssh.setMaterial(null);
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.ItemClear")));
	}
	
	private static void deleteAll(Player player, SignShop ssh)
	{
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		final int sshid = ssh.getId();
		final String sshname = ssh.getSignShopName();
		final ItemStack is = ssh.getItemStack();
		final String displayname = is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : MaterialHandler.getMaterial(is.getType());
		final long amount = ssh.getItemStorageCurrent();
		player.closeInventory();
		plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.DeleteAll.Delete")
				.replace("%id%", String.valueOf(sshid))
				.replace("%signshop%", sshname)
				.replace("%displayname%", displayname)
				.replace("%amount%", String.valueOf(amount))));
		return;
	}
	
	private static void openShopLog(Player player, SignShop ssh)
	{
		player.closeInventory();
		Bukkit.dispatchCommand(player, CommandSuggest.get(CommandExecuteType.SHOPLOG)+" "+ssh.getSignShopName());
		return;
	}
	
	private static void setAccountDefault(Player player, SignShop ssh, Inventory inv, SettingsLevel settingsLevel)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		int acid = 0;
		Account ac = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.SHOP, plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL));
		if(ac == null)
		{
			ac = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.MAIN, plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL));
			if(ac == null)
			{
				ssh.setAccountId(0);
			} else
			{
				acid = ac.getID();
			}
		} else
		{
			acid = ac.getID();
		}
		ssh.setAccountId(acid);
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.SetAccount.Set")));
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, false);
	}
	
	private static void setClearASH(Player player, SignShop ssh, Inventory inv, SettingsLevel settingsLevel)
	{
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		int acid = 0;
		ssh.setAccountId(acid);
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.SetStorage.Set")));
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, false);
	}
	
	private static void setClear(Player player, SignShop ssh, String type, Inventory inv, SettingsLevel settingsLevel)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		switch(type)
		{
		default: break;
		case "NUMTEXT": ssh.setNumText("");
		case "BUY": ssh.setBuyAmount(null); break;
		case "SELL": ssh.setSellAmount(null); break;
		case "POSSIBLEBUY": ssh.setPossibleBuy(-1); break;
		case "POSSIBLESELL": ssh.setPossibleSell(-1); break;
		case "DISCOUNT": ssh.setDiscountStart(0); ssh.setDiscountEnd(0); break;
		case "DISCOUNTBUY": ssh.setDiscountBuyAmount(null); break;
		case "DISCOUNTSELL": ssh.setDiscountSellAmount(null); break;
		case "DISCOUNTPOSSIBLEBUY": ssh.setDiscountPossibleBuy(-1); break;
		case "DISCOUNTPOSSIBLESELL": ssh.setDiscountPossibleSell(-1); break;
		}
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, false);
	}
	
	private static void switchSettingsLevel(Player player, SignShop ssh, String type, Inventory inv, SettingsLevel settingsLevel)
	{
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, false);
	}
	
	private static void setToggle(Player player, SignShop ssh, String type, Inventory inv, SettingsLevel settingsLevel)
	{
		switch(type)
		{
		default: break;
		case "BUY": ssh.setCanBuy(!ssh.canBuy()); break;
		case "SELL": ssh.setCanSell(!ssh.canSell()); break;
		case "UBUY": ssh.setUnlimitedBuy(!ssh.isUnlimitedBuy()); break;
		case "USELL": ssh.setUnlimitedSell(!ssh.isUnlimitedSell()); break;
		}
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, false);
	}
	
	private static void openNumpad(Player player, SignShop ssh, String type, Inventory inv, SettingsLevel settingsLevel)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		GuiHandler.openNumpad(ssh, player, type, settingsLevel, true);
	}
	
	private static void takeOver(Player player, SignShop ssh, String type, Inventory inv, SettingsLevel settingsLevel)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		switch(type)
		{
		default: break;
		case "ACCOUNT":
			if(!MatchApi.isInteger(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			if(!MatchApi.isPositivNumber(Integer.parseInt(ssh.getNumText())))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("IsNegativ")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			Account ac = plugin.getIFHEco().getAccount(Integer.parseInt(ssh.getNumText()));
			if(ac == null)
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AccountNotExist")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			if(!plugin.getIFHEco().canManageAccount(ac, player.getUniqueId(), AccountManagementType.CAN_WITHDRAW))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoWithdrawRights")));
				break;
			}
			ssh.setAccountId(Integer.parseInt(ssh.getNumText()));
			break;
		case "ASH":
			if(!MatchApi.isInteger(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			if(!MatchApi.isPositivNumber(Integer.parseInt(ssh.getNumText())))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("IsNegativ")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			//TODO
			break;
		case "BUY":
			if(!MatchApi.isDouble(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoDouble")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setBuyAmount(Double.parseDouble(ssh.getNumText()));
			break;
		case "SELL":
			if(!MatchApi.isDouble(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoDouble")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setSellAmount(Double.parseDouble(ssh.getNumText()));
			break;
		case "POSSIBLEBUY":
			if(!MatchApi.isLong(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setPossibleBuy(Long.parseLong(ssh.getNumText()));
			break;
		case "POSSIBLESELL":
			if(!MatchApi.isLong(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setPossibleSell(Long.parseLong(ssh.getNumText()));
			break;
		case "DISCOUNT_START":
			String p1 = plugin.getYamlHandler().getConfig().getString("SignShop.DiscountTimePattern");
			if(!TimeHandler.isDateTime(ssh.getNumText(), p1))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.DiscountTimeNotFit")
						.replace("%value%", ssh.getNumText())
						.replace("%pattern%", p1)));
				break;
			}
			ssh.setDiscountStart(TimeHandler.getDateTime(ssh.getNumText(),
					p1));
			break;
		case "DISCOUNT_END":
			String p2 = plugin.getYamlHandler().getConfig().getString("SignShop.DiscountTimePattern");
			if(!TimeHandler.isDateTime(ssh.getNumText(), p2))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.DiscountTimeNotFit")
						.replace("%value%", ssh.getNumText())
						.replace("%pattern%", p2)));
				break;
			}
			ssh.setDiscountStart(TimeHandler.getDateTime(ssh.getNumText(),
					p2));
			break;
		case "DISCOUNTBUY":
			if(!MatchApi.isDouble(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoDouble")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setDiscountBuyAmount(Double.parseDouble(ssh.getNumText()));
			break;
		case "DISCOUNTSELL":
			if(!MatchApi.isDouble(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoDouble")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setDiscountSellAmount(Double.parseDouble(ssh.getNumText()));
			break;
		case "DISCOUNTPOSSIBLEBUY":
			if(!MatchApi.isLong(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setDiscountPossibleBuy(Long.parseLong(ssh.getNumText()));
			break;
		case "DISCOUNTPOSSIBLESELL":
			if(!MatchApi.isLong(ssh.getNumText()))
			{
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoNumber")
						.replace("%value%", ssh.getNumText())));
				break;
			}
			ssh.setDiscountPossibleSell(Long.parseLong(ssh.getNumText()));
			break;
		}
		ssh.setNumText("");
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, true);
	}
	
	private static void numpad(Player player, SignShop ssh, String type, GuiType gt, Inventory inv, SettingsLevel settingsLevel)
	{
		if(isTooMuchShop(player, ssh))
		{
			return;
		}
		if(!ssh.getOwner().equals(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NotOwner")));
			return;
		}
		String t = "";
		switch(gt)
		{
		default: break;
		case NUMPAD_ACCOUNT: t = "ACCOUNT"; break;
		case NUMPAD_ASH: t = "ASH"; break;
		case NUMPAD_BUY: t = "BUY"; break;
		case NUMPAD_SELL: t = "SELL"; break;
		case NUMPAD_POSSIBLE_BUY: t = "POSSIBLEBUY"; break;
		case NUMPAD_POSSIBLE_SELL: t = "POSSIBLESELL"; break;
		case NUMPAD_DISCOUNT_START: t = "DISCOUNT_START"; break;
		case NUMPAD_DISCOUNT_END: t = "DISCOUNT_END"; break;
		case NUMPAD_DISCOUNT_BUY: t = "DISCOUNTBUY"; break;
		case NUMPAD_DISCOUNT_SELL: t = "DISCOUNTSELL"; break;
		case NUMPAD_DISCOUNT_POSSIBLE_BUY: t = "DISCOUNTPOSSIBLEBUY"; break;
		case NUMPAD_DISCOUNT_POSSIBLE_SELL: t = "DISCOUNTPOSSIBLESELL"; break;
		}
		ssh.setNumText(ssh.getNumText()+type);
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		GuiHandler.openNumpad(ssh, player, t, settingsLevel, false);
	}
	
	private static void cancelNumpad(Player player, SignShop ssh, Inventory inv, SettingsLevel settingsLevel)
	{
		GuiHandler.openAdministration(ssh, player, settingsLevel, inv, true);
	}
}