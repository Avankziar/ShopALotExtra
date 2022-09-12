package main.java.me.avankziar.sale.spigot.gui.listener;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.gui.GuiValues;
import main.java.me.avankziar.sale.spigot.gui.events.UpperGuiClickEvent;

public class UpperListener implements Listener
{
	private SaLE plugin;
	
	public UpperListener(SaLE plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onUpperGui(UpperGuiClickEvent event) throws IOException
	{
		if(!event.getPluginName().equals(GuiValues.PLUGINNAME))
		{
			return;
		}
		switch(event.getInventoryIdentifier())
		{
		case GuiValues.ITEM_REPLACER_INVENTORY:
			new FunctionHandler(plugin).upperFunction1();
			return;
		}
	}
}