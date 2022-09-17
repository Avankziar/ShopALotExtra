package main.java.me.avankziar.sale.spigot.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import main.java.me.avankziar.sale.spigot.database.Language.ISO639_2B;
import main.java.me.avankziar.sale.spigot.gui.events.ClickType;
import main.java.me.avankziar.sale.spigot.gui.events.SettingsLevel;
import main.java.me.avankziar.sale.spigot.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.permission.Bypass;

public class YamlManager
{
	private ISO639_2B languageType = ISO639_2B.GER;
	//The default language of your plugin. Mine is german.
	private ISO639_2B defaultLanguageType = ISO639_2B.GER;
	
	//Per Flatfile a linkedhashmap.
	private static LinkedHashMap<String, Language> configSpigotKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> commandsKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> languageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> bmlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> matlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> enchlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<GuiType, LinkedHashMap<String, Language>> guiKeys = new LinkedHashMap<>();
	
	public YamlManager()
	{
		initConfig();
		initCommands();
		initLanguage();
		initBonusMalusLanguage();
		initMaterialLanguage();
		initGuiAdministration();
		initGuiNumpad();
		initGuiShop();
	}
	
	public ISO639_2B getLanguageType()
	{
		return languageType;
	}

	public void setLanguageType(ISO639_2B languageType)
	{
		this.languageType = languageType;
	}
	
	public ISO639_2B getDefaultLanguageType()
	{
		return defaultLanguageType;
	}
	
	public LinkedHashMap<String, Language> getConfigSpigotKey()
	{
		return configSpigotKeys;
	}
	
	public LinkedHashMap<String, Language> getCommandsKey()
	{
		return commandsKeys;
	}
	
	public LinkedHashMap<String, Language> getLanguageKey()
	{
		return languageKeys;
	}
	
	public LinkedHashMap<String, Language> getBonusMalusLanguageKey()
	{
		return bmlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getMaterialLanguageKey()
	{
		return matlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getEnchantmentLanguageKey()
	{
		return enchlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getGuiKey(GuiType guiType)
	{
		return guiKeys.get(guiType);
	}
	
	public LinkedHashMap<GuiType, LinkedHashMap<String, Language>> getGuiKey()
	{
		return guiKeys;
	}
	
	/*
	 * The main methode to set all paths in the yamls.
	 */
	public void setFileInput(YamlConfiguration yml, LinkedHashMap<String, Language> keyMap, String key, ISO639_2B languageType)
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(keyMap.get(key).languageValues.get(languageType).length == 1)
		{
			if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
			{
				yml.set(key, ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", ""));
			} else
			{
				yml.set(key, keyMap.get(key).languageValues.get(languageType)[0]);
			}
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add(((String) o).replace("\r\n", ""));
					} else
					{
						stringList.add(o.toString().replace("\r\n", ""));
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	public void initConfig() //INFO:Config
	{
		configSpigotKeys.put("useIFHAdministration"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("IFHAdministrationPath"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"sale"}));
		/*
		 * The normale single path. In the config make it no sense to add other language as English
		 * But the ISO639_2B is here the default language from this plugin!
		 */
		configSpigotKeys.put("ServerName"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"hub"}));
		
		configSpigotKeys.put("Mysql.Status"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configSpigotKeys.put("Mysql.Host"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"127.0.0.1"}));
		configSpigotKeys.put("Mysql.Port"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				3306}));
		configSpigotKeys.put("Mysql.DatabaseName"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"mydatabase"}));
		configSpigotKeys.put("Mysql.SSLEnabled"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configSpigotKeys.put("Mysql.AutoReconnect"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("Mysql.VerifyServerCertificate"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configSpigotKeys.put("Mysql.User"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"admin"}));
		configSpigotKeys.put("Mysql.Password"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"not_0123456789"}));
		
		configSpigotKeys.put("Enable.SignShop"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("Enable.Auction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("EnableMechanic.BonusMalus"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		
		configSpigotKeys.put("CleanUpTask.Player.Active"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("CleanUpTask.Player.DeleteAfterXDaysOffline"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				365}));
		configSpigotKeys.put("CleanUpTask.ShopLog.Active"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("CleanUpTask.ShopLog.DeleteAfterXDays"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				365}));
		configSpigotKeys.put("CleanUpTask.ShopDailyLog.Active"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("CleanUpTask.ShopDailyLog.DeleteAfterXDays"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				365}));
		configSpigotKeys.put("CleanUpTask.ShoppingLog.Active"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("CleanUpTask.ShoppingLog.DeleteAfterXDays"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				365}));
		configSpigotKeys.put("CleanUpTask.ShoppingDailyLog.Active"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("CleanUpTask.ShoppingDailyLog.DeleteAfterXDays"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				365}));
		
		configSpigotKeys.put("Mechanic.CountPerm"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"ADDUP"}));
		configSpigotKeys.put("SignShop.SignInitializationLine"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"[SaleShop]"}));
		configSpigotKeys.put("SignShop.DefaultStartItemStorage"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				3456}));
		configSpigotKeys.put("SignShop.CostToAdd1Storage"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"dollar;1000.0",
				"token;99.0"}));
		configSpigotKeys.put("SignShop.DiscountTimePattern"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"yyyy.MM.dd.HH:mm:ss"}));
		configSpigotKeys.put("SignShop.Sign.Line4CalculateInStack"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("SignShop.Tax.BuyInPercent"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				1.0}));
		configSpigotKeys.put("SignShop.Tax.SellInPercent"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				1.1}));
	}
	
	//INFO:Commands
	public void initCommands()
	{
		comBypass();
		commandsInput("base", "base", "perm.command.perm", 
				"/base [pagenumber]", "/base ", false,
				"&c/base &f| Infoseite für alle Befehle.",
				"&c/base &f| Info page for all commands.",
				"&bBefehlsrecht für &f/base",
				"&bCommandright for &f/base",
				"&eBasisbefehl für das BaseTemplate Plugin.",
				"&eGroundcommand for the BaseTemplate Plugin.");
		String basePermission = "perm.base.";
		argumentInput("base_argument", "argument", basePermission,
				"/base argument <id>", "/econ deletelog ", false,
				"&c/base argument &f| Ein Subbefehl",
				"&c/base argument &f| A Subcommand.",
				"&bBefehlsrecht für &f/base argument",
				"&bCommandright for &f/base argument",
				"&eBasisbefehl für das BaseTemplate Plugin.",
				"&eGroundcommand for the BaseTemplate Plugin.");
	}
	
	private void comBypass() //INFO:ComBypass
	{
		List<Bypass.Permission> list = new ArrayList<Bypass.Permission>(EnumSet.allOf(Bypass.Permission.class));
		for(Bypass.Permission ept : list)
		{
			commandsKeys.put("Bypass."+ept.toString()
					, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
					"sale."+ept.toString().toLowerCase().replace("_", ".")}));
		}
		
		List<Bypass.CountPermission> list2 = new ArrayList<Bypass.CountPermission>(EnumSet.allOf(Bypass.CountPermission.class));
		for(Bypass.CountPermission ept : list2)
		{
			commandsKeys.put("Count."+ept.toString()
					, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
					"sale."+ept.toString().toLowerCase().replace("_", ".")}));
		}
	}
	
	private void commandsInput(String path, String name, String basePermission, 
			String suggestion, String commandString, boolean putUpCmdPermToBonusMalusSystem,
			String helpInfoGerman, String helpInfoEnglish,
			String dnGerman, String dnEnglish,
			String exGerman, String exEnglish)
	{
		commandsKeys.put(path+".Name"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				name}));
		commandsKeys.put(path+".Permission"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				basePermission}));
		commandsKeys.put(path+".Suggestion"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".PutUpCommandPermToBonusMalusSystem"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				putUpCmdPermToBonusMalusSystem}));
		commandsKeys.put(path+".CommandString"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
		commandsKeys.put(path+".Displayname"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				dnGerman,
				dnEnglish}));
		commandsKeys.put(path+".Explanation"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				exGerman,
				exEnglish}));
	}
	
	private void argumentInput(String path, String argument, String basePermission, 
			String suggestion, String commandString, boolean putUpCmdPermToBonusMalusSystem,
			String helpInfoGerman, String helpInfoEnglish,
			String dnGerman, String dnEnglish,
			String exGerman, String exEnglish)
	{
		commandsKeys.put(path+".Argument"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				argument}));
		commandsKeys.put(path+".Permission"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				basePermission+"."+argument}));
		commandsKeys.put(path+".Suggestion"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".PutUpCommandPermToBonusMalusSystem"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				putUpCmdPermToBonusMalusSystem}));
		commandsKeys.put(path+".CommandString"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
		commandsKeys.put(path+".Displayname"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				dnGerman,
				dnEnglish}));
		commandsKeys.put(path+".Explanation"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				exGerman,
				exEnglish}));
	}
	
	public void initLanguage() //INFO:Languages
	{
		languageKeys.put("InputIsWrong",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDeine Eingabe ist fehlerhaft! Klicke hier auf den Text, um weitere Infos zu bekommen!",
						"&cYour input is incorrect! Click here on the text to get more information!"}));
		languageKeys.put("NoPermission",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast dafür keine Rechte!",
						"&cYou dont not have the rights!"}));
		languageKeys.put("NoPlayerExist",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Spieler existiert nicht!",
						"&cThe player does not exist!"}));
		languageKeys.put("NoNumber",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas Argument &f%value% &cmuss eine ganze Zahl sein.",
						"&cThe argument &f%value% &must be an integer."}));
		languageKeys.put("NoDouble",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas Argument &f%value% &cmuss eine Gleitpunktzahl sein!",
						"&cThe argument &f%value% &must be a floating point number!"}));
		languageKeys.put("IsNegativ",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas Argument &f%value% &cmuss eine positive Zahl sein!",
						"&cThe argument &f%value% &must be a positive number!"}));
		languageKeys.put("AccountNotExist",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas Konto &f%value% &existiert nicht!",
						"&cThe account &f%value% &cdont exist!"}));
		languageKeys.put("NoWithdrawRights",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast für das angegeben Konto keine Abheberechte!",
						"&cYou have no withdrawal rights for the specified account!"}));
		languageKeys.put("GeneralHover",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKlick mich!",
						"&eClick me!"}));
		languageKeys.put("Headline", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e=====&7[&6SaLE&7]&e=====",
						"&e=====&7[&6SaLE&7]&e====="}));
		languageKeys.put("Next", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e&nnächste Seite &e==>",
						"&e&nnext page &e==>"}));
		languageKeys.put("Past", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&e<== &nvorherige Seite",
						"&e<== &nprevious page"}));
		languageKeys.put("IsTrue", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&a✔",
						"&a✔"}));
		languageKeys.put("IsFalse", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&c✖",
						"&c✖"}));
		languageKeys.put("NotOwner"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu kannst diesen Wert nicht ändern, da dir der Shop nicht gehört.",
						"&cYou can't change this value because you don't own the store."}));
		languageKeys.put("Economy.AddStorage.Category", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"Lagerraumerweiterung",
						"Stockroomextension"}));
		languageKeys.put("Economy.AddStorage.Comment", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eEine Lagerraumerweiterung von &f%amount% &eItems des Shops &f%id%&e.",
						"&eA stockroom extension of &f%amount% &eItems of the shop &f%id%&e."}));
		languageKeys.put("Economy.Buy.Category", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"Shopverkauf",
						"Shopsale"}));
		languageKeys.put("Economy.Buy.Comment", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBeim Shop &f%shop% &bwurden &r%item% &rx &e%amount% &bgekauft.",
						"&bAt the shop &f%shop% &bwere &r%item% &rx &e%amount% &bpurchased."}));
		languageKeys.put("Economy.Sell.Category", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"Shopankauf",
						"Shoppurchase"}));
		languageKeys.put("Economy.Sell.Comment", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bDer Shop &f%shop% &bhat &r%item% &rx &e%amount% &bankauft.",
						"&bThe shop &f%shop% &bhad &r%item% &rx &e%amount% &bpurchased."}));
		languageKeys.put("PlayerInteractListener.ShopItemIsNull", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu kannst das Administrationsgui nicht aufrufen, da der Shop noch kein Item gesetzt hat!",
						"&cYou can not call the administration gui, because the store has not set an item yet!"}));
		languageKeys.put("SignChangeListener.MaterialIsAir", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"Leer",
						"Empty"}));
		languageKeys.put("SignChangeListener.AlreadyHaveMaximalSignShop", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast schon die maximale Anzahl von SignShops erstellt. Lösche zuerst SignShops bevor neue erstellt werden! Aktuelle &f%actual% &cvon %max%",
						"&cYou have already created the maximum number of SignShops. First delete SignShops before creating new ones! Current &f%actual% &cof %max%"}));
		languageKeys.put("SignChangeListener.ShopCreated", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast den Shop &f%name% &eerstellt! &bStelle mit einem &fItem in der Hand &bund einem &fRechtsklick &bauf das Shopschild das Item ein, "
						+ "danach kommst du durch einen &fLinksklick &bins Einstellungsmenu um dort den Shop nach Belieben einzustellen.",
						"&eYou have created the store &f%name%! &bSet the item with an &f item in your hand &band a &fright click &bon the store sign, "
						+ "then you come by a &fleft click &bin the settings menu to set the store as desired."}));
		languageKeys.put("SignHandler.Line1", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"VK: %amount%",
						"B: %amount%"}));
		languageKeys.put("SignHandler.Line2", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"AK: %amount%",
						"S: %amount%"}));
		languageKeys.put("SignHandler.PercentColor.100AndAbove", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&9"}));
		languageKeys.put("SignHandler.PercentColor.Between100And75", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&b"}));
		languageKeys.put("SignHandler.PercentColor.Between75And50", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&c"}));
		languageKeys.put("SignHandler.PercentColor.Between50And25", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&e"}));
		languageKeys.put("SignHandler.PercentColor.Between25And10", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&#FF8800"}));
		languageKeys.put("SignHandler.PercentColor.Between10And0", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&c"}));
		languageKeys.put("SignHandler.PercentColor.0AndLess", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&4"}));
		languageKeys.put("SignHandler.AttachedNewItemStackToShop", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&aDu hast das Item &r%name% &azum Shop &f%signshop% &ahinzugefügt!",
						"&aYou have added the item &r%name% &ato the store &f%signshop%&a!"}));
		languageKeys.put("SignHandler.ItemsAddedToShop", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast &f%amount% &eItems dem Shop hinzugefügt. Zz.: &r%now%",
						"&eYou have added &f%amount% &eitems to the store. Attn: &r%now%"}));
		languageKeys.put("SignHandler.NoItemsIsSetUp", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat noch kein Items eingesetzt, somit kann kein Item auf die Hand ausgegeben werden!",
						"&cThe store has not yet deployed an item, so no item can be spent on the hand!"}));
		languageKeys.put("SignHandler.ItemsRemovedToShop", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast &f%amount% &eItems aus dem Shop entfernt. Zz.: &r%now%",
						"&eYou have removed &f%amount% &eItems from the store. Attn: &r%now%"}));
		languageKeys.put("AdminstrationFunctionHandler.AddStorage.YouDontHaveAccountToWithdraw"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast keinen Account um die Kosten abziehen zu können.",
						"&cYou do not have an account to be able to deduct the costs."}));
		languageKeys.put("AdminstrationFunctionHandler.AddStorage.Transaction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&6Es wurden von dem Konto &e%fromaccount% &f%formatwithdraw% &6abgezogen und an &e%toaccount% &6überwiesen.",
						"&bKategorie: &f%category% &f| &bNotiz: &f%comment%",
						"&6It was deducted from the account &e%fromaccount% &f%formatwithdraw% &6and transferred to &e%toaccount%&6.",
						"&bCategory: &f%category% &f| &bComment: &f%comment%"}));
		languageKeys.put("AdminstrationFunctionHandler.AddStorage.Withdraw"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&6Es wurden von dem Konto &e%fromaccount% &f%formatwithdraw% &6abgezogen.",
						"&bKategorie: &f%category% &f| &bNotiz: &f%comment%",
						"&6It was deducted from the account &e%fromaccount% &f%formatwithdraw%&6.",
						"&bCategory: &f%category% &f| &bComment: &f%comment%"}));
		languageKeys.put("AdminstrationFunctionHandler.DeleteAll.NotOwner"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu kannst den Shop nicht auflösen, da er dir nicht gehört!",
						"&cYou can not dissolve the store, because it is not yours!"}));
		languageKeys.put("AdminstrationFunctionHandler.DeleteAll.Delete"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast den Shop &f%id%-%signshop% &caufgelöst. Es befanden sich noch &f%amount% &r%displayname% &cItems im Shop, welche nun verloren sind.",
						"&cYou have closed the store &f%id%-%signshop%&c. There were still &f%amount% &r%displayname% &citems in the store, which are now lost."}));
		languageKeys.put("AdminstrationFunctionHandler.SetAccount.Set"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eSignshop Account wurde gesetzt!",
						"&eSignshop account was set!"}));
		languageKeys.put("AdminstrationFunctionHandler.SetStorage.Set"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eSignshop ASH-Lager ID wurde gesetzt!",
						"&eSignshop ASH-storage ID was set!"}));
		languageKeys.put("AdminstrationFunctionHandler.ItemClear"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast das Item des Shop zurückgesetzt. Nun kann ein neues Item in den Shop eingetragen werden.",
						"&eYou have reset the item of the store. Now you can add a new item to the store."}));
		languageKeys.put("AdminstrationFunctionHandler.DiscountTimeNotFit"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDas eingestellte Datum/Uhrzeit &f%value% &cpasst nicht mit dem Muster &f%pattern% &czusammen!",
						"&cThe set date/time &f%value% &cdoes not match the pattern &f%pattern% &c!"}));
		languageKeys.put("ShopFunctionHandler.Buy.NoGoodsInStock"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat keine Ware mehr zum verkaufen!",
						"&cThe store has no more goods for sale!"}));
		languageKeys.put("ShopFunctionHandler.Buy.NotInit"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat noch keinen Verkauf-Wert festgelegt! Somit kann nicht gekauft werden!",
						"&cThe store has not yet set a sale value! Thus can not be bought!"}));
		languageKeys.put("ShopFunctionHandler.Buy.PossibleIsZero"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat keine Ware mehr zum Kaufen ausgestellt!",
						"&cThe store has no more goods on display for purchase!"}));
		languageKeys.put("ShopFunctionHandler.Buy.ShopHaveNotAccountReady"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat kein zugewiesenes Konto!",
						"&cThe store has no assigned account!"}));
		languageKeys.put("ShopFunctionHandler.Buy.YouDontHaveAccountToWithdraw"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast keinen Account um die Kosten abziehen zu können.",
						"&cYou do not have an account to be able to deduct the costs."}));
		languageKeys.put("ShopFunctionHandler.Transaction"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&6Es wurden von dem Konto &e%fromaccount% &f%formatwithdraw% &6abgezogen und &f%formatdeposit% &6an &e%toaccount% &6überwiesen.",
						"&eGezahlte Steuern&7: &f%formattax%",
						"&bKategorie: &f%category% &f| &bNotiz: &f%comment%",
						"&6It was deducted from the account &e%fromaccount% &f%formatwithdraw% &6and transferred &f%formatdeposit% &6to &e%toaccount%&6.",
						"&eTaxes paid&7: &f%formattax%",
						"&bCategory: &f%category% &f| &bComment: &f%comment%"}));
		languageKeys.put("ShopFunctionHandler.Sell.ShopIsFull"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop ist voll!",
						"&cThe store is full!"}));
		languageKeys.put("ShopFunctionHandler.Sell.NotInit"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat noch keinen Ankauf-Wert festgelegt! Somit kann nicht verkauft werden!",
						"&cThe store has not yet set a purchase value! Thus can not be sold!"}));
		languageKeys.put("ShopFunctionHandler.Sell.PossibleIsZero"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat keinen Platz zum Ankaufen ausgestellt!",
						"&cThe store has not issued a place to buy!"}));
		languageKeys.put("ShopFunctionHandler.Sell.ShopHaveNotAccountReady"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat kein zugewiesenes Konto!",
						"&cThe store has no assigned account!"}));
		languageKeys.put("ShopFunctionHandler.Sell.YouDontHaveAccountToWithdraw"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast keinen Account um den Gewinn einzuzahlen.",
						"&cYou do not have an account to deposit the winnings."}));
		languageKeys.put("ShopFunctionHandler.Subscribe"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast den Shop &a%shop% &eabonniert!",
						"&eYou have subscribed the shop &a%shop%&e!"}));
		languageKeys.put("ShopFunctionHandler.Unsubscribe"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eDu hast den Shop &c%shop% &edeabonniert!",
						"&eYou have unsubscribed the shop &a%shop%&e!"}));
	}
	
	public void initBonusMalusLanguage() //INFO:BonusMalusLanguages
	{
		bmlanguageKeys.put(Bypass.Permission.SHOP_CREATION.toString()+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eShop-Erstellungsrecht",
						"&eShop creation right"}));
		bmlanguageKeys.put(Bypass.Permission.SHOP_CREATION.toString()+".Explanation",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&ePermission, welche erlaubt SchilderShop",
						"&eerstellen zu können.",
						"&ePermission, which allows",
						"&eto create SchilderShop."}));
		bmlanguageKeys.put(Bypass.CountPermission.SHOP_CREATION_AMOUNT_.toString()+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eAnzahl zu erstellende Shops",
						"&eNumber of stores to be created"}));
		bmlanguageKeys.put(Bypass.CountPermission.SHOP_CREATION_AMOUNT_.toString()+".Explanation",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eZählpermission, welche die Anzahl",
						"&ezu erstellender definiert.",
						"&eCounting mission, which defines",
						"&ethe number to be created."}));
	}
	
	public void initMaterialLanguage() //INFO:MaterialLanguages
	{
		List<Material> list = new ArrayList<Material>(EnumSet.allOf(Material.class)); //TODO
		/*for(Material m : list)
		{
			String ger = "";
			String eng = "";
			switch(m)
			{
			default:
				ger = m.toString(); eng = m.toString(); break;
			case ACACIA_BOAT:
			case ACACIA_BUTTON:
			case ACACIA_CHEST_BOAT:
			case ACACIA_DOOR:
			case ACACIA_FENCE:
			case ACACIA_FENCE_GATE:
			case ACACIA_LEAVES:
			case ACACIA_LOG:
			case ACACIA_PLANKS:
			case ACACIA_PRESSURE_PLATE:
			case ACACIA_SAPLING:
			case ACACIA_SIGN:
			case ACACIA_SLAB:
			case ACACIA_STAIRS:
			case ACACIA_TRAPDOOR:
			case ACACIA_WALL_SIGN:
			case ACACIA_WOOD:
			case ACTIVATOR_RAIL:
			case AIR:
			case ALLAY_SPAWN_EGG:
			case ALLIUM:
			case AMETHYST_BLOCK:
			case AMETHYST_CLUSTER:
			case AMETHYST_SHARD:
			case ANCIENT_DEBRIS:
			case ANDESITE:
			case ANDESITE_SLAB:
			case ANDESITE_STAIRS:
			case ANDESITE_WALL:
			case ANVIL:
			case APPLE:
			case ARMOR_STAND:
			case ARROW:
			case ATTACHED_MELON_STEM:
			case ATTACHED_PUMPKIN_STEM:
			case AXOLOTL_BUCKET:
			case AXOLOTL_SPAWN_EGG:
			case AZALEA:
			case AZALEA_LEAVES:
			case AZURE_BLUET:
			case BAKED_POTATO:
			case BAMBOO:
			case BAMBOO_SAPLING:
			case BARREL:
			case BARRIER:
			case BASALT:
			case BAT_SPAWN_EGG:
			case BEACON:
			case BEDROCK:
			case BEE_NEST:
			case BEE_SPAWN_EGG:
			case BEEF:
			case BEEHIVE:
			case BEETROOT:
			case BEETROOT_SEEDS:
			case BEETROOT_SOUP:
			case BEETROOTS:
			case BELL:
			case BIG_DRIPLEAF:
			case BIG_DRIPLEAF_STEM:
			case BIRCH_BOAT:
			case BIRCH_BUTTON:
			case BIRCH_CHEST_BOAT:
			case BIRCH_DOOR:
			case BIRCH_FENCE:
			case BIRCH_FENCE_GATE:
			case BIRCH_LEAVES:
			case BIRCH_LOG:
			case BIRCH_PLANKS:
			case BIRCH_PRESSURE_PLATE:
			case BIRCH_SAPLING:
			case BIRCH_SIGN:
			case BIRCH_SLAB:
			case BIRCH_STAIRS:
			case BIRCH_TRAPDOOR:
			case BIRCH_WALL_SIGN:
			case BIRCH_WOOD:
			case BLACK_BANNER:
			case BLACK_BED:
			case BLACK_CANDLE:
			case BLACK_CANDLE_CAKE:
			case BLACK_CARPET:
			case BLACK_CONCRETE:
			case BLACK_CONCRETE_POWDER:
			case BLACK_DYE:
			case BLACK_GLAZED_TERRACOTTA:
			case BLACK_SHULKER_BOX:
			case BLACK_STAINED_GLASS:
			case BLACK_STAINED_GLASS_PANE:
			case BLACK_TERRACOTTA:
			case BLACK_WALL_BANNER:
			case BLACK_WOOL:
			case BLACKSTONE:
			case BLACKSTONE_SLAB:
			case BLACKSTONE_STAIRS:
			case BLACKSTONE_WALL:
			case BLAST_FURNACE:
			case BLAZE_POWDER:
			case BLAZE_ROD:
			case BLAZE_SPAWN_EGG:
			case BLUE_BANNER:
			case BLUE_BED:
			case BLUE_CANDLE:
			case BLUE_CANDLE_CAKE:
			case BLUE_CARPET:
			case BLUE_CONCRETE:
			case BLUE_CONCRETE_POWDER:
			case BLUE_DYE:
			case BLUE_GLAZED_TERRACOTTA:
			case BLUE_ICE:
			case BLUE_ORCHID:
			case BLUE_SHULKER_BOX:
			case BLUE_STAINED_GLASS:
			case BLUE_STAINED_GLASS_PANE:
			case BLUE_TERRACOTTA:
			case BLUE_WALL_BANNER:
			case BLUE_WOOL:
			case BONE:
			case BONE_BLOCK:
			case BONE_MEAL:
			case BOOK:
			case BOOKSHELF:
			case BOW:
			case BOWL:
			case BRAIN_CORAL:
			case BRAIN_CORAL_BLOCK:
			case BRAIN_CORAL_FAN:
			case BRAIN_CORAL_WALL_FAN:
			case BREAD:
			case BREWING_STAND:
			case BRICK:
			case BRICK_SLAB:
			case BRICK_STAIRS:
			case BRICK_WALL:
			case BRICKS:
			case BROWN_BANNER:
			case BROWN_BED:
			case BROWN_CANDLE:
			case BROWN_CANDLE_CAKE:
			case BROWN_CARPET:
			case BROWN_CONCRETE:
			case BROWN_CONCRETE_POWDER:
			case BROWN_DYE:
			case BROWN_GLAZED_TERRACOTTA:
			case BROWN_MUSHROOM:
			case BROWN_MUSHROOM_BLOCK:
			case BROWN_SHULKER_BOX:
			case BROWN_STAINED_GLASS:
			case BROWN_STAINED_GLASS_PANE:
			case BROWN_TERRACOTTA:
			case BROWN_WALL_BANNER:
			case BROWN_WOOL:
			case BUBBLE_COLUMN:
			case BUBBLE_CORAL:
			case BUBBLE_CORAL_BLOCK:
			case BUBBLE_CORAL_FAN:
			case BUBBLE_CORAL_WALL_FAN:
			case BUCKET:
			case BUDDING_AMETHYST:
			case BUNDLE:
			case CACTUS:
			}
			matlanguageKeys.put(m.toString(),
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger,
							eng}));
		}*/
	}
	
	@SuppressWarnings("deprecation")
	public void initEnchantmentLanguage() //INFO:EnchantmentLanguages
	{
		List<Enchantment> list = Arrays.asList(Enchantment.values());
		
		for(Enchantment e : list)
		{
			enchlanguageKeys.put(e.toString(),
					new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
							e.getName()}));
		}
	}
	
	public void initGuiAdministration() //INFO:GuiAdministration
	{
		LinkedHashMap<String, Language> admin = new LinkedHashMap<>();
		String path = "4"; //InfoItem, wie es ist
		admin.put(path+".IsInfoItem",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		path = "13"; //InfoZumShop
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_ORE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cInfo zum Shop &f%displayname%",
						"&cInfo from Shop &f%displayname%"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cEigentümer: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cLager Aktuelle Items: &f%itemstoragecurrent%",
						"&cLager Gesamter Itemsplatz: &f%itemstoragetotal%",
						"&cVerkaufpreis: &f%buyraw1%",
						"&cAnkaufpreis: &f%sellraw1%",
						
						"&cOwner: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cStorage Items Actual: &f%itemstoragecurrent%",
						"&cStorage Items Total: &f%itemstoragetotal%",
						"&cBuyprice: &f%buyraw1%",
						"&cSellprice: &f%sellraw1%"}));
		admin.put(path+".Lore."+SettingsLevel.ADVANCED.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cEigentümer: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cErstellungsdatum: &f%creationdate%",
						"&cLager Aktuelle Items: &f%itemstoragecurrent%",
						"&cLager Gesamter Itemsplatz: &f%itemstoragetotal%",
						"&cVerkauf Aktiv: &f%buytoggle%",
						"&cAnkauf Aktiv: &f%selltoggle%",
						"&cVerkaufpreis: &f%buyraw1%",
						"&cAnkaufpreis: &f%sellraw1%",
						"&cMöglicher Verkauf/Ankauf: &f%possiblebuy% <> %possiblesell%",
						
						"&cOwner: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cCreationdatum: &f%creationdate%",
						"&cStorage Items Actual: &f%itemstoragecurrent%",
						"&cStorage Items Total: &f%itemstoragetotal%",
						"&cBuy Active: &f%buytoggle%",
						"&cSell Active: &f%selltoggle%",
						"&cBuyprice: &f%buyraw1%",
						"&cSellprice: &f%sellraw1%",
						"&cPossible Buy/Sell: &f%possiblebuy% <> %possiblesell%"}));
		admin.put(path+".Lore."+SettingsLevel.EXPERT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cEigentümer: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cErstellungsdatum: &f%creationdate%",
						"&cLager Aktuelle Items: &f%itemstoragecurrent%",
						"&cLager Gesamter Itemsplatz: &f%itemstoragetotal%",
						"&cLocation: &f%server%-%world%-&7%x%&f/&7%y%&f/&7%z%",
						"&cAccount: &f%accountid% - %accountname%",
						"&cVerkauf Aktiv: &f%buytoggle%",
						"&cAnkauf Aktiv: &f%selltoggle%",
						"&cVerkaufpreis: &f%buyraw1%",
						"&cAnkaufpreis: &f%sellraw1%",
						"&cMöglicher Verkauf/Ankauf: &f%possiblebuy% <> %possiblesell%",
						"&cUnlimitierter Verkauf: &f%unlimitedbuy%",
						"&cUnlimitierter Ankauf: &f%unlimitedsell%",
						
						"&cOwner: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cCreationdatum: &f%creationdate%",
						"&cStorage Items Actual: &f%itemstoragecurrent%",
						"&cStorage Items Total: &f%itemstoragetotal%",
						"&cLocation: &f%server%-%world%-&7%x%&f/&7%y%&f/&7%z%",
						"&cAccount: &f%accountid% - %accountname%",
						"&cBuy Active: &f%buytoggle%",
						"&cSell Active: &f%selltoggle%",
						"&cBuyprice: &f%buyraw1%",
						"&cSellprice: &f%sellraw1%",
						"&cPossible Buy/Sell: &f%possiblebuy% <> %possiblesell%",
						"&cUnlimited Buy: &f%unlimitedbuy%",
						"&cUnlimited Sell: &f%unlimitedsell%"}));
		admin.put(path+".Lore."+SettingsLevel.MASTER.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cId: &f%id%",
						"&cEigentümer: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cErstellungsdatum: &f%creationdate%",
						"&cLager Aktuelle Items: &f%itemstoragecurrent%",
						"&cLager Gesamter Itemsplatz: &f%itemstoragetotal%",
						"&cLocation: &f%server%-%world%-&7%x%&f/&7%y%&f/&7%z%",
						"&cAccount: &f%accountid% - %accountname%",
						"&cVerkauf Aktiv: &f%buytoggle%",
						"&cAnkauf Aktiv: &f%selltoggle%",
						"&cVerkaufpreis: &f%buyraw1%",
						"&cAnkaufpreis: &f%sellraw1%",
						"&cMöglicher Verkauf/Ankauf: &f%possiblebuy% <> %possiblesell%",
						"&cRabatt Start: &f%discountstart%",
						"&cRabatt Ende: %discountend%",
						"&cRabattverkaufpreis: &f%discountbuy1%",
						"&cRabattankaufpreis: &f%discountsell1%",
						"&cMöglicher Rabatt Verkauf: &f%discountpossiblebuy%",
						"&cMöglicher Rabatt Ankauf: &f%discountpossiblesell%",
						"&cLagersystemID: &f%storageid%",
						"&cUnlimitierter Verkauf: &f%unlimitedbuy%",
						"&cUnlimitierter Ankauf: &f%unlimitedsell%",
						
						"&cId: &f%id%",
						"&cOwner: &f%owner%",
						"&cShopname: &f%signshopname%",
						"&cCreationdatum: &f%creationdate%",
						"&cStorage Items Actual: &f%itemstoragecurrent%",
						"&cStorage Items Total: &f%itemstoragetotal%",
						"&cLocation: &f%server%>%world%>&7%x%&f/&7%y%&f/&7%z%",
						"&cAccount: &f%accountid% - %accountname%",
						"&cBuy Active: &f%buytoggle%",
						"&cSell Active: &f%selltoggle%",
						"&cBuyprice: &f%buyraw1%",
						"&cSellprice: &f%sellraw1%",
						"&cPossible Buy/Sell: &f%possiblebuy% <> %possiblesell%",
						"&cDiscount Start: &f%discountstart%",
						"&cDiscount Ende: &f%discountend%",
						"&cDiscountbuyprice: &f%discountbuy1%",
						"&cDiscountsellprice: &f%discountsell1%",
						"&cPossible Discount Buy: &f%discountpossiblebuy%",
						"&cPossible Discount Sell: &f%discountpossiblesell%",
						"&cStoragesystemID: &f%storageid%",
						"&cUnlimited Buy: &f%unlimitedbuy%",
						"&cUnlimited Sell: &f%unlimitedsell%"}));
		path = "6"; //ItemStack clear
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.ADVANCED.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.COMPOSTER.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cItem zurücksetzen",
						"&cReset item"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dSetzt das Item des Shop zurück.",
						"&bFunktioniert nur, wenn alle Items",
						"&baus dem Lagerraum entfernt worden sind.",
						"&bDanach kann man ein neues Item setzen.",
						"&bResets the item of the shio.",
						"&bWorks only when all items have been",
						"&bremoved from the storage room.",
						"&bAfter that you can set a new item."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_BUY.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_BUY.toString()}));
		path = "36"; //Toggle Buy
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.ADVANCED.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_ORE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dToggel Verkauf (zz. &r%buytoggle%&d)",
						"&dToggle Buying (atm. &r%buytoggle%&d)"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bToggelt den Verkauf.",
						"&bSollte der Verkauf ausgeschaltet sein,",
						"&bist der Verkauf nicht mehr möglich.",
						"&bToggles the buying",
						"&bIf the buying is turned off,",
						"&bthe buying is no longer possible."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_BUY.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_BUY.toString()}));
		path = "37"; //Toggle Sell
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.ADVANCED.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.IRON_ORE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dToggel Ankauf (zz. &r%selltoggle%&d)",
						"&dToggle Selling (atm. &r%selltoggle%&d)"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bToggelt den Ankauf.",
						"&bSollte der Ankauf ausgeschaltet sein,",
						"&bist der Ankauf nicht mehr möglich.",
						"&bToggles the selling",
						"&bIf the seling is turned off,",
						"&bthe selling is no longer possible."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_BUY.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_BUY.toString()}));
		path = "45"; //SetBuy
		admin.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_INGOT.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Verkaufswertes für 1 Item",
						"&dEnter the buy value for 1 item"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%buyraw1%",
						"&cQ &bfür das Entfernen des Preises.",
						"&cLinks-/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%buyraw1%",
						"&cQ &bfor removing the price.",
						"&cLinks-/Rechtsklick &bto open the numpad gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETBUY_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETBUY_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETBUY_OPEN_NUMPAD.toString()}));
		path = "46"; //SetSell
		admin.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.IRON_INGOT.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Ankaufswertes für 1 Item",
						"&dEnter the sell value for 1 item"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%sellraw1%",
						"&cQ &bzum entfernen des Preises.",
						"&cLinks-/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%sell1%",
						"&cQ &bfor removing the price.",
						"&cLinks-/Rechtsklick &bto open the numpad gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETSELL_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETSELL_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETSELL_OPEN_NUMPAD.toString()}));
		path = "0"; //SettingsLevelToggle
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material."+SettingsLevel.BASE.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.OAK_WOOD.toString()}));
		admin.put(path+".Material."+SettingsLevel.ADVANCED.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.STONE.toString()}));
		admin.put(path+".Material."+SettingsLevel.EXPERT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DIAMOND.toString()}));
		admin.put(path+".Material."+SettingsLevel.MASTER.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.NETHERITE_INGOT.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dSwitcht die Gui-Level-Ansicht",
						"&dSwitch the Gui level view"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cLinksklick &bfür das Basis Level.",
						"&cRechtsklick &bfür das Fortgeschrittene Level.",
						"&cShift Linksklick &bfür das Experten Level.",
						"&cShift Rechtsklick &bfür das Meister Level.",
						"&cLeftclick &bfor the basic level.",
						"&cRightclick &bfor the advanced level.",
						"&cShift-Leftclick  &bfor the expert level.",
						"&cShift-Rightclick &bfor the master level."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETTINGSLEVEL_SETTO_BASE.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETTINGSLEVEL_SETTO_ADVANCED.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETTINGSLEVEL_SETTO_EXPERT.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETTINGSLEVEL_SETTO_MASTER.toString()}));
		path = "9"; //SetAccount
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.OAK_DOOR.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Wirtschaftsaccount per ID",
						"&dEnter the economyaccount per ID"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%accountid% - %accountname%",
						"&cQ &bzum stellen auf den Default Account.",
						"&cLinks-/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%accountid% - %accountname%",
						"&cQ &bto set to the default account.",
						"&cLeft/right click &to open the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETACCOUNT_DEFAULT.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETACCOUNT_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETACCOUNT_OPEN_NUMPAD.toString()}));
		path = "18"; //AddStorage
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.CHEST.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dErhöht den Lageraum des Shop",
						"&dSwitch the Gui level view"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%itemstoragetotal%",
						"&cLinksklick &berhöht den Lagerraum um 1 Item.",
						"&cRechtsklick &berhöht den Lagerraum um 16 Items.",
						"&cShift Linksklick &berhöht den Lagerraum um 64 Items.",
						"&cShift Rechtsklick &berhögt den Lagerraum um 576 Items",
						"&bAtm.: &f%itemstoragetotal%",
						"&cLeftclick &bincreases the storage space by 1 item.",
						"&cRightclick &bincreases the storage space by 16 items.",
						"&cShift-Leftclick  &bincreases the storage space by 64 items.",
						"&cShift-Rightclick &bincreases the storage space by 576 items."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_1.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_16.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_64.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_576.toString()}));
		path = "15"; //Open Shoplog
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.CHEST.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dÖffnet das Shoplog",
						"&dOpen the shoplog"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cLinks- und Rechtsklick &böffnet den Shoplog.",
						"&bEs erscheint im Chat der ShopLog.",
						"&cLeft/rightclick &bopen the shoplog.",
						"&bThe ShopLog appears in the chat."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_OPEN_SHOPLOG.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_OPEN_SHOPLOG.toString()}));
		path = "1"; //SetASH
		admin.put(path+".UseASH",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BARREL.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Verteilersystem von ASH per ID",
						"&dEnter the distributionsystem from ASH per ID"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%storageid%",
						"&cQ &bstellt das Lagersystemverlinkung auf 0.",
						"&bUnd somit außer Betrieb.",
						"&cLinks-/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%storageid%",
						"&cQ &bsets the storage system link to 0.",
						"&bAnd thus out of operation.",
						"&cLeft/right click &to open the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETASH_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETASH_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETASH_OPEN_NUMPAD.toString()}));
		path = "10"; //Unlimited Toggle Buy
		admin.put(path+".Permission",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_BLOCK.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dToggel Unlimitierter Verkauf (zz. &f%unlimitedbuy%&d)",
						"&dToggle unlimited Buying (atm. &f%unlimitedbuy%&d)"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bToggelt den unlimitierten Verkauf.",
						"&bSollte der unlimitierter Verkauf angeschaltet sein,",
						"&bkönnen unbegrenzt Items verkauft werden, ohne jedweden Vorrat.",
						"&bToggles the unlimited buying",
						"&bIf the unlimited buying is turned on,",
						"&bunlimited items can be sold, without any stock."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_UNLIMITED_TOGGLE_BUY.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_UNLIMITED_TOGGLE_BUY.toString()}));
		path = "19"; //Unlimited Toggle Sell
		admin.put(path+".Permission",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_BLOCK.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dToggel Unlimitierter Ankauf (zz. &r%unlimitedsell%&d)",
						"&dToggle unlimited Selling (atm. &r%unlimitedsell%&d)"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bToggelt den unlimitierten Ankauf.",
						"&bSollte der unlimitierter Ankauf angeschaltet sein,",
						"&bkönnen unbegrenzt Items ankauft werden,",
						"&bohne jedweden Platz im Lagerraum.",
						"&bToggles the unlimited selling",
						"&bIf the unlimited selling is turned on,",
						"&bunlimited number of items can be purchased",
						"&bwithout any space in the storeroom."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_UNLIMITED_TOGGLE_SELL.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_UNLIMITED_TOGGLE_SELL.toString()}));
		path = "21"; //Possible Buy
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.ADVANCED.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_NUGGET.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe der Anzahl der verbleibenen Verkäufe",
						"&dEnter the number of buying remaining"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%possiblebuy%",
						"&bVerbleibene Verkäufe, sind die Anzahl",
						"&ban Verkäufe die noch getätig werden können.",
						"&bIst die Zahl auf 0 können keine Items mehr verkauft werden.",
						"&b-1 bedeutet die Mechanik ist ausgeschaltet.",
						"&cQ &bfür das Zurücksetzten auf -1.",
						"&cLinks/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%possiblebuy%",
						"&bRemaining sales, are the number of sales",
						"&bthat can still be made.",
						"&bIf the number is 0, no more items can be sold.",
						"&b-1 means the mechanics is switched off.",
						"&cQ &bfor resetting to -1.",
						"&cLeft/right click &to open the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SWAP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.NUMPAD_1.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_OPEN_NUMPAD.toString()}));
		path = "23"; //Possible Sell
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.ADVANCED.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.IRON_NUGGET.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe der Anzahl der verbleibenen Ankäufe",
						"&dEnter the number of purchase remaining"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%possiblesell%",
						"&bVerbleibene Ankäufe, sind die Anzahl an",
						"&bAnkäufe die noch getätig werden können.",
						"&bIst die Zahl auf 0 können keine Items mehr ankauft werden.",
						"&b-1 bedeutet die Mechanik ist ausgeschaltet.",
						"&cQ &bfür das Zurücksetzten auf -1.",
						"&cLinks/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%possiblesell%",
						"&bRemaining purchase, are the number of",
						"&bpurchase that can still be made.",
						"&bIf the number is 0, no more items can be purchase.",
						"&b-1 means the mechanics is switched off.",
						"&cQ &bfor resetting to -1.",
						"&cLeft/right click &to open the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_SELL_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_SELL_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_SELL_OPEN_NUMPAD.toString()}));
		path = "8"; //Delete All
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BARRIER.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dLöschung des Shops",
						"&dDeleting of the shop"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cLinks/Rechtsklick &blöscht den Shop.",
						"&cAchtung!",
						"&bDurch das Klicken wird unverzüglich der gesamte Shop",
						"&bmit allen noch verbliebenen Items gelöscht!",
						"&cAchtung!",
						"&cLeft/rightclick &bopen the shoplog.",
						"&cAttention!",
						"&bClicking immediately deletes the entire",
						"&bstore with all remaining items!",
						"&cAttention"}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_DELETE_ALL.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_DELETE_ALL.toString()}));
		path = "44"; //Discount Clear
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.ENDER_EYE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dAbbrechen der Rabattaktion",
						"&dCancel of the discount promotion"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cLinks- und Rechtsklick &bbricht die Rabattaktion ab.",
						"&bDabei wird nur der Rabattzeitraum zurückgesetzt.",
						"&cLeft - and rightclick &bcancels the discount promotion.",
						"&bThis only resets the discount period."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNT_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNT_CLEAR.toString()}));
		path = "43"; //Discount Open CMD
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.CAKE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Zeitraums der Rabattaktion",
						"&dEnter the period of the discount promotion"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"Zz.: %discountstart% - %discountend%",
						"&cLinksklick &böffnet das Numpad Gui für die Eingabe des Starts.",
						"&cRechtsklick &Böffnet das Numpad Gui für die Eingabe des Endes.",
						"&bDie Eingabe erfolgt Standart gemäß so: yyyy.MM.dd.HH:mm:ss",
						"Atm.: %discountstart% - %discountend%",
						"&cLeft click &open the Numpad Gui for entering the start.",
						"&cRight click &opens the Numpad Gui for entering the end.",
						"&bThe input is done according to the following standard: yyyy.MM.dd.HH:mm:ss"}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNT_START_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNT_END_OPEN_NUMPAD.toString()}));
		path = "30"; //Discount Possible Buy
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.LAPIS_ORE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe der Anzahl der verbleibenen Rabattverkäufe",
						"&dEnter the number of discount sales remaining"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%discountpossiblebuy%",
						"&bVerbleibene Verkäufe, sind die Anzahl an",
						"&bRabattverkäufe die noch getätig werden können.",
						"&bIst die Zahl auf 0 können keine Items mehr",
						"&bverkauft werden solange die Rabattaktion läuft.",
						"&b-1 bedeutet die Mechanik ist ausgeschaltet.",
						"&cQ &bfür das Zurücksetzten auf -1.",
						"&cLinks/Rechtsklick &böffnet das Numpad Gui.",
						
						"&bAtm.: &f%discountpossiblebuy%",
						"&bRemaining sales, are the number of discountsales",
						"&bthat can still be made.",
						"&bIf the number is 0, no more items can be sold as",
						"&blong as the discount promotion is running.",
						"&b-1 means the mechanics is switched off.",
						"&cQ &bfor resetting to -1.",
						"&cLeft/right click &opens the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_OPEN_NUMPAD.toString()}));
		path = "32"; //Discount Possible Sell
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.REDSTONE_ORE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe der Anzahl der verbleibenen Rabattankäufe",
						"&dEnter the number of discount purchase remaining"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%discountpossiblesell%",
						"&bVerbleibene Ankäufe, sind die Anzahl an",
						"&bAnkäufe die noch getätig werden können.",
						"&bIst die Zahl auf 0 können keine Items mehr",
						"&bankauft werden solange die Rabattaktion läuft.",
						"&b-1 bedeutet die Mechanik ist ausgeschaltet.",
						"&cQ &bfür das Zurücksetzten auf -1.",
						"&cLinks/Rechtsklick &böffnet das Numpad Gui.",
						"&bAtm.: &f%discountpossiblesell%",
						"&bRemaining purchase, are the number of purchase",
						"&bthat can still be made.",
						"&bIf the number is 0, no more items can be",
						"&bpurchased as long as the discount campaign is running.",
						"&b-1 means the mechanics is switched off.",
						"&cQ &bfor resetting to -1.",
						"&cLeft/right click &opens the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_OPEN_NUMPAD.toString()}));
		path = "52"; //Discount SetBuy
		admin.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.LAPIS_LAZULI.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Rabattverkaufswertes für 1 Item",
						"&dEnter the discount sale value for 1 item"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%discountbuy1%",
						"&cQ &bfür das Entfernen des Preises.",
						"&cLinks-/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%discountbuy1%",
						"&cQ &bfor removing the price.",
						"&cLinks-/Rechtsklick &bto open the numpad gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTBUY_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTBUY_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTBUY_OPEN_NUMPAD.toString()}));
		path = "53"; //Discount SetSell
		admin.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.MASTER.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.REDSTONE.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dEingabe des Rabattankaufswertes für 1 Item",
						"&dEnter the discount purchase value for 1 item"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%discountsell1%",
						"&cQ &bfür das Entfernen des Preises.",
						"&cLinks-/Rechtsklick &bzum öffnen des Numpad Gui.",
						
						"&bAtm.: &f%discountsell1%",
						"&cQ &bfor removing the price.",
						"&cLinks-/Rechtsklick &bto open the numpad gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTSELL_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTSELL_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETDISCOUNTSELL_OPEN_NUMPAD.toString()}));
		guiKeys.put(GuiType.ADMINISTRATION, admin);
		
		//------------------------------------
		/*admin.put(path+".IsInfoItem",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".Permission",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		admin.put(path+".UseASH",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						false}));
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&d",
						"&d"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&b",
						"&b",
						"&b",
						"&b"}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));
		admin.put(path+".ClickFunction."+ClickType.CTRL_DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));
		admin.put(path+".ClickFunction."+ClickType.SWAP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						""}));*/
	}
	
	public void initGuiNumpad() //INFO:GuiAdministration
	{
		LinkedHashMap<String, Language> numpad = new LinkedHashMap<>();
		String path = "12"; //7
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						7}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f7 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_7.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_7.toString()}));
		path = "13"; //8
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						8}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f8 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_8.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_8.toString()}));
		path = "14"; //9
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						9}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f9 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_9.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_9.toString()}));
		path = "21"; //4
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						4}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f4 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_4.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_4.toString()}));
		path = "22"; //5
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						5}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f5 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_5.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_5.toString()}));
		path = "23"; //6
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						6}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f6 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_6.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_6.toString()}));
		path = "30"; //1
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						1}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f1 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_1.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_1.toString()}));
		path = "31"; //2
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						2}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f2 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_2.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_2.toString()}));
		path = "32"; //3
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.DARK_OAK_BUTTON.toString()}));
		numpad.put(path+".Amount",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						3}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f3 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_3.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_3.toString()}));
		path = "38"; //.
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.OAK_BUTTON.toString()}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f. &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_DECIMAL.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_DECIMAL.toString()}));
		path = "42"; //:
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.OAK_FENCE.toString()}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&f: &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &r%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_COLON.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_COLON.toString()}));
		path = "49"; //Clear
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BARRIER.toString()}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eZurücksetzen",
						"&eReset"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%numtext%",
						"&bSetzt die Eingabe zurück.",
						"&bAtm.: &r%numtext%",
						"&bResets the input."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_CLEAR.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_CLEAR.toString()}));
		path = "45"; //Cancel
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.RED_BANNER.toString()}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cZurück zum Administrations-Gui",
						"&cBack to the administration gui"}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_CANCEL.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_CANCEL.toString()}));
		LinkedHashMap<String, Language> numpad_ACCOUNT = new LinkedHashMap<>();
		numpad_ACCOUNT.putAll(numpad);
		LinkedHashMap<String, Language> numpad_ASH = new LinkedHashMap<>();
		numpad_ASH.putAll(numpad);
		LinkedHashMap<String, Language> numpad_BUY = new LinkedHashMap<>();
		numpad_BUY.putAll(numpad);
		LinkedHashMap<String, Language> numpad_SELL = new LinkedHashMap<>();
		numpad_SELL.putAll(numpad);
		LinkedHashMap<String, Language> numpad_POSSIBLEBUY = new LinkedHashMap<>();
		numpad_POSSIBLEBUY.putAll(numpad);
		LinkedHashMap<String, Language> numpad_POSSIBLESELL = new LinkedHashMap<>();
		numpad_POSSIBLESELL.putAll(numpad);
		LinkedHashMap<String, Language> numpad_DISCOUNTSTART = new LinkedHashMap<>();
		numpad_DISCOUNTSTART.putAll(numpad);
		LinkedHashMap<String, Language> numpad_DISCOUNTEND = new LinkedHashMap<>();
		numpad_DISCOUNTEND.putAll(numpad);
		LinkedHashMap<String, Language> numpad_DISCOUNTBUY = new LinkedHashMap<>();
		numpad_DISCOUNTBUY.putAll(numpad);
		LinkedHashMap<String, Language> numpad_DISCOUNTSELL = new LinkedHashMap<>();
		numpad_DISCOUNTSELL.putAll(numpad);
		LinkedHashMap<String, Language> numpad_DISCOUNTPOSSIBLEBUY = new LinkedHashMap<>();
		numpad_DISCOUNTPOSSIBLEBUY.putAll(numpad);
		LinkedHashMap<String, Language> numpad_DISCOUNTPOSSIBLESELL = new LinkedHashMap<>();
		numpad_DISCOUNTPOSSIBLESELL.putAll(numpad);
		path = "53"; //TakeOver
		String sSL = path+".SettingLevel";
		Language lSL = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				SettingsLevel.NOLEVEL.toString()});
		String sMat = path+".Material";
		Language lMat = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				Material.GREEN_BANNER.toString()});
		String sDN = path+".Displayname";
		Language lDN = new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				"&aÜbernahme der Eingabe",
				"&cAcceptance of the input"});
		String sLo = path+".Lore";
		Language lLo = new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
				"&bZz.: &r%numtext%", "&bÜbernimmt die Eingabe und kehre zum Administration Gui zurück.",
				"&bAtm.: &r%numtext%", "&bAccept the input and return to the Administration Gui."});
		String sLC = path+".ClickFunction."+ClickType.LEFT.toString();
		Language lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETACCOUNT_TAKEOVER.toString()});
		String sRC = path+".ClickFunction."+ClickType.RIGHT.toString();
		Language lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETACCOUNT_TAKEOVER.toString()});
		numpad_ACCOUNT.put(sSL, lSL);
		numpad_ACCOUNT.put(sMat, lMat);
		numpad_ACCOUNT.put(sDN, lDN);
		numpad_ACCOUNT.put(sLo, lLo);
		numpad_ACCOUNT.put(sLC, lLC);
		numpad_ACCOUNT.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_ACCOUNT, numpad_ACCOUNT);
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETASH_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETASH_TAKEOVER.toString()});
		numpad_ASH.put(sSL, lSL);
		numpad_ASH.put(sMat, lMat);
		numpad_ASH.put(sDN, lDN);
		numpad_ASH.put(sLo, lLo);
		numpad_ASH.put(sLC, lLC);
		numpad_ASH.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_ASH, numpad_ASH);
		//-------------
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETBUY_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETBUY_TAKEOVER.toString()});
		numpad_BUY.put(sSL, lSL);
		numpad_BUY.put(sMat, lMat);
		numpad_BUY.put(sDN, lDN);
		numpad_BUY.put(sLo, lLo);
		numpad_BUY.put(sLC, lLC);
		numpad_BUY.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_BUY, numpad_BUY);
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETSELL_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETSELL_TAKEOVER.toString()});
		numpad_SELL.put(sSL, lSL);
		numpad_SELL.put(sMat, lMat);
		numpad_SELL.put(sDN, lDN);
		numpad_SELL.put(sLo, lLo);
		numpad_SELL.put(sLC, lLC);
		numpad_SELL.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_SELL, numpad_SELL);
		//-------------
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_TAKEOVER.toString()});
		numpad_POSSIBLEBUY.put(sSL, lSL);
		numpad_POSSIBLEBUY.put(sMat, lMat);
		numpad_POSSIBLEBUY.put(sDN, lDN);
		numpad_POSSIBLEBUY.put(sLo, lLo);
		numpad_POSSIBLEBUY.put(sLC, lLC);
		numpad_POSSIBLEBUY.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_POSSIBLE_BUY, numpad_POSSIBLEBUY);
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_SELL_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_SELL_TAKEOVER.toString()});
		numpad_POSSIBLESELL.put(sSL, lSL);
		numpad_POSSIBLESELL.put(sMat, lMat);
		numpad_POSSIBLESELL.put(sDN, lDN);
		numpad_POSSIBLESELL.put(sLo, lLo);
		numpad_POSSIBLESELL.put(sLC, lLC);
		numpad_POSSIBLESELL.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_POSSIBLE_SELL, numpad_POSSIBLESELL);
		//--------------
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNT_START_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNT_START_TAKEOVER.toString()});
		numpad_DISCOUNTSTART.put(sSL, lSL);
		numpad_DISCOUNTSTART.put(sMat, lMat);
		numpad_DISCOUNTSTART.put(sDN, lDN);
		numpad_DISCOUNTSTART.put(sLo, lLo);
		numpad_DISCOUNTSTART.put(sLC, lLC);
		numpad_DISCOUNTSTART.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_DISCOUNT_START, numpad_DISCOUNTSTART);
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNT_END_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNT_END_TAKEOVER.toString()});
		numpad_DISCOUNTEND.put(sSL, lSL);
		numpad_DISCOUNTEND.put(sMat, lMat);
		numpad_DISCOUNTEND.put(sDN, lDN);
		numpad_DISCOUNTEND.put(sLo, lLo);
		numpad_DISCOUNTEND.put(sLC, lLC);
		numpad_DISCOUNTEND.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_DISCOUNT_END, numpad_DISCOUNTEND);
		//----------------
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTBUY_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTBUY_TAKEOVER.toString()});
		numpad_DISCOUNTBUY.put(sSL, lSL);
		numpad_DISCOUNTBUY.put(sMat, lMat);
		numpad_DISCOUNTBUY.put(sDN, lDN);
		numpad_DISCOUNTBUY.put(sLo, lLo);
		numpad_DISCOUNTBUY.put(sLC, lLC);
		numpad_DISCOUNTBUY.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_DISCOUNT_BUY, numpad_DISCOUNTBUY);
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTSELL_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTSELL_TAKEOVER.toString()});
		numpad_DISCOUNTSELL.put(sSL, lSL);
		numpad_DISCOUNTSELL.put(sMat, lMat);
		numpad_DISCOUNTSELL.put(sDN, lDN);
		numpad_DISCOUNTSELL.put(sLo, lLo);
		numpad_DISCOUNTSELL.put(sLC, lLC);
		numpad_DISCOUNTSELL.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_DISCOUNT_SELL, numpad_DISCOUNTSELL);
		//----------------
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_TAKEOVER.toString()});
		numpad_DISCOUNTPOSSIBLEBUY.put(sSL, lSL);
		numpad_DISCOUNTPOSSIBLEBUY.put(sMat, lMat);
		numpad_DISCOUNTPOSSIBLEBUY.put(sDN, lDN);
		numpad_DISCOUNTPOSSIBLEBUY.put(sLo, lLo);
		numpad_DISCOUNTPOSSIBLEBUY.put(sLC, lLC);
		numpad_DISCOUNTPOSSIBLEBUY.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_DISCOUNT_POSSIBLE_BUY, numpad_DISCOUNTPOSSIBLEBUY);
		lLC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_TAKEOVER.toString()});
		lRC = new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				ClickFunctionType.ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_TAKEOVER.toString()});
		numpad_DISCOUNTPOSSIBLESELL.put(sSL, lSL);
		numpad_DISCOUNTPOSSIBLESELL.put(sMat, lMat);
		numpad_DISCOUNTPOSSIBLESELL.put(sDN, lDN);
		numpad_DISCOUNTPOSSIBLESELL.put(sLo, lLo);
		numpad_DISCOUNTPOSSIBLESELL.put(sLC, lLC);
		numpad_DISCOUNTPOSSIBLESELL.put(sRC, lRC);
		guiKeys.put(GuiType.NUMPAD_DISCOUNT_POSSIBLE_SELL, numpad_DISCOUNTPOSSIBLESELL);
	}
	
	public void initGuiShop() //INFO:GuiShop
	{
		LinkedHashMap<String, Language> shop = new LinkedHashMap<>();
		String path = "4"; //InfoItem, wie es ist
		shop.put(path+".IsInfoItem",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		path = "13"; //InfoButton, welcher alle Daten des Items anzeigt
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.PAPER.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dHier folgt einer Auflistung aller Eigenschaften des Items",
						"&dHere follows a list of all properties of the item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDisplayname: &r%itemdisplayname%",
						"&cMaterial: &r%material%",
						
						"&cDisplayname: &r%itemdisplayname%",
						"&cMaterial: &r%material%"})); //TODO mehr hinzufügen
		shop.put(path+".InfoLore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cVerzauberungen: ",
						"&cEnchantments: "}));
		path = "8"; //Subscribe
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.LECTERN.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dAbonniere den Shop!",
						"&dSubscribe to the store!"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &r%subscribe%",
						"&bAbonniere oder deabonniere hier den Shop!",
						"&bAtm.: &r%subscribe%",
						"&bSubscribe or unsubscribe to the store here!"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_TOGGLE_SUBSCRIBE.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_TOGGLE_SUBSCRIBE.toString()}));
		path = "27"; //Buy1
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.YELLOW_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x1 Item",
						"&eBuy x1 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBuy x1 items or as much space is left in the inventory",
						"&boder der Shop noch vorrätig hat für &r%buy1%",
						"&bBuy x1 items or as many are still in inventory",
						"&bor the shop still in stock for &r%buy1%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_1.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_1.toString()}));
		path = "28"; //Buy16
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.YELLOW_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x16 Item",
						"&eBuy x16 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBuy x16 items or as much space is left in the inventory",
						"&boder der Shop noch vorrätig hat für &r%buy16%",
						"&bBuy x16 items or as many are still in inventory",
						"&bor the shop still in stock for &r%buy16%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_16.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_16.toString()}));
		path = "36"; //Buy32
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.YELLOW_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x32 Item",
						"&eBuy x32 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBuy x32 items or as much space is left in the inventory",
						"&boder der Shop noch vorrätig hat für &r%buy32%",
						"&bBuy x32 items or as many are still in inventory",
						"&bor the shop still in stock for &r%buy32%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_32.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_32.toString()}));
		path = "37"; //Buy64
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.YELLOW_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x64 Item",
						"&eBuy x64 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBuy x64 items or as much space is left in the inventory",
						"&boder der Shop noch vorrätig hat für &r%buy64%",
						"&bBuy x64 items or as many are still in inventory",
						"&bor the shop still in stock for &r%buy64%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_64.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_64.toString()}));
		path = "45"; //Buy576
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.YELLOW_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x576 Item",
						"&eBuy x576 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBuy x576 items or as much space is left in the inventory",
						"&boder der Shop noch vorrätig hat für &r%buy576%",
						"&bBuy x576 items or as many are still in inventory",
						"&bor the shop still in stock for &r%buy576%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_576.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_576.toString()}));
		path = "46"; //Buy2304
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.YELLOW_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x2304 Item",
						"&eBuy x2304 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bBuy x2304 items or as much space is left in the inventory",
						"&boder der Shop noch vorrätig hat für &r%buy2304%",
						"&bBuy x2304 items or as many are still in inventory",
						"&bor the shop still in stock for &r%buy2304%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_2304.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_2304.toString()}));
		path = "34"; //Sell1
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BROWN_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x1 Item",
						"&eSell x1 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bVerkaufe x1 Items oder soviele noch im Inventar sind",
						"&boder der Shop annimmt für &r%sell1%",
						"&bSell x1 items or as many are still in inventory",
						"&bor the store accepts for &r%sell1%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_1.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_1.toString()}));
		path = "35"; //Sell16
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BROWN_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x16 Item",
						"&eSell x16 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bVerkaufe x16 Items oder soviele noch im Inventar sind",
						"&boder der Shop annimmt für &r%sell16%",
						"&bSell x16 items or as many are still in inventory",
						"&bor the store accepts for &r%sell16%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_16.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_16.toString()}));
		path = "43"; //Sell32
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BROWN_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x32 Item",
						"&eSell x32 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bVerkaufe x32 Items oder soviele noch im Inventar sind",
						"&boder der Shop annimmt für &r%sell32%",
						"&bSell x32 items or as many are still in inventory",
						"&bor the store accepts for &r%sell32%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_32.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_32.toString()}));
		path = "44"; //Sell64
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BROWN_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x64 Item",
						"&eSell x64 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bVerkaufe x64 Items oder soviele noch im Inventar sind",
						"&boder der Shop annimmt für &r%sell64%",
						"&bSell x64 items or as many are still in inventory",
						"&bor the store accepts for &r%sell64%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_64.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_64.toString()}));
		path = "52"; //Sell576
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BROWN_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x576 Item",
						"&eSell x576 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bVerkaufe x576 Items oder soviele noch im Inventar sind",
						"&boder der Shop annimmt für &r%sell576%",
						"&bSell x576 items or as many are still in inventory",
						"&bor the store accepts for &r%sell576%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_576.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_576.toString()}));
		path = "53"; //Sell2304
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BROWN_BANNER}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x2304 Item",
						"&eSell x2304 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bVerkaufe x2304 Items oder soviele noch im Inventar sind",
						"&boder der Shop annimmt für &r%sell2304%",
						"&bSell x2304 items or as many are still in inventory",
						"&bor the store accepts for &r%sell2304%"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_2304.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_2304.toString()}));
		path = "31"; //DiscountInfo
		/*shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&d",
						"&d"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&b",
						"&b",
						"&b",
						"&b"}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_1.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_1.toString()}));*/
		guiKeys.put(GuiType.SHOP, shop);
	}
}