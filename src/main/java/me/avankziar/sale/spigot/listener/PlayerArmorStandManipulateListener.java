package main.java.me.avankziar.sale.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import main.java.me.avankziar.sale.spigot.handler.ItemHologramHandler;
import main.java.me.avankziar.sale.spigot.objects.ItemHologram;

public class PlayerArmorStandManipulateListener implements Listener
{
	@EventHandler
	public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event)
	{
		for(ItemHologram ish : ItemHologramHandler.taskMap.values())
		{
			if(ish.cancelManipulateEvent(event.getRightClicked()))
			{
				event.setCancelled(true);
				break;
			}
		}
	}
}