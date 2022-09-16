package main.java.me.avankziar.sale.spigot.handler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import main.java.me.avankziar.sale.spigot.SaLE;

public class MaterialHandler
{
	private static HashMap<Material, String> materialLocalization = new HashMap<>();
	
	public static void init(SaLE plugin)
	{
		List<Material> list = new ArrayList<Material>(EnumSet.allOf(Material.class));
		for(Material m : list)
		{
			materialLocalization.put(m, plugin.getYamlHandler().getMaterialLang().getString(m.toString(), m.toString()));
		}
	}
	
	public static String getMaterial(Material mat)
	{
		String s = materialLocalization.get(mat);
		return s != null ? s : mat.toString();
	}
}