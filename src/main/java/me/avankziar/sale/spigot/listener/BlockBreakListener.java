package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;

public class BlockBreakListener implements Listener
{
	private SaLE plugin;
	
	public BlockBreakListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		BlockState bs = event.getBlock().getState();
		if(!(bs instanceof Sign))
		{
			return;
		}
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.SIGNSHOP,
				"`server` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
				plugin.getServername(), player.getWorld().getName(),
				event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()))
		{
			event.setCancelled(true);
			return;
		}
	}
}