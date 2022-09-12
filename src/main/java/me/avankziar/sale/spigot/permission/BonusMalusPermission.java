package main.java.me.avankziar.sale.spigot.permission;

import org.bukkit.entity.Player;

import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;

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
	
	public static boolean hasPermission(Player player, Bypass.Permission bypassPermission, BaseConstructor bc)
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
}