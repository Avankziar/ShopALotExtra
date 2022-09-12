package main.java.me.avankziar.sale.spigot.gui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.gui.GuiValues;
import main.java.me.avankziar.sale.spigot.gui.events.BottomGuiClickEvent;

public class BottomListener implements Listener
{
	private SaLE plugin;
	
	public BottomListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBottomGui(BottomGuiClickEvent event)
	{
		if(!event.getPluginName().equals(GuiValues.PLUGINNAME))
		{
			return;
		}
		switch(event.getInventoryIdentifier())
		{
		case GuiValues.ITEM_REPLACER_INVENTORY:
			new FunctionHandler(plugin).bottomFunction1();
			return;
		}
	}
}