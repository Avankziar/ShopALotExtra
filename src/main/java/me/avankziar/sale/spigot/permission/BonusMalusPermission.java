package main.java.me.avankziar.sale.spigot.permission;

import org.bukkit.entity.Player;

import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler.CountType;

public class BonusMalusPermission
{
	public static boolean hasPermission(Player player, BaseConstructor bc)
	{
		if(BaseConstructor.getPlugin().getBonusMalus() != null)
		{
			return BaseConstructor.getPlugin().getBonusMalus().getResult(
					player.getUniqueId(),
					player.hasPermission(bc.getPermission()),
					BaseConstructor.getPlugin().pluginName.toLowerCase()+":"+bc.getPath(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
		}
		return player.hasPermission(bc.getPermission());
	}
	
	public static boolean hasPermission(Player player, Bypass.Permission bypassPermission)
	{
		if(BaseConstructor.getPlugin().getBonusMalus() != null)
		{
			return BaseConstructor.getPlugin().getBonusMalus().getResult(
					player.getUniqueId(),
					player.hasPermission(Bypass.get(bypassPermission)),
					BaseConstructor.getPlugin().pluginName.toLowerCase()+":"+bypassPermission.toString().toLowerCase(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
		}
		return player.hasPermission(Bypass.get(bypassPermission));
	}
	
	public static boolean hasPermission(Player player, String permission)
	{
		if(BaseConstructor.getPlugin().getBonusMalus() != null)
		{
			return BaseConstructor.getPlugin().getBonusMalus().getResult(
					player.getUniqueId(),
					player.hasPermission(permission),
					BaseConstructor.getPlugin().pluginName.toLowerCase()+":"+permission.toLowerCase(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
		}
		return player.hasPermission(permission);
	}
	
	public static int getPermissionCount(Player player, Bypass.CountPermission bypassCountPermission)
	{
		if(player.hasPermission(Bypass.get(bypassCountPermission)+"*"))
		{
			return Integer.MAX_VALUE;
		}
		int possibleAmount = 0;
		CountType ct = new ConfigHandler().getCountPermType();
		switch(ct)
		{
		case ADDUP:
			for(int i = 5000; i >= 0; i--)
			{
				if(player.hasPermission(Bypass.get(bypassCountPermission)+i))
				{
					possibleAmount += i;
				}
			}
			break;
		case HIGHEST:
			for(int i = 5000; i >= 0; i--)
			{
				if(player.hasPermission(Bypass.get(bypassCountPermission)+i))
				{
					possibleAmount = i;
					break;
				}
			}
			break;
		}
		if(BaseConstructor.getPlugin().getBonusMalus() != null)
		{
			return (int) BaseConstructor.getPlugin().getBonusMalus().getResult(
					player.getUniqueId(),
					possibleAmount,
					BaseConstructor.getPlugin().pluginName.toLowerCase()+":"+bypassCountPermission.toString().toLowerCase(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
		}
		return possibleAmount;
	}
}