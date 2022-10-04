package main.java.me.avankziar.sale.spigot.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import main.java.me.avankziar.sale.spigot.SaLE;

public class MaterialHandler
{
	private static HashMap<Material, String> materialLocalization = new HashMap<>();
	
	public static void init(SaLE plugin)
	{
		List<Material> list = Arrays.asList(new Material[]
				{
						Material.BAKED_POTATO //TODO
				});
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
	
	public static String getMaterial(Material mat, Material sign)
	{
		String s = materialLocalization.get(mat);
		switch(sign)
		{
		default: return s != null ? s : mat.toString();
		case ACACIA_SIGN:
		case ACACIA_WALL_SIGN:
		case BIRCH_SIGN:
		case BIRCH_WALL_SIGN:
		case CRIMSON_SIGN:
		case CRIMSON_WALL_SIGN:
		case DARK_OAK_SIGN:
		case DARK_OAK_WALL_SIGN:
		case JUNGLE_SIGN:
		case JUNGLE_WALL_SIGN:
		case MANGROVE_SIGN:
		case MANGROVE_WALL_SIGN:
		case OAK_SIGN:
		case OAK_WALL_SIGN:
		case SPRUCE_SIGN:
		case SPRUCE_WALL_SIGN:
		case WARPED_SIGN:
		case WARPED_WALL_SIGN:
			String c = SaLE.getPlugin().getYamlHandler().getMaterialLang().getString(sign.toString()+"_SignStartColor", "");
			return s != null ? c+s : c+mat.toString();
		}
	}
	
	public static String getSignColor(Material sign)
	{
		switch(sign)
		{
		default: return "&r";
		case ACACIA_SIGN:
		case ACACIA_WALL_SIGN:
		case BIRCH_SIGN:
		case BIRCH_WALL_SIGN:
		case CRIMSON_SIGN:
		case CRIMSON_WALL_SIGN:
		case DARK_OAK_SIGN:
		case DARK_OAK_WALL_SIGN:
		case JUNGLE_SIGN:
		case JUNGLE_WALL_SIGN:
		case MANGROVE_SIGN:
		case MANGROVE_WALL_SIGN:
		case OAK_SIGN:
		case OAK_WALL_SIGN:
		case SPRUCE_SIGN:
		case SPRUCE_WALL_SIGN:
		case WARPED_SIGN:
		case WARPED_WALL_SIGN:
			String c = SaLE.getPlugin().getYamlHandler().getMaterialLang().getString(sign.toString()+"_SignStartColor", "");
			return c;
		}
	}
}