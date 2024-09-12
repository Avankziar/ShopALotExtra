package main.java.me.avankziar.sale.spigot.cmd.shop;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.handler.SignHandler;

public class ARG_BreakToggle extends ArgumentModule
{
	private SaLE plugin;
	
	public ARG_BreakToggle(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale shop breaktoggle
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		Player player = (Player) sender;
		if(SignHandler.isBreakToggle(player.getUniqueId()))
		{
			SignHandler.breakToggle.remove(player.getUniqueId().toString());
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.BreakToggle.Deactive")));
		} else
		{
			SignHandler.breakToggle.add(player.getUniqueId().toString());
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.BreakToggle.Active")));
		}
	}
}