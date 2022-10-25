package main.java.me.avankziar.sale.spigot.cmdtree;

import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

public class CommandConstructor extends BaseConstructor
{
    public ArrayList<ArgumentConstructor> subcommands;
    public ArrayList<String> tablist;

	public CommandConstructor(CommandExecuteType cet, String path, boolean canConsoleAccess,
    		ArgumentConstructor...argumentConstructors)
    {
		super(
				cet,
				getPlugin().getYamlHandler().getCommands().getString(path+".Name"),
				path,
				getPlugin().getYamlHandler().getCommands().getString(path+".Permission"),
				getPlugin().getYamlHandler().getCommands().getString(path+".Suggestion"),
				getPlugin().getYamlHandler().getCommands().getString(path+".CommandString"),
				getPlugin().getYamlHandler().getCommands().getString(path+".HelpInfo"),
				canConsoleAccess,
				getPlugin().getYamlHandler().getCommands().getBoolean(path+".BonusMalusSystem.PutUpCommandPerm", false));
        this.subcommands = new ArrayList<>();
        this.tablist = new ArrayList<>();
        for(ArgumentConstructor ac : argumentConstructors)
        {
        	this.subcommands.add(ac);
        	this.tablist.add(ac.getName());
        }
        getPlugin().getCommandTree().add(this);
    }
	
	public CommandConstructor(CommandExecuteType cet, YamlConfiguration y, String path, boolean canConsoleAccess,
    		ArgumentConstructor...argumentConstructors)
    {
		super(cet, 
				y.getString(path+".Name"),
				path,
				y.getString(path+".Permission"),
				y.getString(path+".Suggestion"),
				y.getString(path+".CommandString"),
				y.getString(path+".HelpInfo"),
				canConsoleAccess,
				getPlugin().getYamlHandler().getCommands().getBoolean(path+".BonusMalusSystem.v", false));
        this.subcommands = new ArrayList<>();
        this.tablist = new ArrayList<>();
        for(ArgumentConstructor ac : argumentConstructors)
        {
        	this.subcommands.add(ac);
        	this.tablist.add(ac.getName());
        }
        getPlugin().getCommandTree().add(this);
    }
}