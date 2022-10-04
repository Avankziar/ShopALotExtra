package main.java.me.avankziar.sale.spigot.gui.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.GUIApi;
import main.java.me.avankziar.sale.spigot.gui.events.BottomGuiClickEvent;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.MaterialHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class BottomListener implements Listener
{
	private SaLE plugin;
	
	public BottomListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBottomGui(BottomGuiClickEvent event)
	{
		if(!event.getPluginName().equals(plugin.pluginName))
		{
			return;
		}
		if(event.getEvent().getCurrentItem() == null || event.getEvent().getCurrentItem().getType() == Material.AIR)
		{
			return;
		}
		final ItemStack is = event.getEvent().getCurrentItem().clone();
		is.setAmount(1);
		if(!(event.getEvent().getWhoClicked() instanceof Player))
		{
			return;
		}
		Player player = (Player) event.getEvent().getWhoClicked();
		if(!GUIApi.isInGui(player.getUniqueId()))
		{
			return;
		}
		GuiType gt = GUIApi.getGuiType(player.getUniqueId());
		if(gt == GuiType.ADMINISTRATION)
		{
			SignShop ssh = GUIApi.getGuiSSH(player.getUniqueId());
			if(ssh == null)
			{
				return;
			}
			if(!ssh.getItemStack().toString().equals(is.toString()))
			{
				return;
			}
			final int amount = event.getEvent().getCurrentItem().getAmount();
			event.getEvent().getCurrentItem().setAmount(0);
			ssh.setItemStorageCurrent(ssh.getItemStorageCurrent()+((long) amount));
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignHandler.ItemsAddedToShop")
					.replace("%amount%", String.valueOf(amount))
					.replace("%now%", String.valueOf(ssh.getItemStorageCurrent())+" / "+String.valueOf(ssh.getItemStorageTotal()))));
			plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
			SignHandler.updateSign(ssh);
		} else if(gt == GuiType.ITEM_INPUT)
		{
			if(event.getEvent().getView().getTitle().startsWith("Shop:"))
			{
				input(event, player, is);
				return;
			}
		}
	}
	
	private void input(BottomGuiClickEvent event, Player player, ItemStack is)
	{
		String[] split = event.getEvent().getView().getTitle().split(":");
		if(split.length != 2)
		{
			return;
		}
		String i = split[1];
		if(!MatchApi.isInteger(i))
		{
			return;
		}
		int sshID = Integer.parseInt(i);
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", sshID);
		if(ssh == null)
		{
			return;
		}
		if(ssh.getItemStack() != null)
		{
			return;
		}
		ssh.setItemStack(is);
		ssh.setMaterial(is.getType());
		ItemMeta im = is.getItemMeta();
		if(im.hasDisplayName())
		{
			ssh.setDisplayName(im.getDisplayName());
		} else
		{
			ssh.setDisplayName(MaterialHandler.getMaterial(is.getType()));
		}
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		SignHandler.updateSign(ssh);
		player.closeInventory();
		GuiHandler.openAdministration(ssh, player, SettingsLevel.BASE, false);
	}
}