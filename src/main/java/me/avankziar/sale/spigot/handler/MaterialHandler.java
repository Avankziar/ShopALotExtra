package main.java.me.avankziar.sale.spigot.handler;

import java.util.HashMap;

import org.bukkit.Material;

import main.java.me.avankziar.sale.spigot.SaLE;

public class MaterialHandler
{
	private static HashMap<Material, String> materialLocalization = new HashMap<>();
	
	public static void init(SaLE plugin)
	{
		//TODO
	}
	
	public static String getMaterial(Material mat)
	{
		String s = materialLocalization.get(mat);
		return s != null ? s : mat.toString();
	}
}