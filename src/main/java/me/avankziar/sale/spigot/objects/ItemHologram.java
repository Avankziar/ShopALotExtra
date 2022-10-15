package main.java.me.avankziar.sale.spigot.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.handler.Base64Handler;
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
					for(Entry<Enchantment, Integer> en : esm.getStoredEnchants().entrySet())
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
			if(im instanceof PotionMeta)
			{
				PotionMeta pm = (PotionMeta) im;
				for(PotionEffect pe : pm.getCustomEffects())
				{
					int level = pe.getAmplifier()+1;
					long dur = pe.getDuration();
					String color = "";
					if(pe.getType() == PotionEffectType.ABSORPTION || pe.getType() == PotionEffectType.CONDUIT_POWER
							|| pe.getType() == PotionEffectType.DAMAGE_RESISTANCE || pe.getType() == PotionEffectType.DOLPHINS_GRACE
							|| pe.getType() == PotionEffectType.FAST_DIGGING || pe.getType() == PotionEffectType.FIRE_RESISTANCE
							|| pe.getType() == PotionEffectType.HEAL || pe.getType() == PotionEffectType.HEALTH_BOOST
							|| pe.getType() == PotionEffectType.HERO_OF_THE_VILLAGE || pe.getType() == PotionEffectType.INCREASE_DAMAGE
							|| pe.getType() == PotionEffectType.INVISIBILITY || pe.getType() == PotionEffectType.JUMP
							|| pe.getType() == PotionEffectType.LUCK || pe.getType() == PotionEffectType.NIGHT_VISION
							|| pe.getType() == PotionEffectType.REGENERATION || pe.getType() == PotionEffectType.SATURATION
							|| pe.getType() == PotionEffectType.SLOW_FALLING || pe.getType() == PotionEffectType.SPEED
							|| pe.getType() == PotionEffectType.WATER_BREATHING)
					{
						color = "&9";
					} else if(pe.getType() == PotionEffectType.BAD_OMEN || pe.getType() == PotionEffectType.BLINDNESS
							|| pe.getType() == PotionEffectType.CONFUSION || pe.getType() == PotionEffectType.DARKNESS
							|| pe.getType() == PotionEffectType.HARM || pe.getType() == PotionEffectType.HUNGER
							|| pe.getType() == PotionEffectType.LEVITATION || pe.getType() == PotionEffectType.POISON
							|| pe.getType() == PotionEffectType.SLOW || pe.getType() == PotionEffectType.SLOW_DIGGING
							|| pe.getType() == PotionEffectType.SLOW_FALLING || pe.getType() == PotionEffectType.UNLUCK
							|| pe.getType() == PotionEffectType.WEAKNESS || pe.getType() == PotionEffectType.WITHER)
					{
						color = "&c";
					} else if(pe.getType() == PotionEffectType.GLOWING)
					{
						color = "&7";
					}
					lines.add(ChatApi.tl(color+SaLE.getPlugin().getEnumTl().getLocalization(pe.getType())
					+" "+GuiHandler.IntegerToRomanNumeral(level)+" >> "+TimeHandler.getDateTime(dur, "mm:ss")));
				}
			}
			if(im instanceof BlockStateMeta)
			{
				BlockStateMeta bsm = (BlockStateMeta) im;
				if(bsm.getBlockState() instanceof ShulkerBox)
				{
					ShulkerBox sh = (ShulkerBox) bsm.getBlockState();
					LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>(); //B64, itemamount
					for(ItemStack its : sh.getSnapshotInventory())
					{
						if(its == null || its.getType() == Material.AIR)
						{
							continue;
						}
						ItemStack c = its.clone();
						c.setAmount(1);
						String b64 = new Base64Handler(c).toBase64();
						int amount = its.getAmount() + (lhm.containsKey(b64) ? lhm.get(b64) : 0);
						lhm.put(b64, amount);
					}
					for(Entry<String, Integer> e : lhm.entrySet())
					{
						ItemStack ist = new Base64Handler(e.getKey()).fromBase64();
						lines.add(ChatApi.tl("&7"+SaLE.getPlugin().getEnumTl().getLocalization(ist.getType())+ "x"+e.getValue()));
					}
				}
			}
		}
		spawn(null, loc.add(0, 0.9, 0), is);
		loc.add(0, -0.9, 0);
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