package main.java.me.avankziar.sale.spigot.cmd.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.ifh.general.economy.currency.CurrencyType;
import main.java.me.avankziar.ifh.spigot.economy.currency.EconomyCurrency;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.assistance.Utility;
import main.java.me.avankziar.sale.spigot.cmd.SaLECommandExecutor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandExecuteType;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.objects.ShoppingLog;
import main.java.me.avankziar.sale.spigot.objects.ShoppingLog.WayType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.permission.BonusMalusPermission;
import main.java.me.avankziar.sale.spigot.permission.Bypass.Permission;

public class ARGSPLog extends ArgumentModule
{
	private SaLE plugin;
	
	public ARGSPLog(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale shopping log [Zahl] [Spieler] [waytype = true = buy]
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		Player player = (Player) sender;
		int page = 0;
		UUID otherplayer = player.getUniqueId();
		WayType wt = null;
		if(args.length >= 3 && MatchApi.isInteger(args[2]))
		{
			page = Integer.parseInt(args[2]);
		}
		ArrayList<String> pagination = new ArrayList<>();
		if(args.length >= 4)
		{
			if(args[3].equals(player.getName()) || BonusMalusPermission.hasPermission(player, Permission.CLIENT_LOG_OTHERPLAYER))
			{
				UUID u = Utility.convertNameToUUID(args[3]);
				if(u != null)
				{
					otherplayer = u;
					pagination.add(args[3]);
				}
			}
		}
		if(args.length >= 5 && MatchApi.isBoolean(args[4]))
		{
			boolean b = Boolean.parseBoolean(args[4]);
			wt = b ? WayType.BUY : WayType.SELL;
		}
		ArrayList<ShoppingLog> spll;
		if(wt != null)
		{
			spll = ShoppingLog.convert(plugin.getMysqlHandler().getList(
					MysqlHandler.Type.SHOPPINGLOG, "`date_time` DESC", page*10, 10,
					"`player_uuid` = ? AND `way_type` = ?", otherplayer.toString(), wt.toString()));
		} else
		{
			spll = ShoppingLog.convert(plugin.getMysqlHandler().getList(
					MysqlHandler.Type.SHOPPINGLOG, "`date_time` DESC", page*10, 10,
					"`player_uuid` = ?", otherplayer.toString()));
		}
		if(spll.size() == 0)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.ShoppingLog.NoLogs")));
			return;
		}
		ArrayList<String> msg = new ArrayList<>();
		msg.add(plugin.getYamlHandler().getLang().getString("Cmd.ShoppingLog.Headline")
				.replace("%page%", String.valueOf(page))
				.replace("%player%", Utility.convertUUIDToName(otherplayer.toString()))
				.replace("%waytype%", wt == null ? "-" : wt.toString()));
		for(ShoppingLog spl : spll)
		{
			String type;
			SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", spl.getSignShopId());
			String shopname = ssh != null ? ssh.getSignShopName() : String.valueOf(spl.getSignShopId());
			EconomyCurrency ec = ssh != null 
					? (plugin.getIFHEco().getAccount(ssh.getAccountId()) != null 
					? plugin.getIFHEco().getAccount(ssh.getAccountId()).getCurrency()
					: plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL))
					: plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL);
			int amo = spl.getItemAmount();
			long time = spl.getDateTime();
			double cost = spl.getAmount();
			ItemStack is = spl.getItemStack();
			if(spl.getWayType() == WayType.BUY)
			{
				type = "Cmd.ShoppingLog.Buy";
			} else
			{
				type = "Cmd.ShoppingLog.Sell";
			}
			String s = plugin.getYamlHandler().getLang().getString(type)
					.replace("%time%", TimeHandler.getDateTime(time, plugin.getYamlHandler().getConfig().getString("SignShop.ShopLog.TimePattern")))
					.replace("%amount%", String.valueOf(amo))
					.replace("%item%", is.getItemMeta().hasDisplayName() 
							? is.getItemMeta().getDisplayName() 
							: SaLE.getPlugin().getEnumTl().getLocalization(is.getType()))
					.replace("%shop%", shopname)
					.replace("%format%", plugin.getIFHEco().format(cost,
							ec));
			msg.add(s);
		}
		for(String s : msg)
		{
			player.sendMessage(ChatApi.tl(s));
		}
		SaLECommandExecutor.pastNextPage(player, page, CommandExecuteType.SALE_CLIENT_LOG, pagination.toArray(new String[pagination.size()]));
	}
}