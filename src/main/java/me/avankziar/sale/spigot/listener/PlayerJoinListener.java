package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
		PlayerData pd = (PlayerData) plugin.getMysqlHandler().getData(MysqlHandler.Type.PLAYERDATA, "`player_uuid` = ?", player.getUniqueId().toString());
		if(pd == null)
		{
			pd = new PlayerData(0, player.getUniqueId(), player.getName(), SettingsLevel.BASE, System.currentTimeMillis());
			plugin.getMysqlHandler().create(MysqlHandler.Type.PLAYERDATA, pd);
		} else
		{
			pd.setName(player.getName());
			pd.setLastLogin(System.currentTimeMillis());
			plugin.getMysqlHandler().updateData(MysqlHandler.Type.PLAYERDATA, pd, "`player_uuid` = ?", player.getUniqueId().toString());
		}
	}
}