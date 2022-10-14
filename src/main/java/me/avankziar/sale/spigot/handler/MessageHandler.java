package main.java.me.avankziar.sale.spigot.handler;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.objects.ListedType;
import main.java.me.avankziar.sale.spigot.objects.ShopAccessType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class MessageHandler
{
	private SaLE plugin;
	
	public MessageHandler()
	{
		this.plugin = SaLE.getPlugin();
	}
	
	public void sendMessage(UUID uuid, String msg)
	{
		if(Bukkit.getPlayer(uuid) != null)
		{
			Bukkit.getPlayer(uuid).sendMessage(ChatApi.tl(msg));
		} else
		{
			if(plugin.getMtB() != null)
			{
				plugin.getMtB().sendMessage(uuid, msg);
			}
		}
	}
	
	public void sendMessageToOwnerAndMember(int shopid, String msg)
	{
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", shopid);
		ArrayList<ShopAccessType> member = ShopAccessType.convert(plugin.getMysqlHandler().getFullList(MysqlHandler.Type.SHOPACCESSTYPE,
				"`id` ASC", "`sign_shop_id` = ? AND `listed_type` = ?", ssh.getId(), ListedType.MEMBER));
		sendMessage(ssh.getOwner(), msg);
		for(ShopAccessType sat : member)
		{
			sendMessage(sat.getUUID(), msg);
		}
	}
	
	public void sendMessageToOwnerAndMember(SignShop ssh, String msg)
	{
		sendMessageToOwnerAndMember(ssh.getId(), msg);
	}
}