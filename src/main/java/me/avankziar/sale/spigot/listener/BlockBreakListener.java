package main.java.me.avankziar.sale.spigot.listener;

import java.util.ArrayList;

import org.bukkit.block.Block;
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
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.general.ChatApi;
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
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(event.isCancelled()
				|| !(event.getBlock().getState() instanceof Sign))
		{
			return;
		}
		Player player = event.getPlayer();
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
		final Block block = event.getBlock();
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				final SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
						"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
						plugin.getServername(), player.getWorld().getName(),
						block.getX(), block.getY(), block.getZ());
				if(ssh != null)
				{
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
					new BukkitRunnable()
					{				
						@Override
						public void run()
						{
							SignHandler.clearSign(block);
						}
					}.runTask(plugin);
				}
			}
		}.runTaskAsynchronously(plugin);
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