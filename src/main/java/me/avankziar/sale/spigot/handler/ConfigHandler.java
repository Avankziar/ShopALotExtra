package main.java.me.avankziar.sale.spigot.handler;

import main.java.me.avankziar.sale.spigot.SaLE;

public class ConfigHandler
{
	private SaLE plugin;
	
	public ConfigHandler()
	{
		this.plugin = SaLE.getPlugin();
	}
	
	public boolean isSignShopEnabled()
	{
		return plugin.getYamlHandler().getConfig().getBoolean("Enable.SignShop", false);
	}
	
	public boolean isAuctionEnabled()
	{
		return plugin.getYamlHandler().getConfig().getBoolean("Enable.Auction", false);
	}
	
	public boolean isMechanicBonusMalusEnabled()
	{
		return plugin.getYamlHandler().getConfig().getBoolean("EnableMechanic.BonusMalus", false);
	}
}