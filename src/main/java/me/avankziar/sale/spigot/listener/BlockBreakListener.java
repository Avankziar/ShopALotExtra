package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

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
		if(event.isCancelled())
		{
			return;
		}
		Player player = event.getPlayer();
		BlockState bs = event.getBlock().getState();
		if(!(bs instanceof Sign))
		{
			return;
		}
		if(!SignHandler.isBreakToggle(player.getUniqueId()))
		{
			if(plugin.getMysqlHandler().exist(MysqlHandler.Type.SIGNSHOP,
					"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
					plugin.getServername(), player.getWorld().getName(),
					event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()))
			{
				event.setCancelled(true);
				return;
			}
		}
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
				"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
				plugin.getServername(), player.getWorld().getName(),
				event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
		if(ssh != null)
		{
			final int sshid = ssh.getId();
			final String sshname = ssh.getSignShopName();
			final ItemStack is = ssh.getItemStack();
			final String displayname = is.getItemMeta().hasDisplayName() 
					? is.getItemMeta().getDisplayName() : SaLE.getPlugin().getEnumTl().getLocalization(is.getType());
			final long amount = ssh.getItemStorageCurrent();
			plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOP,
					"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
					plugin.getServername(), player.getWorld().getName(),
					event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.DeleteAll.Delete")
					.replace("%id%", String.valueOf(sshid))
					.replace("%signshop%", sshname)
					.replace("%displayname%", displayname)
					.replace("%amount%", String.valueOf(amount))));
			SignHandler.clearSign(event.getBlock());
			return;
		}		
	}
}