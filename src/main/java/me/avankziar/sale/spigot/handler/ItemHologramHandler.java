package main.java.me.avankziar.sale.spigot.handler;

import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.objects.ItemHologram;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class ItemHologramHandler
{
	public static LinkedHashMap<Long, ItemHologram> taskMap = new LinkedHashMap<>();
	
	public static void spawnHologram(SignShop ssh)
	{
		if(!SaLE.getPlugin().getYamlHandler().getConfig().getBoolean("SignShop.ItemHologram.CanSpawn", false))
		{
			return;
		}
		if(!SaLE.getPlugin().getServername().equals(ssh.getServer())
			|| Bukkit.getWorld(ssh.getWorld()) == null)
		{
			return;
		}
		Location loc = new Location(Bukkit.getWorld(ssh.getWorld()), ssh.getX(), ssh.getY(), ssh.getZ());
		long timer = System.currentTimeMillis()
				+ SaLE.getPlugin().getYamlHandler().getConfig().getLong("SignShop.ItemHologram.VisibilityTimeInSeconds", 5)*1000;
		ItemHologram ish = new ItemHologram(ssh.getItemStack(), loc);
		taskMap.put(timer, ish);
	}
}