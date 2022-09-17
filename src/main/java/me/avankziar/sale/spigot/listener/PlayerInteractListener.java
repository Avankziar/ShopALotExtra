package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class PlayerInteractListener implements Listener
{
	private SaLE plugin;
	
	public PlayerInteractListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		Block b = event.getClickedBlock();
		if(b == null)
		{
			return;
		}
		BlockState bs = b.getState();
		if(!(bs instanceof Sign))
		{
			return;
		}
		Player player = event.getPlayer();
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
				"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
				plugin.getServername(), player.getWorld().getName(),
				b.getX(), b.getY(), b.getZ());
		if(ssh == null)
		{
			return;
		}
		if(event.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			if(ssh.getOwner().equals(player.getUniqueId()) || SignHandler.isMember(ssh, player.getUniqueId()))
			{
				if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					SignHandler.takeOutItemFromShop(ssh, player);
					return;
				}
			}
		} else if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(ssh.getOwner().equals(player.getUniqueId()) || SignHandler.isMember(ssh, player.getUniqueId()))
			{
				if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					if(ssh.getItemStack() == null || ssh.getItemStack().getType() == Material.AIR)
					{
						player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.ShopItemIsNull")
								.replace("%name%", ssh.getDisplayName())));
						return;
					}
					GuiHandler.openAdministration(ssh, player, SettingsLevel.BASE, true);
					return;
				} else
				{
					if(SignHandler.putInItemIntoShop(ssh, player, player.getInventory().getItemInMainHand()))
					{
						return;
					}
				}
			}
		}
		GuiHandler.openShop(ssh, player, SettingsLevel.BASE, false);
	}
}