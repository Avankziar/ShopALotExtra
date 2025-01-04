package main.java.me.avankziar.sale.spigot.listener;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.objects.SettingsLevel;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.hook.WorldGuardHook;
import main.java.me.avankziar.sale.spigot.objects.ListedType;
import main.java.me.avankziar.sale.spigot.objects.PlayerData;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class PlayerInteractListener implements Listener
{
	private SaLE plugin;
	private static LinkedHashMap<String, Long> cooldown = new LinkedHashMap<>();
	//private static DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN); //REMOVEME
	
	public PlayerInteractListener(SaLE plugin)
	{
		this.plugin = plugin;
		//formatter.setMaximumFractionDigits(2); //REMOVEME
		//formatter.setMinimumFractionDigits(0); //REMOVEME
	}
	
	/*private void log(Player player, String log)
	{
		if(player.hasPermission("sale.debug.show"))
		{
			player.sendMessage("SaLE Log: "+log); //REMOVEME
		}
	}
	
	private String format(double d)
	{
		return formatter.format(d/1000); REMOVEME
	}*/
	
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
		final Player player = event.getPlayer();
		final Action action = event.getAction();
		final long start = System.currentTimeMillis();
		//log(player, format(System.currentTimeMillis()-start)+"s Call Mysql SignShop Object");
		final SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP,
				"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
				plugin.getServername(), player.getWorld().getName(),
				b.getX(), b.getY(), b.getZ());
		//log(player, format(System.currentTimeMillis()-start)+"s Called SignShop Object");
		if(ssh != null)
		{
			if(SignHandler.isBreakToggle(player.getUniqueId()))
			{
				//log(player, format(System.currentTimeMillis()-start)+"s BreakToggel true, return");
				return;
			}
			event.setCancelled(true);
		} else
		{
			//log(player, format(System.currentTimeMillis()-start)+"s Return, dont SignShop at that Location.");
			return;
		}
		dodo(event, player, ssh, b, bs, action, start);
	}
	
	public void dodo(final PlayerInteractEvent event, Player player, SignShop ssh, Block b, BlockState bs, Action action, long start)
	{
		//log(player, format(System.currentTimeMillis()-start)+"s BreakToggel?");
		if(SignHandler.isBreakToggle(player.getUniqueId()))
		{
			//log(player, format(System.currentTimeMillis()-start)+"s BreakToggel true, return");
			return;
		}
		//log(player, format(System.currentTimeMillis()-start)+"s Cooldown?");
		if(isOnCooldown(player))
		{
			//log(player, format(System.currentTimeMillis()-start)+"s Cooldown, return");
			return;
		}
		//log(player, format(System.currentTimeMillis()-start)+"s Call Mysql Playerdata Object");
		PlayerData pd = (PlayerData) plugin.getMysqlHandler().getData(
				MysqlHandler.Type.PLAYERDATA, "`player_uuid` = ?", player.getUniqueId().toString());
		//log(player, format(System.currentTimeMillis()-start)+"s Called Mysql Playerdata Object");
		//log(player, format(System.currentTimeMillis()-start)+"s Check is new Shop?");
		if((ssh.getMaterial() == Material.AIR)
				&& (SignHandler.isOwner(ssh, player.getUniqueId())
				|| SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId())
				|| SignHandler.isBypassToggle(player.getUniqueId())))
		{
			//log(player, format(System.currentTimeMillis()-start)+"s Is new Shop, call Async");
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					//log(player, format(System.currentTimeMillis()-start)+"s New Shop Async Open Gui");
					GuiHandler.openInputInfo(ssh, player, pd.getLastSettingLevel(), true);
					//log(player, format(System.currentTimeMillis()-start)+"s New Shop Async Opend Gui, return");
				}
			}.runTaskAsynchronously(plugin);
			return;
		}
		if(action == Action.LEFT_CLICK_BLOCK)
		{
			//log(player, format(System.currentTimeMillis()-start)+"s Action == LeftClickBlock Start");
			//log(player, format(System.currentTimeMillis()-start)+"s IsOwner || isMember?");
			if(SignHandler.isOwner(ssh, player.getUniqueId()) || SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId()))
			{
				//log(player, format(System.currentTimeMillis()-start)+"s MainItemInHand == AIR?");
				if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
				{
					//log(player, format(System.currentTimeMillis()-start)+"s Take Items from Shop");
					SignHandler.takeOutItemFromShop(ssh, player);
					//log(player, format(System.currentTimeMillis()-start)+"s Taked Items from Shop, return");
					return;
				}
			}
			//log(player, format(System.currentTimeMillis()-start)+"s SignShop isItemHologram?");
			if(ssh.isItemHologram())
			{
				//log(player, format(System.currentTimeMillis()-start)+"s Start Spawn ItemHologram");
				ItemHologramHandler.spawnHologram(ssh);
			}
			//log(player, format(System.currentTimeMillis()-start)+"s Action LeftClickBlock end, return");
			return;
		} else if(action == Action.RIGHT_CLICK_BLOCK)
		{
			//log(player, format(System.currentTimeMillis()-start)+"s Action == RightClickBlock Start");
			//log(player, format(System.currentTimeMillis()-start)+"s IsOwner || isMember || IsBypass?");
			if(SignHandler.isOwner(ssh, player.getUniqueId())
					|| SignHandler.isListed(ListedType.MEMBER, ssh, player.getUniqueId())
					|| SignHandler.isBypassToggle(player.getUniqueId()))
			{
				//log(player, format(System.currentTimeMillis()-start)+"s SignShop Item != null?");
				if(ssh.getItemStack() != null)
				{
					///log(player, format(System.currentTimeMillis()-start)+"s MainItemInHand == AIR?");
					if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR)
					{
						if(ssh.getItemStack() == null || ssh.getItemStack().getType() == Material.AIR)
						{
							player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.ShopItemIsNull")
									.replace("%name%", ssh.getDisplayName())));
							//log(player, format(System.currentTimeMillis()-start)+"s Return");
							return;
						}
						//log(player, format(System.currentTimeMillis()-start)+"s call Async Open Admin Gui");
						new BukkitRunnable()
						{
							@Override
							public void run()
							{
								//log(player, format(System.currentTimeMillis()-start)+"s Start Async, Open Admin Gui");
								GuiHandler.openAdministration(ssh, player,
										plugin.getYamlHandler().getConfig().getBoolean("SignShop.Gui.ForceSettingsLevel", false)
										? SettingsLevel.valueOf(plugin.getYamlHandler().getConfig().getString("SignShop.Gui.ToBeForcedSettingsLevel", "BASE"))
										: pd.getLastSettingLevel(), true);
								//log(player, format(System.currentTimeMillis()-start)+"s End Async, Open Admin Gui");
							}
						}.runTaskAsynchronously(plugin);
						//log(player, format(System.currentTimeMillis()-start)+"s Update Sign");
						SignHandler.updateSign(ssh);
						//log(player, format(System.currentTimeMillis()-start)+"s End Update Sign, return");
						return;
					} else
					{
						//log(player, format(System.currentTimeMillis()-start)+"s Item put into Shop?");
						if(SignHandler.putInItemIntoShop(ssh, player, player.getInventory().getItemInMainHand()))
						{
							//log(player, format(System.currentTimeMillis()-start)+"s Item put into Shop end, return");
							return;
						}
					}
				}
			}
		}
		//log(player, format(System.currentTimeMillis()-start)+"s Called Async, to Open Shop Gui");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				//log(player, format(System.currentTimeMillis()-start)+"s Start Async, to Open Shop Gui");
				//log(player, format(System.currentTimeMillis()-start)+"s Player are Allowed to Access Shop (Lists)?");
				if((ssh.getListedType() == ListedType.BLACKLIST && SignHandler.isListed(ListedType.BLACKLIST, ssh, player.getUniqueId()))
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
					//event.setCancelled(true);
					//log(player, format(System.currentTimeMillis()-start)+"s Player cannot access Shop because of Lists");
					return;
				}
				//log(player, format(System.currentTimeMillis()-start)+"s Start Open ShopGui");
				if(SaLE.getWorldGuard())
				{
					if(!WorldGuardHook.canUsageShop(player, b.getLocation()))
					{
						player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("PlayerInteractListener.WorldGuardUsageDeny")));
						return;
					}
				}
				GuiHandler.openShop(ssh, player, pd.getLastSettingLevel(), false);
				//log(player, format(System.currentTimeMillis()-start)+"s End Open ShopGui, Async End");
			}
		}.runTaskAsynchronously(plugin);
		//log(player, format(System.currentTimeMillis()-start)+"s Call Sync, Sign Update");
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				//log(player, format(System.currentTimeMillis()-start)+"s Start Sync, Sign Update");
				SignHandler.updateSign(ssh);
				//log(player, format(System.currentTimeMillis()-start)+"s End Sync, Sign Update");
			}
		}.runTask(plugin);
		//log(player, format(System.currentTimeMillis()-start)+"s Global End");
	}
	
	@EventHandler
	public void OnEditBook(PlayerEditBookEvent event)
	{
		Set<Material> set = new HashSet<>();
		set.add(Material.ACACIA_SIGN);
		set.add(Material.ACACIA_WALL_SIGN);
		set.add(Material.BIRCH_SIGN);
		set.add(Material.BIRCH_WALL_SIGN);
		set.add(Material.CRIMSON_SIGN);
		set.add(Material.CRIMSON_WALL_SIGN);
		set.add(Material.DARK_OAK_SIGN);
		set.add(Material.DARK_OAK_WALL_SIGN);
		set.add(Material.JUNGLE_SIGN);
		set.add(Material.JUNGLE_WALL_SIGN);
		set.add(Material.MANGROVE_SIGN);
		set.add(Material.MANGROVE_WALL_SIGN);
		set.add(Material.OAK_SIGN);
		set.add(Material.OAK_WALL_SIGN);
		set.add(Material.SPRUCE_SIGN);
		set.add(Material.SPRUCE_WALL_SIGN);
		set.add(Material.WARPED_SIGN);
		set.add(Material.WARPED_WALL_SIGN);
		Block b = event.getPlayer().getTargetBlock(set, 4);
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
		event.setCancelled(true);
	}
	
	private boolean isOnCooldown(Player player)
	{
		if(cooldown.containsKey(player.getUniqueId().toString()))
		{
			long c = cooldown.get(player.getUniqueId().toString());
			if(c > System.currentTimeMillis())
			{
				return true;
			}
		}
		addCooldown(player);
		return false;
	}
	
	private void addCooldown(Player player)
	{
		cooldown.put(player.getUniqueId().toString(), System.currentTimeMillis()+1500L);
	}
}