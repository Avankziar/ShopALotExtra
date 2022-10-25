package main.java.me.avankziar.sale.spigot.cmd.sale;

import java.io.IOException;

import org.bukkit.command.CommandSender;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;

public class ARGShopping extends ArgumentModule
{
	private SaLE plugin;
	
	public ARGShopping(SaLE plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	//sale shopping ... 
	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		sender.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("Cmd.OtherCmd")));
	}
}