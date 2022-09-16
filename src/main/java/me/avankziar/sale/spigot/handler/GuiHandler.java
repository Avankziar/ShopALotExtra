package main.java.me.avankziar.sale.spigot.handler;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
import main.java.me.avankziar.sale.spigot.objects.SignShop;
import main.java.me.avankziar.sale.spigot.permission.BonusMalusPermission;
import main.java.me.avankziar.sale.spigot.permission.Bypass;

public class GuiHandler
{
	private static SaLE plugin = SaLE.getPlugin();
	public static String SIGNSHOP_ID = "signshop_id";
	
	public static void openAdministration(SignShop ssh, Player player, SettingsLevel settingsLevel, boolean closeInv)
	{
		GuiType gt = GuiType.ADMINISTRATION;
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, ssh.getSignShopName(), 
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
		GuiType gt = GuiType.ADMINISTRATION;
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, "Shop "+ssh.getSignShopName(), settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openShop(SignShop ssh, Player player, SettingsLevel settingsLevel, Inventory inv, boolean closeInv)
	{
		GuiType gt = GuiType.ADMINISTRATION;
		GUIApi gui = new GUIApi(plugin.pluginName, inv, gt.toString(), settingsLevel);
		SignShop ssh2 = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", ssh.getId());
		openGui(ssh2, player, gt, gui, settingsLevel, closeInv);
	}
	
	public static void openNumpad(SignShop ssh, Player player, String type, SettingsLevel settingsLevel, boolean closeInv)
	{
		GuiType gt = null;
		switch(type)
		{
		default: break;
		case "ACCOUNT": gt = GuiType.NUMPAD_ACCOUNT; break;
		case "ASH": gt = GuiType.NUMPAD_ASH; break;
		case "BUY": gt = GuiType.NUMPAD_BUY; break;
		case "SELL": gt = GuiType.NUMPAD_SELL; break;
		case "POSSIBLEBUY": gt = GuiType.NUMPAD_POSSIBLE_BUY; break;
		case "POSSIBLESELL": gt = GuiType.NUMPAD_POSSIBLE_SELL; break;
		case "DISCOUNT_START": gt = GuiType.NUMPAD_DISCOUNT_START; break;
		case "DISCOUNT_END": gt = GuiType.NUMPAD_DISCOUNT_END; break;
		case "DISCOUNTBUY": gt = GuiType.NUMPAD_DISCOUNT_BUY; break;
		case "DISCOUNTSELL": gt = GuiType.NUMPAD_DISCOUNT_SELL; break;
		case "DISCOUNTPOSSIBLEBUY": gt = GuiType.NUMPAD_DISCOUNT_POSSIBLE_BUY; break;
		case "DISCOUNTPOSSIBLESELL": gt = GuiType.NUMPAD_DISCOUNT_POSSIBLE_SELL; break;
		}
		GUIApi gui = new GUIApi(plugin.pluginName, gt.toString(), null, 6, ssh.getSignShopName()+" Numpad", settingsLevel);
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
			if(y.get(i+".IsInfoItem") == null)
			{
				if(y.get(i+".Material") == null)
				{
					continue;
				}
			}
			SettingsLevel itemSL = SettingsLevel.valueOf(y.getString(i+".SettingsLevel"));
			if(settingsLevel.getOrdinal() < itemSL.getOrdinal())
			{
				if(SettingsLevel.NOLEVEL != itemSL)
				{
					continue;
				}
			}
			if(y.get(i+".Permission") != null)
			{
				if(!BonusMalusPermission.hasPermission(player, Bypass.get(Bypass.Permission.SHOP_GUI_BYPASS)))
				{
					continue;
				}
			}
			if(y.get(i+".canBuy") != null)
			{
				if(y.getBoolean(i+".CanBuy"))
				{
					if(!ssh.canBuy())
					{
						continue;
					}
				}
			}
			if(y.get(i+".canSell") != null)
			{
				if(y.getBoolean(i+".CanSell"))
				{
					if(!ssh.canSell())
					{
						continue;
					}
				}
			}
			if(y.get(i+".UseASH") != null)
			{
				if(y.getBoolean(i+".UseASH"))
				{
					//TODO ASH IFH abfrage
					continue;
				}
			}
			if(y.get(i+".IsInfoItem") != null && y.getBoolean(i+".IsInfoItem"))
			{
				ItemStack is = ssh.getItemStack();
				gui.add(i, is, itemSL, true, null, getClickFunction(y, String.valueOf(i)));
				continue;
			}
			Material mat = null;
			if(y.get(i+".Material."+settingsLevel.toString()) != null)
			{
				mat = Material.valueOf(y.getString(i+".Material."+settingsLevel.toString()));
			} else
			{
				try
				{
					mat = Material.valueOf(y.getString(i+".Material"));
				} catch(Exception e)
				{
					continue;
				}
			}
			int amount = 1;
			if(y.get(i+".Amount") != null)
			{
				amount = y.getInt(i+".Amount");
			}
			List<String> lore = null;
			String il = null;
			if(y.get(i+".Lore."+settingsLevel.toString()) != null)
			{
				lore = y.getStringList(i+".Lore."+settingsLevel.toString());
			} else
			{
				if(y.get(i+".Lore") != null)
				{
					lore = y.getStringList(i+".Lore");
					if(y.get(i+".InfoLore") != null)
					{
						il = y.getString(i+".InfoLore");
					}
				}
			}
			lore = getLorePlaceHolder(ssh, player, lore, ac, dg, useSI, useSy, ts, ds);
			if(il != null)
			{
				lore.add(il);
				for(Entry<Enchantment, Integer> en : ssh.getItemStack().getEnchantments().entrySet())
				{
					String name = EnchantmentHandler.getEnchantment(en.getKey());
					int level = en.getValue()+1;
					lore.add("&b"+name+": "+level);
				}
			}
			String displayname = y.get(i+".Displayname") != null ? y.getString(i+".Displayname") : MaterialHandler.getMaterial(mat);
			displayname = getStringPlaceHolder(ssh, player, displayname, ac, dg, useSI, useSy, ts, ds);
			ItemStack is = new ItemStack(mat, amount);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(displayname);
			if(lore != null)
			{
				im.setLore(lore);
			}
			is.setItemMeta(im);
			LinkedHashMap<String, Entry<GUIApi.Type, Object>> map = new LinkedHashMap<>();
			map.put(SIGNSHOP_ID, new AbstractMap.SimpleEntry<GUIApi.Type, Object>(GUIApi.Type.INTEGER, ssh.getId()));
			gui.add(i, is, itemSL, true, map, getClickFunction(y, String.valueOf(i)));
		}
		if(closeInv)
		{
			player.closeInventory();
		}
		gui.open(player);
	}
	
	private static List<String> getLorePlaceHolder(SignShop ssh, Player player, List<String> lore,
			Account ac, int dg, boolean useSI, boolean useSy, String ts, String ds)
	{
		List<String> list = new ArrayList<>();
		for(String s : lore)
		{
			list.add(getStringPlaceHolder(ssh, player, s, ac, dg, useSI, useSy, ts, ds));
		}
		return list;
	}
	
	private static String getStringPlaceHolder(SignShop ssh, Player player, String text,
			Account ac, int dg, boolean useSI, boolean useSy, String ts, String ds)
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
			s = s.replace("%owner%", Utility.convertUUIDToName(ssh.getOwner().toString()) == null 
							? "/" : Utility.convertUUIDToName(ssh.getOwner().toString()));
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
			s = s.replace("%numtext%", ssh.getNumText());
		}
		if(text.contains("%player%"))
		{
			s = s.replace("%player%", player.getName());
		}
		if(text.contains("%displayname%"))
		{
			s = s.replace("%displayname%", ssh.getDisplayName());
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
			s = s.replace("%storageid%", String.valueOf(ssh.getStorageID()));
		}
		if(text.contains("%creationdate%"))
		{
			s = s.replace("%creationdate%", TimeHandler.getDateTime(ssh.getCreationDateTime()));
		}
		if(text.contains("%discountstart%"))
		{
			s = s.replace("%discountstart%", TimeHandler.getDateTime(ssh.getDiscountStart()));
		}
		if(text.contains("%discountend%"))
		{
			s = s.replace("%discountend%", TimeHandler.getDateTime(ssh.getDiscountEnd()));
		}
		if(text.contains("%possiblebuy%"))
		{
			s = s.replace("%possiblebuy%", String.valueOf(ssh.getPossibleBuy()));
		}
		if(text.contains("%possiblesell%"))
		{
			s = s.replace("%possiblesell%", String.valueOf(ssh.getPossibleSell()));
		}
		if(text.contains("%discountpossiblebuy%"))
		{
			s = s.replace("%discountpossiblebuy%", String.valueOf(ssh.getDiscountPossibleBuy()));
		}
		if(text.contains("%discountpossiblesell%"))
		{
			s = s.replace("%discountpossiblesell%", String.valueOf(ssh.getDiscountPossibleSell()));
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
		if(text.contains("%buyraw1%"))
		{
			s = s.replace("%buyraw1%", ssh.getBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getBuyAmount(), ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%sellraw1%"))
		{
			s = s.replace("%sellraw1%", ssh.getSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getSellAmount(), ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));		
		}
		if(text.contains("%buy1%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy1%", ssh.getBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount(), ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%buy1%", ssh.getDiscountBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount(), ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
			}
		}
		if(text.contains("%buy16%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy16%", ssh.getBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*16, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%buy16%", ssh.getDiscountBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*16, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
			}
		}
		if(text.contains("%buy32%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy32%", ssh.getBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*32, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%buy32%", ssh.getDiscountBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*32, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
			}
		}
		if(text.contains("%buy64%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy64%", ssh.getBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*64, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%buy64%", ssh.getDiscountBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*64, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			}
		}
		if(text.contains("%buy576%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy576%", ssh.getBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*576, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%buy576%", ssh.getDiscountBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*576, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%buy2304%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%buy2304%", ssh.getBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getBuyAmount()*2304, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%buy2304%", ssh.getDiscountBuyAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*2304, ac.getCurrency(), dg, buyFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%sell1%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell1%", ssh.getSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount(), ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%sell1%", ssh.getDiscountSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount(), ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%sell16%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell16%", ssh.getSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*16, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%sell16%", ssh.getDiscountSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*16, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%sell32%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell32%", ssh.getSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*32, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%sell32%", ssh.getDiscountSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*32, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%sell64%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell64%", ssh.getSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*64, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%sell64%", ssh.getDiscountSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*64, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%sell576%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell576%", ssh.getSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*576, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%sell576%", ssh.getDiscountSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*576, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%sell2304%"))
		{
			if(!inDiscount)
			{
				s = s.replace("%sell2304%", ssh.getSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getSellAmount()*2304, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			} else
			{
				s = s.replace("%sell2304%", ssh.getDiscountSellAmount() == null ? "/" : 
					plugin.getIFHEco().format(ssh.getDiscountSellAmount()*2304, ac.getCurrency(), dg, sellFrac, useSI, useSy, text, text));
			}			
		}
		if(text.contains("%discountbuy1%"))
		{
			s = s.replace("%discountbuy1%", ssh.getDiscountBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount(), ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountbuy16%"))
		{
			s = s.replace("%discountbuy16%", ssh.getDiscountBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*16, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountbuy32%"))
		{
			s = s.replace("%discountbuy32%", ssh.getDiscountBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*32, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountbuy64%"))
		{
			s = s.replace("%discountbuy64%", ssh.getDiscountBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*64, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountbuy576%"))
		{
			s = s.replace("%discountbuy576%", ssh.getDiscountBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*576, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountbuy2304%"))
		{
			s = s.replace("%discountbuy2304%", ssh.getDiscountBuyAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountBuyAmount()*2304, ac.getCurrency(), dg, dbuyFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountsell1%"))
		{
			s = s.replace("%discountsell1%", ssh.getDiscountSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount(), ac.getCurrency(), dg, dsellFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountsell16%"))
		{
			s = s.replace("%discountsell16%", ssh.getDiscountSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*16, ac.getCurrency(), dg, dsellFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountsell32%"))
		{
			s = s.replace("%discountsell32%", ssh.getDiscountSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*32, ac.getCurrency(), dg, dsellFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountsell64%"))
		{
			s = s.replace("%discountsell64%", ssh.getDiscountSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*64, ac.getCurrency(), dg, dsellFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountsell576%"))
		{
			s = s.replace("%discountsell576%", ssh.getDiscountSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*576, ac.getCurrency(), dg, dsellFrac, useSI, useSy, text, text));
		}
		if(text.contains("%discountsell2304%"))
		{
			s = s.replace("%discountsell2304%", ssh.getDiscountSellAmount() == null ? "/" : 
				plugin.getIFHEco().format(ssh.getDiscountSellAmount()*2304, ac.getCurrency(), dg, dsellFrac, useSI, useSy, text, text));
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