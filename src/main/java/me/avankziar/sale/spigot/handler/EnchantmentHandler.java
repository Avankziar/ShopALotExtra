package main.java.me.avankziar.sale.spigot.handler;

import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;

import main.java.me.avankziar.sale.spigot.SaLE;

public class EnchantmentHandler
{
	private static HashMap<String, String> enchantmentLocalization = new HashMap<>();//Enchanment, Locale
	
	@SuppressWarnings("deprecation")
	public static void init(SaLE plugin)
	{
		for(Enchantment e : Enchantment.values())
		{
			enchantmentLocalization.put(e.getName(), plugin.getYamlHandler().getMaterialLang().getString(e.getName(), e.getName()));		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getEnchantment(Enchantment ench)
	{
		String s = enchantmentLocalization.get(ench.getName());
		return s != null ? s : ench.getName();
	}
}