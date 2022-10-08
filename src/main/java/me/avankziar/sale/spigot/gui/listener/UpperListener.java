package main.java.me.avankziar.sale.spigot.gui.listener;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.gui.events.ClickType;
import main.java.me.avankziar.sale.spigot.gui.events.UpperGuiClickEvent;
import main.java.me.avankziar.sale.spigot.handler.GuiHandler;
import main.java.me.avankziar.sale.spigot.handler.gui.AdminstrationFunctionHandler;
import main.java.me.avankziar.sale.spigot.handler.gui.ShopFunctionHandler;
import main.java.me.avankziar.sale.spigot.objects.ClickFunctionType;
import main.java.me.avankziar.sale.spigot.objects.GuiType;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

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
		if(!event.getPluginName().equals(plugin.pluginName))
		{
			return;
		}
		if(!(event.getEvent().getWhoClicked() instanceof Player))
		{
			return;
		}
		Player player = (Player) event.getEvent().getWhoClicked();
		GuiType gt = null;
		try
		{
			gt = GuiType.valueOf(event.getInventoryIdentifier());
		} catch(Exception e)
		{
			return;
		}
		if(!event.getValuesInteger().containsKey(GuiHandler.SIGNSHOP_ID))
		{
			return;
		}
		int sshID = event.getValuesInteger().get(GuiHandler.SIGNSHOP_ID);
		SignShop ssh = (SignShop) plugin.getMysqlHandler().getData(MysqlHandler.Type.SIGNSHOP, "`id` = ?", sshID);
		if(ssh == null)
		{
			return;
		}
		UUID otheruuid = null;
		if(event.getValuesString().containsKey(GuiHandler.PLAYER_UUID))
		{
			otheruuid = UUID.fromString(event.getValuesString().get(GuiHandler.PLAYER_UUID));
		}
		ClickType ct = getClickFunctionType(event.getEvent().getClick(), event.getEvent().getHotbarButton());
		if(ct == null)
		{
			return;
		}
		ClickFunctionType cft = null;
		try
		{
			cft = ClickFunctionType.valueOf(event.getFunction(ct));
		} catch(Exception e)
		{
			return;
		}
		if(cft == null)
		{
			return;
		}
		switch(gt)
		{
		case ITEM_INPUT:
			break;
		case ADMINISTRATION:
		case NUMPAD_ACCOUNT:
		case NUMPAD_ASH:
		case NUMPAD_BUY:
		case NUMPAD_DISCOUNT_BUY:
		case NUMPAD_DISCOUNT_END:
		case NUMPAD_DISCOUNT_POSSIBLE_BUY:
		case NUMPAD_DISCOUNT_POSSIBLE_SELL:
		case NUMPAD_DISCOUNT_SELL:
		case NUMPAD_DISCOUNT_START:
		case NUMPAD_POSSIBLE_BUY:
		case NUMPAD_POSSIBLE_SELL:
		case NUMPAD_SELL:
		case KEYBOARD_BLACKLIST:
		case KEYBOARD_CUSTOM:
		case KEYBOARD_MEMBER:
		case KEYBOARD_SIGNSHOPNAME:
		case KEYBOARD_WHITELIST:
			AdminstrationFunctionHandler
				.doClickFunktion(gt, cft, player, ssh, event.getEvent().getClickedInventory(), event.getSettingsLevel(), otheruuid);
			break;
		case SHOP:
			ShopFunctionHandler.doClickFunktion(gt, cft, player, ssh, event.getEvent().getClickedInventory(), event.getSettingsLevel()); break;
		}
	}
	
	private ClickType getClickFunctionType(org.bukkit.event.inventory.ClickType ct, int hotbarButton)
	{
		switch(ct)
		{
		default: return null;
		case LEFT: return ClickType.LEFT;
		case RIGHT: return ClickType.RIGHT;
		case DROP: return ClickType.DROP;
		case SHIFT_LEFT: return ClickType.SHIFT_LEFT;
		case SHIFT_RIGHT: return ClickType.SHIFT_RIGHT;
		case CONTROL_DROP: return ClickType.CTRL_DROP;
		case NUMBER_KEY:
			if(hotbarButton < 0)
			{
				return null;
			}
			int i = hotbarButton+1;
			switch(i)
			{
			default: return null;
			case 1: return ClickType.NUMPAD_1;
			case 2: return ClickType.NUMPAD_2;
			case 3: return ClickType.NUMPAD_3;
			case 4: return ClickType.NUMPAD_4;
			case 5: return ClickType.NUMPAD_5;
			case 6: return ClickType.NUMPAD_6;
			case 7: return ClickType.NUMPAD_7;
			case 8: return ClickType.NUMPAD_8;
			case 9: return ClickType.NUMPAD_9;
			}
		}
	}
}