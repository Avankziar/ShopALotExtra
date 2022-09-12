package main.java.me.avankziar.sale.spigot.gui.events;

/*
 * Als Einstellung zu verstehen, dass man bei bestimmten LEvel, nur bestimmte einstellmöglichkeiten sieht.
 * Um Spieler nicht zu überforndern
 */
public enum SettingsLevel
{
	NOLEVEL, BASE, ADVANCED, EXPERT, MASTER;
	
	public String getName()
	{
		switch(this)
		{
		case BASE:
			return "BASE";
		case ADVANCED:
			return "ADVANCED";
		case EXPERT:
			return "EXPERT";
		case MASTER:
			return "MASTER";
		case NOLEVEL:
			return "NOLEVEL";
		}
		return null;
	}
}