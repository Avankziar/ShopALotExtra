package main.java.me.avankziar.sale.spigot.handler;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.general.ChatApiV;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.objects.ListedType;
import main.java.me.avankziar.sale.spigot.objects.ShopAccessType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

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
			} else if(plugin.getMtV() != null)
			{
				plugin.getMtV().sendMessage(uuid, ChatApiV.oldBukkitFormat(msg));
			}
		}
	}
	
	public void sendMessageToOwnerAndMember(int shopid, String msg)
	{
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", shopid);
		ArrayList<ShopAccessType> member = ShopAccessType.convert(plugin.getMysqlHandler().getFullList(MysqlHandler.Type.SHOPACCESSTYPE,
				"`id` ASC", "`sign_shop_id` = ? AND `listed_type` = ?", ssh.getId(), ListedType.MEMBER.toString()));
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
	
	public void sendMessage(UUID uuid, ArrayList<ArrayList<BaseComponent>> listInList, ArrayList<String> list)
	{
		if(Bukkit.getPlayer(uuid) != null)
		{
			for(ArrayList<BaseComponent> l : listInList)
			{
				TextComponent tc = ChatApi.tc("");
				tc.setExtra(l);
				Bukkit.getPlayer(uuid).spigot().sendMessage(tc);
			}
		} else
		{
			if(plugin.getMtB() != null)
			{
				plugin.getBctB().sendMessage(uuid, listInList);
			} else if(plugin.getMtV() != null)
			{
				plugin.getMtV().sendMessage(uuid, list.toArray(new String[list.size()]));
			}
		}
	}
	
	public void sendMessageToOwnerAndMember(int shopid, ArrayList<ArrayList<BaseComponent>> listInList, ArrayList<String> list)
	{
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", shopid);
		ArrayList<ShopAccessType> member = ShopAccessType.convert(plugin.getMysqlHandler().getFullList(MysqlHandler.Type.SHOPACCESSTYPE,
				"`id` ASC", "`sign_shop_id` = ? AND `listed_type` = ?", ssh.getId(), ListedType.MEMBER.toString()));
		sendMessage(ssh.getOwner(), listInList, list);
		for(ShopAccessType sat : member)
		{
			sendMessage(sat.getUUID(), listInList, list);
		}
	}
	
	public void sendMessageToOwnerAndMember(SignShop ssh, ArrayList<ArrayList<BaseComponent>> listInList, ArrayList<String> list)
	{
		sendMessageToOwnerAndMember(ssh.getId(), listInList, list);
	}
}