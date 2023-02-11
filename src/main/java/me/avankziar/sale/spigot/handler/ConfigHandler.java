package main.java.me.avankziar.sale.spigot.handler;

import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;

public class ConfigHandler
{
	public enum CountType
	{
		HIGHEST, ADDUP;
	}
	
	public CountType getCountPermType()
	{
		String s = BaseConstructor.getPlugin().getYamlHandler().getConfig().getString("Mechanic.CountPerm", "HIGHEST");
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
	
	public boolean isSignShopEnabled()
	{
		return BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Enable.SignShop", false);
	}
	
	public boolean isAuctionEnabled()
	{
		return BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Enable.Auction", false);
	}
	
	public boolean isMechanicBonusMalusEnabled()
	{
		return BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("EnableMechanic.BonusMalus", false);
	}
	
	public String getSignShopInitLine()
	{
		return BaseConstructor.getPlugin().getYamlHandler().getConfig().getString("SignShop.SignInitializationLine", "[SaleShop]");
	}
	
	public long getDefaulStartItemStorage()
	{
		return BaseConstructor.getPlugin().getYamlHandler().getConfig().getLong("SignShop.DefaultStartItemStorage", 3456);
	}
	
	public boolean shopCanTradeShulker()
	{
		return BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("SignShop.ShopCanTradeShulker", true);
	}
}