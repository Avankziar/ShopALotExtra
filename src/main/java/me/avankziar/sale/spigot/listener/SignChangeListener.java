package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.ifh.general.economy.account.AccountCategory;
import main.java.me.avankziar.ifh.general.economy.currency.CurrencyType;
import main.java.me.avankziar.ifh.spigot.economy.account.Account;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.modifiervalueentry.Bypass;
import main.java.me.avankziar.sale.spigot.modifiervalueentry.ModifierValueEntry;
import main.java.me.avankziar.sale.spigot.objects.ListedType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class SignChangeListener implements Listener
{
	private SaLE plugin;
	
	public SignChangeListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event)
	{
		if(event.isCancelled())
		{
			return;
		}
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.SIGNSHOP,
				"`server_name` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
				plugin.getServername(), event.getBlock().getWorld().getName(),
				event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()))
		{
			event.setCancelled(true);
			return;
		}
		if(!event.getLine(0).equalsIgnoreCase(new ConfigHandler().getSignShopInitLine()))
		{
			return;
		}
		Player player = event.getPlayer();
		if(!plugin.getYamlHandler().getConfig().getBoolean("Enable.SignShop", false))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Mechanic.SignShopIsntEnabled")));
			return;
		}
		if(plugin.getYamlHandler().getConfig().getStringList("SignShop.ForbiddenWorld").contains(event.getBlock().getWorld().getName()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignHandler.ForbiddenWorld")));
			return;
		}
		if(!ModifierValueEntry.hasPermission(player, Bypass.Permission.SHOP_CREATION))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoPermission")));
			return;
		}
		int signShopAmount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SIGNSHOP, "`player_uuid` = ?", player.getUniqueId().toString());
		int maxSignShopAmount = ModifierValueEntry.getResult(player, Bypass.Counter.SHOP_CREATION_AMOUNT_);
		if(signShopAmount > maxSignShopAmount)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignChangeListener.AlreadyHaveMaximalSignShop")
					.replace("%actual%", String.valueOf(signShopAmount))
					.replace("%max%", String.valueOf(maxSignShopAmount))
					));
			return;
		}
		int lastnumber = plugin.getMysqlHandler().lastID(MysqlHandler.Type.SIGNSHOP)+1;
		int acid = 0;
		if(plugin.getIFHEco() != null)
		{
			Account ac = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.SHOP, plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL));
			if(ac == null)
			{
				ac = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.MAIN, plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL));
			}
			acid = ac.getID();
		}
		long defaultStartItemStorage = new ConfigHandler().getDefaulStartItemStorage();
		SignShop ssh = new SignShop(
				0, player.getUniqueId(),
				"Shop_"+lastnumber, acid, System.currentTimeMillis(), null, null, Material.AIR,
				defaultStartItemStorage, 0,
				-1.0, -1.0, -1, -1,
				0, 0, -1.0, -1.0, -1, -1, 
				plugin.getServername(), player.getWorld().getName(),
				event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), 
				0, false, false, true, true, "", false, ListedType.ALL, false);
		plugin.getMysqlHandler().create(MysqlHandler.Type.SIGNSHOP, ssh);
		event.setLine(0, ChatApi.tl(SignHandler.getSignLine(0, ssh, event.getBlock())));
		event.setLine(1, ChatApi.tl(SignHandler.getSignLine(1, ssh, event.getBlock())));
		event.setLine(2, ChatApi.tl(SignHandler.getSignLine(2, ssh, event.getBlock())));
		event.setLine(3, ChatApi.tl(SignHandler.getSignLine(3, ssh, event.getBlock())));
		Block b = event.getBlock();
		if(b == null)
		{
			return;
		}
		BlockState bs = b.getState();
		if(!(bs instanceof Sign))
		{
			return;
		}
		Sign sign = (Sign) bs;
		sign.setWaxed(true);
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignChangeListener.ShopCreated")
				.replace("%name%", ssh.getSignShopName())
				));
		if(b.getBlockData() instanceof org.bukkit.block.data.type.WallSign)
		{
			org.bukkit.block.data.type.WallSign ws = (org.bukkit.block.data.type.WallSign) b.getBlockData();
			Block behind = b.getRelative(ws.getFacing().getOppositeFace());
			if(!behind.hasMetadata(BlockBreakListener.SIGNSHOP_CONTACTBLOCK))
			{
				behind.setMetadata(BlockBreakListener.SIGNSHOP_CONTACTBLOCK, new FixedMetadataValue(BaseConstructor.getPlugin(), true));
			}
		} else if(b.getBlockData() instanceof org.bukkit.block.data.type.HangingSign)
		{
			Block above = b.getRelative(BlockFace.UP);
			if(!above.hasMetadata(BlockBreakListener.SIGNSHOP_CONTACTBLOCK))
			{
				above.setMetadata(BlockBreakListener.SIGNSHOP_CONTACTBLOCK,
						new FixedMetadataValue(BaseConstructor.getPlugin(), true));
			}
		} else
		{
			Block under = b.getRelative(BlockFace.DOWN);
			if(!under.hasMetadata(BlockBreakListener.SIGNSHOP_CONTACTBLOCK))
			{
				under.setMetadata(BlockBreakListener.SIGNSHOP_CONTACTBLOCK, new FixedMetadataValue(BaseConstructor.getPlugin(), true));
			}
		}
		return;
	}
}