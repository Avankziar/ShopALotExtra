package main.java.me.avankziar.sale.spigot.cmd.shop;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler.Type;

public class ARG_ToggleSearchAll extends ArgumentModule
{
	private SaLE plugin;
	
	public ARG_ToggleSearchAll(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale shop togglesearchall <true/false>
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		Player player = (Player) sender;
		if(isOnCooldown(player.getUniqueId()))
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("OnCooldown")));
			return;
		}
		setCooldown(player.getUniqueId(), 15, TimeUnit.SECONDS);
		boolean t = false;
		if(args.length >= 3)
		{
			if(MatchApi.isBoolean(args[2]))
			{
				t = Boolean.valueOf(args[2]);
			}
		}
		boolean toggle = t;
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				doAsync(player, toggle);
			}
		}.runTaskAsynchronously(plugin);
	}
	
	private void doAsync(Player player, boolean toggle)
	{
		int a = plugin.getMysqlHandler().rawQuery(Type.SIGNSHOP,
				"UPDATE `%table%` SET `searchable` = ? WHERE `player_uuid` = ?",
				toggle, player.getUniqueId().toString());
		if(toggle)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.ToggleSearchAll.ToggleSearch")
					.replace("%a%", String.valueOf(a))));
		} else
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.ToggleSearchAll.ToggleUnsearch")
					.replace("%a%", String.valueOf(a))));
		}
	}
}