package main.java.me.avankziar.sale.spigot.cmd.shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.assistance.Utility;
import main.java.me.avankziar.sale.spigot.cmd.SaLECommandExecutor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandExecuteType;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.modifiervalueentry.Bypass.Permission;
import main.java.me.avankziar.sale.spigot.modifiervalueentry.ModifierValueEntry;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.objects.SignShopDailyLog;

public class ARGSDailyLog extends ArgumentModule
{
	private SaLE plugin;
	
	public ARGSDailyLog(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale shop dailylog [Zahl] [shopid] [Spieler]
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		Player player = (Player) sender;
		int page = 0;
		int shopid = 0;
		UUID otherplayer = player.getUniqueId();
		if(args.length >= 3 && MatchApi.isInteger(args[2]))
		{
			page = Integer.parseInt(args[2]);
		}
		ArrayList<String> pagination = new ArrayList<>();
		if(args.length >= 4 && MatchApi.isInteger(args[3]))
		{
			shopid = Integer.parseInt(args[3]);
			pagination.add(String.valueOf(shopid));
		}
		if(args.length >= 5)
		{
			if(args[4].equals(player.getName()) || ModifierValueEntry.hasPermission(player, Permission.SHOP_LOG_OTHERPLAYER))
			{
				UUID u = Utility.convertNameToUUID(args[4]);
				if(u != null)
				{
					otherplayer = u;
					pagination.add(args[4]);
				}
			}
		}
		ArrayList<SignShopDailyLog> ssdll;
		if(shopid > 0)
		{
			ssdll = SignShopDailyLog.convert(plugin.getMysqlHandler().getList(
					MysqlHandler.Type.SIGNSHOPDAILYLOG, "`dates` DESC", page*10, 10,
					"`player_uuid` = ? AND `sign_shop_id` = ?", otherplayer.toString(), shopid));
		} else
		{
			ssdll = SignShopDailyLog.convert(plugin.getMysqlHandler().getList(
					MysqlHandler.Type.SIGNSHOPDAILYLOG, "`dates` DESC", page*10, 10,
					"`player_uuid` = ?", otherplayer.toString()));
		}
		if(ssdll.size() == 0)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.ShopDailyLog.NoLogs")));
			return;
		}
		ArrayList<String> msg = new ArrayList<>();
		msg.add(plugin.getYamlHandler().getLang().getString("Cmd.ShopDailyLog.Headline")
				.replace("%page%", String.valueOf(page))
				.replace("%shopid%", shopid == 0 ? "-" : String.valueOf(shopid))
				.replace("%player%", Utility.convertUUIDToName(otherplayer.toString())));
		for(SignShopDailyLog ssdl : ssdll)
		{
			SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssdl.getSignShopId());
			String shopname = ssh != null ? ssh.getSignShopName() : String.valueOf(ssdl.getSignShopId());
			int bamo = ssdl.getBuyItemAmount();
			int samo = ssdl.getSellItemAmount();
			long date = ssdl.getDate();
			double bcost = ssdl.getBuyAmount();
			double scost = ssdl.getSellAmount();
			String s = plugin.getYamlHandler().getLang().getString("Cmd.ShopDailyLog.Log")
					.replace("%time%", TimeHandler.getDateTime(date,
							plugin.getYamlHandler().getConfig().getString("SignShop.ShopDailyLog.TimePattern")))
					.replace("%buyamo%", String.valueOf(bamo))
					.replace("%sellamo%", String.valueOf(samo))
					.replace("%shop%", shopname)
					.replace("%buyformat%", String.valueOf(Utility.getNumberFormat(bcost, 2)))
					.replace("%sellformat%", String.valueOf(Utility.getNumberFormat(scost, 2)));
			msg.add(s);
		}
		for(String s : msg)
		{
			player.sendMessage(ChatApi.tl(s));
		}
		SaLECommandExecutor.pastNextPage(player, page, CommandExecuteType.SALE_SHOP_DAILYLOG, pagination.toArray(new String[pagination.size()]));
	}
}