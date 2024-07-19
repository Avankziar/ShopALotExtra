package main.java.me.avankziar.sale.spigot.cmd.sale;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class ARGDebug extends ArgumentModule
{
	private SaLE plugin;
	
	public ARGDebug(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale debug searchvoidsign
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		Player player = (Player) sender;
		String identifier = args[1];
		switch(identifier)
		{
		default:
			break;
		case "blockface":
			Block b = player.getTargetBlock(null, 10);
			if(!(b.getState() instanceof org.bukkit.block.Sign))
			{
				player.sendMessage("No Sign found!");
				return;
			}
			if(b.getBlockData() instanceof org.bukkit.block.data.type.WallSign)
			{
				org.bukkit.block.data.type.WallSign ws = (org.bukkit.block.data.type.WallSign) b.getBlockData();
				Block behind = b.getRelative(ws.getFacing().getOppositeFace());
				player.sendMessage("Behind Block Type = "+behind.getType());
			} else
			{
				Block under = b.getRelative(BlockFace.DOWN);
				player.sendMessage("Bottom Block Type = "+under.getType());
			}
			break;
		case "searchvoidsign": //Sucht ob es Schilder gibt, die in der Mysql existieren aber nicht Ingame als Schild.
			searchvoidsign(player);
			break;
		case "searchvoidsignandclear":
			searchvoidsignandclear(player);
			break;
		case "itemcomparison":
			if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInOffHand() == null)
			{
				player.sendMessage("Items in Main/Off Hand are null!");
				return;
			}
			player.sendMessage(ChatApi.tl("&7===&cItemComparison&7==="));
			player.sendMessage("Item MainHand: "+(player.getInventory().getItemInMainHand().hasItemMeta() 
					? player.getInventory().getItemInMainHand().getItemMeta().toString()
					: "No ItemMeta"));
			player.sendMessage("Item OffHand: "+(player.getInventory().getItemInOffHand().hasItemMeta() 
					? player.getInventory().getItemInOffHand().getItemMeta().toString()
					: "No ItemMeta"));
			player.sendMessage(ChatApi.tl("&7vvvvvvvvvv"));
			player.sendMessage("Items are same: "+plugin.getItemStackComparison()
				.isSimilar(player.getInventory().getItemInMainHand(), player.getInventory().getItemInOffHand()));
			player.sendMessage(ChatApi.tl("&7^^^^^^^^^^^"));
			break;
		}
	}
	
	private void searchvoidsign(final Player player)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				player.sendMessage("Please be waiting. Async Process ");
				ArrayList<SignShop> alss = SignShop.convert(plugin.getMysqlHandler().getFullList(
						Type.SIGNSHOP, "`id` ASC", "`server_name` = ?", SaLE.getPlugin().getServername()));
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						int i = 0;
						int ii = 0;
						for(SignShop ss : alss)
						{
							Block block = Bukkit.getWorld(ss.getWorld()).getBlockAt(ss.getX(), ss.getY(), ss.getZ());
							
							if(!(block.getState() instanceof org.bukkit.block.Sign))
							{
								i++;
							} else
							{
								ii++;
							}
						}
						if(player != null)
						{
							player.sendMessage("SignShops Ingame, which are no Signs = "+i);
							player.sendMessage("SignShops Ingame, which are Signs = "+ii);
						}
					}
				}.runTask(plugin);
			}
		}.runTaskAsynchronously(plugin);
	}
	
	private void searchvoidsignandclear(final Player player)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				player.sendMessage("Please be waiting. Async Process ");
				ArrayList<SignShop> alss = SignShop.convert(plugin.getMysqlHandler().getFullList(
						Type.SIGNSHOP, "`id` ASC", "`server_name` = ?", SaLE.getPlugin().getServername()));
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						int i = 0;
						for(SignShop ss : alss)
						{
							Block block = Bukkit.getWorld(ss.getWorld()).getBlockAt(ss.getX(), ss.getY(), ss.getZ());
							if(!(block.getState() instanceof org.bukkit.block.Sign))
							{
								i++;
								plugin.getMysqlHandler().deleteData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ss.getId());
							}
						}
						if(player != null)
						{
							player.sendMessage("SignShops Ingame, which are no Signs = "+i);
						}
					}
				}.runTask(plugin);
			}
		}.runTaskAsynchronously(plugin);
	}
}