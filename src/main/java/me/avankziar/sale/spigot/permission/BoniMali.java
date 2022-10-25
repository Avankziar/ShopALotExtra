package main.java.me.avankziar.sale.spigot.permission;

import main.java.me.avankziar.sale.spigot.SaLE;

public enum BoniMali
{
	COST_ADDING_STORAGE,
	SHOP_BUYING_TAX,
	SHOP_SELLING_TAX;
	
	public String getBonusMalus()
	{
		return SaLE.getPlugin().pluginName.toLowerCase()+":"+this.toString().toLowerCase();
	}
}
