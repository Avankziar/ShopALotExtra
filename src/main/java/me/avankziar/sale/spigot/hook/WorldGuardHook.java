package main.java.me.avankziar.sale.spigot.hook;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import main.java.me.avankziar.sale.spigot.modifiervalueentry.Bypass;
import main.java.me.avankziar.sale.spigot.modifiervalueentry.ModifierValueEntry;

public class WorldGuardHook
{
	public static StateFlag SHOP_CREATE;
	public static StateFlag SHOP_USAGE;
	
	public static boolean init()
	{
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try 
		{
			StateFlag sc = new StateFlag("sale-shop-create", true);
	        registry.register(sc);
	        SHOP_CREATE = sc;
	        StateFlag su = new StateFlag("sale-shop-use", true);
	        registry.register(su);
	        SHOP_USAGE = su;
	    } catch (FlagConflictException e) 
		{
	        return false;
	    }
		return true;
	}
	
	public static boolean canCreateShop(Player player, Location pointOne)
	{
		RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc1 = BukkitAdapter.adapt(pointOne);
        return ModifierValueEntry.hasPermission(player, Bypass.Permission.SHOP_CREATION_WORLDGUARD)
        		? true : query.testState(loc1, WorldGuardPlugin.inst().wrapPlayer(player), SHOP_CREATE);
	}
	
	public static boolean canUsageShop(Player player, Location pointOne)
	{
		RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc1 = BukkitAdapter.adapt(pointOne);
        return ModifierValueEntry.hasPermission(player, Bypass.Permission.SHOP_USAGE_WORLDGUARD)
        		? true : query.testState(loc1, WorldGuardPlugin.inst().wrapPlayer(player), SHOP_USAGE);
	}
}