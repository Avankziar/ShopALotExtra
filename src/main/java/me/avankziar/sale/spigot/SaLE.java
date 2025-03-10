package main.java.me.avankziar.sale.spigot;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.sale.spigot.assistance.BackgroundTask;
import main.java.me.avankziar.sale.spigot.assistance.Utility;
import main.java.me.avankziar.sale.spigot.cmd.SaLECommandExecutor;
import main.java.me.avankziar.sale.spigot.cmd.TabCompletion;
import main.java.me.avankziar.sale.spigot.cmd.client.ARGSPDailyLog;
import main.java.me.avankziar.sale.spigot.cmd.client.ARGSPLog;
import main.java.me.avankziar.sale.spigot.cmd.sale.ARGDebug;
import main.java.me.avankziar.sale.spigot.cmd.sale.ARGDelete;
import main.java.me.avankziar.sale.spigot.cmd.sale.ARGShop;
import main.java.me.avankziar.sale.spigot.cmd.sale.ARGShopping;
import main.java.me.avankziar.sale.spigot.cmd.sale.ARGSubscribed;
import main.java.me.avankziar.sale.spigot.cmd.shop.ARG_BreakToggle;
import main.java.me.avankziar.sale.spigot.cmd.shop.ARG_SDailyLog;
import main.java.me.avankziar.sale.spigot.cmd.shop.ARG_SLog;
import main.java.me.avankziar.sale.spigot.cmd.shop.ARG_SearchBuy;
import main.java.me.avankziar.sale.spigot.cmd.shop.ARG_SearchSell;
import main.java.me.avankziar.sale.spigot.cmd.shop.ARG_Toggle;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.ArgumentModule;
import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandConstructor;
import main.java.me.avankziar.sale.spigot.cmdtree.CommandExecuteType;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.database.MysqlSetup;
import main.java.me.avankziar.sale.spigot.database.YamlHandler;
import main.java.me.avankziar.sale.spigot.database.YamlManager;
import main.java.me.avankziar.sale.spigot.gui.listener.BottomListener;
import main.java.me.avankziar.sale.spigot.gui.listener.GuiPreListener;
import main.java.me.avankziar.sale.spigot.gui.listener.UpperListener;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler;
import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.handler.MaterialHandler;
import main.java.me.avankziar.sale.spigot.hook.WorldGuardHook;
import main.java.me.avankziar.sale.spigot.ifh.SignShopProvider;
import main.java.me.avankziar.sale.spigot.listener.BlockBreakListener;
import main.java.me.avankziar.sale.spigot.listener.PlayerArmorStandManipulateListener;
import main.java.me.avankziar.sale.spigot.listener.PlayerInteractListener;
import main.java.me.avankziar.sale.spigot.listener.PlayerJoinListener;
import main.java.me.avankziar.sale.spigot.listener.ShopPostTransactionListener;
import main.java.me.avankziar.sale.spigot.listener.SignChangeListener;
import main.java.me.avankziar.sale.spigot.metrics.Metrics;
import main.java.me.avankziar.sale.spigot.modifiervalueentry.Bypass;
import main.java.me.avankziar.sale.spigot.objects.ItemHologram;
import me.avankziar.ifh.general.modifier.ModificationType;
import me.avankziar.ifh.general.modifier.Modifier;
import me.avankziar.ifh.general.statistic.Statistic;
import me.avankziar.ifh.general.valueentry.ValueEntry;
import me.avankziar.ifh.spigot.administration.Administration;
import me.avankziar.ifh.spigot.comparison.ItemStackComparison;
import me.avankziar.ifh.spigot.economy.Economy;
import me.avankziar.ifh.spigot.interfaces.EnumTranslation;
import me.avankziar.ifh.spigot.teleport.Teleport;
import me.avankziar.ifh.spigot.tobungee.chatlike.BaseComponentToBungee;
import me.avankziar.ifh.spigot.tobungee.chatlike.MessageToBungee;
import me.avankziar.ifh.spigot.tovelocity.chatlike.MessageToVelocity;

public class SaLE extends JavaPlugin
{
	public static Logger logger;
	private static SaLE plugin;
	public String pluginName = "SaLE";
	private YamlHandler yamlHandler;
	private YamlManager yamlManager;
	private MysqlSetup mysqlSetup;
	private MysqlHandler mysqlHandler;
	private Utility utility;
	private BackgroundTask backgroundTask;
	
	private ArrayList<BaseConstructor> helpList = new ArrayList<>();
	private ArrayList<CommandConstructor> commandTree = new ArrayList<>();
	private LinkedHashMap<String, ArgumentModule> argumentMap = new LinkedHashMap<>();
	private ArrayList<String> players = new ArrayList<>();
	
	public static String infoCommand = "/";
	
	private me.avankziar.ifh.spigot.shop.SignShop signShopProvider;
	
	private Administration administrationConsumer;
	private EnumTranslation enumTranslationConsumer;
	private ItemStackComparison itemStackComparisonConsumer;
	private Economy ecoConsumer;
	private ValueEntry valueEntryConsumer;
	private Modifier modifierConsumer;
	private MessageToBungee mtbConsumer;
	private MessageToVelocity mtvConsumer;
	private BaseComponentToBungee bctbConsumer;
	private Teleport teleportConsumer;
	private static boolean worldGuard = false;
	private Statistic statisticConsumer;
	
	private net.milkbowl.vault.economy.Economy vEco;
	
	public void onLoad() 
	{
		setupWordEditGuard();
	}
	
	public void onEnable()
	{
		plugin = this;
		logger = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=SaLE
		logger.info(" ███████╗ █████╗ ██╗     ███████╗ | API-Version: "+plugin.getDescription().getAPIVersion());
		logger.info(" ██╔════╝██╔══██╗██║     ██╔════╝ | Author: "+plugin.getDescription().getAuthors().toString());
		logger.info(" ███████╗███████║██║     █████╗   | Plugin Website: "+plugin.getDescription().getWebsite());
		logger.info(" ╚════██║██╔══██║██║     ██╔══╝   | Depend Plugins: "+plugin.getDescription().getDepend().toString());
		logger.info(" ███████║██║  ██║███████╗███████╗ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepend().toString());
		logger.info(" ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝ | LoadBefore: "+plugin.getDescription().getLoadBefore().toString());
		
		setupIFHAdministration();
		
		yamlHandler = new YamlHandler(this);
		
		String path = plugin.getYamlHandler().getConfig().getString("IFHAdministrationPath");
		boolean adm = plugin.getAdministration() != null 
				&& plugin.getYamlHandler().getConfig().getBoolean("useIFHAdministration")
				&& plugin.getAdministration().isMysqlPathActive(path);
		if(adm || yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlHandler = new MysqlHandler(plugin);
			mysqlSetup = new MysqlSetup(plugin, adm, path);
		} else
		{
			logger.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
			return;
		}
		
		setupIFHItemStackComparison();
		
		utility = new Utility(plugin);
		backgroundTask = new BackgroundTask(this);
		
		setupBypassPerm();
		setupCommandTree();
		setupListeners();
		setupIFHProvider();
		setupIFHConsumer();
		MaterialHandler.init(plugin);
		setupBstats();
	}
	
	public void onDisable()
	{
		logger.info(pluginName + " despawn all Holograms");
		for(Entry<String, ItemHologram> e : ItemHologramHandler.taskMap.entrySet())
		{
			e.getValue().despawn();
		}
		logger.info(pluginName + " do all open Shoplogs!");
		if(backgroundTask != null)
		{
			backgroundTask.doShopLog();
		}
		logger.info(pluginName + " done all open Shoplogs!");
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		logger.info(pluginName + " is disabled!");
	}

	public static SaLE getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}

	public void setYamlManager(YamlManager yamlManager)
	{
		this.yamlManager = yamlManager;
	}
	
	public MysqlSetup getMysqlSetup() 
	{
		return mysqlSetup;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public BackgroundTask getBackgroundTask()
	{
		return backgroundTask;
	}
	
	public String getServername()
	{
		return getPlugin().getAdministration() != null ? getPlugin().getAdministration().getSpigotServerName() 
				: getPlugin().getYamlHandler().getConfig().getString("ServerName");
	}
	
	private void setupCommandTree()
	{		
		infoCommand += plugin.getYamlHandler().getCommands().getString("sale.Name");
		
		TabCompletion tab = new TabCompletion(plugin);
		
		ArgumentConstructor debug = new ArgumentConstructor(CommandExecuteType.SALE_DEBUG, "sale_debug", 0, 1, 1, false, null);
		new ARGDebug(plugin, debug);
		
		ArgumentConstructor delete = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_DELETE, "sale_shop_delete", 1, 1, 99, false, null);
		new ARGDelete(plugin, delete);
		
		ArgumentConstructor breaktoggle = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_BREAKTOGGLE, "sale_shop_breaktoggle",
				1, 1, 1, false, null);
		new ARG_BreakToggle(plugin, breaktoggle);
		ArgumentConstructor toggle = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_TOGGLE, "sale_shop_toggle", 1, 1, 1, false, null);
		new ARG_Toggle(plugin, toggle);
		ArgumentConstructor slog = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_LOG, "sale_shop_log", 1, 1, 5, false, null);
		new ARG_SLog(plugin, slog);
		ArgumentConstructor sdailylog = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_DAILYLOG, "sale_shop_dailylog", 1, 1, 4, false, null);
		new ARG_SDailyLog(plugin, sdailylog);
		ArgumentConstructor searchbuy = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_SEARCHBUY, "sale_shop_searchbuy", 1, 1, 999, false, null);
		new ARG_SearchBuy(plugin, searchbuy);
		ArgumentConstructor searchsell = new ArgumentConstructor(CommandExecuteType.SALE_SHOP_SEARCHBUY, "sale_shop_searchsell", 1, 1, 999, false, null);
		new ARG_SearchSell(plugin, searchsell);
		ArgumentConstructor shop = new ArgumentConstructor(CommandExecuteType.SALE_SHOP, "sale_shop", 0, 0, 0, false, null,
				breaktoggle, delete, toggle, slog, sdailylog, searchbuy, searchsell);
		new ARGShop(plugin, shop);
		
		
		ArgumentConstructor splog = new ArgumentConstructor(CommandExecuteType.SALE_CLIENT_LOG, "sale_client_log",
				1, 1, 4, false, null);
		new ARGSPLog(plugin, splog);
		ArgumentConstructor spdailylog = new ArgumentConstructor(CommandExecuteType.SALE_CLIENT_DAILYLOG, "sale_client_dailylog",
				1, 1, 3, false, null);
		new ARGSPDailyLog(plugin, spdailylog);
		ArgumentConstructor client = new ArgumentConstructor(CommandExecuteType.SALE_CLIENT, "sale_client", 0, 0, 0, false, null,
				splog, spdailylog);
		new ARGShopping(plugin, client);	
		
		ArrayList<String> page = new ArrayList<>();
		page.addAll(Arrays.asList("0"));
		ArrayList<String> subsType = new ArrayList<>();
		subsType.addAll(plugin.getYamlHandler().getConfig().getStringList("SignShop.Subscribe.TabList"));
		subsType.sort(Comparator.naturalOrder());
		LinkedHashMap<Integer, ArrayList<String>> subs = new LinkedHashMap<>();
		subs.put(1, page);
		subs.put(2, subsType);
		subs.put(3, subsType);
		subs.put(4, subsType);
		subs.put(5, subsType);
		subs.put(6, subsType);
		subs.put(7, subsType);
		subs.put(8, subsType);
		ArgumentConstructor subscribed = new ArgumentConstructor(CommandExecuteType.SALE_SUBSCRIBED, "sale_subscribed", 0, 0, 10, false, subs);
		new ARGSubscribed(plugin, subscribed);	
		
		CommandConstructor sale = new CommandConstructor(CommandExecuteType.SALE, "sale", false,
				debug, shop, client, subscribed);
		registerCommand(sale.getPath(), sale.getName());
		getCommand(sale.getName()).setExecutor(new SaLECommandExecutor(plugin, sale));
		getCommand(sale.getName()).setTabCompleter(tab);
		Collections.sort(helpList, new Comparator<BaseConstructor>()
		{
		    public int compare(BaseConstructor s1, BaseConstructor s2) 
		    {
		        return s1.getPath().compareToIgnoreCase(s2.getPath());
		    }
		});
	}
	
	private void setupBypassPerm()
	{
		String path = "Count.";
		for(Bypass.Counter bypass : new ArrayList<Bypass.Counter>(EnumSet.allOf(Bypass.Counter.class)))
		{
			Bypass.set(bypass, yamlHandler.getCommands().getString(path+bypass.toString()));
		}
		path = "Bypass.";
		for(Bypass.Permission bypass : new ArrayList<Bypass.Permission>(EnumSet.allOf(Bypass.Permission.class)))
		{
			Bypass.set(bypass, yamlHandler.getCommands().getString(path+bypass.toString()));
		}
	}
	
	public ArrayList<BaseConstructor> getCommandHelpList()
	{
		return helpList;
	}
	
	public void addingCommandHelps(BaseConstructor... objects)
	{
		for(BaseConstructor bc : objects)
		{
			helpList.add(bc);
		}
	}
	
	public ArrayList<CommandConstructor> getCommandTree()
	{
		return commandTree;
	}
	
	public CommandConstructor getCommandFromPath(String commandpath)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getPath().equalsIgnoreCase(commandpath))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public CommandConstructor getCommandFromCommandString(String command)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getName().equalsIgnoreCase(command))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public void registerCommand(String... aliases) 
	{
		PluginCommand command = getCommand(aliases[0], plugin);
	 
		command.setAliases(Arrays.asList(aliases));
		getCommandMap().register(plugin.getDescription().getName(), command);
	}
	 
	private static PluginCommand getCommand(String name, SaLE plugin) 
	{
		PluginCommand command = null;
	 
		try 
		{
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
	 
			command = c.newInstance(name, plugin);
		} catch (SecurityException e) 
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} catch (InstantiationException e) 
		{
			e.printStackTrace();
		} catch (InvocationTargetException e) 
		
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
	 
		return command;
	}
	 
	private static CommandMap getCommandMap() 
	{
		CommandMap commandMap = null;
	 
		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) 
			{
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);
	 
				commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
			}
		} catch (NoSuchFieldException e) 
		{
			e.printStackTrace();
		} catch (SecurityException e) 
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	 
		return commandMap;
	}
	
	public LinkedHashMap<String, ArgumentModule> getArgumentMap()
	{
		return argumentMap;
	}
	
	public ArrayList<String> getMysqlPlayers()
	{
		return players;
	}

	public void setMysqlPlayers(ArrayList<String> players)
	{
		this.players = players;
	}
	
	public void setupListeners()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SignChangeListener(plugin), plugin);
		pm.registerEvents(new PlayerJoinListener(plugin), plugin);
		pm.registerEvents(new PlayerInteractListener(plugin), plugin);
		pm.registerEvents(new BlockBreakListener(plugin), plugin);
		pm.registerEvents(new PlayerArmorStandManipulateListener(), plugin);
		pm.registerEvents(new GuiPreListener(plugin), plugin);
		pm.registerEvents(new BottomListener(plugin), plugin);
		pm.registerEvents(new UpperListener(plugin), plugin);
		pm.registerEvents(new ShopPostTransactionListener(), plugin);
	}
	
	public boolean reload() throws IOException
	{
		if(!yamlHandler.loadYamlHandler())
		{
			return false;
		}
		if(yamlHandler.getConfig().getBoolean("Mysql.Status", false))
		{
			if(!mysqlSetup.loadMysqlSetup())
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
	}
	
	public boolean existHook(String externPluginName)
	{
		if(plugin.getServer().getPluginManager().getPlugin(externPluginName) == null)
		{
			return false;
		}
		logger.info(pluginName+" hook with "+externPluginName);
		return true;
	}
	
	private void setupIFHAdministration()
	{ 
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
		RegisteredServiceProvider<me.avankziar.ifh.spigot.administration.Administration> rsp = 
                getServer().getServicesManager().getRegistration(Administration.class);
		if (rsp == null) 
		{
		   return;
		}
		administrationConsumer = rsp.getProvider();
		logger.info(pluginName + " detected InterfaceHub >>> Administration.class is consumed!");
	}
	
	public Administration getAdministration()
	{
		return administrationConsumer;
	}
	
	public void setupIFHProvider()
	{
		setupIFHShop();
	}
	
	public void setupIFHConsumer()
	{
		setupIFHValueEntry();
		setupIFHModifier();
		setupIFHEnumTranslation();
		setupIFHEconomy();
		setupIFHMessageToBungee();
		setupIFHMessageToVelocity();
		setupIFHBaseComponentToBungee();
		setupIFHTeleport();
		setupIFHStatistic();
	}
	
	public void setupIFHValueEntry()
	{
		if(!new ConfigHandler().isMechanicValueEntryEnabled())
		{
			return;
		}
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
				    	return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.general.valueentry.ValueEntry> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.general.valueentry.ValueEntry.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    valueEntryConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> ValueEntry.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}
				if(getValueEntry() != null)
				{
					for(BaseConstructor bc : getCommandHelpList())
					{
						if(!bc.isPutUpCmdPermToValueEntrySystem())
						{
							continue;
						}
						if(getValueEntry().isRegistered(bc.getValueEntryPath()))
						{
							continue;
						}
						getValueEntry().register(
								bc.getValueEntryPath(),
								bc.getValueEntryDisplayName(),
								bc.getValueEntryExplanation());
					}
					List<Bypass.Permission> list = new ArrayList<Bypass.Permission>(EnumSet.allOf(Bypass.Permission.class));
					for(Bypass.Permission ept : list)
					{
						if(getValueEntry().isRegistered(ept.getValueLable()))
						{
							continue;
						}
						List<String> lar = plugin.getYamlHandler().getMVELang().getStringList(ept.toString()+".Explanation");
						getValueEntry().register(
								ept.getValueLable(),
								plugin.getYamlHandler().getMVELang().getString(ept.toString()+".Displayname", ept.toString()),
								lar.toArray(new String[lar.size()]));
					}
				}
			}
        }.runTaskTimer(plugin, 0L, 20*2);
	}
	
	public ValueEntry getValueEntry()
	{
		return valueEntryConsumer;
	}
	
	private boolean setupIFHShop()
	{
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return false;
	    }
		signShopProvider = new SignShopProvider(plugin);
    	plugin.getServer().getServicesManager().register(
    			me.avankziar.ifh.spigot.shop.SignShop.class,
    	signShopProvider,
        this,
        ServicePriority.Normal);
    	logger.info(pluginName + " detected InterfaceHub >>> SignShop.class is provided!");
		return false;
	}
	
	private void setupIFHEnumTranslation() 
	{
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
				    	return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.spigot.interfaces.EnumTranslation> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.spigot.interfaces.EnumTranslation.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    enumTranslationConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> EnumTranslation.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 0L, 20*2);
	}
	
	public EnumTranslation getEnumTl()
	{
		return enumTranslationConsumer;
	}
	
	private void setupIFHItemStackComparison() 
	{
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
			logger.severe("ItemStackComparison Interface dependency cannot found!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
	    	return;
	    }
	    RegisteredServiceProvider<me.avankziar.ifh.spigot.comparison.ItemStackComparison> rsp = 
                         getServer().getServicesManager().getRegistration(
                        		 me.avankziar.ifh.spigot.comparison.ItemStackComparison.class);
	    if(rsp == null) 
	    {
	    	logger.severe("ItemStackComparison Interface dependency cannot found!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
	    	return;
	    }
	    itemStackComparisonConsumer = rsp.getProvider();
	    logger.info(pluginName + " detected InterfaceHub >>> ItemStackComparison.class is consumed!");
	}
	
	public ItemStackComparison getItemStackComparison()
	{
		return itemStackComparisonConsumer;
	}
	
	private void setupIFHEconomy()
    {
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")
				&& !plugin.getServer().getPluginManager().isPluginEnabled("Vault")) 
	    {
			logger.severe("Plugin InterfaceHub or Vault are missing!");
			logger.severe("Disable "+pluginName+"!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
	    	return;
	    }
		if(plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub"))
		{
			RegisteredServiceProvider<me.avankziar.ifh.spigot.economy.Economy> rsp = 
	                getServer().getServicesManager().getRegistration(Economy.class);
			if (rsp == null) 
			{
				RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp2 = getServer()
		        		.getServicesManager()
		        		.getRegistration(net.milkbowl.vault.economy.Economy.class);
		        if (rsp2 == null) 
		        {
		        	logger.severe("A economy plugin which supported InterfaceHub or Vault is missing!");
					logger.severe("Disable "+pluginName+"!");
					Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
		            return;
		        }
		        vEco = rsp2.getProvider();
		        logger.info(pluginName + " detected Vault >>> Economy.class is consumed!");
				return;
			}
			ecoConsumer = rsp.getProvider();
			logger.info(pluginName + " detected InterfaceHub >>> Economy.class is consumed!");
		} else
		{
			RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp = getServer()
	        		.getServicesManager()
	        		.getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (rsp == null) 
	        {
	        	logger.severe("A economy plugin which supported Vault is missing!");
				logger.severe("Disable "+pluginName+"!");
				Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
	            return;
	        }
	        vEco = rsp.getProvider();
	        logger.info(pluginName + " detected Vault >>> Economy.class is consumed!");
		}
        return;
    }
	
	public Economy getIFHEco()
	{
		return this.ecoConsumer;
	}
	
	public net.milkbowl.vault.economy.Economy getVaultEco()
	{
		return this.vEco;
	}
	
	private void setupIFHModifier() 
	{
		if(!new ConfigHandler().isMechanicModifierEnabled())
		{
			return;
		}
        if(Bukkit.getPluginManager().getPlugin("InterfaceHub") == null) 
        {
            return;
        }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
						return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.general.modifier.Modifier> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.general.modifier.Modifier.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    logger.info(pluginName + " detected InterfaceHub >>> Modifier.class is consumed!");
				    modifierConsumer = rsp.getProvider();
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}
				if(getModifier() != null)
				{
					List<Bypass.Counter> list = new ArrayList<Bypass.Counter>(EnumSet.allOf(Bypass.Counter.class));
					for(Bypass.Counter ept : list)
					{
						if(getModifier().isRegistered(ept.getModification()))
						{
							continue;
						}
						ModificationType bmt = null;
						switch(ept)
						{
						case SHOP_CREATION_AMOUNT_:
						case SHOP_ITEMSTORAGE_AMOUNT_:
						case COST_ADDING_STORAGE:
						case SHOP_SUBSCRIPTION_:
							bmt = ModificationType.UP;
							break;
						case SHOP_BUYING_TAX:
						case SHOP_SELLING_TAX:
							bmt = ModificationType.DOWN;
							break;							
						}
						List<String> lar = plugin.getYamlHandler().getMVELang().getStringList(ept.toString()+".Explanation");
						getModifier().register(
								ept.getModification(),
								plugin.getYamlHandler().getMVELang().getString(ept.toString()+".Displayname", ept.toString()),
								bmt,
								lar.toArray(new String[lar.size()]));
					}
				}
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public Modifier getModifier()
	{
		return modifierConsumer;
	}
	
	private void setupIFHMessageToBungee() 
	{
        if(Bukkit.getPluginManager().getPlugin("InterfaceHub") == null) 
        {
            return;
        }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
						return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.spigot.tobungee.chatlike.MessageToBungee> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.spigot.tobungee.chatlike.MessageToBungee.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    mtbConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> MessageToBungee.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public MessageToBungee getMtB()
	{
		return mtbConsumer;
	}
	
	private void setupIFHMessageToVelocity() 
	{
        if(Bukkit.getPluginManager().getPlugin("InterfaceHub") == null) 
        {
            return;
        }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
						return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.spigot.tovelocity.chatlike.MessageToVelocity> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.spigot.tovelocity.chatlike.MessageToVelocity.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    mtvConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> MessageToVelocity.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public MessageToVelocity getMtV()
	{
		return mtvConsumer;
	}
	
	private void setupIFHBaseComponentToBungee() 
	{
        if(Bukkit.getPluginManager().getPlugin("InterfaceHub") == null) 
        {
            return;
        }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
						return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.spigot.tobungee.chatlike.BaseComponentToBungee> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.spigot.tobungee.chatlike.BaseComponentToBungee.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    bctbConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> BaseComponentToBungee.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public BaseComponentToBungee getBctB()
	{
		return bctbConsumer;
	}
	
	private void setupIFHTeleport() 
	{
        if(Bukkit.getPluginManager().getPlugin("InterfaceHub") == null) 
        {
            return;
        }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
						return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.spigot.teleport.Teleport> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.spigot.teleport.Teleport.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    teleportConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> Teleport.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public Teleport getTeleport()
	{
		return teleportConsumer;
	}
	
	private void setupIFHStatistic() 
	{
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
        new BukkitRunnable()
        {
        	int i = 0;
			@Override
			public void run()
			{
				try
				{
					if(i == 20)
				    {
						cancel();
				    	return;
				    }
				    RegisteredServiceProvider<me.avankziar.ifh.general.statistic.Statistic> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 me.avankziar.ifh.general.statistic.Statistic.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    statisticConsumer = rsp.getProvider();
				    logger.info(pluginName + " detected InterfaceHub >>> Statistic.class is consumed!");
				    new main.java.me.avankziar.sale.spigot.ifh.StatisticHandler(plugin);
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 0L, 20*2);
	}
	
	public Statistic getStatistic()
	{
		return statisticConsumer;
	}
	
	private void setupWordEditGuard()
	{
		if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null)
		{
			worldGuard = WorldGuardHook.init();
		}
	}
	
	public static boolean getWorldGuard()
	{
		return worldGuard;
	}
	
	public void setupBstats()
	{
		int pluginId = 17588;
        new Metrics(this, pluginId);
	}
}