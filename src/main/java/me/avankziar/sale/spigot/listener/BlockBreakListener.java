package main.java.me.avankziar.sale.spigot.listener;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class BlockBreakListener implements Listener
{
	private SaLE plugin;
	public static String SIGNSHOP_CONTACTBLOCK = BaseConstructor.getPlugin().pluginName+":"+"SIGNSHOP_CONTACTBLOCK";
	
	public BlockBreakListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
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
			if(event.getBlock().hasMetadata(SIGNSHOP_CONTACTBLOCK))
			{
				event.setCancelled(true);
				return;
			}
		}
		if(!SignHandler.isBreakToggle(player.getUniqueId()))
		{
			if(plugin.getMysqlHandler().exist(MysqlHandler.Type.SIGNSHOP,
					"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
					plugin.getServername(), player.getWorld().getName(),
					event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()))
			{
				player.sendMessage("3"); //REMOVEME
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
			if(event.getBlock().getBlockData() instanceof org.bukkit.block.data.type.WallSign)
			{
				org.bukkit.block.data.type.WallSign ws = (org.bukkit.block.data.type.WallSign) event.getBlock().getBlockData();
				Block behind = event.getBlock().getRelative(ws.getFacing().getOppositeFace());
				behind.removeMetadata(SIGNSHOP_CONTACTBLOCK, plugin);
			} else if(event.getBlock().getBlockData() instanceof org.bukkit.block.data.type.HangingSign)
			{
				Block above = event.getBlock().getRelative(BlockFace.UP);
				above.removeMetadata(SIGNSHOP_CONTACTBLOCK, plugin);
			} else
			{
				Block under = event.getBlock().getRelative(BlockFace.DOWN);
				under.removeMetadata(SIGNSHOP_CONTACTBLOCK, plugin);
			}
			final int sshid = ssh.getId();
			final String sshname = ssh.getSignShopName();
			final ItemStack is = ssh.getItemStack();
			final String displayname = is.getItemMeta().hasDisplayName() 
					? is.getItemMeta().getDisplayName() : 
						(plugin.getEnumTl() != null 
						? SaLE.getPlugin().getEnumTl().getLocalization(is.getType())
						: is.getType().toString());
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
	
	
	@EventHandler
	public void onTNT(BlockExplodeEvent event)
	{
		if(event.isCancelled())
		{
			return;
		}
		ArrayList<Integer> indexs = new ArrayList<>();
		int i = 0;
		for(Block b : event.blockList())
		{
			BlockState bs = b.getState();
			if(!(bs instanceof Sign))
			{
				continue;
			}
			SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
					"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
					plugin.getServername(), b.getWorld().getName(),	b.getX(), b.getY(), b.getZ());
			if(ssh == null)
			{
				continue;
			}
			indexs.add(i);
			i++;
		}
		for(Integer index : indexs)
		{
			event.blockList().remove(index.intValue());
		}
	}
	
	@EventHandler
	public void onCreeper(EntityExplodeEvent event)
	{
		if(event.isCancelled())
		{
			return;
		}
		ArrayList<Integer> indexs = new ArrayList<>();
		int i = 0;
		for(Block b : event.blockList())
		{
			BlockState bs = b.getState();
			if(!(bs instanceof Sign))
			{
				continue;
			}
			SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
					"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
					plugin.getServername(), b.getWorld().getName(),	b.getX(), b.getY(), b.getZ());
			if(ssh == null)
			{
				continue;
			}
			indexs.add(i);
			i++;
		}
		for(Integer index : indexs)
		{
			event.blockList().remove(index.intValue());
		}
	}
}