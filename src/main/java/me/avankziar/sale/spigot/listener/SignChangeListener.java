package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import main.java.me.avankziar.ifh.general.assistance.ChatApi;
import main.java.me.avankziar.ifh.general.economy.account.AccountCategory;
import main.java.me.avankziar.ifh.general.economy.currency.CurrencyType;
import main.java.me.avankziar.ifh.spigot.economy.account.Account;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.permission.BonusMalusPermission;
import main.java.me.avankziar.sale.spigot.permission.Bypass;

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
		if(!BonusMalusPermission.hasPermission(player, Bypass.Permission.SHOP_CREATION))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("NoPermission")));
			return;
		}
		int signShopAmount = plugin.getMysqlHandler().getCount(MysqlHandler.Type.SIGNSHOP, "`player_uuid` = ?", player.getUniqueId().toString());
		int maxSignShopAmount = BonusMalusPermission.getPermissionCount(player, Bypass.CountPermission.SHOP_CREATION_AMOUNT_);
		if(signShopAmount > maxSignShopAmount)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignChangeListener.AlreadyHaveMaximalSignShop")
					.replace("%actual%", String.valueOf(signShopAmount))
					.replace("%max%", String.valueOf(maxSignShopAmount))
					));
			return;
		}
		int lastnumber = plugin.getMysqlHandler().lastID(MysqlHandler.Type.SIGNSHOP)+1;
		Account ac = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.SHOP, plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL));
		if(ac == null)
		{
			ac = plugin.getIFHEco().getDefaultAccount(player.getUniqueId(), AccountCategory.MAIN, plugin.getIFHEco().getDefaultCurrency(CurrencyType.DIGITAL));
		}
		long defaultStartItemStorage = new ConfigHandler().getDefaulStartItemStorage();
		SignShop ssh = new SignShop(
				0, player.getUniqueId(),
				"Shop_"+lastnumber, ac.getID(), System.currentTimeMillis(), null, "Shop_"+lastnumber, Material.AIR,
				defaultStartItemStorage, 0,
				-1.0, -1.0, -1, -1,
				0, 0, -1.0, -1.0, -1, -1, 
				plugin.getServername(), player.getWorld().getName(),
				event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), 
				0, false, false, true, true, "");
		plugin.getMysqlHandler().create(MysqlHandler.Type.SIGNSHOP, ssh);
		event.setLine(0, ChatApi.tl(SignHandler.getSignLine(0, ssh)));
		event.setLine(1, ChatApi.tl(SignHandler.getSignLine(1, ssh)));
		event.setLine(2, ChatApi.tl(SignHandler.getSignLine(2, ssh)));
		event.setLine(3, ChatApi.tl(SignHandler.getSignLine(3, ssh)));
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignChangeListener.ShopCreated")
				.replace("%name%", ssh.getDisplayName())
				));
		return;
	}
}