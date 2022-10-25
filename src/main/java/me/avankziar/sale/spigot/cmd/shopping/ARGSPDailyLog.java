package main.java.me.avankziar.sale.spigot.cmd.shopping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.assistance.Utility;
import main.java.me.avankziar.sale.spigot.cmd.SaLECommandExecutor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandExecuteType;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.objects.ShoppingDailyLog;
import main.java.me.avankziar.sale.spigot.permission.BonusMalusPermission;
import main.java.me.avankziar.sale.spigot.permission.Bypass.Permission;

public class ARGSPDailyLog extends ArgumentModule
{
	private SaLE plugin;
	
	public ARGSPDailyLog(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale shopping dailylog [Zahl] [Spieler]
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		Player player = (Player) sender;
		int page = 0;
		UUID otherplayer = player.getUniqueId();
		if(args.length >= 3 && MatchApi.isInteger(args[2]))
		{
			page = Integer.parseInt(args[2]);
		}
		ArrayList<String> pagination = new ArrayList<>();
		if(args.length >= 4)
		{
			if(args[3].equals(player.getName()) || BonusMalusPermission.hasPermission(player, Permission.SHOPPING_LOG_OTHERPLAYER))
			{
				UUID u = Utility.convertNameToUUID(args[3]);
				if(u != null)
				{
					otherplayer = u;
					pagination.add(args[3]);
				}
			}
		}
		ArrayList<ShoppingDailyLog> ssdll = ShoppingDailyLog.convert(plugin.getMysqlHandler().getList(
				MysqlHandler.Type.SHOPPINGLOG, "`dates` DESC", page, 10,
				"`player_uuid` = ?", otherplayer.toString()));
		ArrayList<String> msg = new ArrayList<>();
		for(ShoppingDailyLog ssdl : ssdll)
		{
			int bamo = ssdl.getBuyItemAmount();
			int samo = ssdl.getSellItemAmount();
			long date = ssdl.getDate();
			double bcost = ssdl.getBuyAmount();
			double scost = ssdl.getSellAmount();
			String s = plugin.getYamlHandler().getLang().getString("Cmd.ShoppingDailyLog")
					.replace("%time%", TimeHandler.getDateTime(date,
							plugin.getYamlHandler().getConfig().getString("SignShop.ShopDailyLog.TimePattern")))
					.replace("%buyamo%", String.valueOf(bamo))
					.replace("%sellamo%", String.valueOf(samo))
					.replace("%buyformat%", String.valueOf(bcost))
					.replace("%sellformat%", String.valueOf(scost));
			msg.add(s);
		}
		for(String s : msg)
		{
			player.sendMessage(ChatApi.tl(s));
		}
		SaLECommandExecutor.pastNextPage(player, page, CommandExecuteType.SALE_SHOPPING_DAILYLOG, pagination.toArray(new String[pagination.size()]));
	}
}