package main.java.me.avankziar.sale.spigot.objects;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;

public class ItemHologram
{
	private ArrayList<UUID> entitys = new ArrayList<>();
	
	public ItemHologram(ItemStack is, Location loc)
	{
		ArrayList<String> lines = new ArrayList<>();
		ItemMeta im = is.getItemMeta();
		lines.add(im.hasDisplayName() ? im.getDisplayName() : SaLE.getPlugin().getEnumTl().getLocalization(is.getType()));
		if(im.hasLore()) 
		{
			lines.addAll(im.getLore());
		} else
		{
			if(Material.ENCHANTED_BOOK != is.getType())
			{
				if(im.hasEnchants())
				{
					for(Entry<Enchantment, Integer> en : is.getEnchantments().entrySet())
					{
						String name = SaLE.getPlugin().getEnumTl().getLocalization(en.getKey());
						int level = en.getValue()+1;
						lines.add(ChatApi.tl("&7"+name+" "+GuiHandler.IntegerToRomanNumeral(level)));
					}
				}
			} else
			{
				if(im instanceof EnchantmentStorageMeta)
				{
					EnchantmentStorageMeta esm = (EnchantmentStorageMeta) im;
					for(Entry<Enchantment, Integer> en : esm.getEnchants().entrySet())
					{
						String name = SaLE.getPlugin().getEnumTl().getLocalization(en.getKey());
						int level = en.getValue()+1;
						lines.add(ChatApi.tl("&7"+name+" "+GuiHandler.IntegerToRomanNumeral(level)));
					}
				}
			}
			if(im instanceof BannerMeta)
			{
				BannerMeta bm = (BannerMeta) im;
				for(Pattern p : bm.getPatterns())
				{
					lines.add(ChatApi.tl("&7"+SaLE.getPlugin().getEnumTl().getLocalization(p.getColor(), p.getPattern())));
				}
			}
		}
		spawn(null, loc.add(0, 0.5, 0), is);
		loc.add(0, -0.5, 0);
		for(int i = 0; i < lines.size(); i++)
		{
			spawn(lines.get(i), loc.add(0, -0.23, 0), null);
		}
	}
	
	private void spawn(String text, Location loc, ItemStack is)
	{
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setVisible(false);
		if(is != null)
		{
			as.getEquipment().setHelmet(is);
		}
		as.addEquipmentLock(EquipmentSlot.HEAD, LockType.REMOVING_OR_CHANGING);
		as.setCanPickupItems(false);
		if(text != null)
		{
			as.setCustomName(text);
		} else
		{
			as.setCustomName(" ");
		}
		as.setCustomNameVisible(true);
		entitys.add(as.getUniqueId());
	}
	
	public void despawn()
	{
		for(UUID as : entitys)
		{
			if(as == null)
			{
				continue;
			}
			Entity e = Bukkit.getEntity(as);
			e.remove();
		}
	}
	
	public boolean cancelManipulateEvent(ArmorStand as)
	{
		String a = as.getLocation().getWorld().getName()+as.getLocation().getX()+as.getLocation().getY()+as.getLocation().getZ();
		for(UUID uu : entitys)
		{
			Entity e = Bukkit.getEntity(uu);
			if(!(e instanceof ArmorStand))
			{
				continue;
			}
			ArmorStand ar = (ArmorStand) e;
			String b = ar.getLocation().getWorld().getName()+ar.getLocation().getX()+ar.getLocation().getY()+ar.getLocation().getZ();
			return a.equals(b);
		}
		return false;
	}
}