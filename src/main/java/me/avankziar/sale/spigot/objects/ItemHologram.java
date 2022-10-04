package main.java.me.avankziar.sale.spigot.objects;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.sale.spigot.handler.MaterialHandler;

public class ItemHologram
{
	private ArrayList<ArmorStand> entitys;
	
	public ItemHologram(ItemStack is, Location loc)
	{
		ArrayList<String> lines = new ArrayList<>();
		lines.add(is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : MaterialHandler.getMaterial(is.getType()));
		if(is.getItemMeta().hasLore()) {lines.addAll(is.getItemMeta().getLore());}
		for(int i = 0; i < lines.size(); i++)
		{
			spawn(lines.get(i), loc.add(0, -(i*0.2), 0)); //TODO die noch nachjustieren
		}
	}
	
	private void spawn(String text, Location loc)
	{
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		as.setCustomName(text);
		as.setCustomNameVisible(true);
		as.setVisible(false);
		entitys.add(as);
	}
	
	public void despawn()
	{
		for(ArmorStand as : entitys)
		{
			as.remove();
		}
	}
	
	public boolean cancelManipulateEvent(ArmorStand as)
	{
		String a = as.getLocation().getWorld().getName()+as.getLocation().getX()+as.getLocation().getY()+as.getLocation().getZ();
		for(ArmorStand ar : entitys)
		{
			String b = ar.getLocation().getWorld().getName()+ar.getLocation().getX()+ar.getLocation().getY()+ar.getLocation().getZ();
			return a.equals(b);
		}
		return false;
	}
}