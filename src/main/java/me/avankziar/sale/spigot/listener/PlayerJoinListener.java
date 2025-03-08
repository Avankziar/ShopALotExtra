package main.java.me.avankziar.sale.spigot.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.objects.SettingsLevel;
import main.java.me.avankziar.sale.spigot.objects.PlayerData;

public class PlayerJoinListener implements Listener
{
	private SaLE plugin;
	
	public PlayerJoinListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		final String name = player.getName();
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				PlayerData pd = (PlayerData) plugin.getMysqlHandler().getData(MysqlHandler.Type.PLAYERDATA, "`player_uuid` = ?", uuid.toString());
				if(pd == null)
				{
					pd = new PlayerData(0, uuid, name, SettingsLevel.BASE, System.currentTimeMillis());
					plugin.getMysqlHandler().create(MysqlHandler.Type.PLAYERDATA, pd);
				} else
				{
					pd.setName(name);
					pd.setLastLogin(System.currentTimeMillis());
					plugin.getMysqlHandler().updateData(MysqlHandler.Type.PLAYERDATA, pd, "`player_uuid` = ?", uuid.toString());
				}
			}
		}.runTaskAsynchronously(plugin);
	}
}