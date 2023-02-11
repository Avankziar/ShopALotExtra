package main.java.me.avankziar.sale.spigot.cmdtree;

import main.java.me.avankziar.sale.spigot.SaLE;

public class BaseConstructor
{
	public static SaLE getPlugin()
	{
		return SaLE.getPlugin();
	}
	
	private String name;
	private String path;
	private String permission;
	private String suggestion;
	private String commandString;
	private String helpInfo;
	private boolean canConsoleAccess;
	private boolean putUpCmdPermToConditionSystem;
	
	public BaseConstructor(CommandExecuteType cet, String name, String path, String permission, String suggestion, String commandString,
			String helpInfo, boolean canConsoleAccess, boolean putUpCmdPermToConditionSystem)
	{
		setName(name);
		setPath(path);
		setPermission(permission);
		setSuggestion(suggestion);
		setCommandString(commandString);
		setHelpInfo(helpInfo);
		setCanConsoleAccess(canConsoleAccess);
		CommandSuggest.set(cet, commandString);
		getPlugin().addingCommandHelps(this);
		setPutUpCmdPermToConditionSystem(putUpCmdPermToConditionSystem);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getPermission()
	{
		return permission;
	}

	public void setPermission(String permission)
	{
		this.permission = permission;
	}

	public String getSuggestion()
	{
		return suggestion;
	}

	public void setSuggestion(String suggestion)
	{
		this.suggestion = suggestion;
	}

	public String getCommandString()
	{
		return commandString;
	}

	public void setCommandString(String commandString)
	{
		this.commandString = commandString;
	}

	public boolean canConsoleAccess()
	{
		return canConsoleAccess;
	}

	public void setCanConsoleAccess(boolean canConsoleAccess)
	{
		this.canConsoleAccess = canConsoleAccess;
	}

	public String getHelpInfo()
	{
		return helpInfo;
	}

	public void setHelpInfo(String helpInfo)
	{
		this.helpInfo = helpInfo;
	}

	public boolean isPutUpCmdPermToConditionSystem()
	{
		return putUpCmdPermToConditionSystem;
	}

	public void setPutUpCmdPermToConditionSystem(boolean putUpCmdPermToConditionSystem)
	{
		this.putUpCmdPermToConditionSystem = putUpCmdPermToConditionSystem;
	}
	
	public String getConditionPath()
	{
		return getPlugin().pluginName.toLowerCase()+"-"+getPath();
	}
}