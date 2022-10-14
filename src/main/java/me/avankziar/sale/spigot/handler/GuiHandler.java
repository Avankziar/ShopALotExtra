package main.java.me.avankziar.sale.spigot.handler;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import main.java.me.avankziar.ifh.spigot.economy.account.Account;
import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.assistance.TimeHandler;
import main.java.me.avankziar.sale.spigot.assistance.Utility;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.GUIApi;
import main.java.me.avankziar.sale.spigot.gui.events.ClickFunction;
import main.java.me.avankziar.sale.spigot.gui.events.ClickType;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.objects.ListedType;
import main.java.me.avankziar.sale.spigot.objects.PlayerData;
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.permission.BonusMalusPermission;
import main.java.me.avankziar.sale.spigot.permission.Bypass;

public class GuiHandler
{
	private static SaLE plugin = SaLE.getPlugin();
	public static String SIGNSHOP_ID = "signshop_id";
	public static String PLAYER_UUID = "player_uuid";
	
	public static void openAdministration(SignShop ssh, Player player, SettingsLevel settingsLevel, boolean closeInv)
	{
		GuiType gt = GuiType.ADMINISTRATION;
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, "Shop: "+ssh.getSignShopName(), 
				settingsLevel == null ? SettingsLevel.BASE : settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openAdministration(SignShop ssh, Player player, SettingsLevel settingsLevel, Inventory inv, boolean closeInv)
	{
		GuiType gt = GuiType.ADMINISTRATION;
		GUIApi gui = new GUIApi(plugin.pluginName, inv, gt.toString(), 
				settingsLevel == null ? SettingsLevel.BASE : settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openShop(SignShop ssh, Player player, SettingsLevel settingsLevel, boolean closeInv)
	{
		GuiType gt = GuiType.SHOP;
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, "Shop "+ssh.getSignShopName(), settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openShop(SignShop ssh, Player player, SettingsLevel settingsLevel, Inventory inv, boolean closeInv)
	{
		GuiType gt = GuiType.SHOP;
		GUIApi gui = new GUIApi(plugin.pluginName, inv, gt.toString(), settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openInputInfo(SignShop ssh, Player player, SettingsLevel settingsLevel, boolean closeInv)
	{
		GuiType gt = GuiType.ITEM_INPUT;
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, "Shop:"+String.valueOf(ssh.getId()), settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openKeyOrNumInput(SignShop ssh, Player player, GuiType gt, SettingsLevel settingsLevel, String keyboardOrNumpad, boolean closeInv)
	{
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, ssh.getSignShopName()+keyboardOrNumpad, settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	private static void openGui(SignShop ssh, Player player, GuiType gt, GUIApi gui, SettingsLevel settingsLevel, boolean closeInv)
	{
		Account ac = plugin.getIFHEco().getAccount(ssh.getAccountId());
		int dg = ac == null ? 0 : plugin.getIFHEco().getDefaultGradationQuantity(ac.getCurrency());
		boolean useSI = ac == null ? false : plugin.getIFHEco().getDefaultUseSIPrefix(ac.getCurrency());
		boolean useSy = ac == null ? false : plugin.getIFHEco().getDefaultUseSymbol(ac.getCurrency());
		String ts = ac == null ? "." : plugin.getIFHEco().getDefaultThousandSeperator(ac.getCurrency());
		String ds = ac == null ? "," : plugin.getIFHEco().getDefaultDecimalSeperator(ac.getCurrency());
		YamlConfiguration y = plugin.getYamlHandler().getGui(gt);
		for(int i = 0; i < 54; i++)
		{
			if(y.get(i+".IsInfoItem") != null && y.getBoolean(i+".IsInfoItem"))
			{
				ItemStack is = ssh.getItemStack();
				LinkedHashMap<String, Entry<GUIApi.Type, Object>> map = new LinkedHashMap<>();
				map.put(SIGNSHOP_ID, new AbstractMap.SimpleEntry<GUIApi.Type, Object>(GUIApi.Type.INTEGER, ssh.getId()));
				gui.add(i, is, settingsLevel, true, map, getClickFunction(y, String.valueOf(i)));
				continue;
			}
			if(y.get(i+".Material") == null && y.get(i+".Material."+settingsLevel.toString()) == null)
			{
				continue;
			}			
			SettingsLevel itemSL = SettingsLevel.valueOf(y.getString(i+".SettingLevel"));
			if(y.get(i+".SettingLevel") == null)
			{
				itemSL = SettingsLevel.NOLEVEL;
			}
			if(settingsLevel.getOrdinal() < itemSL.getOrdinal())
			{
				continue;
			}
			if(y.get(i+".Permission") != null)
			{
				if(!BonusMalusPermission.hasPermission(player, Bypass.get(Bypass.Permission.SHOP_GUI_BYPASS)+y.get(i+".Permission")))
				{
					continue;
				}
			}
			if(y.get(i+".CanBuy") != null)
			{
				if(y.getBoolean(i+".CanBuy"))
				{
					if(!ssh.canBuy())
					{
						continue;
					}
					if(gt == GuiType.SHOP)
					{
						if(SignHandler.isDiscount(ssh, System.currentTimeMillis()))
						{
							if((ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0)
									&& (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0))
							{
								continue;
							}
						} else
						{
							if(ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0)
							{
								continue;
							}
						}
					}
				}
			}
			if(y.get(i+".CanSell") != null)
			{
				if(y.getBoolean(i+".CanSell"))
				{
					if(!ssh.canSell())
					{
						continue;
					}
					if(gt == GuiType.SHOP)
					{
						if(SignHandler.isDiscount(ssh, System.currentTimeMillis()))
						{
							if((ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0)
									&& (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0))
							{
								continue;
							}
						} else
						{
							if(ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0)
							{
								continue;
							}
						}
					}
				}
			}
			if(y.get(i+".UseASH") != null)
			{
				if(y.getBoolean(i+".UseASH"))
				{
					if(plugin.getPCS() == null)
					{
						continue;
					}
				}
			}
			Material mat = null;
			ItemStack is = null;
			if(y.get(i+".Material."+settingsLevel.toString()) != null)
			{
				mat = Material.valueOf(y.getString(i+".Material."+settingsLevel.toString()));
				if(mat == Material.PLAYER_HEAD && y.getString(i+"."+settingsLevel.toString()+".PlayerHeadTexture") != null)
				{
					is = getSkull(y.getString(i+"."+settingsLevel.getName()+".PlayerHeadTexture"));
				}
			} else
			{
				try
				{
					mat = Material.valueOf(y.getString(i+".Material"));
					if(mat == Material.PLAYER_HEAD && y.getString(i+".HeadTexture") != null)
					{
						is = getSkull(y.getString(i+".HeadTexture"));
					}
				} catch(Exception e)
				{
					continue;
				}
			}
			String playername = null;
			UUID otheruuid = null;
			if(y.get(i+".PlayerSearchNum") != null)
			{
				if(ssh.getNumText().isBlank() || ssh.getNumText().isEmpty())
				{
					continue;
				}
				int num = y.getInt(i+".PlayerSearchNum");
				ArrayList<Object> l = plugin.getMysqlHandler().getList(
						MysqlHandler.Type.PLAYERDATA, "`player_name` ASC", num, 1, "`player_name` like ?", "%"+ssh.getNumText()+"%");
				if(l == null || l.isEmpty())
				{
					continue;
				}
				PlayerData pd = PlayerData.convert(l).get(0);
				playername = pd.getName();
				otheruuid = pd.getUUID();
				is = new ItemStack(Material.PLAYER_HEAD);
				ItemMeta im = is.getItemMeta();
				if(!(im instanceof SkullMeta))
				{
					continue;
				}
				SkullMeta sm = (SkullMeta) im;
				sm.setOwningPlayer(Bukkit.getOfflinePlayer(pd.getUUID()));
				is.setItemMeta(sm);
			}
			int amount = 1;
			if(y.get(i+".Amount") != null)
			{
				amount = y.getInt(i+".Amount");
			}
			ArrayList<String> lore = null;
			if(y.get(i+".Lore."+settingsLevel.toString()) != null)
			{
				lore = (ArrayList<String>) y.getStringList(i+".Lore."+settingsLevel.toString());
			} else
			{
				if(y.get(i+".Lore") != null)
				{
					lore = (ArrayList<String>) y.getStringList(i+".Lore");
				}
			}
			if(lore != null)
			{
				lore = (ArrayList<String>) getLorePlaceHolder(ssh, player, lore, ac, dg, useSI, useSy, ts, ds, playername);
			}
			
			if(y.get(i+".InfoLore") != null && y.getBoolean(i+".InfoLore"))
			{
				if(lore == null)
				{
					lore = new ArrayList<>();
				}
				ArrayList<String> infoLore = getStringPlaceHolder(ssh.getItemStack(), ssh.getOwner());
				for(String s : infoLore)
				{
					lore.add(ChatApi.tl(s));
				}
			}
			String displayname = y.get(i+".Displayname") != null 
					? y.getString(i+".Displayname") 
					: (playername != null ? playername : SaLE.getPlugin().getEnumTl().getLocalization(mat));
			displayname = getStringPlaceHolder(ssh, player, displayname, ac, dg, useSI, useSy, ts, ds, playername);
			if(is == null)
			{
				is = new ItemStack(mat, amount);
			} else
			{
				is.setAmount(amount);
			}
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(displayname);
			if(lore != null)
			{
				im.setLore(lore);
			}
			is.setItemMeta(im);
			LinkedHashMap<String, Entry<GUIApi.Type, Object>> map = new LinkedHashMap<>();
			map.put(SIGNSHOP_ID, new AbstractMap.SimpleEntry<GUIApi.Type, Object>(GUIApi.Type.INTEGER, ssh.getId()));
			if(otheruuid != null)
			{
				map.put(PLAYER_UUID, new AbstractMap.SimpleEntry<GUIApi.Type, Object>(GUIApi.Type.STRING, otheruuid.toString()));
			}
			gui.add(i, is, settingsLevel, true, map, getClickFunction(y, String.valueOf(i)));
		}
		if(closeInv)
		{
			player.closeInventory();
		}
		gui.open(player, gt, ssh.getId());
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getSkull(String url) 
	{
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if (url == null || url.isEmpty())
            return skull;
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = org.apache.commons.codec.binary.Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }
	
	private static List<String> getLorePlaceHolder(SignShop ssh, Player player, List<String> lore,
			Account ac, int dg, boolean useSI, boolean useSy, String ts, String ds, String playername)
	{
		List<String> list = new ArrayList<>();
		for(String s : lore)
		{
			String a = getStringPlaceHolder(ssh, player, s, ac, dg, useSI, useSy, ts, ds, playername);
			list.add(a);
		}
		return list;
	}
	
	private static ArrayList<String> getStringPlaceHolder(ItemStack is, UUID uuid)
	{
		if(is == null)
		{
			return new ArrayList<>();
		}
		ArrayList<String> list = new ArrayList<>();
		YamlConfiguration y = plugin.getYamlHandler().getLang();
		list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.Owner") 
				+ (Utility.convertUUIDToName(uuid.toString()) == null 
				? "/" : Utility.convertUUIDToName(uuid.toString()))));
		list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.Displayname") + (is.getItemMeta().hasDisplayName() 
				? is.getItemMeta().getDisplayName() : SaLE.getPlugin().getEnumTl().getLocalization(is.getType()))));
		list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.Material") + SaLE.getPlugin().getEnumTl().getLocalization(is.getType())));
		ItemMeta im = is.getItemMeta();
		if(im instanceof Damageable)
		{
			Damageable dam = (Damageable) im;
			list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.Damageable") + dam.getDamage()));
		}
		if(im instanceof Repairable)
		{
			Repairable rep = (Repairable) im;
			if(rep.hasRepairCost())
			{
				list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.Repairable") + rep.getRepairCost()));
			}
		}
		if(im.getItemFlags().size() > 0)
		{
			list.add(y.getString("GuiHandler.InfoLore.ItemFlag"));
			for(ItemFlag itf : im.getItemFlags())
			{
				list.add(ChatApi.tl("&7"+plugin.getEnumTl().getLocalization(itf)));
			}
		}		
		if(Material.ENCHANTED_BOOK != is.getType())
		{
			if(im.hasEnchants())
			{
				list.add(y.getString("GuiHandler.InfoLore.Enchantment"));
				for(Entry<Enchantment, Integer> en : is.getEnchantments().entrySet())
				{
					int level = en.getValue()+1;
					list.add(ChatApi.tl("&7"+plugin.getEnumTl().getLocalization(en.getKey())
					+" "+GuiHandler.IntegerToRomanNumeral(level)));
				}
			}
		} else
		{
			if(im instanceof EnchantmentStorageMeta)
			{
				EnchantmentStorageMeta esm = (EnchantmentStorageMeta) im;
				if(esm.hasStoredEnchants())
				{
					list.add(y.getString("GuiHandler.InfoLore.StorageEnchantment"));
					for(Entry<Enchantment, Integer> en : esm.getEnchants().entrySet())
					{
						int level = en.getValue()+1;
						list.add(ChatApi.tl("&7"+plugin.getEnumTl().getLocalization(en.getKey())
						+" "+GuiHandler.IntegerToRomanNumeral(level)));
					}
				}
			}
		}
		if(im instanceof PotionMeta)
		{
			
		}
		if(im instanceof AxolotlBucketMeta)
		{
			AxolotlBucketMeta abm = (AxolotlBucketMeta) im;
			list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.AxolotlBucketMeta") + abm.getVariant().toString()));
		}
		if(im instanceof BannerMeta)
		{
			BannerMeta bm = (BannerMeta) im;
			list.add(ChatApi.tl(y.getString("GuiHandler.InfoLore.BannerMeta")));
			for(Pattern pa : bm.getPatterns())
			{
				list.add(ChatApi.tl("&7"+SaLE.getPlugin().getEnumTl().getLocalization(pa.getColor(), pa.getPattern())));
			}
		}
		if(im instanceof BookMeta)
		{
			
		}
		if(im instanceof LeatherArmorMeta)
		{
			
		}
		if(im instanceof MapMeta)
		{
			
		}
		if(im instanceof SkullMeta)
		{
			
		}
		if(im instanceof SpawnEggMeta)
		{
			
		}
		if(im instanceof SuspiciousStewMeta)
		{
			
		}
		if(im instanceof TropicalFishBucketMeta)
		{
			
		}
		return list;
	}
	
	//thanks https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java
	public static String IntegerToRomanNumeral(int input) 
	{
	    if (input < 1 || input > 3999)
	        return String.valueOf(input);
	    String s = "";
	    while (input >= 1000) {
	        s += "M";
	        input -= 1000;        }
	    while (input >= 900) {
	        s += "CM";
	        input -= 900;
	    }
	    while (input >= 500) {
	        s += "D";
	        input -= 500;
	    }
	    while (input >= 400) {
	        s += "CD";
	        input -= 400;
	    }
	    while (input >= 100) {
	        s += "C";
	        input -= 100;
	    }
	    while (input >= 90) {
	        s += "XC";
	        input -= 90;
	    }
	    while (input >= 50) {
	        s += "L";
	        input -= 50;
	    }
	    while (input >= 40) {
	        s += "XL";
	        input -= 40;
	    }
	    while (input >= 10) {
	        s += "X";
	        input -= 10;
	    }
	    while (input >= 9) {
	        s += "IX";
	        input -= 9;
	    }
	    while (input >= 5) {
	        s += "V";
	        input -= 5;
	    }
	    while (input >= 4) {
	        s += "IV";
	        input -= 4;
	    }
	    while (input >= 1) {
	        s += "I";
	        input -= 1;
	    }    
	    return s;
	}
	
	private static String getStringPlaceHolder(SignShop ssh, Player player, String text,
			Account ac, int dg, boolean useSI, boolean useSy, String ts, String ds, String playername)
	{
		int buyFrac = 0;
		if(ssh.getBuyAmount() != null)
		{
			buyFrac = String.valueOf(ssh.getBuyAmount().doubleValue()).split("\\.")[1].length();
		}
		int sellFrac = 0;
		if(ssh.getSellAmount() != null)
		{
			sellFrac = String.valueOf(ssh.getSellAmount().doubleValue()).split("\\.")[1].length();
		}
		int dbuyFrac = 0;
		if(ssh.getDiscountBuyAmount() != null)
		{
			dbuyFrac = String.valueOf(ssh.getDiscountBuyAmount().doubleValue()).split("\\.")[1].length();
		}
		int dsellFrac = 0;
		if(ssh.getDiscountSellAmount() != null)
		{
			dsellFrac = String.valueOf(ssh.getDiscountSellAmount().doubleValue()).split("\\.")[1].length();
		}
		boolean inDiscount = System.currentTimeMillis() >= ssh.getDiscountStart() && System.currentTimeMillis() < ssh.getDiscountEnd();
		String s = text;
		if(text.contains("%owner%"))
		{
			if(ssh.getOwner() == null)
			{
				s = s.replace("%owner%", "/");
			} else
			{
				s = s.replace("%owner%", Utility.convertUUIDToName(ssh.getOwner().toString()) == null 
						? "/" : Utility.convertUUIDToName(ssh.getOwner().toString()));
			}
		}
		if(text.contains("%isonblacklist%"))
		{
			if(playername != null)
			{
				UUID uuid = Utility.convertNameToUUID(playername);
				s = s.replace("%isonblacklist%", 
						uuid == null ? "/" :
							getBoolean(plugin.getMysqlHandler().exist(MysqlHandler.Type.SHOPACCESSTYPE,
								"`player_uuid` = ? AND `sign_shop_id` = ? AND `listed_type` = ?",
								uuid.toString(), ssh.getId(), ListedType.BLACKLIST.toString()))
						);
			} else
			{
				s = s.replace("%isonblacklist%", "/");
			}
		}
		if(text.contains("%isonwhitelist%"))
		{
			if(playername != null)
			{
				UUID uuid = Utility.convertNameToUUID(playername);
				s = s.replace("%isonwhitelist%", 
						uuid == null ? "/" :
							getBoolean(plugin.getMysqlHandler().exist(MysqlHandler.Type.SHOPACCESSTYPE,
								"`player_uuid` = ? AND `sign_shop_id` = ? AND `listed_type` = ?",
								uuid.toString(), ssh.getId(), ListedType.WHITELIST.toString()))
						);
			} else
			{
				s = s.replace("%isonwhitelist%", "/");
			}
		}
		if(text.contains("%ismember%"))
		{
			if(playername != null)
			{
				UUID uuid = Utility.convertNameToUUID(playername);
				s = s.replace("%ismember%", 
						uuid == null ? "/" :
							getBoolean(plugin.getMysqlHandler().exist(MysqlHandler.Type.SHOPACCESSTYPE,
								"`player_uuid` = ? AND `sign_shop_id` = ? AND `listed_type` = ?",
								uuid.toString(), ssh.getId(), ListedType.MEMBER.toString()))
						);
			} else
			{
				s = s.replace("%ismember%", "/");
			}
		}
		if(text.contains("%isoncustom%"))
		{
			if(playername != null)
			{
				UUID uuid = Utility.convertNameToUUID(playername);
				s = s.replace("%isoncustom%", 
						uuid == null ? "/" :
							getBoolean(plugin.getMysqlHandler().exist(MysqlHandler.Type.SHOPACCESSTYPE,
								"`player_uuid` = ? AND `sign_shop_id` = ? AND `listed_type` = ?",
								uuid.toString(), ssh.getId(), ListedType.CUSTOM.toString()))
						);
			} else
			{
				s = s.replace("%isoncustom%", "/");
			}
		}
		if(text.contains("%id%"))
		{
			s = s.replace("%id%", String.valueOf(ssh.getId()));
		}
		if(text.contains("%subscribe%"))
		{
			s = s.replace("%subscribe%", getBoolean(plugin.getMysqlHandler().exist(MysqlHandler.Type.SUBSCRIBEDSHOP,
					"`player_uuid` = ? AND `sign_shop_id` = ?", player.getUniqueId().toString(), ssh.getId())));
		}
		if(text.contains("%numtext%"))
		{
			s = s.replace("%numtext%", "'"+ssh.getNumText()+"'");
		}
		if(text.contains("%player%"))
		{
			s = s.replace("%player%", player.getName());
		}
		if(text.contains("%displayname%"))
		{
			s = s.replace("%displayname%", ssh.getDisplayName() == null ? "/" : ssh.getDisplayName());
		}
		if(text.contains("%signshopname%"))
		{
			s = s.replace("%signshopname%", ssh.getSignShopName());
		}
		if(text.contains("%server%"))
		{
			s = s.replace("%server%", ssh.getServer());
		}
		if(text.contains("%world%"))
		{
			s = s.replace("%world%", ssh.getWorld());
		}
		if(text.contains("%x%"))
		{
			s = s.replace("%x%", String.valueOf(ssh.getX()));
		}
		if(text.contains("%y%"))
		{
			s = s.replace("%y%", String.valueOf(ssh.getY()));
		}
		if(text.contains("%z%"))
		{
			s = s.replace("%z%", String.valueOf(ssh.getZ()));
		}
		if(text.contains("%accountid%"))
		{
			s = s.replace("%accountid%", String.valueOf(ssh.getAccountId()));
		}
		if(text.contains("%accountname%"))
		{
			s = s.replace("%accountname%", (ac == null || ac.getID() == 0) ? "/" : ac.getAccountName());
		}
		if(text.contains("%storageid%"))
		{
			s = s.replace("%storageid%", ssh.getStorageID() == 0 ? "/" : String.valueOf(ssh.getStorageID()));
		}
		if(text.contains("%creationdate%"))
		{
			s = s.replace("%creationdate%", TimeHandler.getDateTime(ssh.getCreationDateTime()));
		}
		if(text.contains("%discountstart%"))
		{
			s = s.replace("%discountstart%", ssh.getDiscountStart() == 0 ? "/" : TimeHandler.getDateTime(ssh.getDiscountStart()));
		}
		if(text.contains("%discountend%"))
		{
			s = s.replace("%discountend%", ssh.getDiscountEnd() == 0 ? "/" :TimeHandler.getDateTime(ssh.getDiscountEnd()));
		}
		if(text.contains("%possiblebuy%"))
		{
			s = s.replace("%possiblebuy%", ssh.getPossibleBuy() < 0 ? "/" : String.valueOf(ssh.getPossibleBuy()));
		}
		if(text.contains("%possiblesell%"))
		{
			s = s.replace("%possiblesell%", ssh.getPossibleSell() < 0 ? "/" : String.valueOf(ssh.getPossibleSell()));
		}
		if(text.contains("%discountpossiblebuy%"))
		{
			s = s.replace("%discountpossiblebuy%", ssh.getDiscountPossibleBuy() < 0 ? "/" : String.valueOf(ssh.getDiscountPossibleBuy()));
		}
		if(text.contains("%discountpossiblesell%"))
		{
			s = s.replace("%discountpossiblesell%", ssh.getDiscountPossibleSell() < 0 ? "/" : String.valueOf(ssh.getDiscountPossibleSell()));
		}
		if(text.contains("%itemstoragecurrent%"))
		{
			s = s.replace("%itemstoragecurrent%", String.valueOf(ssh.getItemStorageCurrent()));
		}
		if(text.contains("%itemstoragetotal%"))
		{
			s = s.replace("%itemstoragetotal%", String.valueOf(ssh.getItemStorageTotal()));
		}
		if(text.contains("%buytoggle%"))
		{
			s = s.replace("%buytoggle%", getBoolean(ssh.canBuy()));
		}
		if(text.contains("%selltoggle%"))
		{
			s = s.replace("%selltoggle%", getBoolean(ssh.canSell()));
		}
		if(text.contains("%unlimitedbuy%"))
		{
			s = s.replace("%unlimitedbuy%", getBoolean(ssh.isUnlimitedBuy()));
		}
		if(text.contains("%unlimitedsell%"))
		{
			s = s.replace("%unlimitedsell%", getBoolean(ssh.isUnlimitedSell()));
		}
		if(text.contains("%glow%"))
		{
			s = s.replace("%glow%", getBoolean(ssh.isSignGlowing()));
		}
		if(text.contains("%listtype%"))
		{
			s = s.replace("%listtype%", 
					plugin.getYamlHandler().getLang().getString("AdminstrationFunctionHandler.ListedType."+ssh.getListedType().toString()));
		}
		if(text.contains("%hologram%"))
		{
			s = s.replace("%hologram%", getBoolean(ssh.isItemHologram()));
		}
		if(text.contains("%buyraw1%"))
		{
			s = s.replace("%buyraw1%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getBuyAmount(), ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%sellraw1%"))
		{
			s = s.replace("%sellraw1%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getSellAmount(), ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));		
		}
		if(text.contains("%buy1%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy1%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount(), ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy1%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount(), ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
			}
		}
		if(text.contains("%buy8%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy8%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*8, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy8%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*8, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
			}
		}
		if(text.contains("%buy16%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy16%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*16, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy16%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*16, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
			}
		}
		if(text.contains("%buy32%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy32%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*32, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy32%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*32, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
			}
		}
		if(text.contains("%buy64%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy64%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*64, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy64%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*64, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			}
		}
		if(text.contains("%buy576%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy576%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*576, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy576%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*576, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%buy1728%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy1728%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*1728, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy1728%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*1728, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%buy2304%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy2304%", (ssh.getBuyAmount() == null || ssh.getBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*2304, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%buy2304%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*2304, ac.getCurrency(), dg, buyFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell1%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell1%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount(), ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell1%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount(), ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell8%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell8%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*8, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell8%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*8, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell16%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell16%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*16, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell16%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*16, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell32%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell32%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*32, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell32%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*32, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell64%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell64%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*64, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell64%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*64, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell576%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell576%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*576, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell576%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*576, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell1728%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell1728%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*1728, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell1728%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*1728, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%sell2304%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell2304%", (ssh.getSellAmount() == null || ssh.getSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*2304, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			} else
			{
				s = s.replace("%sell2304%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*2304, ac.getCurrency(), dg, sellFrac, useSI, useSy, ts, ds));
			}			
		}
		if(text.contains("%discountbuy1%"))
		{
			s = s.replace("%discountbuy1%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount(), ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy8%"))
		{
			s = s.replace("%discountbuy8%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*8, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy16%"))
		{
			s = s.replace("%discountbuy16%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*16, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy32%"))
		{
			s = s.replace("%discountbuy32%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*32, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy64%"))
		{
			s = s.replace("%discountbuy64%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*64, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy576%"))
		{
			s = s.replace("%discountbuy576%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*576, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy1728%"))
		{
			s = s.replace("%discountbuy1728%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*1728, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountbuy2304%"))
		{
			s = s.replace("%discountbuy2304%", (ssh.getDiscountBuyAmount() == null || ssh.getDiscountBuyAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*2304, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell1%"))
		{
			s = s.replace("%discountsell1%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount(), ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell8%"))
		{
			s = s.replace("%discountsell8%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*8, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell16%"))
		{
			s = s.replace("%discountsell16%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*16, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell32%"))
		{
			s = s.replace("%discountsell32%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*32, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell64%"))
		{
			s = s.replace("%discountsell64%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*64, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell576%"))
		{
			s = s.replace("%discountsell576%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*576, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell1728%"))
		{
			s = s.replace("%discountsell1728%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*1728, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		if(text.contains("%discountsell2304%"))
		{
			s = s.replace("%discountsell2304%", (ssh.getDiscountSellAmount() == null || ssh.getDiscountSellAmount() < 0.0) ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*2304, ac.getCurrency(), dg, dsellFrac, useSI, useSy, ts, ds));
		}
		return ChatApi.tl(s);
	}
	
	private static String getBoolean(boolean boo)
	{
		return boo ? plugin.getYamlHandler().getLang().getString("IsTrue") : plugin.getYamlHandler().getLang().getString("IsFalse");
	}
	
	private static ClickFunction[] getClickFunction(YamlConfiguration y, String pathBase)
	{
		ArrayList<ClickFunction> ctar = new ArrayList<>();
		List<ClickType> list = new ArrayList<ClickType>(EnumSet.allOf(ClickType.class));
		for(ClickType ct : list)
		{
			if(y.get(pathBase+".ClickFunction."+ct.toString()) == null)
			{
				continue;
			}
			ClickFunctionType cft = null;
			try
			{
				cft = ClickFunctionType.valueOf(y.getString(pathBase+".ClickFunction."+ct.toString()));
			} catch(Exception e)
			{
				continue;
			}
			ctar.add(new ClickFunction(ct, cft));
		}
		return ctar.toArray(new ClickFunction[ctar.size()]);
	}
}