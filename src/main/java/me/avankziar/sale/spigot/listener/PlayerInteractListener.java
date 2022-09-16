package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
		Player player = event.getPlayer();
		if(event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		Block b = event.getClickedBlock();
		if(b == null)
		{
			return;
		}
		BlockData bd = b.getBlockData();
		if(!(bd instanceof Sign))
		{
			return;
		}
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
				"`server` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
				plugin.getServername(), player.getWorld().getName(),
				event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ());
		if(ssh == null)
		{
			event.setCancelled(true);
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