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
	
	public String getSignShopInitLine()
	{
		return plugin.getYamlHandler().getConfig().getString("SignShop.SignInitializationLine", "[SaleShop]");
	}
	
	public enum CountType
	{
		HIGHEST, ADDUP;
	}
	
	public CountType getCountPermType()
	{
		String s = plugin.getYamlHandler().getConfig().getString("Mechanic.CountPerm", "HIGHEST");
		CountType ct;
		try
		{
			ct = CountType.valueOf(s);
		} catch (Exception e)
		{
			ct = CountType.HIGHEST;
		}
		return ct;
	}
	
	public long getDefaulStartItemStorage()
	{
		return plugin.getYamlHandler().getConfig().getLong("SignShop.SignInitializationLine", 3456);
	}
}