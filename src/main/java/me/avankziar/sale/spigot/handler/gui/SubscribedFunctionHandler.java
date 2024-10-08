package main.java.me.avankziar.sale.spigot.handler.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.cmd.sale.ARGSubscribed;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.sale.spigot.gui.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.gui.objects.SettingsLevel;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class SubscribedFunctionHandler
{
	public static void doClickFunktion(ClickFunctionType cft, Player player, SignShop ssh,
			Inventory openInv, int page, String where, boolean openshop_OR_location)
	{
		switch(cft)
		{
		default: return;
		case SUBSCRIBED: subscribed(player, ssh, openshop_OR_location); break;
		case SUBSCRIBED_PAST:
		case SUBSCRIBED_NEXT: pagination(player, page, where, openInv, openshop_OR_location); break;			
		}
	}
	
	private static void subscribed(Player player, SignShop ssh, boolean openshop_OR_location)
	{
		if(openshop_OR_location)
		{
			if(!SaLE.getPlugin().getServername().equals(ssh.getServer()))
			{
				player.sendMessage(ChatApi.tl(SaLE.getPlugin().getYamlHandler().getLang().getString("Cmd.Search.TeleportIsNull")));
				List<String> list = GuiHandler.getLorePlaceHolder(ssh, player,
						SaLE.getPlugin().getYamlHandler().getLang().getStringList("Cmd.Subscribed.LocationInfo"), player.getName());
				list.stream().forEach(x -> player.sendMessage(ChatApi.tl(x)));
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						player.closeInventory();
					}
				}.runTask(SaLE.getPlugin());
			} else
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						player.closeInventory();
						GuiHandler.openShop(ssh, player, SettingsLevel.BASE, false);
					}
				}.runTask(SaLE.getPlugin());
			}
		} else
		{
			List<String> list = GuiHandler.getLorePlaceHolder(ssh, player,
					SaLE.getPlugin().getYamlHandler().getLang().getStringList("Cmd.Subscribed.LocationInfo"), player.getName());
			list.stream().forEach(x -> player.sendMessage(ChatApi.tl(x)));
		}
	}
	
	private static void pagination(Player player, int page, String where, Inventory inv, boolean openshop_OR_location)
	{
		String sql = "SELECT * FROM `"+Type.SIGNSHOP.getValue()+"` ";
		ArrayList<SignShop> list = ARGSubscribed.getSubscribed(sql, where, page);
		GuiHandler.openSubscribed(list, player, page, where, true, inv, openshop_OR_location);
	}
}