package main.java.me.avankziar.sale.spigot;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
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

import main.java.me.avankziar.ifh.general.bonusmalus.BonusMalus;
import main.java.me.avankziar.ifh.spigot.administration.Administration;
import main.java.me.avankziar.ifh.spigot.economy.Economy;
import main.java.me.avankziar.ifh.spigot.interfaces.EnumTranslation;
import main.java.me.avankziar.ifh.spigot.storage.PhysicalChestStorage;
import main.java.me.avankziar.sale.spigot.assistance.BackgroundTask;
import main.java.me.avankziar.sale.spigot.assistance.Utility;
import main.java.me.avankziar.sale.spigot.cmd.SaLECommandExecutor;
import main.java.me.avankziar.sale.spigot.cmd.TabCompletion;
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
import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.handler.MaterialHandler;
import main.java.me.avankziar.sale.spigot.ifh.SignShopProvider;
import main.java.me.avankziar.sale.spigot.listener.BlockBreakListener;
import main.java.me.avankziar.sale.spigot.listener.PlayerArmorStandManipulateListener;
import main.java.me.avankziar.sale.spigot.listener.PlayerInteractListener;
import main.java.me.avankziar.sale.spigot.listener.PlayerJoinListener;
import main.java.me.avankziar.sale.spigot.listener.SignChangeListener;
import main.java.me.avankziar.sale.spigot.objects.ItemHologram;
import main.java.me.avankziar.sale.spigot.permission.Bypass;

public class SaLE extends JavaPlugin
{
	public static Logger log;
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
	
	private main.java.me.avankziar.ifh.spigot.shop.SignShop signShopProvider;
	
	private Administration administrationConsumer;
	private EnumTranslation enumTranslationConsumer;
	private PhysicalChestStorage pcsConsumer;
	private Economy ecoConsumer;
	private BonusMalus bonusMalusConsumer;
	
	public void onEnable()
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=SaLE
		log.info(" ███████╗ █████╗ ██╗     ███████╗ | API-Version: "+plugin.getDescription().getAPIVersion());
		log.info(" ██╔════╝██╔══██╗██║     ██╔════╝ | Author: "+plugin.getDescription().getAuthors().toString());
		log.info(" ███████╗███████║██║     █████╗   | Plugin Website: "+plugin.getDescription().getWebsite());
		log.info(" ╚════██║██╔══██║██║     ██╔══╝   | Depend Plugins: "+plugin.getDescription().getDepend().toString());
		log.info(" ███████║██║  ██║███████╗███████╗ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepend().toString());
		log.info(" ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝ | LoadBefore: "+plugin.getDescription().getLoadBefore().toString());
		
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
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
			return;
		}
		
		utility = new Utility(plugin);
		backgroundTask = new BackgroundTask(this);
		
		setupBypassPerm();
		setupCommandTree();
		setupListeners();
		setupIFH();
		MaterialHandler.init(plugin);
	}
	
	public void onDisable()
	{
		for(Entry<String, ItemHologram> e : ItemHologramHandler.taskMap.entrySet())
		{
			e.getValue().despawn();
		}
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		log.info(pluginName + " is disabled!");
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
		infoCommand += plugin.getYamlHandler().getCommands().getString("base.Name");
		
		TabCompletion tab = new TabCompletion(plugin);
		
		CommandConstructor base = new CommandConstructor(CommandExecuteType.SALE, "base", false);
		registerCommand(base.getPath(), base.getName());
		getCommand(base.getName()).setExecutor(new SaLECommandExecutor(plugin, base));
		getCommand(base.getName()).setTabCompleter(tab);
	}
	
	private void setupBypassPerm()
	{
		String path = "Count.";
		for(Bypass.CountPermission bypass : new ArrayList<Bypass.CountPermission>(EnumSet.allOf(Bypass.CountPermission.class)))
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
		log.info(pluginName+" hook with "+externPluginName);
		return true;
	}
	
	private void setupIFHAdministration()
	{ 
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
		RegisteredServiceProvider<main.java.me.avankziar.ifh.spigot.administration.Administration> rsp = 
                getServer().getServicesManager().getRegistration(Administration.class);
		if (rsp == null) 
		{
		   return;
		}
		administrationConsumer = rsp.getProvider();
		log.info(pluginName + " detected InterfaceHub >>> Administration.class is consumed!");
	}
	
	public Administration getAdministration()
	{
		return administrationConsumer;
	}
	
	private void setupIFH()
	{
		if(setupShop())
		{
			return;
		}
		setupIFHEnumTranslation();
		setupIFHPhysicalChestStorage();
		setupIFHEconomy();
		setupBonusMalus();
	}
	
	private boolean setupShop()
	{
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
			log.severe("IFH is not set in the Plugin " + pluginName + "! Disable plugin!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
	    	return false;
	    }
		signShopProvider = new SignShopProvider(plugin);
    	plugin.getServer().getServicesManager().register(
    			main.java.me.avankziar.ifh.spigot.shop.SignShop.class,
    	signShopProvider,
        this,
        ServicePriority.Normal);
    	log.info(pluginName + " detected InterfaceHub >>> SignShop.class is provided!");
		return false;
	}
	
	private void setupIFHEnumTranslation() 
	{
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
			log.severe("IFH is not set in the Plugin " + pluginName + "! Disable plugin!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
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
						log.severe("IFH is not set in the Plugin " + pluginName + "! Disable plugin!");
						Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
				    	return;
				    }
				    RegisteredServiceProvider<main.java.me.avankziar.ifh.spigot.interfaces.EnumTranslation> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 main.java.me.avankziar.ifh.spigot.interfaces.EnumTranslation.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    enumTranslationConsumer = rsp.getProvider();
				    log.info(pluginName + " detected InterfaceHub >>> EnumTranslation.class is consumed!");
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
	
	private void setupIFHPhysicalChestStorage() 
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
				    RegisteredServiceProvider<main.java.me.avankziar.ifh.spigot.storage.PhysicalChestStorage> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 main.java.me.avankziar.ifh.spigot.storage.PhysicalChestStorage.class);
				    if(rsp == null) 
				    {
				    	i++;
				        return;
				    }
				    pcsConsumer = rsp.getProvider();
				    log.info(pluginName + " detected InterfaceHub >>> PhysicalChestStorage.class is consumed!");
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}			    
			}
        }.runTaskTimer(plugin, 0L, 20*2);
	}
	
	public PhysicalChestStorage getPCS()
	{
		return pcsConsumer;
	}
	
	private void setupIFHEconomy()
    {
		if(!plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
	    {
	    	return;
	    }
		RegisteredServiceProvider<main.java.me.avankziar.ifh.spigot.economy.Economy> rsp = 
                getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) 
		{
			log.severe("A economy plugin which supported InterfaceHub is missing!");
			log.severe("Disable "+pluginName+"!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(plugin);
			return;
		}
		ecoConsumer = rsp.getProvider();
		log.info(pluginName + " detected InterfaceHub >>> Economy.class is consumed!");
        return;
    }
	
	public Economy getIFHEco()
	{
		return this.ecoConsumer;
	}
	
	private void setupBonusMalus() 
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
				    RegisteredServiceProvider<main.java.me.avankziar.ifh.general.bonusmalus.BonusMalus> rsp = 
		                             getServer().getServicesManager().getRegistration(
		                            		 main.java.me.avankziar.ifh.general.bonusmalus.BonusMalus.class);
				    if(rsp == null) 
				    {
				    	//Check up to 20 seconds after the start, to connect with the provider
				    	i++;
				        return;
				    }
				    bonusMalusConsumer = rsp.getProvider();
				    cancel();
				} catch(NoClassDefFoundError e)
				{
					cancel();
				}
				/*if(getBonusMalus() != null)
				{
					for(BaseConstructor bc : getCommandHelpList())
					{
						if(!bc.isPutUpCmdPermToBonusMalusSystem())
						{
							continue;
						}
						if(getBonusMalus().isRegistered(pluginName.toLowerCase()+":"+bc.getPath()))
						{
							continue;
						}
						String[] ex = {plugin.getYamlHandler().getCommands().getString(bc.getPath()+".Explanation")};
						getBonusMalus().register(
								pluginName.toLowerCase()+":"+bc.getPath(),
								plugin.getYamlHandler().getCommands().getString(bc.getPath()+".Displayname", "Command "+bc.getName()),
								true,
								BonusMalusType.UP, MultiplicationCalculationType.MULTIPLICATION,
								ex);
					}
					if(!new ConfigHandler().isMechanicBonusMalusEnabled())
					{
						return;
					}
					List<Bypass.Permission> list = new ArrayList<Bypass.Permission>(EnumSet.allOf(Bypass.Permission.class));
					for(Bypass.Permission ept : list)
					{
						if(!getBonusMalus().isRegistered(ept.toString().toLowerCase()))
						{
							BonusMalusType bmt = null;
							switch(ept)
							{
							case SHOP_CREATION:
								bmt = BonusMalusType.UP; break;
							}
							List<String> lar = plugin.getYamlHandler().getBMLang().getStringList(ept.toString()+".Explanation");
							getBonusMalus().register(
									pluginName.toLowerCase()+":"+ept.toString().toLowerCase(),
									plugin.getYamlHandler().getBMLang().getString(ept.toString()+".Displayname", ept.toString()),
									true,
									bmt, MultiplicationCalculationType.MULTIPLICATION,
									lar.toArray(new String[lar.size()]));
						}
					}
					List<Bypass.CountPermission> list2 = new ArrayList<Bypass.CountPermission>(EnumSet.allOf(Bypass.CountPermission.class));
					for(Bypass.CountPermission ept : list2)
					{
						if(!getBonusMalus().isRegistered(ept.toString().toLowerCase()))
						{
							BonusMalusType bmt = null;
							switch(ept)
							{
							case SHOP_CREATION_AMOUNT_:
								bmt = BonusMalusType.UP;
								break;
							}
							List<String> lar = plugin.getYamlHandler().getBMLang().getStringList(ept.toString()+".Explanation");
							getBonusMalus().register(
									ept.getBonusMalus(),
									plugin.getYamlHandler().getBMLang().getString(ept.toString()+".Displayname", ept.toString()),
									false,
									bmt, MultiplicationCalculationType.MULTIPLICATION,
									lar.toArray(new String[lar.size()]));
						}
					}
					//TODO BonusMalus Class
				}*/
			}
        }.runTaskTimer(plugin, 20L, 20*2);
	}
	
	public BonusMalus getBonusMalus()
	{
		return bonusMalusConsumer;
	}
}