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
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.ListedType;
import main.java.me.avankziar.sale.spigot.objects.PlayerData;
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
		if(BlockBreakListener.breakToggle.contains(player.getUniqueId().toString()))
		{
			return;
		}
		PlayerData pd = (PlayerData) plugin.getMysqlHandler().getData(
				MysqlHandler.Type.PLAYERDATA, "`player_uuid` = ?", player.getUniqueId().toString());
		if((ssh.getMaterial() == Material.AIR)
				&& (SignHandler.isOwner(ssh, player.getUniqueId())
				|| SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId())
				|| SignHandler.isBypassToggle(player.getUniqueId())))
		{
			GuiHandler.openInputInfo(ssh, player, pd.getLastSettingLevel(), true);
			event.setCancelled(true);
			return;
		}
		if(event.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			if(SignHandler.isOwner(ssh, player.getUniqueId()) || SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId()))
			{
				if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					SignHandler.takeOutItemFromShop(ssh, player);
					event.setCancelled(true);
					return;
				}
			}
			if(ssh.isItemHologram())
			{
				ItemHologramHandler.spawnHologram(ssh);
			}				
			event.setCancelled(true);
			return;
		} else if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(SignHandler.isOwner(ssh, player.getUniqueId())
					|| SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId())
					|| SignHandler.isBypassToggle(player.getUniqueId()))
			{
				if(ssh.getItemStack() != null)
				{
					if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
					{
						if(ssh.getItemStack() == null || ssh.getItemStack().getType() == Material.AIR)
						{
							player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.ShopItemIsNull")
									.replace("%name%", ssh.getDisplayName())));
							event.setCancelled(true);
							return;
						}
						GuiHandler.openAdministration(ssh, player, pd.getLastSettingLevel(), true);
						SignHandler.updateSign(ssh);
						event.setCancelled(true);
						return;
					} else
					{
						if(SignHandler.putInItemIntoShop(ssh, player, player.getInventory().getItemInMainHand()))
						{
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
		if(((ssh.getListedType() == ListedType.BLACKLIST || ssh.getListedType() == ListedType.ALL)  
				&& SignHandler.isListed(ListedType.BLACKLIST, ssh, player.getUniqueId()))
				
				|| (ssh.getListedType() == ListedType.WHITELIST && !SignHandler.isListed(ListedType.WHITELIST, ssh, player.getUniqueId()))
				|| (ssh.getListedType() == ListedType.MEMBER && !SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId()))
				|| (ssh.getListedType() == ListedType.CUSTOM && !SignHandler.isListed(ListedType.CUSTOM, ssh, player.getUniqueId()))
			)
		{
			switch(ssh.getListedType())
			{
			case ALL:
				break;
			case BLACKLIST:
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.IsBlackList")
						.replace("%name%", ssh.getSignShopName())));
				break;
			case WHITELIST:
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.IsNotWhiteList")
						.replace("%name%", ssh.getSignShopName())));
				break;
			case CUSTOM:
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.IsNotCustomList")
						.replace("%name%", ssh.getSignShopName())));
				break;
			case MEMBER:
				player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.IsNotMember")
						.replace("%name%", ssh.getSignShopName())));
				break;
			}
			event.setCancelled(true);
			return;
		}
		GuiHandler.openShop(ssh, player, pd.getLastSettingLevel(), false);
		SignHandler.updateSign(ssh);
	}
}