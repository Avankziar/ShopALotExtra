package main.java.me.avankziar.sale.spigot.permission;

import java.util.LinkedHashMap;

import main.java.me.avankziar.sale.spigot.SaLE;

public class Bypass
{
	public enum Permission
	{
		SHOP_CREATION,
		SHOP_GUI_BYPASS,
		SHOP_LOG_OTHERPLAYER,
		SHOPPING_LOG_OTHERPLAYER;
		
		public String getBonusMalus()
		{
			return SaLE.getPlugin().pluginName.toLowerCase()+":"+this.toString().toLowerCase();
		}
	}
	private static LinkedHashMap<Bypass.Permission, String> mapPerm = new LinkedHashMap<>();
	
	public static void set(Bypass.Permission bypass, String perm)
	{
		mapPerm.put(bypass, perm);
	}
	
	public static String get(Bypass.Permission bypass)
	{
		return mapPerm.get(bypass);
	}
	
	public enum CountPermission
	{
		SHOP_CREATION_AMOUNT_,
		SHOP_ITEMSTORAGE_AMOUNT_;
		
		public String getBonusMalus()
		{
			return SaLE.getPlugin().pluginName.toLowerCase()+":"+this.toString().toLowerCase();
		}
	}
	private static LinkedHashMap<Bypass.CountPermission, String> mapCount = new LinkedHashMap<>();
	
	public static void set(Bypass.CountPermission bypass, String perm)
	{
		mapCount.put(bypass, perm);
	}
	
	public static String get(Bypass.CountPermission bypass)
	{
		return mapCount.get(bypass);
	}
}