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
import main.java.me.avankziar.sale.spigot.objects.ListedType;
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
		initGuiItemInput();
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
				false}));
		configSpigotKeys.put("SignShop.Tax.BuyInPercent"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				1.0}));
		configSpigotKeys.put("SignShop.Tax.SellInPercent"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				1.1}));
		configSpigotKeys.put("SignShop.ItemHologram.CanSpawn"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configSpigotKeys.put("SignShop.ItemHologram.VisibilityTimeInSeconds"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				5}));
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
						"&eEine Lagerraumerweiterung von &f%past% &eauf &f%now% (+%amount%) &eItems des Shops &f%name%&e.",
						"&eA stockroom extension from &f%past% &eto &f%now% (+%amount%) &eItems of the store &f%name%&e."}));
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
		languageKeys.put("FileError"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cEs existiert ein Fehler in der Datei: &f%file%",
						"&cThere is an error in the file: &f%file%"}));
		languageKeys.put("PlayerInteractListener.ShopItemIsNull", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu kannst das Administrationsgui nicht aufrufen, da der Shop noch kein Item gesetzt hat!",
						"&cYou can not call the administration gui, because the store has not set an item yet!"}));
		languageKeys.put("SignChangeListener.MaterialIsAir", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&4Leer",
						"&4Empty"}));
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
		languageKeys.put("PlayerInteractListener.IsBlackList", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu steht für diesen Shop auf der schwarzen Liste.",
						"&cYou are blacklisted for this store."}));
		languageKeys.put("PlayerInteractListener.IsNotWhiteList", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu steht für diesen Shop nicht auf der weißen Liste.",
						"&cYou are not on the white list for this store."}));
		languageKeys.put("PlayerInteractListener.IsNotCustomList", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu steht für diesen Shop nicht auf der benutzerdefinierten Liste.",
						"&cYou are not on the custom list for this store."}));
		languageKeys.put("PlayerInteractListener.IsNotMember", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu musst Mitglied dieses Shops sein um darauf zugreifen zu können.",
						"&cYou must be a member of this store to access it."}));
		languageKeys.put("SignHandler.Line1Discount", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&#FF8800K: &#ff00ff%amount%",
						"&#FF8800B: &#ff00ff%amount%"}));
		languageKeys.put("SignHandler.Line2Discount", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&#00f07eV: &#ff00ff%amount%",
						"&#00f07eS: &#ff00ff%amount%"}));
		languageKeys.put("SignHandler.Line1", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&#FF8800K: &r%amount%",
						"&#FF8800B: &r%amount%"}));
		languageKeys.put("SignHandler.Line2", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&#00f07eV: &r%amount%",
						"&#00f07eS: &r%amount%"}));
		languageKeys.put("SignHandler.PercentColor.100AndAbove", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&9"}));
		languageKeys.put("SignHandler.PercentColor.Between100And75", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&b"}));
		languageKeys.put("SignHandler.PercentColor.Between75And50", 
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						"&a"}));
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
		languageKeys.put("GuiHandler.InfoLore.Displayname", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDisplayname: &f",
						"&cDisplayname: &f"}));
		languageKeys.put("GuiHandler.InfoLore.Material", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cMaterial: &f",
						"&cMaterial: &f"}));
		languageKeys.put("GuiHandler.InfoLore.ItemFlag", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cItemFlag: ",
						"&cItemFlag: "}));
		languageKeys.put("GuiHandler.InfoLore.Enchantment", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cVerzauberungen: ",
						"&cEnchantments: "}));
		languageKeys.put("GuiHandler.InfoLore.StorageEnchantment", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cGelagerte Verzauberungen: ",
						"&cStored Enchantments: "}));
		languageKeys.put("GuiHandler.InfoLore.Damageable", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cErhaltener Schaden: &f",
						"&cDamage received: &f"}));
		languageKeys.put("GuiHandler.InfoLore.Repairable", 
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cReparaturkosten: &f",
						"&cRepair costs: &f"}));
		languageKeys.put("AdminstrationFunctionHandler.AddStorage.YouDontHaveAccountToWithdraw"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu hast keinen Account um die Kosten abziehen zu können.",
						"&cYou do not have an account to be able to deduct the costs."}));
		languageKeys.put("AdminstrationFunctionHandler.AddStorage.TooManyAlreadyAsStorage"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDer Shop hat schon zu viel Lagerraum, als es ihm bzw. dir möglich ist!",
						"&cThe store already has too much storage space than it is possible for him or you!"}));
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
		languageKeys.put("AdminstrationFunctionHandler.SetStorage.NotOwner"
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cDu bist nicht der Eigentümer des Lagersystem!",
						"&cYou are not the owner of the storage system!"}));
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
		languageKeys.put("AdminstrationFunctionHandler.ListedType."+ListedType.ALL.toString()
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&aAlle",
						"&aAlle"}));
		languageKeys.put("AdminstrationFunctionHandler.ListedType."+ListedType.BLACKLIST.toString()
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cBlackList",
						"&cBlacklist"}));
		languageKeys.put("AdminstrationFunctionHandler.ListedType."+ListedType.WHITELIST.toString()
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&#FF8800Whitelist",
						"&#FF8800Whitelist"}));
		languageKeys.put("AdminstrationFunctionHandler.ListedType."+ListedType.MEMBER.toString()
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bMitglieder",
						"&bMember"}));
		languageKeys.put("AdminstrationFunctionHandler.ListedType."+ListedType.CUSTOM.toString()
				, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dBenutzerdefiniert",
						"&dCustom"}));
		
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
		List<Material> signList = 
				Arrays.asList(new Material[] {
						Material.ACACIA_SIGN,
						Material.ACACIA_WALL_SIGN,
						Material.BIRCH_SIGN,
						Material.BIRCH_WALL_SIGN,
						Material.CRIMSON_SIGN,
						Material.CRIMSON_WALL_SIGN,
						Material.DARK_OAK_SIGN,
						Material.DARK_OAK_WALL_SIGN,
						Material.JUNGLE_SIGN,
						Material.JUNGLE_WALL_SIGN,
						Material.MANGROVE_SIGN,
						Material.MANGROVE_WALL_SIGN,
						Material.OAK_SIGN,
						Material.OAK_WALL_SIGN,
						Material.SPRUCE_SIGN,
						Material.SPRUCE_WALL_SIGN,
						Material.WARPED_SIGN,
						Material.WARPED_WALL_SIGN,
				});
		for(Material m : signList)
		{
			String c = "";
			switch(m)
			{
			default: continue;
			case OAK_SIGN:
			case OAK_WALL_SIGN:
			case BIRCH_SIGN:
			case BIRCH_WALL_SIGN:
			case JUNGLE_SIGN:
			case JUNGLE_WALL_SIGN:
				c = "&0"; break;
			case SPRUCE_SIGN:
			case SPRUCE_WALL_SIGN:
			case ACACIA_SIGN:
			case ACACIA_WALL_SIGN:
			case DARK_OAK_SIGN:
			case DARK_OAK_WALL_SIGN:
			case MANGROVE_SIGN:
			case MANGROVE_WALL_SIGN:
			case CRIMSON_SIGN:
			case CRIMSON_WALL_SIGN:
				c = "&f"; break;
			case WARPED_SIGN:
			case WARPED_WALL_SIGN:
				c = "&e"; break;
			}
			matlanguageKeys.put(m.toString()+"_SignStartColor",
					new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
							c}));
		}
		for(Material m : Material.values())
		{
			String ger = "";
			String eng = "";
			switch(m)
			{
			default:
				//ger = "ger = \""+m.toString()+"\"; eng = \""+m.toString()+"\"; break;"; eng = m.toString(); break;
				ger = eng = m.toString(); break;
			case AIR: ger = "Luft"; eng = "AIR"; break;
			case STONE: ger = "Stein"; eng = "STONE"; break;
			case GRANITE: ger = "Granit"; eng = "GRANITE"; break;
			case POLISHED_GRANITE: ger = "polierter Granit"; eng = "POLISHED_GRANITE"; break;
			case DIORITE: ger = "Diorit"; eng = "DIORITE"; break;
			case POLISHED_DIORITE: ger = "polierter Diorit"; eng = "POLISHED_DIORITE"; break;
			case ANDESITE: ger = "Andesit"; eng = "ANDESITE"; break;
			case POLISHED_ANDESITE: ger = "polierter Andesit"; eng = "POLISHED_ANDESITE"; break;
			case DEEPSLATE: ger = "Tiefenschiefer"; eng = "DEEPSLATE"; break;
			case COBBLED_DEEPSLATE: ger = "Bruchtiefenschiefer"; eng = "COBBLED_DEEPSLATE"; break;
			case POLISHED_DEEPSLATE: ger = "polierter Tiefenschiefer"; eng = "POLISHED_DEEPSLATE"; break;
			case CALCITE: ger = "Kalzit"; eng = "CALCITE"; break;
			case TUFF: ger = "Tuffstein"; eng = "TUFF"; break;
			case DRIPSTONE_BLOCK: ger = "Tropfsteinblock"; eng = "DRIPSTONE_BLOCK"; break;
			case GRASS_BLOCK: ger = "Grasblock"; eng = "GRASS_BLOCK"; break;
			case DIRT: ger = "Erde"; eng = "DIRT"; break;
			case COARSE_DIRT: ger = "grobe Erde"; eng = "COARSE_DIRT"; break;
			case PODZOL: ger = "Podsol"; eng = "PODZOL"; break;
			case ROOTED_DIRT: ger = "Wurzelerde"; eng = "ROOTED_DIRT"; break;
			case MUD: ger = "Schlamm"; eng = "MUD"; break;
			case CRIMSON_NYLIUM: ger = "Nezel"; eng = "CRIMSON_NYLIUM"; break;
			case WARPED_NYLIUM: ger = "Wirr-Nezel"; eng = "WARPED_NYLIUM"; break;
			case COBBLESTONE: ger = "Bruchstein"; eng = "COBBLESTONE"; break;
			case OAK_PLANKS: ger = "Eichenholzbretter"; eng = "OAK_PLANKS"; break;
			case SPRUCE_PLANKS: ger = "Fichtenholzbretter"; eng = "SPRUCE_PLANKS"; break;
			case BIRCH_PLANKS: ger = "Birkenholzbretter"; eng = "BIRCH_PLANKS"; break;
			case JUNGLE_PLANKS: ger = "Tropenholzbretter"; eng = "JUNGLE_PLANKS"; break;
			case ACACIA_PLANKS: ger = "Akazienholzbretter"; eng = "ACACIA_PLANKS"; break;
			case DARK_OAK_PLANKS: ger = "Schwarzeichenholzbretter"; eng = "DARK_OAK_PLANKS"; break;
			case MANGROVE_PLANKS: ger = "Mangrovenbretter"; eng = "MANGROVE_PLANKS"; break;
			case CRIMSON_PLANKS: ger = "Karmesinbretter"; eng = "CRIMSON_PLANKS"; break;
			case WARPED_PLANKS: ger = "Wirrbretter"; eng = "WARPED_PLANKS"; break;
			case OAK_SAPLING: ger = "Eichensetzling"; eng = "OAK_SAPLING"; break;
			case SPRUCE_SAPLING: ger = "Fichtensetzling"; eng = "SPRUCE_SAPLING"; break;
			case BIRCH_SAPLING: ger = "Birkensetzling"; eng = "BIRCH_SAPLING"; break;
			case JUNGLE_SAPLING: ger = "Tropenbaumsetzling"; eng = "JUNGLE_SAPLING"; break;
			case ACACIA_SAPLING: ger = "Akaziensetzling"; eng = "ACACIA_SAPLING"; break;
			case DARK_OAK_SAPLING: ger = "Schwarzeichensetzling"; eng = "DARK_OAK_SAPLING"; break;
			case MANGROVE_PROPAGULE: ger = "Mangroven-Keimling"; eng = "MANGROVE_PROPAGULE"; break;
			case BEDROCK: ger = "Grundgestein"; eng = "BEDROCK"; break;
			case SAND: ger = "Sand"; eng = "SAND"; break;
			case RED_SAND: ger = "roter Sand"; eng = "RED_SAND"; break;
			case GRAVEL: ger = "Kies"; eng = "GRAVEL"; break;
			case COAL_ORE: ger = "Steinkohle"; eng = "COAL_ORE"; break;
			case DEEPSLATE_COAL_ORE: ger = "Tiefenschiefer-Steinkohle"; eng = "DEEPSLATE_COAL_ORE"; break;
			case IRON_ORE: ger = "Eisenerz"; eng = "IRON_ORE"; break;
			case DEEPSLATE_IRON_ORE: ger = "Tiefenschiefer-Eisenerz"; eng = "DEEPSLATE_IRON_ORE"; break;
			case COPPER_ORE: ger = "Kupfererz"; eng = "COPPER_ORE"; break;
			case DEEPSLATE_COPPER_ORE: ger = "Tiefenschiefer-Kupfererz"; eng = "DEEPSLATE_COPPER_ORE"; break;
			case GOLD_ORE: ger = "GOLD_ORE"; eng = "Golderz"; break;
			case DEEPSLATE_GOLD_ORE: ger = "Tiefenschiefer-Golderz"; eng = "DEEPSLATE_GOLD_ORE"; break;
			case REDSTONE_ORE: ger = "REDSTONE_ORE"; eng = "Redstone-Erz"; break;
			case DEEPSLATE_REDSTONE_ORE: ger = "Tiefenschiefer-Redstone-Erz"; eng = "DEEPSLATE_REDSTONE_ORE"; break;
			case EMERALD_ORE: ger = "Smaragderz"; eng = "EMERALD_ORE"; break;
			case DEEPSLATE_EMERALD_ORE: ger = "Tiefenschiefer-Smaragderz"; eng = "DEEPSLATE_EMERALD_ORE"; break;
			case LAPIS_ORE: ger = "Lapislazulierz"; eng = "LAPIS_ORE"; break;
			case DEEPSLATE_LAPIS_ORE: ger = "Tiefenschiefer-Lapislazulierz"; eng = "DEEPSLATE_LAPIS_ORE"; break;
			case DIAMOND_ORE: ger = "Diamanterz"; eng = "DIAMOND_ORE"; break;
			case DEEPSLATE_DIAMOND_ORE: ger = "Tiefenschiefer-Diamanterz"; eng = "DEEPSLATE_DIAMOND_ORE"; break;
			case NETHER_GOLD_ORE: ger = "Nethergolderz"; eng = "NETHER_GOLD_ORE"; break;
			case NETHER_QUARTZ_ORE: ger = "Netherquarzerz"; eng = "NETHER_QUARTZ_ORE"; break;
			case ANCIENT_DEBRIS: ger = "Antiker Schrott"; eng = "ANCIENT_DEBRIS"; break;
			case COAL_BLOCK: ger = "Kohleblock"; eng = "COAL_BLOCK"; break;
			case RAW_IRON_BLOCK: ger = "Roheisenblock"; eng = "RAW_IRON_BLOCK"; break;
			case RAW_COPPER_BLOCK: ger = "Rohkupferblock"; eng = "RAW_COPPER_BLOCK"; break;
			case RAW_GOLD_BLOCK: ger = "Rohgoldblock"; eng = "RAW_GOLD_BLOCK"; break;
			case AMETHYST_BLOCK: ger = "Amethystblock"; eng = "AMETHYST_BLOCK"; break;
			case BUDDING_AMETHYST: ger = "Amethystknospenblock"; eng = "BUDDING_AMETHYST"; break;
			case IRON_BLOCK: ger = "Eisenblock"; eng = "IRON_BLOCK"; break;
			case COPPER_BLOCK: ger = "Kupferblock"; eng = "COPPER_BLOCK"; break;
			case GOLD_BLOCK: ger = "Goldblock"; eng = "GOLD_BLOCK"; break;
			case DIAMOND_BLOCK: ger = "Diamantblock"; eng = "DIAMOND_BLOCK"; break;
			case NETHERITE_BLOCK: ger = "Netheritblock"; eng = "NETHERITE_BLOCK"; break;
			case EXPOSED_COPPER: ger = "Angelaufener Kupferblock"; eng = "EXPOSED_COPPER"; break;
			case WEATHERED_COPPER: ger = "Verwitterter Kupferblock"; eng = "WEATHERED_COPPER"; break;
			case OXIDIZED_COPPER: ger = "Oxidierter Kupferblock"; eng = "OXIDIZED_COPPER"; break;
			case CUT_COPPER: ger = "Geschnittener Kupferblock"; eng = "CUT_COPPER"; break;
			case EXPOSED_CUT_COPPER: ger = "Angelaufener geschnittener Kupferblock"; eng = "EXPOSED_CUT_COPPER"; break;
			case WEATHERED_CUT_COPPER: ger = "Verwitterter geschnittener Kupferblock"; eng = "WEATHERED_CUT_COPPER"; break;
			case OXIDIZED_CUT_COPPER: ger = "Oxidierter geschnittener Kupferblock"; eng = "OXIDIZED_CUT_COPPER"; break;
			case CUT_COPPER_STAIRS: ger = "Geschnittene Kupfertreppe"; eng = "CUT_COPPER_STAIRS"; break;
			case EXPOSED_CUT_COPPER_STAIRS: ger = "Angelaufene geschnittene Kupfertreppe"; eng = "EXPOSED_CUT_COPPER_STAIRS"; break;
			case WEATHERED_CUT_COPPER_STAIRS: ger = "Verwitterte geschnittene Kupfertreppe"; eng = "WEATHERED_CUT_COPPER_STAIRS"; break;
			case OXIDIZED_CUT_COPPER_STAIRS: ger = "Oxidierte geschnittene Kupfertreppe"; eng = "OXIDIZED_CUT_COPPER_STAIRS"; break;
			case CUT_COPPER_SLAB: ger = "Geschnittene Kupferstufe"; eng = "CUT_COPPER_SLAB"; break;
			case EXPOSED_CUT_COPPER_SLAB: ger = "Angelaufene geschnittene Kupferstufe"; eng = "EXPOSED_CUT_COPPER_SLAB"; break;
			case WEATHERED_CUT_COPPER_SLAB: ger = "Verwitterte geschnittene Kupferstufe"; eng = "WEATHERED_CUT_COPPER_SLAB"; break;
			case OXIDIZED_CUT_COPPER_SLAB: ger = "Oxidierte geschnittene Kupferstufe"; eng = "OXIDIZED_CUT_COPPER_SLAB"; break;
			case WAXED_COPPER_BLOCK: ger = "Gewachster Kupferblock"; eng = "WAXED_COPPER_BLOCK"; break;
			case WAXED_EXPOSED_COPPER: ger = "Gewachster angelaufener Kupferblock"; eng = "WAXED_EXPOSED_COPPER"; break;
			case WAXED_WEATHERED_COPPER: ger = "Gewachster verwitterter Kupferblock"; eng = "WAXED_WEATHERED_COPPER"; break;
			case WAXED_OXIDIZED_COPPER: ger = "Gewachster oxidierter Kupferblock"; eng = "WAXED_OXIDIZED_COPPER"; break;
			case WAXED_CUT_COPPER: ger = "Gewachster geschnittener Kupferblock"; eng = "WAXED_CUT_COPPER"; break;
			case WAXED_EXPOSED_CUT_COPPER: ger = "Gewachster angelaufener geschnittener Kupferblock"; eng = "WAXED_EXPOSED_CUT_COPPER"; break;
			case WAXED_WEATHERED_CUT_COPPER: ger = "Gewachster verwitterter geschnittener Kupferblock"; eng = "WAXED_WEATHERED_CUT_COPPER"; break;
			case WAXED_OXIDIZED_CUT_COPPER: ger = "Gewachster oxidierter geschnittener Kupferblock"; eng = "WAXED_OXIDIZED_CUT_COPPER"; break;
			case WAXED_CUT_COPPER_STAIRS: ger = "Gewachste geschnittene Kupfertreppe"; eng = "WAXED_CUT_COPPER_STAIRS"; break;
			case WAXED_EXPOSED_CUT_COPPER_STAIRS: ger = "Gewachste angelaufene geschnittene Kupfertreppe"; eng = "WAXED_EXPOSED_CUT_COPPER_STAIRS"; break;
			case WAXED_WEATHERED_CUT_COPPER_STAIRS: ger = "Gewachste verwitterte geschnittene Kupfertreppe"; eng = "WAXED_WEATHERED_CUT_COPPER_STAIRS"; break;
			case WAXED_OXIDIZED_CUT_COPPER_STAIRS: ger = "Gewachste oxidierte geschnittene Kupfertreppe"; eng = "WAXED_OXIDIZED_CUT_COPPER_STAIRS"; break;
			case WAXED_CUT_COPPER_SLAB: ger = "Gewachste geschnittene Kupferstufe"; eng = "WAXED_CUT_COPPER_SLAB"; break;
			case WAXED_EXPOSED_CUT_COPPER_SLAB: ger = "Gewachste angelaufene geschnittene Kupferstufe"; eng = "WAXED_EXPOSED_CUT_COPPER_SLAB"; break;
			case WAXED_WEATHERED_CUT_COPPER_SLAB: ger = "Gewachste verwitterte geschnittene Kupferstufe"; eng = "WAXED_WEATHERED_CUT_COPPER_SLAB"; break;
			case WAXED_OXIDIZED_CUT_COPPER_SLAB: ger = "Gewachste oxidierte geschnittene Kupferstufe"; eng = "WAXED_OXIDIZED_CUT_COPPER_SLAB"; break;
			case OAK_LOG: ger = "Eichenstamm"; eng = "OAK_LOG"; break;
			case SPRUCE_LOG: ger = "Fichtenstamm"; eng = "SPRUCE_LOG"; break;
			case BIRCH_LOG: ger = "Birkenstamm"; eng = "BIRCH_LOG"; break;
			case JUNGLE_LOG: ger = "Tropenbaumstamm"; eng = "JUNGLE_LOG"; break;
			case ACACIA_LOG: ger = "Akazienstamm"; eng = "ACACIA_LOG"; break;
			case DARK_OAK_LOG: ger = "Schwarzeichenstamm"; eng = "DARK_OAK_LOG"; break;
			case MANGROVE_LOG: ger = "Mangrovenstamm"; eng = "MANGROVE_LOG"; break;
			case MANGROVE_ROOTS: ger = "Mangrovenwurzeln"; eng = "MANGROVE_ROOTS"; break;
			case MUDDY_MANGROVE_ROOTS: ger = "Schlammige Mangrovenwurzeln"; eng = "MUDDY_MANGROVE_ROOTS"; break;
			case CRIMSON_STEM: ger = "Karmesinstiel"; eng = "CRIMSON_STEM"; break;
			case WARPED_STEM: ger = "Wirrstiel"; eng = "WARPED_STEM"; break;
			case STRIPPED_OAK_LOG: ger = "Entrindeter Eichenstamm"; eng = "STRIPPED_OAK_LOG"; break;
			case STRIPPED_SPRUCE_LOG: ger = "Entrindeter Fichtenstamm"; eng = "STRIPPED_SPRUCE_LOG"; break;
			case STRIPPED_BIRCH_LOG: ger = "Entrindeter Birkenstamm"; eng = "STRIPPED_BIRCH_LOG"; break;
			case STRIPPED_JUNGLE_LOG: ger = "Entrindeter Tropenbaumstamm"; eng = "STRIPPED_JUNGLE_LOG"; break;
			case STRIPPED_ACACIA_LOG: ger = "Entrindeter Akazienstamm"; eng = "STRIPPED_ACACIA_LOG"; break;
			case STRIPPED_DARK_OAK_LOG: ger = "Entrindeter Schwarzeichenstamm"; eng = "STRIPPED_DARK_OAK_LOG"; break;
			case STRIPPED_MANGROVE_LOG: ger = "Entrindeter Mangrovenstamm"; eng = "STRIPPED_MANGROVE_LOG"; break;
			case STRIPPED_CRIMSON_STEM: ger = "Geschälter Karmesinstiel"; eng = "STRIPPED_CRIMSON_STEM"; break;
			case STRIPPED_WARPED_STEM: ger = "Geschälter Wirrstiel"; eng = "STRIPPED_WARPED_STEM"; break;
			case STRIPPED_OAK_WOOD: ger = "Entrindetes Eichenholz"; eng = "STRIPPED_OAK_WOOD"; break;
			case STRIPPED_SPRUCE_WOOD: ger = "Entrindetes Fichtenholz"; eng = "STRIPPED_SPRUCE_WOOD"; break;
			case STRIPPED_BIRCH_WOOD: ger = "Entrindetes Birkenholz"; eng = "STRIPPED_BIRCH_WOOD"; break;
			case STRIPPED_JUNGLE_WOOD: ger = "Entrindetes Tropenholz"; eng = "STRIPPED_JUNGLE_WOOD"; break;
			case STRIPPED_ACACIA_WOOD: ger = "Entrindetes Akazienholz"; eng = "STRIPPED_ACACIA_WOOD"; break;
			case STRIPPED_DARK_OAK_WOOD: ger = "Entrindetes Schwarzeichenholz"; eng = "STRIPPED_DARK_OAK_WOOD"; break;
			case STRIPPED_MANGROVE_WOOD: ger = "Entrindetes Mangrovenholz"; eng = "STRIPPED_MANGROVE_WOOD"; break;
			case STRIPPED_CRIMSON_HYPHAE: ger = "Geschälte Karmesinhyphen"; eng = "STRIPPED_CRIMSON_HYPHAE"; break;
			case STRIPPED_WARPED_HYPHAE: ger = "Geschälte Wirrhyphen"; eng = "STRIPPED_WARPED_HYPHAE"; break;
			case OAK_WOOD: ger = "Eichenholz"; eng = "OAK_WOOD"; break;
			case SPRUCE_WOOD: ger = "Fichtenholz"; eng = "SPRUCE_WOOD"; break;
			case BIRCH_WOOD: ger = "Birkenholz"; eng = "BIRCH_WOOD"; break;
			case JUNGLE_WOOD: ger = "Tropenholz"; eng = "JUNGLE_WOOD"; break;
			case ACACIA_WOOD: ger = "Akazienholz"; eng = "ACACIA_WOOD"; break;
			case DARK_OAK_WOOD: ger = "Schwarzeichenholz"; eng = "DARK_OAK_WOOD"; break;
			case MANGROVE_WOOD: ger = "Mangrovenholz"; eng = "MANGROVE_WOOD"; break;
			case CRIMSON_HYPHAE: ger = "Karmesinhyphen"; eng = "CRIMSON_HYPHAE"; break;
			case WARPED_HYPHAE: ger = "Wirrhyphen"; eng = "WARPED_HYPHAE"; break;
			case OAK_LEAVES: ger = "Eichenlaub"; eng = "OAK_LEAVES"; break;
			case SPRUCE_LEAVES: ger = "Fichtennadeln"; eng = "SPRUCE_LEAVES"; break;
			case BIRCH_LEAVES: ger = "Birkenlaub"; eng = "BIRCH_LEAVES"; break;
			case JUNGLE_LEAVES: ger = "Tropenbaumlaub"; eng = "JUNGLE_LEAVES"; break;
			case ACACIA_LEAVES: ger = "Akazienlaub"; eng = "ACACIA_LEAVES"; break;
			case DARK_OAK_LEAVES: ger = "Schwarzeichenlaub"; eng = "DARK_OAK_LEAVES"; break;
			case MANGROVE_LEAVES: ger = "Mangrovenlaub"; eng = "MANGROVE_LEAVES"; break;
			case AZALEA_LEAVES: ger = "Azaleenlaub"; eng = "AZALEA_LEAVES"; break;
			case FLOWERING_AZALEA_LEAVES: ger = "Blühendes Azaleenlaub"; eng = "FLOWERING_AZALEA_LEAVES"; break;
			case SPONGE: ger = "Schwamm"; eng = "SPONGE"; break;
			case WET_SPONGE: ger = "Nasser Schwamm"; eng = "WET_SPONGE"; break;
			case GLASS: ger = "Glas"; eng = "GLASS"; break;
			case TINTED_GLASS: ger = "Getöntes Glas"; eng = "TINTED_GLASS"; break;
			case LAPIS_BLOCK: ger = "Lapislazuliblock"; eng = "LAPIS_BLOCK"; break;
			case SANDSTONE: ger = "Sandstein"; eng = "SANDSTONE"; break;
			case CHISELED_SANDSTONE: ger = "Gemeißelter Sandstein"; eng = "CHISELED_SANDSTONE"; break;
			case CUT_SANDSTONE: ger = "Geschnittener Sandstein"; eng = "CUT_SANDSTONE"; break;
			case COBWEB: ger = "Spinnennetz"; eng = "COBWEB"; break;
			case GRASS: ger = "Gras"; eng = "GRASS"; break;
			case FERN: ger = "Farn"; eng = "FERN"; break;
			case AZALEA: ger = "Azalee"; eng = "AZALEA"; break;
			case FLOWERING_AZALEA: ger = "Blühende Azalee"; eng = "FLOWERING_AZALEA"; break;
			case DEAD_BUSH: ger = "Toter Busch"; eng = "DEAD_BUSH"; break;
			case SEAGRASS: ger = "Seegras"; eng = "SEAGRASS"; break;
			case SEA_PICKLE: ger = "Seegurke"; eng = "SEA_PICKLE"; break;
			case WHITE_WOOL: ger = "Weiße Wolle"; eng = "WHITE_WOOL"; break;
			case ORANGE_WOOL: ger = "Orange Wolle"; eng = "ORANGE_WOOL"; break;
			case MAGENTA_WOOL: ger = "Magenta Wolle"; eng = "MAGENTA_WOOL"; break;
			case LIGHT_BLUE_WOOL: ger = "Hellblaue Wolle"; eng = "LIGHT_BLUE_WOOL"; break;
			case YELLOW_WOOL: ger = "Gelbe Wolle"; eng = "YELLOW_WOOL"; break;
			case LIME_WOOL: ger = "Hellgrüne Wolle"; eng = "LIME_WOOL"; break;
			case PINK_WOOL: ger = "Rosa Wolle"; eng = "PINK_WOOL"; break;
			case GRAY_WOOL: ger = "Graue Wolle"; eng = "GRAY_WOOL"; break;
			case LIGHT_GRAY_WOOL: ger = "Hellgraue Wolle"; eng = "LIGHT_GRAY_WOOL"; break;
			case CYAN_WOOL: ger = "Türkise Wolle"; eng = "CYAN_WOOL"; break;
			case PURPLE_WOOL: ger = "Violette Wolle"; eng = "PURPLE_WOOL"; break;
			case BLUE_WOOL: ger = "Blaue Wolle"; eng = "BLUE_WOOL"; break;
			case BROWN_WOOL: ger = "Braune Wolle"; eng = "BROWN_WOOL"; break;
			case GREEN_WOOL: ger = "Grüne Wolle"; eng = "GREEN_WOOL"; break;
			case RED_WOOL: ger = "Rote Wolle"; eng = "RED_WOOL"; break;
			case BLACK_WOOL: ger = "Schwarze Wolle"; eng = "BLACK_WOOL"; break;
			case DANDELION: ger = "Löwenzahn"; eng = "DANDELION"; break;
			case POPPY: ger = "Mohn"; eng = "POPPY"; break;
			case BLUE_ORCHID: ger = "Baue Orchidee"; eng = "BLUE_ORCHID"; break;
			case ALLIUM: ger = "Zierlauch"; eng = "ALLIUM"; break;
			case AZURE_BLUET: ger = "Porzellansternchen"; eng = "AZURE_BLUET"; break;
			case RED_TULIP: ger = "Rote Tulpe"; eng = "RED_TULIP"; break;
			case ORANGE_TULIP: ger = "Orange Tulpe"; eng = "ORANGE_TULIP"; break;
			case WHITE_TULIP: ger = "Weiße Tulpe"; eng = "WHITE_TULIP"; break;
			case PINK_TULIP: ger = "Rosa Tulpe"; eng = "PINK_TULIP"; break;
			case OXEYE_DAISY: ger = "Margerite"; eng = "OXEYE_DAISY"; break;
			case CORNFLOWER: ger = "Kornblume"; eng = "CORNFLOWER"; break;
			case LILY_OF_THE_VALLEY: ger = "Maiglöckchen"; eng = "LILY_OF_THE_VALLEY"; break;
			case WITHER_ROSE: ger = "Wither-Rose"; eng = "WITHER_ROSE"; break;
			case SPORE_BLOSSOM: ger = "Sporenblüte"; eng = "SPORE_BLOSSOM"; break;
			case BROWN_MUSHROOM: ger = "Brauner Pilz"; eng = "BROWN_MUSHROOM"; break;
			case RED_MUSHROOM: ger = "Roter Pilz"; eng = "RED_MUSHROOM"; break;
			case CRIMSON_FUNGUS: ger = "Karmesinpilz"; eng = "CRIMSON_FUNGUS"; break;
			case WARPED_FUNGUS: ger = "Wirrpilz"; eng = "WARPED_FUNGUS"; break;
			case CRIMSON_ROOTS: ger = "Karmesinwurzeln"; eng = "CRIMSON_ROOTS"; break;
			case WARPED_ROOTS: ger = "Wirrwurzeln"; eng = "WARPED_ROOTS"; break;
			case NETHER_SPROUTS: ger = "Nethersprossen"; eng = "NETHER_SPROUTS"; break;
			case WEEPING_VINES: ger = "Trauerranken"; eng = "WEEPING_VINES"; break;
			case TWISTING_VINES: ger = "Zwirbelranken"; eng = "TWISTING_VINES"; break;
			case SUGAR_CANE: ger = "Zuckerrohr"; eng = "SUGAR_CANE"; break;
			case KELP: ger = "Seetang"; eng = "KELP"; break;
			case MOSS_CARPET: ger = "Moosteppich"; eng = "MOSS_CARPET"; break;
			case MOSS_BLOCK: ger = "Moosblock"; eng = "MOSS_BLOCK"; break;
			case HANGING_ROOTS: ger = "Hängende Wurzeln"; eng = "HANGING_ROOTS"; break;
			case BIG_DRIPLEAF: ger = "Großes Tropfblatt"; eng = "BIG_DRIPLEAF"; break;
			case SMALL_DRIPLEAF: ger = "Kleines Tropfblatt"; eng = "SMALL_DRIPLEAF"; break;
			case BAMBOO: ger = "Bambus"; eng = "BAMBOO"; break;
			case OAK_SLAB: ger = "Eichenholzstufe"; eng = "OAK_SLAB"; break;
			case SPRUCE_SLAB: ger = "Fichtenholzstufe"; eng = "SPRUCE_SLAB"; break;
			case BIRCH_SLAB: ger = "Birkenholzstufe"; eng = "BIRCH_SLAB"; break;
			case JUNGLE_SLAB: ger = "Tropenholzstufe"; eng = "JUNGLE_SLAB"; break;
			case ACACIA_SLAB: ger = "Akazienholzstufe"; eng = "ACACIA_SLAB"; break;
			case DARK_OAK_SLAB: ger = "Schwarzeichenholzstufe"; eng = "DARK_OAK_SLAB"; break;
			case MANGROVE_SLAB: ger = "Mangrovenholzstufe"; eng = "MANGROVE_SLAB"; break;
			case CRIMSON_SLAB: ger = "Karmesinstufe"; eng = "CRIMSON_SLAB"; break;
			case WARPED_SLAB: ger = "Wirrstufe"; eng = "WARPED_SLAB"; break;
			case STONE_SLAB: ger = "Steinstufe"; eng = "STONE_SLAB"; break;
			case SMOOTH_STONE_SLAB: ger = "Glatte Steinstufe"; eng = "SMOOTH_STONE_SLAB"; break;
			case SANDSTONE_SLAB: ger = "Sandsteinstufe"; eng = "SANDSTONE_SLAB"; break;
			case CUT_SANDSTONE_SLAB: ger = "Geschnittene Sandsteinstufe"; eng = "CUT_SANDSTONE_SLAB"; break;
			case PETRIFIED_OAK_SLAB: ger = "Versteinerte Eichenholzstufe"; eng = "PETRIFIED_OAK_SLAB"; break;
			case COBBLESTONE_SLAB: ger = "Bruchsteinstufe"; eng = "COBBLESTONE_SLAB"; break;
			case BRICK_SLAB: ger = "Ziegelstufe"; eng = "BRICK_SLAB"; break;
			case STONE_BRICK_SLAB: ger = "Steinziegelstufe"; eng = "STONE_BRICK_SLAB"; break;
			case MUD_BRICK_SLAB: ger = "Schlammziegelstufe"; eng = "MUD_BRICK_SLAB"; break;
			case NETHER_BRICK_SLAB: ger = "Netherziegelstufe"; eng = "NETHER_BRICK_SLAB"; break;
			case QUARTZ_SLAB: ger = "Quarzstufe"; eng = "QUARTZ_SLAB"; break;
			case RED_SANDSTONE_SLAB: ger = "Rote Sandsteinstufe"; eng = "RED_SANDSTONE_SLAB"; break;
			case CUT_RED_SANDSTONE_SLAB: ger = "Geschnittene rote Sandsteinstufe"; eng = "CUT_RED_SANDSTONE_SLAB"; break;
			case PURPUR_SLAB: ger = "Purpurstufe"; eng = "PURPUR_SLAB"; break;
			case PRISMARINE_SLAB: ger = "Prismarinstufe"; eng = "PRISMARINE_SLAB"; break;
			case PRISMARINE_BRICK_SLAB: ger = "Prismarinziegelstufe"; eng = "PRISMARINE_BRICK_SLAB"; break;
			case DARK_PRISMARINE_SLAB: ger = "Dunkle Prismarinstufe"; eng = "DARK_PRISMARINE_SLAB"; break;
			case SMOOTH_QUARTZ: ger = "Glatter Quarzblock"; eng = "SMOOTH_QUARTZ"; break;
			case SMOOTH_RED_SANDSTONE: ger = "Glatter roter Sandstein"; eng = "SMOOTH_RED_SANDSTONE"; break;
			case SMOOTH_SANDSTONE: ger = "Glatter Sandstein"; eng = "SMOOTH_SANDSTONE"; break;
			case SMOOTH_STONE: ger = "Glatter Stein"; eng = "SMOOTH_STONE"; break;
			case BRICKS: ger = "Ziegelsteine"; eng = "BRICKS"; break;
			case BOOKSHELF: ger = "Bücherregal"; eng = "BOOKSHELF"; break;
			case MOSSY_COBBLESTONE: ger = "Bemooster Bruchstein"; eng = "MOSSY_COBBLESTONE"; break;
			case OBSIDIAN: ger = "Obsidian"; eng = "OBSIDIAN"; break;
			case TORCH: ger = "Fackel"; eng = "TORCH"; break;
			case END_ROD: ger = "Endstab"; eng = "END_ROD"; break;
			case CHORUS_PLANT: ger = "Choruspflanze"; eng = "CHORUS_PLANT"; break;
			case CHORUS_FLOWER: ger = "Chorusblüte"; eng = "CHORUS_FLOWER"; break;
			case PURPUR_BLOCK: ger = "Purpurblock"; eng = "PURPUR_BLOCK"; break;
			case PURPUR_PILLAR: ger = "Purpursäule"; eng = "PURPUR_PILLAR"; break;
			case PURPUR_STAIRS: ger = "Purpurtreppe"; eng = "PURPUR_STAIRS"; break;
			case SPAWNER: ger = "SPAWNER"; eng = "SPAWNER"; break;
			case CHEST: ger = "Truhe"; eng = "CHEST"; break;
			case CRAFTING_TABLE: ger = "Werkbank"; eng = "CRAFTING_TABLE"; break;
			case FARMLAND: ger = "Ackerboden"; eng = "FARMLAND"; break;
			case FURNACE: ger = "Ofen"; eng = "FURNACE"; break;
			case LADDER: ger = "Leiter"; eng = "LADDER"; break;
			case COBBLESTONE_STAIRS: ger = "Bruchsteinstufe"; eng = "COBBLESTONE_STAIRS"; break;
			case SNOW: ger = "Schnee"; eng = "SNOW"; break;
			case ICE: ger = "Eis"; eng = "ICE"; break;
			case SNOW_BLOCK: ger = "Schneeblock"; eng = "SNOW_BLOCK"; break;
			case CACTUS: ger = "kaktus"; eng = "CACTUS"; break;
			case CLAY: ger = "Ton"; eng = "CLAY"; break;
			case JUKEBOX: ger = "Plattenspieler"; eng = "JUKEBOX"; break;
			case OAK_FENCE: ger = "Eichenholzzaun"; eng = "OAK_FENCE"; break;
			case SPRUCE_FENCE: ger = "Fichtenholzzaun"; eng = "SPRUCE_FENCE"; break;
			case BIRCH_FENCE: ger = "Birkenholzzaun"; eng = "BIRCH_FENCE"; break;
			case JUNGLE_FENCE: ger = "Tropenholzzaun"; eng = "JUNGLE_FENCE"; break;
			case ACACIA_FENCE: ger = "Akazienholzzaun"; eng = "ACACIA_FENCE"; break;
			case DARK_OAK_FENCE: ger = "Schwarzeichenholzzaun"; eng = "DARK_OAK_FENCE"; break;
			case MANGROVE_FENCE: ger = "Mangrovenholzzaun"; eng = "MANGROVE_FENCE"; break;
			case CRIMSON_FENCE: ger = "Karmesinzaun"; eng = "CRIMSON_FENCE"; break;
			case WARPED_FENCE: ger = "Wirrzaun"; eng = "WARPED_FENCE"; break;
			case PUMPKIN: ger = "Kürbis"; eng = "PUMPKIN"; break;
			case CARVED_PUMPKIN: ger = "Geschnitzter Kürbis"; eng = "CARVED_PUMPKIN"; break;
			case JACK_O_LANTERN: ger = "Kürbislaterne"; eng = "JACK_O_LANTERN"; break;
			case NETHERRACK: ger = "Netherrack"; eng = "NETHERRACK"; break;
			case SOUL_SAND: ger = "Seelensand"; eng = "SOUL_SAND"; break;
			case SOUL_SOIL: ger = "Seelenerde"; eng = "SOUL_SOIL"; break;
			case BASALT: ger = "Basalt"; eng = "BASALT"; break;
			case POLISHED_BASALT: ger = "Polierter Basalt"; eng = "POLISHED_BASALT"; break;
			case SMOOTH_BASALT: ger = "Glatter Basalt"; eng = "SMOOTH_BASALT"; break;
			case SOUL_TORCH: ger = "Seelenfackel"; eng = "SOUL_TORCH"; break;
			case GLOWSTONE: ger = "Leuchtstein"; eng = "GLOWSTONE"; break;
			case INFESTED_STONE: ger = "Befallener Stein"; eng = "INFESTED_STONE"; break;
			case INFESTED_COBBLESTONE: ger = "Befallener Bruchstein"; eng = "INFESTED_COBBLESTONE"; break;
			case INFESTED_STONE_BRICKS: ger = "Befallene Steinziegel"; eng = "INFESTED_STONE_BRICKS"; break;
			case INFESTED_MOSSY_STONE_BRICKS: ger = "Befallene bemooste Steinziegel"; eng = "INFESTED_MOSSY_STONE_BRICKS"; break;
			case INFESTED_CRACKED_STONE_BRICKS: ger = "Befallene rissige Steinziegel"; eng = "INFESTED_CRACKED_STONE_BRICKS"; break;
			case INFESTED_CHISELED_STONE_BRICKS: ger = "Befallene gemeißelte Steinziegel"; eng = "INFESTED_CHISELED_STONE_BRICKS"; break;
			case INFESTED_DEEPSLATE: ger = "Befallener Tiefenschiefer"; eng = "INFESTED_DEEPSLATE"; break;
			case STONE_BRICKS: ger = "Steinziegel"; eng = "STONE_BRICKS"; break;
			case MOSSY_STONE_BRICKS: ger = "Bemooste Steinziegel"; eng = "MOSSY_STONE_BRICKS"; break;
			case CRACKED_STONE_BRICKS: ger = "Rissige Steinziegel"; eng = "CRACKED_STONE_BRICKS"; break;
			case CHISELED_STONE_BRICKS: ger = "Gemeißelte Steinziegel"; eng = "CHISELED_STONE_BRICKS"; break;
			case PACKED_MUD: ger = "Fester Schlamm"; eng = "PACKED_MUD"; break;
			case MUD_BRICKS: ger = "Schlammziegel"; eng = "MUD_BRICKS"; break;
			case DEEPSLATE_BRICKS: ger = "Tiefenschieferziegel"; eng = "DEEPSLATE_BRICKS"; break;
			case CRACKED_DEEPSLATE_BRICKS: ger = "Rissige Tiefenschieferziegel"; eng = "CRACKED_DEEPSLATE_BRICKS"; break;
			case DEEPSLATE_TILES: ger = "Tiefenschieferfliesen"; eng = "DEEPSLATE_TILES"; break;
			case CRACKED_DEEPSLATE_TILES: ger = "Rissige Tiefenschieferfliesen"; eng = "CRACKED_DEEPSLATE_TILES"; break;
			case CHISELED_DEEPSLATE: ger = "Gemeißelter Tiefenschiefer"; eng = "CHISELED_DEEPSLATE"; break;
			case REINFORCED_DEEPSLATE: ger = "Verstärkter Tiefenschiefer"; eng = "REINFORCED_DEEPSLATE"; break;
			case BROWN_MUSHROOM_BLOCK: ger = "Brauner Pilzblock"; eng = "BROWN_MUSHROOM_BLOCK"; break;
			case RED_MUSHROOM_BLOCK: ger = "Roter Pilzblock"; eng = "RED_MUSHROOM_BLOCK"; break;
			case MUSHROOM_STEM: ger = "Pilzstiel"; eng = "MUSHROOM_STEM"; break;
			case IRON_BARS: ger = "Eisengitter"; eng = "IRON_BARS"; break;
			case CHAIN: ger = "Kette"; eng = "CHAIN"; break;
			case GLASS_PANE: ger = "Glasscheibe"; eng = "GLASS_PANE"; break;
			case MELON: ger = "Melone"; eng = "MELON"; break;
			case VINE: ger = "Ranken"; eng = "VINE"; break;
			case GLOW_LICHEN: ger = "Leuchtflechte"; eng = "GLOW_LICHEN"; break;
			case BRICK_STAIRS: ger = "Ziegeltreppe"; eng = "BRICK_STAIRS"; break;
			case STONE_BRICK_STAIRS: ger = "Steinziegeltreppe"; eng = "STONE_BRICK_STAIRS"; break;
			case MUD_BRICK_STAIRS: ger = "Schlammziegeltreppe"; eng = "MUD_BRICK_STAIRS"; break;
			case MYCELIUM: ger = "Myzel"; eng = "MYCELIUM"; break;
			case LILY_PAD: ger = "Seerosenblatt"; eng = "LILY_PAD"; break;
			case NETHER_BRICKS: ger = "Netherziegel"; eng = "NETHER_BRICKS"; break;
			case CRACKED_NETHER_BRICKS: ger = "Rissige Netherziegel"; eng = "CRACKED_NETHER_BRICKS"; break;
			case CHISELED_NETHER_BRICKS: ger = "Gemeißelte Netherziegel"; eng = "CHISELED_NETHER_BRICKS"; break;
			case NETHER_BRICK_FENCE: ger = "Netherziegelzaun"; eng = "NETHER_BRICK_FENCE"; break;
			case NETHER_BRICK_STAIRS: ger = "Netherziegeltreppe"; eng = "NETHER_BRICK_STAIRS"; break;
			case SCULK: ger = "Sculk"; eng = "SCULK"; break;
			case SCULK_VEIN: ger = "Sculk-Ader"; eng = "SCULK_VEIN"; break;
			case SCULK_CATALYST: ger = "Sculk-Katalysator"; eng = "SCULK_CATALYST"; break;
			case SCULK_SHRIEKER: ger = "Sculk-Kreischer"; eng = "SCULK_SHRIEKER"; break;
			case ENCHANTING_TABLE: ger = "Zaubertisch"; eng = "ENCHANTING_TABLE"; break;
			case END_PORTAL_FRAME: ger = "Endportalrahmen"; eng = "END_PORTAL_FRAME"; break;
			case END_STONE: ger = "Endstein"; eng = "END_STONE"; break;
			case END_STONE_BRICKS: ger = "Endsteinziegel"; eng = "END_STONE_BRICKS"; break;
			case DRAGON_EGG: ger = "Drachenei"; eng = "DRAGON_EGG"; break;
			case SANDSTONE_STAIRS: ger = "Sandsteintreppe"; eng = "SANDSTONE_STAIRS"; break;
			case ENDER_CHEST: ger = "Endertruhe"; eng = "ENDER_CHEST"; break;
			case EMERALD_BLOCK: ger = "Smaragdblock"; eng = "EMERALD_BLOCK"; break;
			case OAK_STAIRS: ger = "Eichenholztreppe"; eng = "OAK_STAIRS"; break;
			case SPRUCE_STAIRS: ger = "Fichtenholztreppe"; eng = "SPRUCE_STAIRS"; break;
			case BIRCH_STAIRS: ger = "Birkenholztreppe"; eng = "BIRCH_STAIRS"; break;
			case JUNGLE_STAIRS: ger = "Tropenholztreppe"; eng = "JUNGLE_STAIRS"; break;
			case ACACIA_STAIRS: ger = "Akazienholztreppe"; eng = "ACACIA_STAIRS"; break;
			case DARK_OAK_STAIRS: ger = "Schwarzeichenholztreppe"; eng = "DARK_OAK_STAIRS"; break;
			case MANGROVE_STAIRS: ger = "Mangrovenholztreppe"; eng = "MANGROVE_STAIRS"; break;
			case CRIMSON_STAIRS: ger = "Karmesintreppe"; eng = "CRIMSON_STAIRS"; break;
			case WARPED_STAIRS: ger = "Wirrtreppe"; eng = "WARPED_STAIRS"; break;
			case COMMAND_BLOCK: ger = "Befehlsblock"; eng = "COMMAND_BLOCK"; break;
			case BEACON: ger = "Leuchtfeuer"; eng = "BEACON"; break;
			case COBBLESTONE_WALL: ger = "Bruchsteinmauer"; eng = "COBBLESTONE_WALL"; break;
			case MOSSY_COBBLESTONE_WALL: ger = "Bemooste Bruchsteinmauer"; eng = "MOSSY_COBBLESTONE_WALL"; break;
			case BRICK_WALL: ger = "Ziegelsteinmauer"; eng = "BRICK_WALL"; break;
			case PRISMARINE_WALL: ger = "Prismarinmauer"; eng = "PRISMARINE_WALL"; break;
			case RED_SANDSTONE_WALL: ger = "Rote Sandsteimauer"; eng = "RED_SANDSTONE_WALL"; break;
			case MOSSY_STONE_BRICK_WALL: ger = "Bemooste Steinziegelmauer"; eng = "MOSSY_STONE_BRICK_WALL"; break;
			case GRANITE_WALL: ger = "Granitmauer"; eng = "GRANITE_WALL"; break;
			case STONE_BRICK_WALL: ger = "Steinziegelmauer"; eng = "STONE_BRICK_WALL"; break;
			case MUD_BRICK_WALL: ger = "Schlammziegelmauer"; eng = "MUD_BRICK_WALL"; break;
			case NETHER_BRICK_WALL: ger = "Netherziegelmauer"; eng = "NETHER_BRICK_WALL"; break;
			case ANDESITE_WALL: ger = "Andesitmauer"; eng = "ANDESITE_WALL"; break;
			case RED_NETHER_BRICK_WALL: ger = "Rote Netherziegelmauer"; eng = "RED_NETHER_BRICK_WALL"; break;
			case SANDSTONE_WALL: ger = "Sandsteinmauer"; eng = "SANDSTONE_WALL"; break;
			case END_STONE_BRICK_WALL: ger = "Endsteinziegelmauer"; eng = "END_STONE_BRICK_WALL"; break;
			case DIORITE_WALL: ger = "Dioritmauer"; eng = "DIORITE_WALL"; break;
			case BLACKSTONE_WALL: ger = "Schwarzsteinmauer"; eng = "BLACKSTONE_WALL"; break;
			case POLISHED_BLACKSTONE_WALL: ger = "Polierte Schwarzsteinmauer"; eng = "POLISHED_BLACKSTONE_WALL"; break;
			case POLISHED_BLACKSTONE_BRICK_WALL: ger = "Polierte Schwarzsteinziegelmauer"; eng = "POLISHED_BLACKSTONE_BRICK_WALL"; break;
			case COBBLED_DEEPSLATE_WALL: ger = "Bruchtiefenschiefermauer"; eng = "COBBLED_DEEPSLATE_WALL"; break;
			case POLISHED_DEEPSLATE_WALL: ger = "Polierte Tiefenschiefermauer"; eng = "POLISHED_DEEPSLATE_WALL"; break;
			case DEEPSLATE_BRICK_WALL: ger = "Tiefenschieferziegelmauer"; eng = "DEEPSLATE_BRICK_WALL"; break;
			case DEEPSLATE_TILE_WALL: ger = "Tiefenschieferfliesenmauer"; eng = "DEEPSLATE_TILE_WALL"; break;
			case ANVIL: ger = "Amboss"; eng = "ANVIL"; break;
			case CHIPPED_ANVIL: ger = "Angeschlagener Amboss"; eng = "CHIPPED_ANVIL"; break;
			case DAMAGED_ANVIL: ger = "Beschädigter Amboss"; eng = "DAMAGED_ANVIL"; break;
			case CHISELED_QUARTZ_BLOCK: ger = "Gemeißelter Quarzblock"; eng = "CHISELED_QUARTZ_BLOCK"; break;
			case QUARTZ_BLOCK: ger = "Quarzblock"; eng = "QUARTZ_BLOCK"; break;
			case QUARTZ_BRICKS: ger = "Quarzziegel"; eng = "QUARTZ_BRICKS"; break;
			case QUARTZ_PILLAR: ger = "Quarzsäule"; eng = "QUARTZ_PILLAR"; break;
			case QUARTZ_STAIRS: ger = "Quarztreppe"; eng = "QUARTZ_STAIRS"; break;
			case WHITE_TERRACOTTA: ger = "Weiße Keramik"; eng = "WHITE_TERRACOTTA"; break;
			case ORANGE_TERRACOTTA: ger = "Orange Keramik"; eng = "ORANGE_TERRACOTTA"; break;
			case MAGENTA_TERRACOTTA: ger = "Magenta Keramik"; eng = "MAGENTA_TERRACOTTA"; break;
			case LIGHT_BLUE_TERRACOTTA: ger = "Hellblaue Keramik"; eng = "LIGHT_BLUE_TERRACOTTA"; break;
			case YELLOW_TERRACOTTA: ger = "Gelbe Keramik"; eng = "YELLOW_TERRACOTTA"; break;
			case LIME_TERRACOTTA: ger = "Hellgrüne Keramik"; eng = "LIME_TERRACOTTA"; break;
			case PINK_TERRACOTTA: ger = "Rosa Keramik"; eng = "PINK_TERRACOTTA"; break;
			case GRAY_TERRACOTTA: ger = "Graue Keramik"; eng = "GRAY_TERRACOTTA"; break;
			case LIGHT_GRAY_TERRACOTTA: ger = "Hellgraue Keramik"; eng = "LIGHT_GRAY_TERRACOTTA"; break;
			case CYAN_TERRACOTTA: ger = "Türkise Keramik"; eng = "CYAN_TERRACOTTA"; break;
			case PURPLE_TERRACOTTA: ger = "Violette Keramik"; eng = "PURPLE_TERRACOTTA"; break;
			case BLUE_TERRACOTTA: ger = "Blaue Keramik"; eng = "BLUE_TERRACOTTA"; break;
			case BROWN_TERRACOTTA: ger = "Braune Keramik"; eng = "BROWN_TERRACOTTA"; break;
			case GREEN_TERRACOTTA: ger = "Grüne Keramik"; eng = "GREEN_TERRACOTTA"; break;
			case RED_TERRACOTTA: ger = "Rote Keramik"; eng = "RED_TERRACOTTA"; break;
			case BLACK_TERRACOTTA: ger = "Schwarze Keramik"; eng = "BLACK_TERRACOTTA"; break;
			case BARRIER: ger = "Barriere"; eng = "BARRIER"; break;
			case LIGHT: ger = "Lichtblock"; eng = "LIGHT"; break;
			case HAY_BLOCK: ger = "Strohballen"; eng = "HAY_BLOCK"; break;
			case WHITE_CARPET: ger = "Weißer Teppich"; eng = "WHITE_CARPET"; break;
			case ORANGE_CARPET: ger = "Oranger Teppich"; eng = "ORANGE_CARPET"; break;
			case MAGENTA_CARPET: ger = "Magenta Teppich"; eng = "MAGENTA_CARPET"; break;
			case LIGHT_BLUE_CARPET: ger = "Hellblauer Teppich"; eng = "LIGHT_BLUE_CARPET"; break;
			case YELLOW_CARPET: ger = "Gelber Teppich"; eng = "YELLOW_CARPET"; break;
			case LIME_CARPET: ger = "Hellgrüner Teppich"; eng = "LIME_CARPET"; break;
			case PINK_CARPET: ger = "Rosa Teppich"; eng = "PINK_CARPET"; break;
			case GRAY_CARPET: ger = "Grauer Teppich"; eng = "GRAY_CARPET"; break;
			case LIGHT_GRAY_CARPET: ger = "Hellgrauer Teppich"; eng = "LIGHT_GRAY_CARPET"; break;
			case CYAN_CARPET: ger = "Türkiser Teppich"; eng = "CYAN_CARPET"; break;
			case PURPLE_CARPET: ger = "Violetter Teppich"; eng = "PURPLE_CARPET"; break;
			case BLUE_CARPET: ger = "Blauer Teppich"; eng = "BLUE_CARPET"; break;
			case BROWN_CARPET: ger = "Brauner Teppich"; eng = "BROWN_CARPET"; break;
			case GREEN_CARPET: ger = "Grüner Teppich"; eng = "GREEN_CARPET"; break;
			case RED_CARPET: ger = "Rpter Teppich"; eng = "RED_CARPET"; break;
			case BLACK_CARPET: ger = "Schwarzer Teppich"; eng = "BLACK_CARPET"; break;
			case TERRACOTTA: ger = "Keramik"; eng = "TERRACOTTA"; break;
			case PACKED_ICE: ger = "Packeis"; eng = "PACKED_ICE"; break;
			case DIRT_PATH: ger = "Trampelpfad"; eng = "DIRT_PATH"; break;
			case SUNFLOWER: ger = "Sonnenblume"; eng = "SUNFLOWER"; break;
			case LILAC: ger = "Flieder"; eng = "LILAC"; break;
			case ROSE_BUSH: ger = "Rosenstrauch"; eng = "ROSE_BUSH"; break;
			case PEONY: ger = "Pfingstrose"; eng = "PEONY"; break;
			case TALL_GRASS: ger = "Hohes Gras"; eng = "TALL_GRASS"; break;
			case LARGE_FERN: ger = "Großer Farn"; eng = "LARGE_FERN"; break;
			case WHITE_STAINED_GLASS: ger = "Weißes Glas"; eng = "WHITE_STAINED_GLASS"; break;
			case ORANGE_STAINED_GLASS: ger = "Oranges Glas"; eng = "ORANGE_STAINED_GLASS"; break;
			case MAGENTA_STAINED_GLASS: ger = "Magenta Glas"; eng = "MAGENTA_STAINED_GLASS"; break;
			case LIGHT_BLUE_STAINED_GLASS: ger = "Hellblaues Glas"; eng = "LIGHT_BLUE_STAINED_GLASS"; break;
			case YELLOW_STAINED_GLASS: ger = "Gelbes Glas"; eng = "YELLOW_STAINED_GLASS"; break;
			case LIME_STAINED_GLASS: ger = "Hellgrünes Glas"; eng = "LIME_STAINED_GLASS"; break;
			case PINK_STAINED_GLASS: ger = "Rosa Glas"; eng = "PINK_STAINED_GLASS"; break;
			case GRAY_STAINED_GLASS: ger = "Graues Glas"; eng = "GRAY_STAINED_GLASS"; break;
			case LIGHT_GRAY_STAINED_GLASS: ger = "Hellgraues Glas"; eng = "LIGHT_GRAY_STAINED_GLASS"; break;
			case CYAN_STAINED_GLASS: ger = "Türkises Glas"; eng = "CYAN_STAINED_GLASS"; break;
			case PURPLE_STAINED_GLASS: ger = "Violettes Glas"; eng = "PURPLE_STAINED_GLASS"; break;
			case BLUE_STAINED_GLASS: ger = "Blaues Glas"; eng = "BLUE_STAINED_GLASS"; break;
			case BROWN_STAINED_GLASS: ger = "Braunes Glas"; eng = "BROWN_STAINED_GLASS"; break;
			case GREEN_STAINED_GLASS: ger = "Grünes Glas"; eng = "GREEN_STAINED_GLASS"; break;
			case RED_STAINED_GLASS: ger = "Rotes Glas"; eng = "RED_STAINED_GLASS"; break;
			case BLACK_STAINED_GLASS: ger = "Schwarzes Glas"; eng = "BLACK_STAINED_GLASS"; break;
			case WHITE_STAINED_GLASS_PANE: ger = "Weiße Glasscheibe"; eng = "WHITE_STAINED_GLASS_PANE"; break;
			case ORANGE_STAINED_GLASS_PANE: ger = "Orange Glasscheibe"; eng = "ORANGE_STAINED_GLASS_PANE"; break;
			case MAGENTA_STAINED_GLASS_PANE: ger = "Magenta Glasscheibe"; eng = "MAGENTA_STAINED_GLASS_PANE"; break;
			case LIGHT_BLUE_STAINED_GLASS_PANE: ger = "Hellblaue Glasscheibe"; eng = "LIGHT_BLUE_STAINED_GLASS_PANE"; break;
			case YELLOW_STAINED_GLASS_PANE: ger = "Gelbe Glasscheibe"; eng = "YELLOW_STAINED_GLASS_PANE"; break;
			case LIME_STAINED_GLASS_PANE: ger = "Hellgrüne Glasscheibe"; eng = "LIME_STAINED_GLASS_PANE"; break;
			case PINK_STAINED_GLASS_PANE: ger = "Rosa Glasscheibe"; eng = "PINK_STAINED_GLASS_PANE"; break;
			case GRAY_STAINED_GLASS_PANE: ger = "Graue Glasscheibe"; eng = "GRAY_STAINED_GLASS_PANE"; break;
			case LIGHT_GRAY_STAINED_GLASS_PANE: ger = "Hellgraue Glasscheibe"; eng = "LIGHT_GRAY_STAINED_GLASS_PANE"; break;
			case CYAN_STAINED_GLASS_PANE: ger = "Türkise Glasscheibe"; eng = "CYAN_STAINED_GLASS_PANE"; break;
			case PURPLE_STAINED_GLASS_PANE: ger = "Violette Glasscheibe"; eng = "PURPLE_STAINED_GLASS_PANE"; break;
			case BLUE_STAINED_GLASS_PANE: ger = "Blaue Glasscheibe"; eng = "BLUE_STAINED_GLASS_PANE"; break;
			case BROWN_STAINED_GLASS_PANE: ger = "Braune Glasscheibe"; eng = "BROWN_STAINED_GLASS_PANE"; break;
			case GREEN_STAINED_GLASS_PANE: ger = "Grüne Glasscheibe"; eng = "GREEN_STAINED_GLASS_PANE"; break;
			case RED_STAINED_GLASS_PANE: ger = "Rote Glasscheibe"; eng = "RED_STAINED_GLASS_PANE"; break;
			case BLACK_STAINED_GLASS_PANE: ger = "Schwarze Glasscheibe"; eng = "BLACK_STAINED_GLASS_PANE"; break;
			case PRISMARINE: ger = "Prismarin"; eng = "PRISMARINE"; break;
			case PRISMARINE_BRICKS: ger = "Prismarinziegel"; eng = "PRISMARINE_BRICKS"; break;
			case DARK_PRISMARINE: ger = "Dunkler Prismarin"; eng = "DARK_PRISMARINE"; break;
			case PRISMARINE_STAIRS: ger = "Prismarintreppe"; eng = "PRISMARINE_STAIRS"; break;
			case PRISMARINE_BRICK_STAIRS: ger = "Prismarinziegeltreppe"; eng = "PRISMARINE_BRICK_STAIRS"; break;
			case DARK_PRISMARINE_STAIRS: ger = "Dunkle Prismarinziegeltreppe"; eng = "DARK_PRISMARINE_STAIRS"; break;
			case SEA_LANTERN: ger = "Seelaterne"; eng = "SEA_LANTERN"; break;
			case RED_SANDSTONE: ger = "Roter Sandstein"; eng = "RED_SANDSTONE"; break;
			case CHISELED_RED_SANDSTONE: ger = "Gemeißelter roter Sandstein"; eng = "CHISELED_RED_SANDSTONE"; break;
			case CUT_RED_SANDSTONE: ger = "Geschnittener roter Sandstein"; eng = "CUT_RED_SANDSTONE"; break;
			case RED_SANDSTONE_STAIRS: ger = "Rote Sandsteintreppe"; eng = "RED_SANDSTONE_STAIRS"; break;
			case REPEATING_COMMAND_BLOCK: ger = "Wiederhol-Befehlsblock"; eng = "REPEATING_COMMAND_BLOCK"; break;
			case CHAIN_COMMAND_BLOCK: ger = "Ketten-Befehlsblock"; eng = "CHAIN_COMMAND_BLOCK"; break;
			case MAGMA_BLOCK: ger = "Magmsablock"; eng = "MAGMA_BLOCK"; break;
			case NETHER_WART_BLOCK: ger = "Netherwarzenblock"; eng = "NETHER_WART_BLOCK"; break;
			case WARPED_WART_BLOCK: ger = "Wirrwarzenblock"; eng = "WARPED_WART_BLOCK"; break;
			case RED_NETHER_BRICKS: ger = "Rote Netherziegel"; eng = "RED_NETHER_BRICKS"; break;
			case BONE_BLOCK: ger = "Knochenblock"; eng = "BONE_BLOCK"; break;
			case STRUCTURE_VOID: ger = "Konstruktionsleere"; eng = "STRUCTURE_VOID"; break;
			case SHULKER_BOX: ger = "Shulker-Kiste"; eng = "SHULKER_BOX"; break;
			case WHITE_SHULKER_BOX: ger = "Weiße Shulker-Kiste"; eng = "WHITE_SHULKER_BOX"; break;
			case ORANGE_SHULKER_BOX: ger = "Orange Shulker-Kiste"; eng = "ORANGE_SHULKER_BOX"; break;
			case MAGENTA_SHULKER_BOX: ger = "Magenta Shulker-Kiste"; eng = "MAGENTA_SHULKER_BOX"; break;
			case LIGHT_BLUE_SHULKER_BOX: ger = "Hellblaue Shulker-Kiste"; eng = "LIGHT_BLUE_SHULKER_BOX"; break;
			case YELLOW_SHULKER_BOX: ger = "Gelbe Shulker-Kiste"; eng = "YELLOW_SHULKER_BOX"; break;
			case LIME_SHULKER_BOX: ger = "Hellgrüne Shulker-Kiste"; eng = "LIME_SHULKER_BOX"; break;
			case PINK_SHULKER_BOX: ger = "Rosa Shulker-Kiste"; eng = "PINK_SHULKER_BOX"; break;
			case GRAY_SHULKER_BOX: ger = "Graue Shulker-Kiste"; eng = "GRAY_SHULKER_BOX"; break;
			case LIGHT_GRAY_SHULKER_BOX: ger = "Hellgraue Shulker-Kiste"; eng = "LIGHT_GRAY_SHULKER_BOX"; break;
			case CYAN_SHULKER_BOX: ger = "Türkise Shulker-Kiste"; eng = "CYAN_SHULKER_BOX"; break;
			case PURPLE_SHULKER_BOX: ger = "Violette Shulker-Kiste"; eng = "PURPLE_SHULKER_BOX"; break;
			case BLUE_SHULKER_BOX: ger = "Blaue Shulker-Kiste"; eng = "BLUE_SHULKER_BOX"; break;
			case BROWN_SHULKER_BOX: ger = "Braune Shulker-Kiste"; eng = "BROWN_SHULKER_BOX"; break;
			case GREEN_SHULKER_BOX: ger = "Grüne Shulker-Kiste"; eng = "GREEN_SHULKER_BOX"; break;
			case RED_SHULKER_BOX: ger = "Rote Shulker-Kiste"; eng = "RED_SHULKER_BOX"; break;
			case BLACK_SHULKER_BOX: ger = "Schwarze Shulker-Kiste"; eng = "BLACK_SHULKER_BOX"; break;
			case WHITE_GLAZED_TERRACOTTA: ger = "Weiße glasierte Keramik"; eng = "WHITE_GLAZED_TERRACOTTA"; break;
			case ORANGE_GLAZED_TERRACOTTA: ger = "Orange glasierte Keramik"; eng = "ORANGE_GLAZED_TERRACOTTA"; break;
			case MAGENTA_GLAZED_TERRACOTTA: ger = "Magenta glasierte Keramik"; eng = "MAGENTA_GLAZED_TERRACOTTA"; break;
			case LIGHT_BLUE_GLAZED_TERRACOTTA: ger = "Hellblaue glasierte Keramik"; eng = "LIGHT_BLUE_GLAZED_TERRACOTTA"; break;
			case YELLOW_GLAZED_TERRACOTTA: ger = "Gelbe glasierte Keramik"; eng = "YELLOW_GLAZED_TERRACOTTA"; break;
			case LIME_GLAZED_TERRACOTTA: ger = "Hellgrüne glasierte Keramik"; eng = "LIME_GLAZED_TERRACOTTA"; break;
			case PINK_GLAZED_TERRACOTTA: ger = "Rosa glasierte Keramik"; eng = "PINK_GLAZED_TERRACOTTA"; break;
			case GRAY_GLAZED_TERRACOTTA: ger = "Graue glasierte Keramik"; eng = "GRAY_GLAZED_TERRACOTTA"; break;
			case LIGHT_GRAY_GLAZED_TERRACOTTA: ger = "Hellgraue glasierte Keramik"; eng = "LIGHT_GRAY_GLAZED_TERRACOTTA"; break;
			case CYAN_GLAZED_TERRACOTTA: ger = "Türkise glasierte Keramik"; eng = "CYAN_GLAZED_TERRACOTTA"; break;
			case PURPLE_GLAZED_TERRACOTTA: ger = "Violette glasierte Keramik"; eng = "PURPLE_GLAZED_TERRACOTTA"; break;
			case BLUE_GLAZED_TERRACOTTA: ger = "Blaue glasierte Keramik"; eng = "BLUE_GLAZED_TERRACOTTA"; break;
			case BROWN_GLAZED_TERRACOTTA: ger = "Braune glasierte Keramik"; eng = "BROWN_GLAZED_TERRACOTTA"; break;
			case GREEN_GLAZED_TERRACOTTA: ger = "Grüne glasierte Keramik"; eng = "GREEN_GLAZED_TERRACOTTA"; break;
			case RED_GLAZED_TERRACOTTA: ger = "Rote glasierte Keramik"; eng = "RED_GLAZED_TERRACOTTA"; break;
			case BLACK_GLAZED_TERRACOTTA: ger = "Schwarze glasierte Keramik"; eng = "BLACK_GLAZED_TERRACOTTA"; break;
			case WHITE_CONCRETE: ger = "Weißer Beton"; eng = "WHITE_CONCRETE"; break;
			case ORANGE_CONCRETE: ger = "Oranger Beton"; eng = "ORANGE_CONCRETE"; break;
			case MAGENTA_CONCRETE: ger = "Magenta Beton"; eng = "MAGENTA_CONCRETE"; break;
			case LIGHT_BLUE_CONCRETE: ger = "Hellblauer Beton"; eng = "LIGHT_BLUE_CONCRETE"; break;
			case YELLOW_CONCRETE: ger = "Gelber Beton"; eng = "YELLOW_CONCRETE"; break;
			case LIME_CONCRETE: ger = "Hellgrüner Beton"; eng = "LIME_CONCRETE"; break;
			case PINK_CONCRETE: ger = "Rosa Beton"; eng = "PINK_CONCRETE"; break;
			case GRAY_CONCRETE: ger = "Grauer Beton"; eng = "GRAY_CONCRETE"; break;
			case LIGHT_GRAY_CONCRETE: ger = "Hellgrauer Beton"; eng = "LIGHT_GRAY_CONCRETE"; break;
			case CYAN_CONCRETE: ger = "Türkiser Beton"; eng = "CYAN_CONCRETE"; break;
			case PURPLE_CONCRETE: ger = "Violetter Beton"; eng = "PURPLE_CONCRETE"; break;
			case BLUE_CONCRETE: ger = "Blauer Beton"; eng = "BLUE_CONCRETE"; break;
			case BROWN_CONCRETE: ger = "Brauner Beton"; eng = "BROWN_CONCRETE"; break;
			case GREEN_CONCRETE: ger = "Grüner Beton"; eng = "GREEN_CONCRETE"; break;
			case RED_CONCRETE: ger = "Roter Beton"; eng = "RED_CONCRETE"; break;
			case BLACK_CONCRETE: ger = "Schwarzer Beton"; eng = "BLACK_CONCRETE"; break;
			case WHITE_CONCRETE_POWDER: ger = "Weißer Trockenbeton"; eng = "WHITE_CONCRETE_POWDER"; break;
			case ORANGE_CONCRETE_POWDER: ger = "Oranger Trockenbeton"; eng = "ORANGE_CONCRETE_POWDER"; break;
			case MAGENTA_CONCRETE_POWDER: ger = "Magenta Trockenbeton"; eng = "MAGENTA_CONCRETE_POWDER"; break;
			case LIGHT_BLUE_CONCRETE_POWDER: ger = "Hellblauer Trockenbeton"; eng = "LIGHT_BLUE_CONCRETE_POWDER"; break;
			case YELLOW_CONCRETE_POWDER: ger = "Gelber Trockenbeton"; eng = "YELLOW_CONCRETE_POWDER"; break;
			case LIME_CONCRETE_POWDER: ger = "Hellgrüner Trockenbeton"; eng = "LIME_CONCRETE_POWDER"; break;
			case PINK_CONCRETE_POWDER: ger = "Rosa Trockenbeton"; eng = "PINK_CONCRETE_POWDER"; break;
			case GRAY_CONCRETE_POWDER: ger = "Grauer Trockenbeton"; eng = "GRAY_CONCRETE_POWDER"; break;
			case LIGHT_GRAY_CONCRETE_POWDER: ger = "Hellgruer Trockenbeton"; eng = "LIGHT_GRAY_CONCRETE_POWDER"; break;
			case CYAN_CONCRETE_POWDER: ger = "Türkiser Trockenbeton"; eng = "CYAN_CONCRETE_POWDER"; break;
			case PURPLE_CONCRETE_POWDER: ger = "Violetter Trockenbeton"; eng = "PURPLE_CONCRETE_POWDER"; break;
			case BLUE_CONCRETE_POWDER: ger = "Blauer Trockenbeton"; eng = "BLUE_CONCRETE_POWDER"; break;
			case BROWN_CONCRETE_POWDER: ger = "Brauner Trockenbeton"; eng = "BROWN_CONCRETE_POWDER"; break;
			case GREEN_CONCRETE_POWDER: ger = "Grüner Trockenbeton"; eng = "GREEN_CONCRETE_POWDER"; break;
			case RED_CONCRETE_POWDER: ger = "Roter Trockenbeton"; eng = "RED_CONCRETE_POWDER"; break;
			case BLACK_CONCRETE_POWDER: ger = "Schwarzer Trockenbeton"; eng = "BLACK_CONCRETE_POWDER"; break;
			case TURTLE_EGG: ger = "Schildkrötenei"; eng = "TURTLE_EGG"; break;
			case DEAD_TUBE_CORAL_BLOCK: ger = "Abgestorbener Orgelkorallenblock"; eng = "DEAD_TUBE_CORAL_BLOCK"; break;
			case DEAD_BRAIN_CORAL_BLOCK: ger = "Abgestorbener Hirnkorallenblock"; eng = "DEAD_BRAIN_CORAL_BLOCK"; break;
			case DEAD_BUBBLE_CORAL_BLOCK: ger = "Abgestorbener Blasenkorallenblock"; eng = "DEAD_BUBBLE_CORAL_BLOCK"; break;
			case DEAD_FIRE_CORAL_BLOCK: ger = "Abgestorbener Feuerkorallenblock"; eng = "DEAD_FIRE_CORAL_BLOCK"; break;
			case DEAD_HORN_CORAL_BLOCK: ger = "Abgestorbener Geweihkorallenblock"; eng = "DEAD_HORN_CORAL_BLOCK"; break;
			case TUBE_CORAL_BLOCK: ger = "Orgelkorallenblock"; eng = "TUBE_CORAL_BLOCK"; break;
			case BRAIN_CORAL_BLOCK: ger = "Hirnkorallenblock"; eng = "BRAIN_CORAL_BLOCK"; break;
			case BUBBLE_CORAL_BLOCK: ger = "Blasenkorallenblock"; eng = "BUBBLE_CORAL_BLOCK"; break;
			case FIRE_CORAL_BLOCK: ger = "Feuerkorallenblock"; eng = "FIRE_CORAL_BLOCK"; break;
			case HORN_CORAL_BLOCK: ger = "Geweihkorallenblock"; eng = "HORN_CORAL_BLOCK"; break;
			case TUBE_CORAL: ger = "Orgelkoralle"; eng = "TUBE_CORAL"; break;
			case BRAIN_CORAL: ger = "Hirnkoralle"; eng = "BRAIN_CORAL"; break;
			case BUBBLE_CORAL: ger = "Blasenkoralle"; eng = "BUBBLE_CORAL"; break;
			case FIRE_CORAL: ger = "Feuerkoralle"; eng = "FIRE_CORAL"; break;
			case HORN_CORAL: ger = "Geweihkoralle"; eng = "HORN_CORAL"; break;
			case DEAD_BRAIN_CORAL: ger = "Abgestorbene Hirnkoralle"; eng = "DEAD_BRAIN_CORAL"; break;
			case DEAD_BUBBLE_CORAL: ger = "Abgestorbene Blasenkoralle"; eng = "DEAD_BUBBLE_CORAL"; break;
			case DEAD_FIRE_CORAL: ger = "Abgestorbene Feuerkoralle"; eng = "DEAD_FIRE_CORAL"; break;
			case DEAD_HORN_CORAL: ger = "Abgestorbene Geweihkoralle"; eng = "DEAD_HORN_CORAL"; break;
			case DEAD_TUBE_CORAL: ger = "Abgestorbene Orgelkoralle"; eng = "DEAD_TUBE_CORAL"; break;
			case TUBE_CORAL_FAN: ger = "Orgelkorallenfächer"; eng = "TUBE_CORAL_FAN"; break;
			case BRAIN_CORAL_FAN: ger = "Hirnkorallenfächer"; eng = "BRAIN_CORAL_FAN"; break;
			case BUBBLE_CORAL_FAN: ger = "Blasenkorallenfächer"; eng = "BUBBLE_CORAL_FAN"; break;
			case FIRE_CORAL_FAN: ger = "Feuerkorallenfächer"; eng = "FIRE_CORAL_FAN"; break;
			case HORN_CORAL_FAN: ger = "Geweihkorallenfächer"; eng = "HORN_CORAL_FAN"; break;
			case DEAD_TUBE_CORAL_FAN: ger = "Abgestorbener Orgelkorallenfächer"; eng = "DEAD_TUBE_CORAL_FAN"; break;
			case DEAD_BRAIN_CORAL_FAN: ger = "Abgestorbener Hirnkorallenfächer"; eng = "DEAD_BRAIN_CORAL_FAN"; break;
			case DEAD_BUBBLE_CORAL_FAN: ger = "Abgestorbener Blasenkorallenfächer"; eng = "DEAD_BUBBLE_CORAL_FAN"; break;
			case DEAD_FIRE_CORAL_FAN: ger = "Abgestorbener Feuerkorallenfächer"; eng = "DEAD_FIRE_CORAL_FAN"; break;
			case DEAD_HORN_CORAL_FAN: ger = "Abgestorbener Geweihkorallenfächer"; eng = "DEAD_HORN_CORAL_FAN"; break;
			case BLUE_ICE: ger = "Blaues Eis"; eng = "BLUE_ICE"; break;
			case CONDUIT: ger = "Aquisator"; eng = "CONDUIT"; break;
			case POLISHED_GRANITE_STAIRS: ger = "Polierte Granittreppe"; eng = "POLISHED_GRANITE_STAIRS"; break;
			case SMOOTH_RED_SANDSTONE_STAIRS: ger = "Glatte rote Sandsteintreppe"; eng = "SMOOTH_RED_SANDSTONE_STAIRS"; break;
			case MOSSY_STONE_BRICK_STAIRS: ger = "Bemooste Steinziegeltreppe"; eng = "MOSSY_STONE_BRICK_STAIRS"; break;
			case POLISHED_DIORITE_STAIRS: ger = "Polierte Diorittreppe"; eng = "POLISHED_DIORITE_STAIRS"; break;
			case MOSSY_COBBLESTONE_STAIRS: ger = "Bemooste Bruchsteintreppe"; eng = "MOSSY_COBBLESTONE_STAIRS"; break;
			case END_STONE_BRICK_STAIRS: ger = "Endsteinziegeltreppe"; eng = "END_STONE_BRICK_STAIRS"; break;
			case STONE_STAIRS: ger = "Steintreppe"; eng = "STONE_STAIRS"; break;
			case SMOOTH_SANDSTONE_STAIRS: ger = "Glatte Sandsteintreppe"; eng = "SMOOTH_SANDSTONE_STAIRS"; break;
			case SMOOTH_QUARTZ_STAIRS: ger = "Glatte Quarztreppe"; eng = "SMOOTH_QUARTZ_STAIRS"; break;
			case GRANITE_STAIRS: ger = "Granittreppe"; eng = "GRANITE_STAIRS"; break;
			case ANDESITE_STAIRS: ger = "Andesittreppe"; eng = "ANDESITE_STAIRS"; break;
			case RED_NETHER_BRICK_STAIRS: ger = "Rote Netherziegeltreppe"; eng = "RED_NETHER_BRICK_STAIRS"; break;
			case POLISHED_ANDESITE_STAIRS: ger = "Polierte Andesittreppe"; eng = "POLISHED_ANDESITE_STAIRS"; break;
			case DIORITE_STAIRS: ger = "Diorittreppe"; eng = "DIORITE_STAIRS"; break;
			case COBBLED_DEEPSLATE_STAIRS: ger = "Bruchtiefenschiefertreppe"; eng = "COBBLED_DEEPSLATE_STAIRS"; break;
			case POLISHED_DEEPSLATE_STAIRS: ger = "Polierte Tiefenschiefertreppe"; eng = "POLISHED_DEEPSLATE_STAIRS"; break;
			case DEEPSLATE_BRICK_STAIRS: ger = "Tiefenschieferziegeltreppe"; eng = "DEEPSLATE_BRICK_STAIRS"; break;
			case DEEPSLATE_TILE_STAIRS: ger = "Tiefenschieferfliesentreppe"; eng = "DEEPSLATE_TILE_STAIRS"; break;
			case POLISHED_GRANITE_SLAB: ger = "Polierte Granitstufe"; eng = "POLISHED_GRANITE_SLAB"; break;
			case SMOOTH_RED_SANDSTONE_SLAB: ger = "Glatte rote Sandsteinstufe"; eng = "SMOOTH_RED_SANDSTONE_SLAB"; break;
			case MOSSY_STONE_BRICK_SLAB: ger = "Bemooste Steinziegelstufe"; eng = "MOSSY_STONE_BRICK_SLAB"; break;
			case POLISHED_DIORITE_SLAB: ger = "Polierte Dioritstufe"; eng = "POLISHED_DIORITE_SLAB"; break;
			case MOSSY_COBBLESTONE_SLAB: ger = "Bemooste Bruchsteinstufe"; eng = "MOSSY_COBBLESTONE_SLAB"; break;
			case END_STONE_BRICK_SLAB: ger = "Endsteinziegelstufe"; eng = "END_STONE_BRICK_SLAB"; break;
			case SMOOTH_SANDSTONE_SLAB: ger = "Glatte Sandsteinstufe"; eng = "SMOOTH_SANDSTONE_SLAB"; break;
			case SMOOTH_QUARTZ_SLAB: ger = "Glatte Quarzstufe"; eng = "SMOOTH_QUARTZ_SLAB"; break;
			case GRANITE_SLAB: ger = "Granitstufe"; eng = "GRANITE_SLAB"; break;
			case ANDESITE_SLAB: ger = "Andesitstufe"; eng = "ANDESITE_SLAB"; break;
			case RED_NETHER_BRICK_SLAB: ger = "Rote Netherziegelstufe"; eng = "RED_NETHER_BRICK_SLAB"; break;
			case POLISHED_ANDESITE_SLAB: ger = "Polierte Andesitstufe"; eng = "POLISHED_ANDESITE_SLAB"; break;
			case DIORITE_SLAB: ger = "Dioritstufe"; eng = "DIORITE_SLAB"; break;
			case COBBLED_DEEPSLATE_SLAB: ger = "Bruchtiefenschieferstufe"; eng = "COBBLED_DEEPSLATE_SLAB"; break;
			case POLISHED_DEEPSLATE_SLAB: ger = "Polierte Tiefenschieferstufe"; eng = "POLISHED_DEEPSLATE_SLAB"; break;
			case DEEPSLATE_BRICK_SLAB: ger = "Tiefenschieferziegelstufe"; eng = "DEEPSLATE_BRICK_SLAB"; break;
			case DEEPSLATE_TILE_SLAB: ger = "Tiefenschieferfliesenstufe"; eng = "DEEPSLATE_TILE_SLAB"; break;
			case SCAFFOLDING: ger = "Gerüst"; eng = "SCAFFOLDING"; break;
			case REDSTONE: ger = "Redstone-Staub"; eng = "REDSTONE"; break;
			case REDSTONE_TORCH: ger = "Redstonefackel"; eng = "REDSTONE_TORCH"; break;
			case REDSTONE_BLOCK: ger = "Redstone-Block"; eng = "REDSTONE_BLOCK"; break;
			case REPEATER: ger = "Redstone-Verstärker"; eng = "REPEATER"; break;
			case COMPARATOR: ger = "Redstone-Komparator"; eng = "COMPARATOR"; break;
			case PISTON: ger = "PISTON"; eng = "Kolben"; break;
			case STICKY_PISTON: ger = "Klebriger Kolben"; eng = "STICKY_PISTON"; break;
			case SLIME_BLOCK: ger = "Schleimblock"; eng = "SLIME_BLOCK"; break;
			case HONEY_BLOCK: ger = "Honigblock"; eng = "HONEY_BLOCK"; break;
			case OBSERVER: ger = "Beobachter"; eng = "OBSERVER"; break;
			case HOPPER: ger = "Trichter"; eng = "HOPPER"; break;
			case DISPENSER: ger = "Werfer"; eng = "DISPENSER"; break;
			case DROPPER: ger = "Spender"; eng = "DROPPER"; break;
			case LECTERN: ger = "Lesepult"; eng = "LECTERN"; break;
			case TARGET: ger = "Zielblock"; eng = "TARGET"; break;
			case LEVER: ger = "Hebel"; eng = "LEVER"; break;
			case LIGHTNING_ROD: ger = "Blitzableiter"; eng = "LIGHTNING_ROD"; break;
			case DAYLIGHT_DETECTOR: ger = "Tageslichtsensor"; eng = "DAYLIGHT_DETECTOR"; break;
			case SCULK_SENSOR: ger = "Sculk-Sensor"; eng = "SCULK_SENSOR"; break;
			case TRIPWIRE_HOOK: ger = "Haken"; eng = "TRIPWIRE_HOOK"; break;
			case TRAPPED_CHEST: ger = "Redstone-Truhe"; eng = "TRAPPED_CHEST"; break;
			case TNT: ger = "TNT"; eng = "TNT"; break;
			case REDSTONE_LAMP: ger = "Redstone-Lampe"; eng = "REDSTONE_LAMP"; break;
			case NOTE_BLOCK: ger = "Notenblock"; eng = "NOTE_BLOCK"; break;
			case STONE_BUTTON: ger = "Steinknopf"; eng = "STONE_BUTTON"; break;
			case POLISHED_BLACKSTONE_BUTTON: ger = "Polierter Schwarzsteinknopf"; eng = "POLISHED_BLACKSTONE_BUTTON"; break;
			case OAK_BUTTON: ger = "Eichenholzknopf"; eng = "OAK_BUTTON"; break;
			case SPRUCE_BUTTON: ger = "Fichtenholzknopf"; eng = "SPRUCE_BUTTON"; break;
			case BIRCH_BUTTON: ger = "Birkenholzknopf"; eng = "BIRCH_BUTTON"; break;
			case JUNGLE_BUTTON: ger = "Tropenholzknopf"; eng = "JUNGLE_BUTTON"; break;
			case ACACIA_BUTTON: ger = "Akazienholzknopf"; eng = "ACACIA_BUTTON"; break;
			case DARK_OAK_BUTTON: ger = "Schwarzeichenholzknopf"; eng = "DARK_OAK_BUTTON"; break;
			case MANGROVE_BUTTON: ger = "Mngrovenholzknopf"; eng = "MANGROVE_BUTTON"; break;
			case CRIMSON_BUTTON: ger = "Karmesinknopf"; eng = "CRIMSON_BUTTON"; break;
			case WARPED_BUTTON: ger = "Wirrknopf"; eng = "WARPED_BUTTON"; break;
			case STONE_PRESSURE_PLATE: ger = "Steindruckplatte"; eng = "STONE_PRESSURE_PLATE"; break;
			case POLISHED_BLACKSTONE_PRESSURE_PLATE: ger = "Polierte Schwarzsteindruckplatte"; eng = "POLISHED_BLACKSTONE_PRESSURE_PLATE"; break;
			case LIGHT_WEIGHTED_PRESSURE_PLATE: ger = "Feinwägeplatte"; eng = "LIGHT_WEIGHTED_PRESSURE_PLATE"; break;
			case HEAVY_WEIGHTED_PRESSURE_PLATE: ger = "Grobwägeplatte"; eng = "HEAVY_WEIGHTED_PRESSURE_PLATE"; break;
			case OAK_PRESSURE_PLATE: ger = "EichenholzdruckplatteE"; eng = "OAK_PRESSURE_PLATE"; break;
			case SPRUCE_PRESSURE_PLATE: ger = "Fichtenholzdruckplatte"; eng = "SPRUCE_PRESSURE_PLATE"; break;
			case BIRCH_PRESSURE_PLATE: ger = "Birkenholzdruckplatte"; eng = "BIRCH_PRESSURE_PLATE"; break;
			case JUNGLE_PRESSURE_PLATE: ger = "Tropenholzdruckplatte"; eng = "JUNGLE_PRESSURE_PLATE"; break;
			case ACACIA_PRESSURE_PLATE: ger = "Akazienholzdruckplatte"; eng = "ACACIA_PRESSURE_PLATE"; break;
			case DARK_OAK_PRESSURE_PLATE: ger = "Schwarzeichenholzdruckplatte"; eng = "DARK_OAK_PRESSURE_PLATE"; break;
			case MANGROVE_PRESSURE_PLATE: ger = "Mangrovenholzdruckplatte"; eng = "MANGROVE_PRESSURE_PLATE"; break;
			case CRIMSON_PRESSURE_PLATE: ger = "Karmesindruckplatte"; eng = "CRIMSON_PRESSURE_PLATE"; break;
			case WARPED_PRESSURE_PLATE: ger = "Wirrdruckplatte"; eng = "WARPED_PRESSURE_PLATE"; break;
			case IRON_DOOR: ger = "Eisentür"; eng = "IRON_DOOR"; break;
			case OAK_DOOR: ger = "Eichenholztür"; eng = "OAK_DOOR"; break;
			case SPRUCE_DOOR: ger = "Fichtenholztür"; eng = "SPRUCE_DOOR"; break;
			case BIRCH_DOOR: ger = "Birkenholztür"; eng = "BIRCH_DOOR"; break;
			case JUNGLE_DOOR: ger = "Tropenholztür"; eng = "JUNGLE_DOOR"; break;
			case ACACIA_DOOR: ger = "Akazienholztür"; eng = "ACACIA_DOOR"; break;
			case DARK_OAK_DOOR: ger = "Schwarzeichenholztür"; eng = "DARK_OAK_DOOR"; break;
			case MANGROVE_DOOR: ger = "Mangrovenholztür"; eng = "MANGROVE_DOOR"; break;
			case CRIMSON_DOOR: ger = "Wirrtür"; eng = "CRIMSON_DOOR"; break;
			case WARPED_DOOR: ger = "Karmesintür"; eng = "WARPED_DOOR"; break;
			case IRON_TRAPDOOR: ger = "Eisenfalltür"; eng = "IRON_TRAPDOOR"; break;
			case OAK_TRAPDOOR: ger = "Eichenholzfalltür"; eng = "OAK_TRAPDOOR"; break;
			case SPRUCE_TRAPDOOR: ger = "Fichtenholzfalltür"; eng = "SPRUCE_TRAPDOOR"; break;
			case BIRCH_TRAPDOOR: ger = "Birkenholzfalltür"; eng = "BIRCH_TRAPDOOR"; break;
			case JUNGLE_TRAPDOOR: ger = "Tropenholzfalltür"; eng = "JUNGLE_TRAPDOOR"; break;
			case ACACIA_TRAPDOOR: ger = "Akazienholzfalltür"; eng = "ACACIA_TRAPDOOR"; break;
			case DARK_OAK_TRAPDOOR: ger = "Schwarzeichenholzfalltür"; eng = "DARK_OAK_TRAPDOOR"; break;
			case MANGROVE_TRAPDOOR: ger = "Mangrovenholzfalltür"; eng = "MANGROVE_TRAPDOOR"; break;
			case CRIMSON_TRAPDOOR: ger = "Karmesinfalltür"; eng = "CRIMSON_TRAPDOOR"; break;
			case WARPED_TRAPDOOR: ger = "Wirrfalltür"; eng = "WARPED_TRAPDOOR"; break;
			case OAK_FENCE_GATE: ger = "Eichenholzzauntor"; eng = "OAK_FENCE_GATE"; break;
			case SPRUCE_FENCE_GATE: ger = "Fichtenholzzauntor"; eng = "SPRUCE_FENCE_GATE"; break;
			case BIRCH_FENCE_GATE: ger = "Birkenholzzauntor"; eng = "BIRCH_FENCE_GATE"; break;
			case JUNGLE_FENCE_GATE: ger = "Tropenholzzauntor"; eng = "JUNGLE_FENCE_GATE"; break;
			case ACACIA_FENCE_GATE: ger = "Akazienholzzauntor"; eng = "ACACIA_FENCE_GATE"; break;
			case DARK_OAK_FENCE_GATE: ger = "Schwarzeichenholzzauntor"; eng = "DARK_OAK_FENCE_GATE"; break;
			case MANGROVE_FENCE_GATE: ger = "Mangrovenholzzauntor"; eng = "MANGROVE_FENCE_GATE"; break;
			case CRIMSON_FENCE_GATE: ger = "Karmesinzauntor"; eng = "CRIMSON_FENCE_GATE"; break;
			case WARPED_FENCE_GATE: ger = "Wirrzauntor"; eng = "WARPED_FENCE_GATE"; break;
			case POWERED_RAIL: ger = "Antriebsschiene"; eng = "POWERED_RAIL"; break;
			case DETECTOR_RAIL: ger = "Sensorschiene"; eng = "DETECTOR_RAIL"; break;
			case RAIL: ger = "Schiene"; eng = "RAIL"; break;
			case ACTIVATOR_RAIL: ger = "Aktivierungsschiene"; eng = "ACTIVATOR_RAIL"; break;
			case SADDLE: ger = "Sattel"; eng = "SADDLE"; break;
			case MINECART: ger = "Lore"; eng = "MINECART"; break;
			case CHEST_MINECART: ger = "Güterlore"; eng = "CHEST_MINECART"; break;
			case FURNACE_MINECART: ger = "Ofenlore"; eng = "FURNACE_MINECART"; break;
			case TNT_MINECART: ger = "TNT-Lore"; eng = "TNT_MINECART"; break;
			case HOPPER_MINECART: ger = "Trichterlore"; eng = "HOPPER_MINECART"; break;
			case CARROT_ON_A_STICK: ger = "Karottenrute"; eng = "CARROT_ON_A_STICK"; break;
			case WARPED_FUNGUS_ON_A_STICK: ger = "Wirrpilzrute"; eng = "WARPED_FUNGUS_ON_A_STICK"; break;
			case ELYTRA: ger = "Elytren"; eng = "ELYTRA"; break;
			case OAK_BOAT: ger = "Eichenholzboit"; eng = "OAK_BOAT"; break;
			case OAK_CHEST_BOAT: ger = "Eichenholztruhenboot"; eng = "OAK_CHEST_BOAT"; break;
			case SPRUCE_BOAT: ger = "Fichtenholzboot"; eng = "SPRUCE_BOAT"; break;
			case SPRUCE_CHEST_BOAT: ger = "Fichtenholztruhenboot"; eng = "SPRUCE_CHEST_BOAT"; break;
			case BIRCH_BOAT: ger = "Birkenholzboot"; eng = "BIRCH_BOAT"; break;
			case BIRCH_CHEST_BOAT: ger = "Birkenholztruhenboot"; eng = "BIRCH_CHEST_BOAT"; break;
			case JUNGLE_BOAT: ger = "Tropenholzboot"; eng = "JUNGLE_BOAT"; break;
			case JUNGLE_CHEST_BOAT: ger = "Tropenholztruehnboot"; eng = "JUNGLE_CHEST_BOAT"; break;
			case ACACIA_BOAT: ger = "Akazienholzboot"; eng = "ACACIA_BOAT"; break;
			case ACACIA_CHEST_BOAT: ger = "Akazienholztruhenboot"; eng = "ACACIA_CHEST_BOAT"; break;
			case DARK_OAK_BOAT: ger = "Schwarzeichenholzboot"; eng = "DARK_OAK_BOAT"; break;
			case DARK_OAK_CHEST_BOAT: ger = "Schwarzeichenholztruehenboot"; eng = "DARK_OAK_CHEST_BOAT"; break;
			case MANGROVE_BOAT: ger = "Mangrovenholzboot"; eng = "MANGROVE_BOAT"; break;
			case MANGROVE_CHEST_BOAT: ger = "Mangrovenholztruhenboot"; eng = "MANGROVE_CHEST_BOAT"; break;
			case STRUCTURE_BLOCK: ger = "Konstruktionsblock"; eng = "STRUCTURE_BLOCK"; break;
			case JIGSAW: ger = "Verbundblock"; eng = "JIGSAW"; break;
			case TURTLE_HELMET: ger = "Schildkrötenpanzer"; eng = "TURTLE_HELMET"; break;
			case SCUTE: ger = "Hornschild"; eng = "SCUTE"; break;
			case FLINT_AND_STEEL: ger = "Feuerzeug"; eng = "FLINT_AND_STEEL"; break;
			case APPLE: ger = "Apfel"; eng = "APPLE"; break;
			case BOW: ger = "Bogen"; eng = "BOW"; break;
			case ARROW: ger = "Pfeil"; eng = "ARROW"; break;
			case COAL: ger = "Kohle"; eng = "COAL"; break;
			case CHARCOAL: ger = "Holzkohle"; eng = "CHARCOAL"; break;
			case DIAMOND: ger = "Diamant"; eng = "DIAMOND"; break;
			case EMERALD: ger = "Smaragd"; eng = "EMERALD"; break;
			case LAPIS_LAZULI: ger = "Lapislazuli"; eng = "LAPIS_LAZULI"; break;
			case QUARTZ: ger = "Netherquarz"; eng = "QUARTZ"; break;
			case AMETHYST_SHARD: ger = "Amethystscherbe"; eng = "AMETHYST_SHARD"; break;
			case RAW_IRON: ger = "Roheisen"; eng = "RAW_IRON"; break;
			case IRON_INGOT: ger = "Eisenbarren"; eng = "IRON_INGOT"; break;
			case RAW_COPPER: ger = "Rohkupfer"; eng = "RAW_COPPER"; break;
			case COPPER_INGOT: ger = "Kupferbarren"; eng = "COPPER_INGOT"; break;
			case RAW_GOLD: ger = "Rohgold"; eng = "RAW_GOLD"; break;
			case GOLD_INGOT: ger = "Goldbarren"; eng = "GOLD_INGOT"; break;
			case NETHERITE_INGOT: ger = "Netheritbarren"; eng = "NETHERITE_INGOT"; break;
			case NETHERITE_SCRAP: ger = "Netheritplatten"; eng = "NETHERITE_SCRAP"; break;
			case WOODEN_SWORD: ger = "Holzschwert"; eng = "WOODEN_SWORD"; break;
			case WOODEN_SHOVEL: ger = "Holzschaufel"; eng = "WOODEN_SHOVEL"; break;
			case WOODEN_PICKAXE: ger = "Holzspitzhacke"; eng = "WOODEN_PICKAXE"; break;
			case WOODEN_AXE: ger = "Holzaxt"; eng = "WOODEN_AXE"; break;
			case WOODEN_HOE: ger = "Holzhacke"; eng = "WOODEN_HOE"; break;
			case STONE_SWORD: ger = "Steinschwert"; eng = "STONE_SWORD"; break;
			case STONE_SHOVEL: ger = "Steinschaufel"; eng = "STONE_SHOVEL"; break;
			case STONE_PICKAXE: ger = "Steinspitzhacke"; eng = "STONE_PICKAXE"; break;
			case STONE_AXE: ger = "Steinaxt"; eng = "STONE_AXE"; break;
			case STONE_HOE: ger = "Steinhacke"; eng = "STONE_HOE"; break;
			case GOLDEN_SWORD: ger = "Goldschwert"; eng = "GOLDEN_SWORD"; break;
			case GOLDEN_SHOVEL: ger = "Goldschaufel"; eng = "GOLDEN_SHOVEL"; break;
			case GOLDEN_PICKAXE: ger = "Goldspitzhacke"; eng = "GOLDEN_PICKAXE"; break;
			case GOLDEN_AXE: ger = "Goldaxt"; eng = "GOLDEN_AXE"; break;
			case GOLDEN_HOE: ger = "Goldhacke"; eng = "GOLDEN_HOE"; break;
			case IRON_SWORD: ger = "Eisenschwert"; eng = "IRON_SWORD"; break;
			case IRON_SHOVEL: ger = "Eisenschaufel"; eng = "IRON_SHOVEL"; break;
			case IRON_PICKAXE: ger = "Eisenspitzhacke"; eng = "IRON_PICKAXE"; break;
			case IRON_AXE: ger = "Eisenaxt"; eng = "IRON_AXE"; break;
			case IRON_HOE: ger = "Eisenhacke"; eng = "IRON_HOE"; break;
			case DIAMOND_SWORD: ger = "Diamantschwert"; eng = "DIAMOND_SWORD"; break;
			case DIAMOND_SHOVEL: ger = "Diamantschaufel"; eng = "DIAMOND_SHOVEL"; break;
			case DIAMOND_PICKAXE: ger = "Diamantspitzhacke"; eng = "DIAMOND_PICKAXE"; break;
			case DIAMOND_AXE: ger = "Diamantaxt"; eng = "DIAMOND_AXE"; break;
			case DIAMOND_HOE: ger = "Diamanthacke"; eng = "DIAMOND_HOE"; break;
			case NETHERITE_SWORD: ger = "Netheritschwert"; eng = "NETHERITE_SWORD"; break;
			case NETHERITE_SHOVEL: ger = "Netheritschaufel"; eng = "NETHERITE_SHOVEL"; break;
			case NETHERITE_PICKAXE: ger = "Netheritspitzhacke"; eng = "NETHERITE_PICKAXE"; break;
			case NETHERITE_AXE: ger = "Netheritaxt"; eng = "NETHERITE_AXE"; break;
			case NETHERITE_HOE: ger = "Netherithacke"; eng = "NETHERITE_HOE"; break;
			case STICK: ger = "Stock"; eng = "STICK"; break;
			case BOWL: ger = "Schüssel"; eng = "BOWL"; break;
			case MUSHROOM_STEW: ger = "Pilzsuppe"; eng = "MUSHROOM_STEW"; break;
			case STRING: ger = "Faden"; eng = "STRING"; break;
			case FEATHER: ger = "Feder"; eng = "FEATHER"; break;
			case GUNPOWDER: ger = "Scharzpulver"; eng = "GUNPOWDER"; break;
			case WHEAT_SEEDS: ger = "Weizenkörner"; eng = "WHEAT_SEEDS"; break;
			case WHEAT: ger = "Weizen"; eng = "WHEAT"; break;
			case BREAD: ger = "Brot"; eng = "BREAD"; break;
			case LEATHER_HELMET: ger = "Lederkappe"; eng = "LEATHER_HELMET"; break;
			case LEATHER_CHESTPLATE: ger = "Lderjacke"; eng = "LEATHER_CHESTPLATE"; break;
			case LEATHER_LEGGINGS: ger = "Lederhose"; eng = "LEATHER_LEGGINGS"; break;
			case LEATHER_BOOTS: ger = "Lederstiefel"; eng = "LEATHER_BOOTS"; break;
			case CHAINMAIL_HELMET: ger = "Kettenhaube"; eng = "CHAINMAIL_HELMET"; break;
			case CHAINMAIL_CHESTPLATE: ger = "Kettenhemd"; eng = "CHAINMAIL_CHESTPLATE"; break;
			case CHAINMAIL_LEGGINGS: ger = "Kettenhose"; eng = "CHAINMAIL_LEGGINGS"; break;
			case CHAINMAIL_BOOTS: ger = "Kettenstiefel"; eng = "CHAINMAIL_BOOTS"; break;
			case IRON_HELMET: ger = "Eisenhelm"; eng = "IRON_HELMET"; break;
			case IRON_CHESTPLATE: ger = "Eisenharnisch"; eng = "IRON_CHESTPLATE"; break;
			case IRON_LEGGINGS: ger = "Eisenbeinschutz"; eng = "IRON_LEGGINGS"; break;
			case IRON_BOOTS: ger = "Eisenstiefel"; eng = "IRON_BOOTS"; break;
			case DIAMOND_HELMET: ger = "Diamanthelm"; eng = "DIAMOND_HELMET"; break;
			case DIAMOND_CHESTPLATE: ger = "Diamantharnisch"; eng = "DIAMOND_CHESTPLATE"; break;
			case DIAMOND_LEGGINGS: ger = "Diamantbeinschutz"; eng = "DIAMOND_LEGGINGS"; break;
			case DIAMOND_BOOTS: ger = "Diamantstiefel"; eng = "DIAMOND_BOOTS"; break;
			case GOLDEN_HELMET: ger = "Goldhelm"; eng = "GOLDEN_HELMET"; break;
			case GOLDEN_CHESTPLATE: ger = "Goldharnisch"; eng = "GOLDEN_CHESTPLATE"; break;
			case GOLDEN_LEGGINGS: ger = "Goldbeinschutz"; eng = "GOLDEN_LEGGINGS"; break;
			case GOLDEN_BOOTS: ger = "Goldstiefel"; eng = "GOLDEN_BOOTS"; break;
			case NETHERITE_HELMET: ger = "Netherithelm"; eng = "NETHERITE_HELMET"; break;
			case NETHERITE_CHESTPLATE: ger = "Netheritharnisch"; eng = "NETHERITE_CHESTPLATE"; break;
			case NETHERITE_LEGGINGS: ger = "Netheritbeinschutz"; eng = "NETHERITE_LEGGINGS"; break;
			case NETHERITE_BOOTS: ger = "Netheritstiefel"; eng = "NETHERITE_BOOTS"; break;
			case FLINT: ger = "Feuerstein"; eng = "FLINT"; break;
			case PORKCHOP: ger = "PORKCHOP"; eng = "PORKCHOP"; break;
			case COOKED_PORKCHOP: ger = "Gebratenes Schweinefleisch"; eng = "COOKED_PORKCHOP"; break;
			case PAINTING: ger = "Gemälde"; eng = "PAINTING"; break;
			case GOLDEN_APPLE: ger = "Goldener Apfel"; eng = "GOLDEN_APPLE"; break;
			case ENCHANTED_GOLDEN_APPLE: ger = "Verzauberter goldener Apfel"; eng = "ENCHANTED_GOLDEN_APPLE"; break;
			case OAK_SIGN: ger = "Eichenholzschild"; eng = "OAK_SIGN"; break;
			case SPRUCE_SIGN: ger = "Fichtenholzschild"; eng = "SPRUCE_SIGN"; break;
			case BIRCH_SIGN: ger = "Birkenholzschild"; eng = "BIRCH_SIGN"; break;
			case JUNGLE_SIGN: ger = "Tropenholzschild"; eng = "JUNGLE_SIGN"; break;
			case ACACIA_SIGN: ger = "Akazienholzschild"; eng = "ACACIA_SIGN"; break;
			case DARK_OAK_SIGN: ger = "Schwarzeichenholzschild"; eng = "DARK_OAK_SIGN"; break;
			case MANGROVE_SIGN: ger = "Mangrovenschild"; eng = "MANGROVE_SIGN"; break;
			case CRIMSON_SIGN: ger = "Karmesinschild"; eng = "CRIMSON_SIGN"; break;
			case WARPED_SIGN: ger = "Wirrschild"; eng = "WARPED_SIGN"; break;
			case BUCKET: ger = "Eimer"; eng = "BUCKET"; break;
			case WATER_BUCKET: ger = "Wassereimer"; eng = "WATER_BUCKET"; break;
			case LAVA_BUCKET: ger = "Lavaeimer"; eng = "LAVA_BUCKET"; break;
			case POWDER_SNOW_BUCKET: ger = "Pulverschneeeimer"; eng = "POWDER_SNOW_BUCKET"; break;
			case SNOWBALL: ger = "Schneeball"; eng = "SNOWBALL"; break;
			case LEATHER: ger = "Leder"; eng = "LEATHER"; break;
			case MILK_BUCKET: ger = "Milcheimer"; eng = "MILK_BUCKET"; break;
			case PUFFERFISH_BUCKET: ger = "Kugelfischeimer"; eng = "PUFFERFISH_BUCKET"; break;
			case SALMON_BUCKET: ger = "Lachseimer"; eng = "SALMON_BUCKET"; break;
			case COD_BUCKET: ger = "Kabeljaueimer"; eng = "COD_BUCKET"; break;
			case TROPICAL_FISH_BUCKET: ger = "Tropenfischeimer"; eng = "TROPICAL_FISH_BUCKET"; break;
			case AXOLOTL_BUCKET: ger = "Axolotleimer"; eng = "AXOLOTL_BUCKET"; break;
			case TADPOLE_BUCKET: ger = "Kaulquappeneimer"; eng = "TADPOLE_BUCKET"; break;
			case BRICK: ger = "Ziegel"; eng = "BRICK"; break;
			case CLAY_BALL: ger = "Tonklumpen"; eng = "CLAY_BALL"; break;
			case DRIED_KELP_BLOCK: ger = "Getrockneter Seetangblock"; eng = "DRIED_KELP_BLOCK"; break;
			case PAPER: ger = "Papier"; eng = "PAPER"; break;
			case BOOK: ger = "Buch"; eng = "BOOK"; break;
			case SLIME_BALL: ger = "Schleimball"; eng = "SLIME_BALL"; break;
			case EGG: ger = "Ei"; eng = "EGG"; break;
			case COMPASS: ger = "Kompass"; eng = "COMPASS"; break;
			case RECOVERY_COMPASS: ger = "Bergungskompass"; eng = "RECOVERY_COMPASS"; break;
			case BUNDLE: ger = "Sack"; eng = "BUNDLE"; break;
			case FISHING_ROD: ger = "Angel"; eng = "FISHING_ROD"; break;
			case CLOCK: ger = "Uhr"; eng = "CLOCK"; break;
			case SPYGLASS: ger = "Fernrohr"; eng = "SPYGLASS"; break;
			case GLOWSTONE_DUST: ger = "Leuchtsteinstaub"; eng = "GLOWSTONE_DUST"; break;
			case COD: ger = "Kabeljau"; eng = "COD"; break;
			case SALMON: ger = "Lachs"; eng = "SALMON"; break;
			case TROPICAL_FISH: ger = "Tropenfisch"; eng = "TROPICAL_FISH"; break;
			case PUFFERFISH: ger = "Kugelfisch"; eng = "PUFFERFISH"; break;
			case COOKED_COD: ger = "Gebratener Kabeljau"; eng = "COOKED_COD"; break;
			case COOKED_SALMON: ger = "Gebratener Lachs"; eng = "COOKED_SALMON"; break;
			case INK_SAC: ger = "Tintensack"; eng = "INK_SAC"; break;
			case GLOW_INK_SAC: ger = "Leuchttintenbeutel"; eng = "GLOW_INK_SAC"; break;
			case COCOA_BEANS: ger = "Kakaobohnen"; eng = "COCOA_BEANS"; break;
			case WHITE_DYE: ger = "Weißer Farbstoff"; eng = "WHITE_DYE"; break;
			case ORANGE_DYE: ger = "Oranger Farbstoff"; eng = "ORANGE_DYE"; break;
			case MAGENTA_DYE: ger = "Magenta Farbstoff"; eng = "MAGENTA_DYE"; break;
			case LIGHT_BLUE_DYE: ger = "Hellblauer Farbstoff"; eng = "LIGHT_BLUE_DYE"; break;
			case YELLOW_DYE: ger = "Gelber Farbstoff"; eng = "YELLOW_DYE"; break;
			case LIME_DYE: ger = "Hellgrüner Farbstoff"; eng = "LIME_DYE"; break;
			case PINK_DYE: ger = "Rosa Farbstoff"; eng = "PINK_DYE"; break;
			case GRAY_DYE: ger = "Grauer Farbstoff"; eng = "GRAY_DYE"; break;
			case LIGHT_GRAY_DYE: ger = "Hellgrauer Farbstoff"; eng = "LIGHT_GRAY_DYE"; break;
			case CYAN_DYE: ger = "Türkiser Farbstoff"; eng = "CYAN_DYE"; break;
			case PURPLE_DYE: ger = "Violetter Farbstoff"; eng = "PURPLE_DYE"; break;
			case BLUE_DYE: ger = "Blauer Farbstoff"; eng = "BLUE_DYE"; break;
			case BROWN_DYE: ger = "Brauner Farbstoff"; eng = "BROWN_DYE"; break;
			case GREEN_DYE: ger = "Grüner Farbstoff"; eng = "GREEN_DYE"; break;
			case RED_DYE: ger = "Roter Farbstoff"; eng = "RED_DYE"; break;
			case BLACK_DYE: ger = "Schwarzer Farbstoff"; eng = "BLACK_DYE"; break;
			case BONE_MEAL: ger = "Knochenmehl"; eng = "BONE_MEAL"; break;
			case BONE: ger = "Knochen"; eng = "BONE"; break;
			case SUGAR: ger = "Zucker"; eng = "SUGAR"; break;
			case CAKE: ger = "Kuchen"; eng = "CAKE"; break;
			case WHITE_BED: ger = "Weisses Bett"; eng = "WHITE_BED"; break;
			case ORANGE_BED: ger = "Oranges Bett"; eng = "ORANGE_BED"; break;
			case MAGENTA_BED: ger = "Magenta Bett"; eng = "MAGENTA_BED"; break;
			case LIGHT_BLUE_BED: ger = "Hellblaues Bett"; eng = "LIGHT_BLUE_BED"; break;
			case YELLOW_BED: ger = "Gelbes Bett"; eng = "YELLOW_BED"; break;
			case LIME_BED: ger = "Hellgrünes Bett"; eng = "LIME_BED"; break;
			case PINK_BED: ger = "Rosa Bett"; eng = "PINK_BED"; break;
			case GRAY_BED: ger = "Graues Bett"; eng = "GRAY_BED"; break;
			case LIGHT_GRAY_BED: ger = "hellgraues Bett"; eng = "LIGHT_GRAY_BED"; break;
			case CYAN_BED: ger = "Türkises Bett"; eng = "CYAN_BED"; break;
			case PURPLE_BED: ger = "Violettes Bett"; eng = "PURPLE_BED"; break;
			case BLUE_BED: ger = "Blaues Bett"; eng = "BLUE_BED"; break;
			case BROWN_BED: ger = "Braunes Bett"; eng = "BROWN_BED"; break;
			case GREEN_BED: ger = "Grünes Bett"; eng = "GREEN_BED"; break;
			case RED_BED: ger = "Rotes Bett"; eng = "RED_BED"; break;
			case BLACK_BED: ger = "Schwarzes Bett"; eng = "BLACK_BED"; break;
			case COOKIE: ger = "Keks"; eng = "COOKIE"; break;
			case FILLED_MAP: ger = "Karte"; eng = "FILLED_MAP"; break;
			case SHEARS: ger = "Schere"; eng = "SHEARS"; break;
			case MELON_SLICE: ger = "Melonenscheibe"; eng = "MELON_SLICE"; break;
			case DRIED_KELP: ger = "Getrockneter Seetang"; eng = "DRIED_KELP"; break;
			case PUMPKIN_SEEDS: ger = "Kürbiskerne"; eng = "PUMPKIN_SEEDS"; break;
			case MELON_SEEDS: ger = "Melonenkerne"; eng = "MELON_SEEDS"; break;
			case BEEF: ger = "Rohes Rindfleisch"; eng = "BEEF"; break;
			case COOKED_BEEF: ger = "Steak"; eng = "COOKED_BEEF"; break;
			case CHICKEN: ger = "Rohes Hühnchen"; eng = "CHICKEN"; break;
			case COOKED_CHICKEN: ger = "Gebratenes Hühnchen"; eng = "COOKED_CHICKEN"; break;
			case ROTTEN_FLESH: ger = "Verrottetes Fleisch"; eng = "ROTTEN_FLESH"; break;
			case ENDER_PEARL: ger = "Enderperle"; eng = "ENDER_PEARL"; break;
			case BLAZE_ROD: ger = "Lohenrute"; eng = "BLAZE_ROD"; break;
			case GHAST_TEAR: ger = "Ghast-Träne"; eng = "GHAST_TEAR"; break;
			case GOLD_NUGGET: ger = "Goldklumpen"; eng = "GOLD_NUGGET"; break;
			case NETHER_WART: ger = "Netherwarze"; eng = "NETHER_WART"; break;
			case POTION: ger = "Wasserflasche"; eng = "POTION"; break;
			case GLASS_BOTTLE: ger = "Glasflasche"; eng = "GLASS_BOTTLE"; break;
			case SPIDER_EYE: ger = "Spinnenauge"; eng = "SPIDER_EYE"; break;
			case FERMENTED_SPIDER_EYE: ger = "Fermentiertes Spinnenauge"; eng = "FERMENTED_SPIDER_EYE"; break;
			case BLAZE_POWDER: ger = "Lohenstaub"; eng = "BLAZE_POWDER"; break;
			case MAGMA_CREAM: ger = "Magmacreme"; eng = "MAGMA_CREAM"; break;
			case BREWING_STAND: ger = "Braustand"; eng = "BREWING_STAND"; break;
			case CAULDRON: ger = "Kessel"; eng = "CAULDRON"; break;
			case ENDER_EYE: ger = "Enderauge"; eng = "ENDER_EYE"; break;
			case GLISTERING_MELON_SLICE: ger = "Glizernde Melonenscheibe"; eng = "GLISTERING_MELON_SLICE"; break;
			case ALLAY_SPAWN_EGG: ger = "Hilfsgeist-Spawn-Ei"; eng = "ALLAY_SPAWN_EGG"; break;
			case AXOLOTL_SPAWN_EGG: ger = "Axolottl-Spawn-Ei"; eng = "AXOLOTL_SPAWN_EGG"; break;
			case BAT_SPAWN_EGG: ger = "Fledermaus-Spawn-Ei"; eng = "BAT_SPAWN_EGG"; break;
			case BEE_SPAWN_EGG: ger = "Bienen-Spawn-Ei"; eng = "BEE_SPAWN_EGG"; break;
			case BLAZE_SPAWN_EGG: ger = "Lohen-Spawn-Ei"; eng = "BLAZE_SPAWN_EGG"; break;
			case CAT_SPAWN_EGG: ger = "Katzen-Spawn-Ei"; eng = "CAT_SPAWN_EGG"; break;
			case CAVE_SPIDER_SPAWN_EGG: ger = "Hohlenspinnen-Spawn-Ei"; eng = "CAVE_SPIDER_SPAWN_EGG"; break;
			case CHICKEN_SPAWN_EGG: ger = "Huhn-Spawn-Ei"; eng = "CHICKEN_SPAWN_EGG"; break;
			case COD_SPAWN_EGG: ger = "Kabeljau-Spawn-Ei"; eng = "COD_SPAWN_EGG"; break;
			case COW_SPAWN_EGG: ger = "Kuh-Spawn-Ei"; eng = "COW_SPAWN_EGG"; break;
			case CREEPER_SPAWN_EGG: ger = "Creeper-Spawn-Ei"; eng = "CREEPER_SPAWN_EGG"; break;
			case DOLPHIN_SPAWN_EGG: ger = "Delphin-Spawn-Ei"; eng = "DOLPHIN_SPAWN_EGG"; break;
			case DONKEY_SPAWN_EGG: ger = "Esel-Spawn-Ei"; eng = "DONKEY_SPAWN_EGG"; break;
			case DROWNED_SPAWN_EGG: ger = "Ertrunkenen-Spawn-Ei"; eng = "DROWNED_SPAWN_EGG"; break;
			case ELDER_GUARDIAN_SPAWN_EGG: ger = "Großer-Wächter-Spawn-Ei"; eng = "ELDER_GUARDIAN_SPAWN_EGG"; break;
			case ENDERMAN_SPAWN_EGG: ger = "Endermen-Spawn-Ei"; eng = "ENDERMAN_SPAWN_EGG"; break;
			case ENDERMITE_SPAWN_EGG: ger = "Endermiten-Spawn-Ei"; eng = "ENDERMITE_SPAWN_EGG"; break;
			case EVOKER_SPAWN_EGG: ger = "Magier-Spawn-Ei"; eng = "EVOKER_SPAWN_EGG"; break;
			case FOX_SPAWN_EGG: ger = "Fuchs-Spawn-Ei"; eng = "FOX_SPAWN_EGG"; break;
			case FROG_SPAWN_EGG: ger = "Frosch-Spawn-Ei"; eng = "FROG_SPAWN_EGG"; break;
			case GHAST_SPAWN_EGG: ger = "Ghast-Spawn-Ei"; eng = "GHAST_SPAWN_EGG"; break;
			case GLOW_SQUID_SPAWN_EGG: ger = "Leuchttintenfisch-Spawn-Ei"; eng = "GLOW_SQUID_SPAWN_EGG"; break;
			case GOAT_SPAWN_EGG: ger = "Ziegen-Spawn-Ei"; eng = "GOAT_SPAWN_EGG"; break;
			case GUARDIAN_SPAWN_EGG: ger = "Wächter-Spawn-Ei"; eng = "GUARDIAN_SPAWN_EGG"; break;
			case HOGLIN_SPAWN_EGG: ger = "Hoglin-Spawn-Ei"; eng = "HOGLIN_SPAWN_EGG"; break;
			case HORSE_SPAWN_EGG: ger = "Pferde-Spawn-Ei"; eng = "HORSE_SPAWN_EGG"; break;
			case HUSK_SPAWN_EGG: ger = "Wüstenzombie-Spawn-Ei"; eng = "HUSK_SPAWN_EGG"; break;
			case LLAMA_SPAWN_EGG: ger = "Lama-Spawn-Ei"; eng = "LLAMA_SPAWN_EGG"; break;
			case MAGMA_CUBE_SPAWN_EGG: ger = "Magmawürfel-Spawn-Ei"; eng = "MAGMA_CUBE_SPAWN_EGG"; break;
			case MOOSHROOM_SPAWN_EGG: ger = "Mooshroom-Spawn-Ei"; eng = "MOOSHROOM_SPAWN_EGG"; break;
			case MULE_SPAWN_EGG: ger = "Maultier-Spawn-Ei"; eng = "MULE_SPAWN_EGG"; break;
			case OCELOT_SPAWN_EGG: ger = "Ozelot-Spawn-Ei"; eng = "OCELOT_SPAWN_EGG"; break;
			case PANDA_SPAWN_EGG: ger = "Panda-Spawn-Ei"; eng = "PANDA_SPAWN_EGG"; break;
			case PARROT_SPAWN_EGG: ger = "Papageien-Spawn-Ei"; eng = "PARROT_SPAWN_EGG"; break;
			case PHANTOM_SPAWN_EGG: ger = "Phantom-Spawn-Ei"; eng = "PHANTOM_SPAWN_EGG"; break;
			case PIG_SPAWN_EGG: ger = "Schweine-Spawn-Ei"; eng = "PIG_SPAWN_EGG"; break;
			case PIGLIN_SPAWN_EGG: ger = "Piglin-Spawn-Ei"; eng = "PIGLIN_SPAWN_EGG"; break;
			case PIGLIN_BRUTE_SPAWN_EGG: ger = "Piglin-Babaren-Spawn-Ei"; eng = "PIGLIN_BRUTE_SPAWN_EGG"; break;
			case PILLAGER_SPAWN_EGG: ger = "Plünderer-Spawn-Ei"; eng = "PILLAGER_SPAWN_EGG"; break;
			case POLAR_BEAR_SPAWN_EGG: ger = "Eisbären-Spawn-Ei"; eng = "POLAR_BEAR_SPAWN_EGG"; break;
			case PUFFERFISH_SPAWN_EGG: ger = "Kugelfisch-Spawn-Ei"; eng = "PUFFERFISH_SPAWN_EGG"; break;
			case RABBIT_SPAWN_EGG: ger = "Kaninchen-Spawn-Ei"; eng = "RABBIT_SPAWN_EGG"; break;
			case RAVAGER_SPAWN_EGG: ger = "Verwüster-Spawn-Ei"; eng = "RAVAGER_SPAWN_EGG"; break;
			case SALMON_SPAWN_EGG: ger = "Lachs-Spawn-Ei"; eng = "SALMON_SPAWN_EGG"; break;
			case SHEEP_SPAWN_EGG: ger = "Schafs-Spawn-Ei"; eng = "SHEEP_SPAWN_EGG"; break;
			case SHULKER_SPAWN_EGG: ger = "Shulker-Spawn-Ei"; eng = "SHULKER_SPAWN_EGG"; break;
			case SILVERFISH_SPAWN_EGG: ger = "Silberfischchen-Spawn-Ei"; eng = "SILVERFISH_SPAWN_EGG"; break;
			case SKELETON_SPAWN_EGG: ger = "Skelett-Spawn-Ei"; eng = "SKELETON_SPAWN_EGG"; break;
			case SKELETON_HORSE_SPAWN_EGG: ger = "Skelett-Pferde-Spawn-Ei"; eng = "SKELETON_HORSE_SPAWN_EGG"; break;
			case SLIME_SPAWN_EGG: ger = "Schleim-Spawn-Ei"; eng = "SLIME_SPAWN_EGG"; break;
			case SPIDER_SPAWN_EGG: ger = "Spinnen-Spawn-Ei"; eng = "SPIDER_SPAWN_EGG"; break;
			case SQUID_SPAWN_EGG: ger = "Tintenfisch-Spawn-Ei"; eng = "SQUID_SPAWN_EGG"; break;
			case STRAY_SPAWN_EGG: ger = "Eiswanderer-Spawn-Ei"; eng = "STRAY_SPAWN_EGG"; break;
			case STRIDER_SPAWN_EGG: ger = "Schreiter-Spawn-Ei"; eng = "STRIDER_SPAWN_EGG"; break;
			case TADPOLE_SPAWN_EGG: ger = "Kaulquappen-Spawn-Ei"; eng = "TADPOLE_SPAWN_EGG"; break;
			case TRADER_LLAMA_SPAWN_EGG: ger = "Händlerlama-Spawn-Ei"; eng = "TRADER_LLAMA_SPAWN_EGG"; break;
			case TROPICAL_FISH_SPAWN_EGG: ger = "Tropenfisch-Spawn-Ei"; eng = "TROPICAL_FISH_SPAWN_EGG"; break;
			case TURTLE_SPAWN_EGG: ger = "Schildkröten-Spawn-Ei"; eng = "TURTLE_SPAWN_EGG"; break;
			case VEX_SPAWN_EGG: ger = "Plagegeister-Spawn-Ei"; eng = "VEX_SPAWN_EGG"; break;
			case VILLAGER_SPAWN_EGG: ger = "Dorfbewohner-Spawn-Ei"; eng = "VILLAGER_SPAWN_EGG"; break;
			case VINDICATOR_SPAWN_EGG: ger = "Diener-Spawn-Ei"; eng = "VINDICATOR_SPAWN_EGG"; break;
			case WANDERING_TRADER_SPAWN_EGG: ger = "Fahrender-Händler-Spawn-Ei"; eng = "WANDERING_TRADER_SPAWN_EGG"; break;
			case WARDEN_SPAWN_EGG: ger = "Wärter-Spawn-Ei"; eng = "WARDEN_SPAWN_EGG"; break;
			case WITCH_SPAWN_EGG: ger = "Hexen-Spawn-Ei"; eng = "WITCH_SPAWN_EGG"; break;
			case WITHER_SKELETON_SPAWN_EGG: ger = "Witherskelett-Spawn-Ei"; eng = "WITHER_SKELETON_SPAWN_EGG"; break;
			case WOLF_SPAWN_EGG: ger = "Wolfs-Spawn-Ei"; eng = "WOLF_SPAWN_EGG"; break;
			case ZOGLIN_SPAWN_EGG: ger = "Zoglin-Spawn-Ei"; eng = "ZOGLIN_SPAWN_EGG"; break;
			case ZOMBIE_SPAWN_EGG: ger = "Zobie-Spawn-Ei"; eng = "ZOMBIE_SPAWN_EGG"; break;
			case ZOMBIE_HORSE_SPAWN_EGG: ger = "Zombiepferde-Spawn-Ei"; eng = "ZOMBIE_HORSE_SPAWN_EGG"; break;
			case ZOMBIE_VILLAGER_SPAWN_EGG: ger = "Zombiedorfbewohner-Spawn-Ei"; eng = "ZOMBIE_VILLAGER_SPAWN_EGG"; break;
			case ZOMBIFIED_PIGLIN_SPAWN_EGG: ger = "Zobifizierter-Piglin-Spawn-Ein"; eng = "ZOMBIFIED_PIGLIN_SPAWN_EGG"; break;
			case EXPERIENCE_BOTTLE: ger = "Erfahrungsfläschchen"; eng = "EXPERIENCE_BOTTLE"; break;
			case FIRE_CHARGE: ger = "Feuerkugel"; eng = "FIRE_CHARGE"; break;
			case WRITABLE_BOOK: ger = "Buch und Feder"; eng = "WRITABLE_BOOK"; break;
			case WRITTEN_BOOK: ger = "Beschriebenes Buch"; eng = "WRITTEN_BOOK"; break;
			case ITEM_FRAME: ger = "Rahmen"; eng = "ITEM_FRAME"; break;
			case GLOW_ITEM_FRAME: ger = "Leuchtrahmen"; eng = "GLOW_ITEM_FRAME"; break;
			case FLOWER_POT: ger = "Blumentopf"; eng = "FLOWER_POT"; break;
			case CARROT: ger = "Karotte"; eng = "CARROT"; break;
			case POTATO: ger = "POTATO"; eng = "POTATO"; break;
			case BAKED_POTATO: ger = "Ofenkartoffel"; eng = "BAKED_POTATO"; break;
			case POISONOUS_POTATO: ger = "Giftige Kartoffel"; eng = "POISONOUS_POTATO"; break;
			case MAP: ger = "Leere Karte"; eng = "MAP"; break;
			case GOLDEN_CARROT: ger = "Goldene Karotte"; eng = "GOLDEN_CARROT"; break;
			case SKELETON_SKULL: ger = "Skelettschädel"; eng = "SKELETON_SKULL"; break;
			case WITHER_SKELETON_SKULL: ger = "Whitherskelettschädel"; eng = "WITHER_SKELETON_SKULL"; break;
			case PLAYER_HEAD: ger = "Spielerkopf"; eng = "PLAYER_HEAD"; break;
			case ZOMBIE_HEAD: ger = "Zombiekopf"; eng = "ZOMBIE_HEAD"; break;
			case CREEPER_HEAD: ger = "Creeperkopf"; eng = "CREEPER_HEAD"; break;
			case DRAGON_HEAD: ger = "Drachenkopf"; eng = "DRAGON_HEAD"; break;
			case NETHER_STAR: ger = "Netherstern"; eng = "NETHER_STAR"; break;
			case PUMPKIN_PIE: ger = "Kürbiskuchen"; eng = "PUMPKIN_PIE"; break;
			case FIREWORK_ROCKET: ger = "Feuerwerksrakete"; eng = "FIREWORK_ROCKET"; break;
			case FIREWORK_STAR: ger = "Feuerwerksstern"; eng = "FIREWORK_STAR"; break;
			case ENCHANTED_BOOK: ger = "Verzaubertes Buch"; eng = "ENCHANTED_BOOK"; break;
			case NETHER_BRICK: ger = "Netherziegel"; eng = "NETHER_BRICK"; break;
			case PRISMARINE_SHARD: ger = "Prismarinscherbe"; eng = "PRISMARINE_SHARD"; break;
			case PRISMARINE_CRYSTALS: ger = "Prismarinekristalle"; eng = "PRISMARINE_CRYSTALS"; break;
			case RABBIT: ger = "Rohes Kaninchen"; eng = "RABBIT"; break;
			case COOKED_RABBIT: ger = "Gebratenes Kaninchen"; eng = "COOKED_RABBIT"; break;
			case RABBIT_STEW: ger = "Kaninchenragout"; eng = "RABBIT_STEW"; break;
			case RABBIT_FOOT: ger = "Hasenpfote"; eng = "RABBIT_FOOT"; break;
			case RABBIT_HIDE: ger = "Kaninchenfell"; eng = "RABBIT_HIDE"; break;
			case ARMOR_STAND: ger = "Rüstungsständer"; eng = "ARMOR_STAND"; break;
			case IRON_HORSE_ARMOR: ger = "Eisner Rossharnisch"; eng = "IRON_HORSE_ARMOR"; break;
			case GOLDEN_HORSE_ARMOR: ger = "Goldener Rossharnisch"; eng = "GOLDEN_HORSE_ARMOR"; break;
			case DIAMOND_HORSE_ARMOR: ger = "Diamantener Rossharnisch"; eng = "DIAMOND_HORSE_ARMOR"; break;
			case LEATHER_HORSE_ARMOR: ger = "Ledener Rossharisch"; eng = "LEATHER_HORSE_ARMOR"; break;
			case LEAD: ger = "Leine"; eng = "LEAD"; break;
			case NAME_TAG: ger = "Namensschild"; eng = "NAME_TAG"; break;
			case COMMAND_BLOCK_MINECART: ger = "Befehlsblocklore"; eng = "COMMAND_BLOCK_MINECART"; break;
			case MUTTON: ger = "Rohes Hammelfleisch"; eng = "MUTTON"; break;
			case COOKED_MUTTON: ger = "Gebratenes Hammelfleisch"; eng = "COOKED_MUTTON"; break;
			case WHITE_BANNER: ger = "Weißes Banner"; eng = "WHITE_BANNER"; break;
			case ORANGE_BANNER: ger = "Oranges Banner"; eng = "ORANGE_BANNER"; break;
			case MAGENTA_BANNER: ger = "Magenta Banner"; eng = "MAGENTA_BANNER"; break;
			case LIGHT_BLUE_BANNER: ger = "Hellblaues Banner"; eng = "LIGHT_BLUE_BANNER"; break;
			case YELLOW_BANNER: ger = "Gelbes Banner"; eng = "YELLOW_BANNER"; break;
			case LIME_BANNER: ger = "Hellgrünes Banner"; eng = "LIME_BANNER"; break;
			case PINK_BANNER: ger = "Rosa Banner"; eng = "PINK_BANNER"; break;
			case GRAY_BANNER: ger = "Graues Banner"; eng = "GRAY_BANNER"; break;
			case LIGHT_GRAY_BANNER: ger = "hellgraues Banner"; eng = "LIGHT_GRAY_BANNER"; break;
			case CYAN_BANNER: ger = "Türkises Banner"; eng = "CYAN_BANNER"; break;
			case PURPLE_BANNER: ger = "Violettes Banner"; eng = "PURPLE_BANNER"; break;
			case BLUE_BANNER: ger = "Blaues Banner"; eng = "BLUE_BANNER"; break;
			case BROWN_BANNER: ger = "Braunes Banner"; eng = "BROWN_BANNER"; break;
			case GREEN_BANNER: ger = "Grünes Banner"; eng = "GREEN_BANNER"; break;
			case RED_BANNER: ger = "Rotes Banner"; eng = "RED_BANNER"; break;
			case BLACK_BANNER: ger = "Schwarzes Banner"; eng = "BLACK_BANNER"; break;
			case END_CRYSTAL: ger = "Enderkristall"; eng = "END_CRYSTAL"; break;
			case CHORUS_FRUIT: ger = "Chorusfrucht"; eng = "CHORUS_FRUIT"; break;
			case POPPED_CHORUS_FRUIT: ger = "Geplatzte Chorusfrucht"; eng = "POPPED_CHORUS_FRUIT"; break;
			case BEETROOT: ger = "Rote Bete"; eng = "BEETROOT"; break;
			case BEETROOT_SEEDS: ger = "Rote-Bete-Samen"; eng = "BEETROOT_SEEDS"; break;
			case BEETROOT_SOUP: ger = "Borschtsch"; eng = "BEETROOT_SOUP"; break;
			case DRAGON_BREATH: ger = "Drachenatem"; eng = "DRAGON_BREATH"; break;
			case SPLASH_POTION: ger = "Werfbare Wasserflasche"; eng = "SPLASH_POTION"; break;
			case SPECTRAL_ARROW: ger = "Spektralpfeil"; eng = "SPECTRAL_ARROW"; break;
			case TIPPED_ARROW: ger = "Nicht herstellbarer getränkter Pfeil"; eng = "TIPPED_ARROW"; break;
			case LINGERING_POTION: ger = "Verweilende Wasserflasche"; eng = "LINGERING_POTION"; break;
			case SHIELD: ger = "Schild"; eng = "SHIELD"; break;
			case TOTEM_OF_UNDYING: ger = "Totem der Unsterblichkeit"; eng = "TOTEM_OF_UNDYING"; break;
			case SHULKER_SHELL: ger = "Shulker-Schale"; eng = "SHULKER_SHELL"; break;
			case IRON_NUGGET: ger = "Eisenklumpen"; eng = "IRON_NUGGET"; break;
			case KNOWLEDGE_BOOK: ger = "Buch des Wissens"; eng = "KNOWLEDGE_BOOK"; break;
			case DEBUG_STICK: ger = "Debug-Stick"; eng = "DEBUG_STICK"; break;
			case MUSIC_DISC_13: ger = "Schallplatte C418-13-"; eng = "MUSIC_DISC_13"; break;
			case MUSIC_DISC_CAT: ger = "Schallplatte C418-Cat"; eng = "MUSIC_DISC_CAT"; break;
			case MUSIC_DISC_BLOCKS: ger = "Schallplatte C418-Blocks"; eng = "MUSIC_DISC_BLOCKS"; break;
			case MUSIC_DISC_CHIRP: ger = "Schallplatte C418-Chirp"; eng = "MUSIC_DISC_CHIRP"; break;
			case MUSIC_DISC_FAR: ger = "Schallplatte C418-Far"; eng = "MUSIC_DISC_FAR"; break;
			case MUSIC_DISC_MALL: ger = "Schallplatte C418-Mall"; eng = "MUSIC_DISC_MALL"; break;
			case MUSIC_DISC_MELLOHI: ger = "Schallplatte C418-Mellohi"; eng = "MUSIC_DISC_MELLOHI"; break;
			case MUSIC_DISC_STAL: ger = "Schallplatte C418-Stal"; eng = "MUSIC_DISC_STAL"; break;
			case MUSIC_DISC_STRAD: ger = "Schallplatte C418-Strad"; eng = "MUSIC_DISC_STRAD"; break;
			case MUSIC_DISC_WARD: ger = "Schallplatte C418-Ward"; eng = "MUSIC_DISC_WARD"; break;
			case MUSIC_DISC_11: ger = "Schallplatte C418-11"; eng = "MUSIC_DISC_11"; break;
			case MUSIC_DISC_WAIT: ger = "Schallplatte C418-Wait"; eng = "MUSIC_DISC_WAIT"; break;
			case MUSIC_DISC_OTHERSIDE: ger = "Schallplatte Lena Raine Otherside"; eng = "MUSIC_DISC_OTHERSIDE"; break;
			case MUSIC_DISC_5: ger = "Schallplatte Samuel Aberg-5"; eng = "MUSIC_DISC_5"; break;
			case MUSIC_DISC_PIGSTEP: ger = "Schallplatte Lena Raine Pigstep"; eng = "MUSIC_DISC_PIGSTEP"; break;
			case DISC_FRAGMENT_5: ger = "Plattenbruchstück"; eng = "DISC_FRAGMENT_5"; break;
			case TRIDENT: ger = "Dreizack"; eng = "TRIDENT"; break;
			case PHANTOM_MEMBRANE: ger = "Phantomhaut"; eng = "PHANTOM_MEMBRANE"; break;
			case NAUTILUS_SHELL: ger = "Nautilisschale"; eng = "NAUTILUS_SHELL"; break;
			case HEART_OF_THE_SEA: ger = "Herz des Meeres"; eng = "HEART_OF_THE_SEA"; break;
			case CROSSBOW: ger = "Armbrust"; eng = "CROSSBOW"; break;
			case SUSPICIOUS_STEW: ger = "Seltsame Suppe"; eng = "SUSPICIOUS_STEW"; break;
			case LOOM: ger = "Webstuhl"; eng = "LOOM"; break;
			case FLOWER_BANNER_PATTERN: ger = "Bannervorlage Blume"; eng = "FLOWER_BANNER_PATTERN"; break;
			case CREEPER_BANNER_PATTERN: ger = "Bannervorlage Creeper"; eng = "CREEPER_BANNER_PATTERN"; break;
			case SKULL_BANNER_PATTERN: ger = "Bannervorlage Schädel"; eng = "SKULL_BANNER_PATTERN"; break;
			case MOJANG_BANNER_PATTERN: ger = "Bannervorlage Mojang-Logo"; eng = "MOJANG_BANNER_PATTERN"; break;
			case GLOBE_BANNER_PATTERN: ger = "Bannervorlage Globus"; eng = "GLOBE_BANNER_PATTERN"; break;
			case PIGLIN_BANNER_PATTERN: ger = "Bannervorlage Schnauze"; eng = "PIGLIN_BANNER_PATTERN"; break;
			case GOAT_HORN: ger = "Bockshorn"; eng = "GOAT_HORN"; break;
			case COMPOSTER: ger = "Komposter"; eng = "COMPOSTER"; break;
			case BARREL: ger = "Fass"; eng = "BARREL"; break;
			case SMOKER: ger = "Räucherofen"; eng = "SMOKER"; break;
			case BLAST_FURNACE: ger = "Schnelzofen"; eng = "BLAST_FURNACE"; break;
			case CARTOGRAPHY_TABLE: ger = "Kartentisch"; eng = "CARTOGRAPHY_TABLE"; break;
			case FLETCHING_TABLE: ger = "Bognertisch"; eng = "FLETCHING_TABLE"; break;
			case GRINDSTONE: ger = "Schleifstein"; eng = "GRINDSTONE"; break;
			case SMITHING_TABLE: ger = "Schmiedetisch"; eng = "SMITHING_TABLE"; break;
			case STONECUTTER: ger = "Steinsäge"; eng = "STONECUTTER"; break;
			case BELL: ger = "Glocke"; eng = "BELL"; break;
			case LANTERN: ger = "Laterne"; eng = "LANTERN"; break;
			case SOUL_LANTERN: ger = "Seelenlaterne"; eng = "SOUL_LANTERN"; break;
			case SWEET_BERRIES: ger = "Süßbeeren"; eng = "SWEET_BERRIES"; break;
			case GLOW_BERRIES: ger = "Leuchtbeeren"; eng = "GLOW_BERRIES"; break;
			case CAMPFIRE: ger = "Lagerfeuer"; eng = "CAMPFIRE"; break;
			case SOUL_CAMPFIRE: ger = "Seelenlagerfeuer"; eng = "SOUL_CAMPFIRE"; break;
			case SHROOMLIGHT: ger = "Pilzlicht"; eng = "SHROOMLIGHT"; break;
			case HONEYCOMB: ger = "Honigwabe"; eng = "HONEYCOMB"; break;
			case BEE_NEST: ger = "Bienennest"; eng = "BEE_NEST"; break;
			case BEEHIVE: ger = "Bienestock"; eng = "BEEHIVE"; break;
			case HONEY_BOTTLE: ger = "Honigflasche"; eng = "HONEY_BOTTLE"; break;
			case HONEYCOMB_BLOCK: ger = "Honigwabenblock"; eng = "HONEYCOMB_BLOCK"; break;
			case LODESTONE: ger = "Leitstein"; eng = "LODESTONE"; break;
			case CRYING_OBSIDIAN: ger = "Weinender Obsidian"; eng = "CRYING_OBSIDIAN"; break;
			case BLACKSTONE: ger = "Schwarzstein"; eng = "BLACKSTONE"; break;
			case BLACKSTONE_SLAB: ger = "Schwarzsteinstufe"; eng = "BLACKSTONE_SLAB"; break;
			case BLACKSTONE_STAIRS: ger = "Schwarzsteintreppe"; eng = "BLACKSTONE_STAIRS"; break;
			case GILDED_BLACKSTONE: ger = "Golddurchzogener Schwarzstein"; eng = "GILDED_BLACKSTONE"; break;
			case POLISHED_BLACKSTONE: ger = "Polierter Schwarzstein"; eng = "POLISHED_BLACKSTONE"; break;
			case POLISHED_BLACKSTONE_SLAB: ger = "Polierte Schwarzsteinstufe"; eng = "POLISHED_BLACKSTONE_SLAB"; break;
			case POLISHED_BLACKSTONE_STAIRS: ger = "Polierte Schwarzsteintreppe"; eng = "POLISHED_BLACKSTONE_STAIRS"; break;
			case CHISELED_POLISHED_BLACKSTONE: ger = "Gemeißelter polierter Schwarzstein"; eng = "CHISELED_POLISHED_BLACKSTONE"; break;
			case POLISHED_BLACKSTONE_BRICKS: ger = "Polierte Schwarzsteinziegel"; eng = "POLISHED_BLACKSTONE_BRICKS"; break;
			case POLISHED_BLACKSTONE_BRICK_SLAB: ger = "Polierte Schwarsteinziegelstufe"; eng = "POLISHED_BLACKSTONE_BRICK_SLAB"; break;
			case POLISHED_BLACKSTONE_BRICK_STAIRS: ger = "Polierte Schwarzsteinziegeltreppe"; eng = "POLISHED_BLACKSTONE_BRICK_STAIRS"; break;
			case CRACKED_POLISHED_BLACKSTONE_BRICKS: ger = "Rissige polierte Schwarzsteinziegel"; eng = "CRACKED_POLISHED_BLACKSTONE_BRICKS"; break;
			case RESPAWN_ANCHOR: ger = "Seelenanker"; eng = "RESPAWN_ANCHOR"; break;
			case CANDLE: ger = "Kerze"; eng = "CANDLE"; break;
			case WHITE_CANDLE: ger = "Weiße Kerze"; eng = "WHITE_CANDLE"; break;
			case ORANGE_CANDLE: ger = "Orange Kerze"; eng = "ORANGE_CANDLE"; break;
			case MAGENTA_CANDLE: ger = "Magenta Kerze"; eng = "MAGENTA_CANDLE"; break;
			case LIGHT_BLUE_CANDLE: ger = "Hellblaue Kerze"; eng = "LIGHT_BLUE_CANDLE"; break;
			case YELLOW_CANDLE: ger = "Gelbe Kerze"; eng = "YELLOW_CANDLE"; break;
			case LIME_CANDLE: ger = "Hellgrüne Kerze"; eng = "LIME_CANDLE"; break;
			case PINK_CANDLE: ger = "Rosa Kerze"; eng = "PINK_CANDLE"; break;
			case GRAY_CANDLE: ger = "Graue Kerze"; eng = "GRAY_CANDLE"; break;
			case LIGHT_GRAY_CANDLE: ger = "Hellgraue Kerze"; eng = "LIGHT_GRAY_CANDLE"; break;
			case CYAN_CANDLE: ger = "Türkise Kerze"; eng = "CYAN_CANDLE"; break;
			case PURPLE_CANDLE: ger = "Violette Kerze"; eng = "PURPLE_CANDLE"; break;
			case BLUE_CANDLE: ger = "Blaue Kerze"; eng = "BLUE_CANDLE"; break;
			case BROWN_CANDLE: ger = "Braune Kerze"; eng = "BROWN_CANDLE"; break;
			case GREEN_CANDLE: ger = "Grüne Kerze"; eng = "GREEN_CANDLE"; break;
			case RED_CANDLE: ger = "Rote Kerze"; eng = "RED_CANDLE"; break;
			case BLACK_CANDLE: ger = "Schwarze Kerze"; eng = "BLACK_CANDLE"; break;
			case SMALL_AMETHYST_BUD: ger = "Kleine Amethystknospe"; eng = "SMALL_AMETHYST_BUD"; break;
			case MEDIUM_AMETHYST_BUD: ger = "Mittlere Amethystknospe"; eng = "MEDIUM_AMETHYST_BUD"; break;
			case LARGE_AMETHYST_BUD: ger = "Große Amethystknospe"; eng = "LARGE_AMETHYST_BUD"; break;
			case AMETHYST_CLUSTER: ger = "Amethysthaufen"; eng = "AMETHYST_CLUSTER"; break;
			case POINTED_DRIPSTONE: ger = "Spitzer Tropfstein"; eng = "POINTED_DRIPSTONE"; break;
			case OCHRE_FROGLIGHT: ger = "Ockernes Froschlicht"; eng = "OCHRE_FROGLIGHT"; break;
			case VERDANT_FROGLIGHT: ger = "Junggrünes Froschlicht"; eng = "VERDANT_FROGLIGHT"; break;
			case PEARLESCENT_FROGLIGHT: ger = "Perlmutternes Froschlicht"; eng = "PEARLESCENT_FROGLIGHT"; break;
			case FROGSPAWN: ger = "Froschlaich"; eng = "FROGSPAWN"; break;
			case ECHO_SHARD: ger = "Echoscherbe"; eng = "ECHO_SHARD"; break;
			case WATER: ger = "Wasser"; eng = "WATER"; break;
			case LAVA: ger = "Lava"; eng = "LAVA"; break;
			case TALL_SEAGRASS: ger = "Hohes Seegras"; eng = "TALL_SEAGRASS"; break;
			case PISTON_HEAD: ger = "Kolben Kopf"; eng = "PISTON_HEAD"; break;
			case MOVING_PISTON: ger = "Verschobener Kolben"; eng = "MOVING_PISTON"; break;
			case WALL_TORCH: ger = "Wand-Fackel"; eng = "WALL_TORCH"; break;
			case FIRE: ger = "Feuer"; eng = "FIRE"; break;
			case SOUL_FIRE: ger = "Seelenfeuer"; eng = "SOUL_FIRE"; break;
			case REDSTONE_WIRE: ger = "Redstone Leitung"; eng = "REDSTONE_WIRE"; break;
			case OAK_WALL_SIGN: ger = "Eichenholzschild Wand"; eng = "OAK_WALL_SIGN"; break;
			case SPRUCE_WALL_SIGN: ger = "Fichtenholzschild Wand"; eng = "SPRUCE_WALL_SIGN"; break;
			case BIRCH_WALL_SIGN: ger = "Birkenholzschild Wand"; eng = "BIRCH_WALL_SIGN"; break;
			case ACACIA_WALL_SIGN: ger = "Akazienholzschild Wand"; eng = "ACACIA_WALL_SIGN"; break;
			case JUNGLE_WALL_SIGN: ger = "Tropenholschildd Wand"; eng = "JUNGLE_WALL_SIGN"; break;
			case DARK_OAK_WALL_SIGN: ger = "Schwarzeichenholzschild Wand"; eng = "DARK_OAK_WALL_SIGN"; break;
			case MANGROVE_WALL_SIGN: ger = "Mangrovenholzschild Wand"; eng = "MANGROVE_WALL_SIGN"; break;
			case REDSTONE_WALL_TORCH: ger = "Wand-Redstonefackel"; eng = "REDSTONE_WALL_TORCH"; break;
			case SOUL_WALL_TORCH: ger = "Wand-Seelen-Fackel"; eng = "SOUL_WALL_TORCH"; break;
			case NETHER_PORTAL: ger = "Netherportal"; eng = "NETHER_PORTAL"; break;
			case ATTACHED_PUMPKIN_STEM: ger = "Vereinter Kürbis"; eng = "ATTACHED_PUMPKIN_STEM"; break;
			case ATTACHED_MELON_STEM: ger = "Vereinte Melone"; eng = "ATTACHED_MELON_STEM"; break;
			case PUMPKIN_STEM: ger = "Kürbisstamm"; eng = "PUMPKIN_STEM"; break;
			case MELON_STEM: ger = "Melonenstamm"; eng = "MELON_STEM"; break;
			case WATER_CAULDRON: ger = "Wasserkessel"; eng = "WATER_CAULDRON"; break;
			case LAVA_CAULDRON: ger = "Lavakessel"; eng = "LAVA_CAULDRON"; break;
			case POWDER_SNOW_CAULDRON: ger = "Puderschneekessel"; eng = "POWDER_SNOW_CAULDRON"; break;
			case END_PORTAL: ger = "Endportal"; eng = "END_PORTAL"; break;
			case COCOA: ger = "Kakao"; eng = "COCOA"; break;
			case TRIPWIRE: ger = "Stolperdraht"; eng = "TRIPWIRE"; break;
			case POTTED_OAK_SAPLING: ger = "Eingetopfter Eichensetzling"; eng = "POTTED_OAK_SAPLING"; break;
			case POTTED_SPRUCE_SAPLING: ger = "Eingetopfter Fichtesetzlin"; eng = "POTTED_SPRUCE_SAPLING"; break;
			case POTTED_BIRCH_SAPLING: ger = "Eingetopfter Birkensetzling"; eng = "POTTED_BIRCH_SAPLING"; break;
			case POTTED_JUNGLE_SAPLING: ger = "Eingetopfter Tropensetzling"; eng = "POTTED_JUNGLE_SAPLING"; break;
			case POTTED_ACACIA_SAPLING: ger = "Eingetopfter Akaziensetzling"; eng = "POTTED_ACACIA_SAPLING"; break;
			case POTTED_DARK_OAK_SAPLING: ger = "Eingetopfter Schwarzeichensetzling"; eng = "POTTED_DARK_OAK_SAPLING"; break;
			case POTTED_MANGROVE_PROPAGULE: ger = "Eingetopfter Mangrovensetzling"; eng = "POTTED_MANGROVE_PROPAGULE"; break;
			case POTTED_FERN: ger = "Eingetopfter Farn"; eng = "POTTED_FERN"; break;
			case POTTED_DANDELION: ger = "Eingetopfter Löwenzahn"; eng = "POTTED_DANDELION"; break;
			case POTTED_POPPY: ger = "Eingetopfter Mohn"; eng = "POTTED_POPPY"; break;
			case POTTED_BLUE_ORCHID: ger = "Eingetopfte Orichide"; eng = "POTTED_BLUE_ORCHID"; break;
			case POTTED_ALLIUM: ger = "Eingetopfter Zierlauch"; eng = "POTTED_ALLIUM"; break;
			case POTTED_AZURE_BLUET: ger = "Eingetopfter Porzelansternchen"; eng = "POTTED_AZURE_BLUET"; break;
			case POTTED_RED_TULIP: ger = "Eingetopfte rote Tulpe"; eng = "POTTED_RED_TULIP"; break;
			case POTTED_ORANGE_TULIP: ger = "Eingetopfte orange Tulpe"; eng = "POTTED_ORANGE_TULIP"; break;
			case POTTED_WHITE_TULIP: ger = "Eingetopfte weiße Tulpe"; eng = "POTTED_WHITE_TULIP"; break;
			case POTTED_PINK_TULIP: ger = "Eingetopfte rosa Tulpe"; eng = "POTTED_PINK_TULIP"; break;
			case POTTED_OXEYE_DAISY: ger = "Eingetopfte Margerite"; eng = "POTTED_OXEYE_DAISY"; break;
			case POTTED_CORNFLOWER: ger = "Eingetopfte Kornblume"; eng = "POTTED_CORNFLOWER"; break;
			case POTTED_LILY_OF_THE_VALLEY: ger = "Eingetopftes Maiglöckchen"; eng = "POTTED_LILY_OF_THE_VALLEY"; break;
			case POTTED_WITHER_ROSE: ger = "Eingetopfte Witherrose"; eng = "POTTED_WITHER_ROSE"; break;
			case POTTED_RED_MUSHROOM: ger = "Eingetopfter roter Pilz"; eng = "POTTED_RED_MUSHROOM"; break;
			case POTTED_BROWN_MUSHROOM: ger = "Eingetopfter brauner Pilz"; eng = "POTTED_BROWN_MUSHROOM"; break;
			case POTTED_DEAD_BUSH: ger = "Eingetopfter toter Busch"; eng = "POTTED_DEAD_BUSH"; break;
			case POTTED_CACTUS: ger = "Eingetopfter Kaktus"; eng = "POTTED_CACTUS"; break;
			case CARROTS: ger = "Karottem"; eng = "CARROTS"; break;
			case POTATOES: ger = "Kartoffeln"; eng = "POTATOES"; break;
			case SKELETON_WALL_SKULL: ger = "Skelettkopf Wand"; eng = "SKELETON_WALL_SKULL"; break;
			case WITHER_SKELETON_WALL_SKULL: ger = "Whiterskelettkopf Wand"; eng = "WITHER_SKELETON_WALL_SKULL"; break;
			case ZOMBIE_WALL_HEAD: ger = "Zombiekopf Wand"; eng = "ZOMBIE_WALL_HEAD"; break;
			case PLAYER_WALL_HEAD: ger = "Spielerkof Wand"; eng = "PLAYER_WALL_HEAD"; break;
			case CREEPER_WALL_HEAD: ger = "Creeperkopf Wand"; eng = "CREEPER_WALL_HEAD"; break;
			case DRAGON_WALL_HEAD: ger = "Drachenkopf Wand"; eng = "DRAGON_WALL_HEAD"; break;
			case WHITE_WALL_BANNER: ger = "Weißes Banner Wand"; eng = "WHITE_WALL_BANNER"; break;
			case ORANGE_WALL_BANNER: ger = "Oranges banner Wand"; eng = "ORANGE_WALL_BANNER"; break;
			case MAGENTA_WALL_BANNER: ger = "Magenta Banner Wand"; eng = "MAGENTA_WALL_BANNER"; break;
			case LIGHT_BLUE_WALL_BANNER: ger = "Hellblaues Banner Wand"; eng = "LIGHT_BLUE_WALL_BANNER"; break;
			case YELLOW_WALL_BANNER: ger = "Gelbes Banner Wand"; eng = "YELLOW_WALL_BANNER"; break;
			case LIME_WALL_BANNER: ger = "Hellgrünes banner Wand"; eng = "LIME_WALL_BANNER"; break;
			case PINK_WALL_BANNER: ger = "Rosa Banner Wand"; eng = "PINK_WALL_BANNER"; break;
			case GRAY_WALL_BANNER: ger = "Graues banner Wand"; eng = "GRAY_WALL_BANNER"; break;
			case LIGHT_GRAY_WALL_BANNER: ger = "Hellgraues Banner Wand"; eng = "LIGHT_GRAY_WALL_BANNER"; break;
			case CYAN_WALL_BANNER: ger = "Türkises banner Wand"; eng = "CYAN_WALL_BANNER"; break;
			case PURPLE_WALL_BANNER: ger = "Violettes Banner Wand"; eng = "PURPLE_WALL_BANNER"; break;
			case BLUE_WALL_BANNER: ger = "Blaues Banner Wand"; eng = "BLUE_WALL_BANNER"; break;
			case BROWN_WALL_BANNER: ger = "Braunes Banner Wand"; eng = "BROWN_WALL_BANNER"; break;
			case GREEN_WALL_BANNER: ger = "Grünes banner Wand"; eng = "GREEN_WALL_BANNER"; break;
			case RED_WALL_BANNER: ger = "Rotes banner Wand"; eng = "RED_WALL_BANNER"; break;
			case BLACK_WALL_BANNER: ger = "Schwarzes Banner Wand"; eng = "BLACK_WALL_BANNER"; break;
			case BEETROOTS: ger = "Rote Beete Samen"; eng = "BEETROOTS"; break;
			case END_GATEWAY: ger = "End Gateway"; eng = "END_GATEWAY"; break;
			case FROSTED_ICE: ger = "Gefrorenes Eis"; eng = "FROSTED_ICE"; break;
			case KELP_PLANT: ger = "Seetang Pflanze"; eng = "KELP_PLANT"; break;
			case DEAD_TUBE_CORAL_WALL_FAN: ger = "Totes Orgelkorallenfächer Wand"; eng = "DEAD_TUBE_CORAL_WALL_FAN"; break;
			case DEAD_BRAIN_CORAL_WALL_FAN: ger = "Totes Hirnkorallenfächer Wand"; eng = "DEAD_BRAIN_CORAL_WALL_FAN"; break;
			case DEAD_BUBBLE_CORAL_WALL_FAN: ger = "Totes Blasenkorallenfächer Wand"; eng = "DEAD_BUBBLE_CORAL_WALL_FAN"; break;
			case DEAD_FIRE_CORAL_WALL_FAN: ger = "Totes Feuerkorallenfächer Wand"; eng = "DEAD_FIRE_CORAL_WALL_FAN"; break;
			case DEAD_HORN_CORAL_WALL_FAN: ger = "Totes Geweihkorallenfächer Wand"; eng = "DEAD_HORN_CORAL_WALL_FAN"; break;
			case TUBE_CORAL_WALL_FAN: ger = "Tote Orgelkorallen Wand"; eng = "TUBE_CORAL_WALL_FAN"; break;
			case BRAIN_CORAL_WALL_FAN: ger = "Tote Hirnkoralle Wand"; eng = "BRAIN_CORAL_WALL_FAN"; break;
			case BUBBLE_CORAL_WALL_FAN: ger = "Tote Blasenkoralle Wand"; eng = "BUBBLE_CORAL_WALL_FAN"; break;
			case FIRE_CORAL_WALL_FAN: ger = "Tote Feuerkoralle Wand"; eng = "FIRE_CORAL_WALL_FAN"; break;
			case HORN_CORAL_WALL_FAN: ger = "Tote Geweihkoralle Wand"; eng = "HORN_CORAL_WALL_FAN"; break;
			case BAMBOO_SAPLING: ger = "Bambussetzling"; eng = "BAMBOO_SAPLING"; break;
			case POTTED_BAMBOO: ger = "Eingetopfter Bambussetzling"; eng = "POTTED_BAMBOO"; break;
			case VOID_AIR: ger = "Leerenluft"; eng = "VOID_AIR"; break;
			case CAVE_AIR: ger = "Höhlenluft"; eng = "CAVE_AIR"; break;
			case BUBBLE_COLUMN: ger = "Blasensäule"; eng = "BUBBLE_COLUMN"; break;
			case SWEET_BERRY_BUSH: ger = "Süßbeerenbusch"; eng = "SWEET_BERRY_BUSH"; break;
			case WEEPING_VINES_PLANT: ger = "Gepflanzte Trauerranken"; eng = "WEEPING_VINES_PLANT"; break;
			case TWISTING_VINES_PLANT: ger = "Gepflanzte Trauerranken"; eng = "TWISTING_VINES_PLANT"; break;
			case CRIMSON_WALL_SIGN: ger = "Karmesinschild Wand"; eng = "CRIMSON_WALL_SIGN"; break;
			case WARPED_WALL_SIGN: ger = "Wirrschild Wand"; eng = "WARPED_WALL_SIGN"; break;
			case POTTED_CRIMSON_FUNGUS: ger = "Eingetopfter Karmesinsetzling"; eng = "POTTED_CRIMSON_FUNGUS"; break;
			case POTTED_WARPED_FUNGUS: ger = "Eingetopfter Wirrsetzling"; eng = "POTTED_WARPED_FUNGUS"; break;
			case POTTED_CRIMSON_ROOTS: ger = "Eingetopfte Karmesinwurzeln"; eng = "POTTED_CRIMSON_ROOTS"; break;
			case POTTED_WARPED_ROOTS: ger = "Eingetopfte Wirrwurzeln"; eng = "POTTED_WARPED_ROOTS"; break;
			case CANDLE_CAKE: ger = "Kuchenkerze"; eng = "CANDLE_CAKE"; break;
			case WHITE_CANDLE_CAKE: ger = "Weiße Kuchenkerze"; eng = "WHITE_CANDLE_CAKE"; break;
			case ORANGE_CANDLE_CAKE: ger = "Orange Kuchenkerze"; eng = "ORANGE_CANDLE_CAKE"; break;
			case MAGENTA_CANDLE_CAKE: ger = "Magenta Kuchenkerze"; eng = "MAGENTA_CANDLE_CAKE"; break;
			case LIGHT_BLUE_CANDLE_CAKE: ger = "Hellbalue Kuchenkerze"; eng = "LIGHT_BLUE_CANDLE_CAKE"; break;
			case YELLOW_CANDLE_CAKE: ger = "Gelbe Kuchenkerze"; eng = "YELLOW_CANDLE_CAKE"; break;
			case LIME_CANDLE_CAKE: ger = "Hellgrüne Kuchenkerze"; eng = "LIME_CANDLE_CAKE"; break;
			case PINK_CANDLE_CAKE: ger = "Rosa Kuchenkerze"; eng = "PINK_CANDLE_CAKE"; break;
			case GRAY_CANDLE_CAKE: ger = "Graue Kuchenkerze"; eng = "GRAY_CANDLE_CAKE"; break;
			case LIGHT_GRAY_CANDLE_CAKE: ger = "Hellgraue Kuchenkerze"; eng = "LIGHT_GRAY_CANDLE_CAKE"; break;
			case CYAN_CANDLE_CAKE: ger = "Türkise Kuchenkerze"; eng = "CYAN_CANDLE_CAKE"; break;
			case PURPLE_CANDLE_CAKE: ger = "Violette Kuchenkerze"; eng = "PURPLE_CANDLE_CAKE"; break;
			case BLUE_CANDLE_CAKE: ger = "Blaue Kuchenkerze"; eng = "BLUE_CANDLE_CAKE"; break;
			case BROWN_CANDLE_CAKE: ger = "Braune Kuchenkerze"; eng = "BROWN_CANDLE_CAKE"; break;
			case GREEN_CANDLE_CAKE: ger = "Grüne Kuchenkerze"; eng = "GREEN_CANDLE_CAKE"; break;
			case RED_CANDLE_CAKE: ger = "Rote Kuchenkerze"; eng = "RED_CANDLE_CAKE"; break;
			case BLACK_CANDLE_CAKE: ger = "Schwarze Kuchenkerze"; eng = "BLACK_CANDLE_CAKE"; break;
			case POWDER_SNOW: ger = "Pulverschnee"; eng = "POWDER_SNOW"; break;
			case CAVE_VINES: ger = "Höhlenranken"; eng = "CAVE_VINES"; break;
			case CAVE_VINES_PLANT: ger = "Höhlenranken gepflanzt"; eng = "CAVE_VINES_PLANT"; break;
			case BIG_DRIPLEAF_STEM: ger = "Großes Tropfsteinblatt-Stamm"; eng = "BIG_DRIPLEAF_STEM"; break;
			case POTTED_AZALEA_BUSH: ger = "Eingetopftes Azalenlaub"; eng = "POTTED_AZALEA_BUSH"; break;
			case POTTED_FLOWERING_AZALEA_BUSH: ger = "Eingetopftes blühendes Azalenlaub"; eng = "POTTED_FLOWERING_AZALEA_BUSH"; break;
			}
			matlanguageKeys.put(m.toString(),
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger,
							eng}));
		}
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
		admin.put(path+".Lore."+SettingsLevel.BASE.toString(),
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
						ClickFunctionType.ADMINISTRATION_ITEM_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ITEM_CLEAR.toString()}));
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
						ClickFunctionType.ADMINISTRATION_TOGGLE_SELL.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_TOGGLE_SELL.toString()}));
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
		admin.put(path+".Material."+SettingsLevel.NOLEVEL.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.OAK_WOOD.toString()}));
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
						"&cRechtsklick &berhöht den Lagerraum um 8 Items.",
						"&cQ &berhöht den Lagerraum um 16 Items.",
						"&cShift Linksklick &berhöht den Lagerraum um 32 Items.",
						"&cShift Rechtsklick &berhögt den Lagerraum um 64 Items",
						"&cCtrl Q &berhögt den Lagerraum um 576 Items",
						"&c1 &berhögt den Lagerraum um 1728 Items",
						"&c2 &berhögt den Lagerraum um 3456 Items",
						"&c3 &berhögt den Lagerraum um 6912 Items",
						"&bAtm.: &f%itemstoragetotal%",
						"&cLeftclick &bincreases the storage space by 1 item.",
						"&cRightclick &bincreases the storage space by 8 items.",
						"&cQ &bincreases the storage space by 16 item.",
						"&cShift-Leftclick  &bincreases the storage space by 32 items.",
						"&cShift-Rightclick &bincreases the storage space by 64 items.",
						"&cCtrl Q &bincreases the storage space by 576 items.",
						"&c1 &bincreases the storage space by 1728 items.",
						"&c2 &bincreases the storage space by 3456 items.",
						"&c3&bincreases the storage space by 6912 items.",}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_1.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_8.toString()}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_16.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_32.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_64.toString()}));
		admin.put(path+".ClickFunction."+ClickType.CTRL_DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_576.toString()}));
		admin.put(path+".ClickFunction."+ClickType.NUMPAD_1.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_1728.toString()}));
		admin.put(path+".ClickFunction."+ClickType.NUMPAD_2.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_3456.toString()}));
		admin.put(path+".ClickFunction."+ClickType.NUMPAD_3.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDSTORAGE_6912.toString()}));
		path = "1"; //SetShop Name
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.BARREL.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dUmbenennung des Shops",
						"&dRename of the shop"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%signshopname%",
						"&cLinks-/Rechtsklick &bzum öffnen des Tastatur Gui.",
						"&bAtm.: &f%signshopname%",
						"&cLeft/right click &to open the Keyboard Gui."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETSIGNSHOPNAME_OPENKEYBOARD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETSIGNSHOPNAME_OPENKEYBOARD.toString()}));
		path = "10"; //SetASH
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
		path = "19"; //Setglowing
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GLOW_INK_SAC.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dLeuchtfunktion des Schild",
						"&dGlow function of the sign"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%glow%",
						"&cLinksklick &banstellen der Leuchtfunktion des Schildes.",
						"&cRechtsklick &babstellen der Leuchtfunktion des Schildes.",
						"&bAtm.: &f%glow%",
						"&cLeft click &bturn on the luminous function of the sign.",
						"&cRight click &bturn off the luminous function of the sign."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETGLOWING.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETUNGLOWING.toString()}));
		path = "2"; //SetListType
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.LECTERN.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dSetzen des Listfunktion des Shops",
						"&dSetting the list function of the store"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%listtype%",
						"&bStellt den Zugang zum Shop auf eine spezielle Liste von Spieler ein.",
						"&bSollten Spieler nicht auf der jeweiligen Liste sein, haben Sie",
						"&bkein Zugriff auf den Shop!",
						"&cLinksklick, &bdass alle Spieler Zugriff auf dem Shop haben.",
						"&cRechtsklick &bnur für Mitglieder.",
						"&cQ &bfür die Blacklist.",
						"&cCtrl Q &bfür die Whitelist.",
						"&cF &bnur für benutzerdefinierte Spieler.",
						"&bAtm.: &f%listtype%",
						"&bSets access to the store to a special list of players.",
						"&bIf players are not on the respective list,",
						"&byou will not have access to the store!",
						"&cLeft click, &bthat all players have access to the store.",
						"&cRight click &bonly for member.",
						"&cQ &bfor the Blacklist.",
						"&cCtrl Q &bfor the Whitelist.",
						"&cF &bfor custom players only."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETLISTEDTYPE_ALL.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETLISTEDTYPE_MEMBER.toString()}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETLISTEDTYPE_BLACKLIST.toString()}));
		admin.put(path+".ClickFunction."+ClickType.CTRL_DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETLISTEDTYPE_WHITELIST.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SWAP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETLISTEDTYPE_CUSTOM.toString()}));
		path = "11"; //AddPlayerToList
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.SKELETON_SKULL.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dHinzufügen oder entfernen von Spieler auf den Listen",
						"&dAdd or remove players from the lists"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bÖffnet einer der Keyboard Guis,",
						"&bum Spieler zu den jeweiligen Listen hinzuzufügen",
						"&boder zu entfernen. Hier können Mitglieder, Black- und Whitelist",
						"&bsowie eine benutzerdefinierte Liste bearbeitet werden.",
						"&cLinksklick &böffnet das Keyboard Gui für die Blacklist.",
						"&cRechtsklick &böffnet das Keyboard Gui für die Whitelist.",
						"&cShift-Linksklick &böffnet das Keyboard Gui für die Mitglieder.",
						"&cShift-Rechtsklick &böffnet das Keyboard Gui für die benutzerdefinierte Liste.",
						"&bOpens one of the Keyboard Guis",
						"&b to add or remove players from the respective lists.",
						"&bMembers, blacklist, whitelist",
						"&band a custom list can be edited here.",
						"&cLeftclick &bopens the keyboard gui for the blacklist.",
						"&cRightclick &bopens the keyboard gui for the whitelist.",
						"&cShift-leftclick &bopens the keyboard Gui for the members.",
						"&cShift-rightclick &bopens the Keyboard Gui for the user-defined list."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_BLACKLIST.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_WHITELIST.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_MEMBER.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_CUSTOM.toString()}));
		path = "20"; //ToggleItemHover
		admin.put(path+".SettingLevel", //FIXME Hier weitermachen
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.END_CRYSTAL.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dToggel ItemHover",
						"&dToggle ItemHover"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%listtype%",
						"&bÖffnet einer der Keyboard Guis,",
						"&bum Spieler zu den jeweiligen Listen hinzuzufügen",
						"&boder zu entfernen. Hier können Mitglieder, Black- und Whitelist",
						"&bsowie eine benutzerdefinierte Liste bearbeitet werden.",
						"&cLinksklick &böffnet das Keyboard Gui für die Blacklist.",
						"&cRechtsklick &böffnet das Keyboard Gui für die Whitelist.",
						"&cShift-Linksklick &böffnet das Keyboard Gui für die Mitglieder.",
						"&cShift-Rechtsklick &böffnet das Keyboard Gui für die benutzerdefinierte Liste.",
						"&bAtm.: &f%listtype%",
						"&bOpens one of the Keyboard Guis",
						"&b to add or remove players from the respective lists.",
						"&bMembers, blacklist, whitelist",
						"&band a custom list can be edited here.",
						"&cLeftclick &bopens the keyboard gui for the blacklist.",
						"&cRightclick &bopens the keyboard gui for the whitelist.",
						"&cShift-leftclick &bopens the keyboard Gui for the members.",
						"&cShift-rightclick &bopens the Keyboard Gui for the user-defined list."}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_BLACKLIST.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_WHITELIST.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_MEMBER.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_CUSTOM.toString()}));
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
		path = "40"; //Unlimited Toggle Buy
		admin.put(path+".Permission",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						".unlimited.buy"}));
		admin.put(path+".CanBuy",
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
		path = "49"; //Unlimited Toggle Sell
		admin.put(path+".Permission",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						".unlimited.sell"}));
		admin.put(path+".CanSell",
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
		admin.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
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
						"&cQ &bfür das Zurücksetzen.",
						"&cLinks/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%possiblebuy%",
						"&bRemaining sales, are the number of sales",
						"&bthat can still be made.",
						"&bIf the number is 0, no more items can be sold.",
						"&cQ &bfor resetting.",
						"&cLeft/right click &to open the Numpad Gui."}));
		admin.put(path+".ClickFunction."+ClickType.DROP.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_CLEAR.toString()}));
		admin.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_OPEN_NUMPAD.toString()}));
		admin.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_SETPOSSIBLE_BUY_OPEN_NUMPAD.toString()}));
		path = "23"; //Possible Sell
		admin.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
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
						"&cQ &bfür das Zurücksetzen.",
						"&cLinks/Rechtsklick &bzum öffnen des Numpad Gui.",
						"&bAtm.: &f%possiblesell%",
						"&bRemaining purchase, are the number of",
						"&bpurchase that can still be made.",
						"&bIf the number is 0, no more items can be purchase.",
						"&cQ &bfor resetting.",
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
		path = "8"; //Delete All but with no items in storage
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
						"&cShift Links/Rechtsklick &blöscht den Shop.",
						"&bFunktioniert nur, wenn alle Items",
						"&baus dem Lagerraum entfernt worden sind.",
						"&cShift Left/rightclick &bdelete the shop.",
						"&bWorks only when all items have been",
						"&bremoved from the storage room."}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_DELETE_WITHOUT_ITEMS_IN_STORAGE.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_DELETE_WITHOUT_ITEMS_IN_STORAGE.toString()}));
		path = "17"; //Delete All
		admin.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.EXPERT.toString()}));
		admin.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.TNT.toString()}));
		admin.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dLöschung des Shops",
						"&dDeleting of the shop"}));
		admin.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&cShift Links/Rechtsklick &blöscht den Shop.",
						"&cAchtung!",
						"&bDurch das Klicken wird unverzüglich der gesamte Shop",
						"&bmit allen noch verbliebenen Items gelöscht!",
						"&cAchtung!",
						"&cShift Left/rightclick &bopen the shoplog.",
						"&cAttention!",
						"&bClicking immediately deletes the entire",
						"&bstore with all remaining items!",
						"&cAttention"}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_DELETE_ALL.toString()}));
		admin.put(path+".ClickFunction."+ClickType.SHIFT_RIGHT.toString(),
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
		path = "43"; //Discount Start/End
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
		admin.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
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
						"&cQ &bfür das Zurücksetzen.",
						"&cLinks/Rechtsklick &böffnet das Numpad Gui.",
						
						"&bAtm.: &f%discountpossiblebuy%",
						"&bRemaining sales, are the number of discountsales",
						"&bthat can still be made.",
						"&bIf the number is 0, no more items can be sold as",
						"&blong as the discount promotion is running.",
						"&cQ &bfor resetting.",
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
		admin.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
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
						"&cQ &bfür das Zurücksetzen.",
						"&cLinks/Rechtsklick &böffnet das Numpad Gui.",
						"&bAtm.: &f%discountpossiblesell%",
						"&bRemaining purchase, are the number of purchase",
						"&bthat can still be made.",
						"&bIf the number is 0, no more items can be",
						"&bpurchased as long as the discount campaign is running.",
						"&cQ &bfor resetting.",
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
	
	public void initGuiNumpad() //INFO:GuiNumpad
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_3.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_3.toString()}));
		path = "40"; //0
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
						"&f0 &7Numpad"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_0.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_0.toString()}));
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
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
						"&bZz.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an.",
						"&bAtm.: &f%numtext%",
						"&bFügt am Ende ein Zeichen an."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_COLON.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_COLON.toString()}));
		path = "48"; //RemoveOnce
		numpad.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		numpad.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.REDSTONE.toString()}));
		numpad.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eUm ein Zeichen zurücksetzen",
						"&eReset by one character"}));
		numpad.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&bZz.: &f%numtext%",
						"&bSetzt die Eingabe um ein Zeichen zurück.",
						"&bAtm.: &f%numtext%",
						"&bResets the input by one character."}));
		numpad.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_REMOVEONCE.toString()}));
		numpad.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.ADMINISTRATION_NUMPAD_REMOVEONCE.toString()}));
		path = "50"; //Clear
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
						"&bZz.: &f%numtext%",
						"&bSetzt die Eingabe zurück.",
						"&bAtm.: &f%numtext%",
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
				"&bZz.: &f%numtext%", "&bÜbernimmt die Eingabe und kehre zum Administration Gui zurück.",
				"&bAtm.: &f%numtext%", "&bAccept the input and return to the Administration Gui."});
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
		shop.put(path+".InfoLore",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
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
		path = "36"; //Buy1
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.SMALL_AMETHYST_BUD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x1 Item",
						"&eBuy x1 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy1%",
						"&bKaufe x1 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy1%",
						"&bBuy x1 items",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_1.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_1.toString()}));
		path = "37"; //Buy8
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.MEDIUM_AMETHYST_BUD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x8 Item",
						"&eBuy x8 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy8%",
						"&bKaufe x8 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy8%",
						"&bBuy x8 items",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_8.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_8.toString()}));
		path = "38"; //Buy16
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.LARGE_AMETHYST_BUD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x16 Item",
						"&eBuy x16 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy16%",
						"&bKaufe x16 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy16%",
						"&bBuy x16 items",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_16.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_16.toString()}));
		path = "39"; //Buy32
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.AMETHYST_CLUSTER.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x32 Item",
						"&eBuy x32 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy32%",
						"&bKaufe x32 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy32%",
						"&bBuy x32 items",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_32.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_32.toString()}));
		path = "45"; //Buy64
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.RAW_GOLD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x64 Item",
						"&eBuy x64 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy64%",
						"&bKaufe x64 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy64%",
						"&bBuy x64 items",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_64.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_64.toString()}));
		path = "46"; //Buy576
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_NUGGET.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x576 Item",
						"&eBuy x576 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy576%",
						"&bKaufe x576 Items (9x Stack)",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy576%",
						"&bBuy x576 items (9x Stack)",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_576.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_576.toString()}));
		path = "47"; //Buy1728
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_INGOT.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x1728 Item",
						"&eBuy x1728 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy1728%",
						"&bKaufe x1728 Items (27x Stack)",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy1728%",
						"&bBuy x1728 items (27x Stack)",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_576.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_576.toString()}));
		path = "48"; //Buy2304
		shop.put(path+".CanBuy",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.GOLD_BLOCK.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eKaufe x2304 Item",
						"&eBuy x2304 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%buy2304%",
						"&bKaufe x2304 Items (36x Stack)",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop noch vorrätig hat.",
						"&fPrice: &r%buy2304%",
						"&bBuy x2304 items (36x Stack)",
						"&bor as many are still in inventory",
						"&bor the shop still in stock."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_2304.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_BUY_2304.toString()}));
		path = "44"; //Sell1
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.SMALL_AMETHYST_BUD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x1 Item",
						"&eSell x1 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell1%",
						"&bVerkaufe x1 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell1%",
						"&bSell x1 items",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_1.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_1.toString()}));
		path = "43"; //Sell8
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.MEDIUM_AMETHYST_BUD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x8 Item",
						"&eSell x8 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell8%",
						"&bVerkaufe x8 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell8%",
						"&bSell x8 items",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_8.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_8.toString()}));
		path = "42"; //Sell16
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.LARGE_AMETHYST_BUD.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x16 Item",
						"&eSell x16 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell16%",
						"&bVerkaufe x16 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell16%",
						"&bSell x16 items",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_16.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_16.toString()}));
		path = "41"; //Sell32
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.AMETHYST_CLUSTER.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x32 Item",
						"&eSell x32 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell32%",
						"&bVerkaufe x32 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell32%",
						"&bSell x32 items",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_32.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_32.toString()}));
		path = "53"; //Sell64
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.RAW_IRON.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x64 Item",
						"&eSell x64 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell64%",
						"&bVerkaufe x64 Items",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell64%",
						"&bSell x64 items",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
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
						Material.IRON_NUGGET.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x576 Item",
						"&eSell x576 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell576%",
						"&bVerkaufe x576 Items (9x Stack)",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell576%",
						"&bSell x576 items (9x Stack)",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_576.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_576.toString()}));
		path = "51"; //Sell1728
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.IRON_INGOT.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x1728 Item",
						"&eSell x1728 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell1728%",
						"&bVerkaufe x1728 Items (27x Stack)",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell1728%",
						"&bSell x1728 items (27x Stack)",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
		shop.put(path+".ClickFunction."+ClickType.LEFT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_1728.toString()}));
		shop.put(path+".ClickFunction."+ClickType.RIGHT.toString(),
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						ClickFunctionType.SHOP_SELL_1728.toString()}));
		path = "50"; //Sell2304
		shop.put(path+".CanSell",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						true}));
		shop.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		shop.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.IRON_BLOCK.toString()}));
		shop.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&eVerkaufe x2304 Item",
						"&eSell x2304 Item"}));
		shop.put(path+".Lore",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&fPreis: &r%sell2304%",
						"&bVerkaufe x2304 Items (36x Stack)",
						"&boder soviele noch im Inventar sind",
						"&boder der Shop annimmt.",
						"&fPrice: &r%sell2304%",
						"&bSell x2304 items (36x Stack)",
						"&bor as many are still in inventory",
						"&bor the store accepts."}));
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
	
	public void initGuiItemInput() //INFO:GuiItemInput
	{
		LinkedHashMap<String, Language> iinput = new LinkedHashMap<>();
		String path = "22"; //Information
		iinput.put(path+".SettingLevel",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						SettingsLevel.NOLEVEL.toString()}));
		iinput.put(path+".Material",
				new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
						Material.PAPER.toString()}));
		iinput.put(path+".Displayname",
				new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
						"&dKlicke auf ein Item zum Hinzufügen des Shops.",
						"&dClick on an item to add the store."}));
		guiKeys.put(GuiType.ITEM_INPUT, iinput);
	}
}