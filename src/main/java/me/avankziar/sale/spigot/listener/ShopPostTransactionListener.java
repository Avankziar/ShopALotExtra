package main.java.me.avankziar.sale.spigot.listener;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.event.ShopPostTransactionEvent;
import main.java.me.avankziar.sale.spigot.handler.Base64Handler;
import main.java.me.avankziar.sale.spigot.objects.ShopLogVar;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class ShopPostTransactionListener implements Listener
{
	public static LinkedHashMap<UUID, LinkedHashMap<UUID, LinkedHashMap<String, ShopLogVar>>> maping = new LinkedHashMap<>(); //shopowner, client, is as Base64, iamount, 5 min Timer
	public static LinkedHashMap<UUID, LinkedHashMap<UUID, LinkedHashMap<String, ShopLogVar>>> maping2 = new LinkedHashMap<>(); //shopowner, client, is as Base64, iamount, 15 min timer
	
	@EventHandler
	public void onShopPostTransaction(ShopPostTransactionEvent event)
	{
		final SignShop ssh = event.getSignShop();
		final UUID client = event.getClient().getUniqueId();
		final long iamount = event.getItemAmount();
		final double costPerItem = event.getCostPerItem();
		doMap(maping, ssh, client, iamount, costPerItem, event.isBuying());
		doMap(maping2, ssh, client, iamount, costPerItem, event.isBuying());
	}
	
	private void doMap(LinkedHashMap<UUID, LinkedHashMap<UUID, LinkedHashMap<String, ShopLogVar>>> base,
			SignShop ssh, UUID client, long iamount, double costPerItem, boolean isBuy)
	{
		UUID owner = ssh.getOwner();
		ItemStack is = ssh.getItemStack();
		LinkedHashMap<UUID, LinkedHashMap<String, ShopLogVar>> sub = base.containsKey(owner) ? base.get(owner) : new LinkedHashMap<>();
		LinkedHashMap<String, ShopLogVar> sub2 = sub.containsKey(client) ? sub.get(client) : new LinkedHashMap<>();
		String b64 = new Base64Handler(is).toBase64();
		ShopLogVar slv = null;
		if(isBuy)
		{
			if(SaLE.getPlugin().getIFHEco() != null)
			{
				slv = (sub2.containsKey(b64) 
						? sub2.get(b64).addBuy(iamount, costPerItem) 
								: new ShopLogVar(ssh.getId(), ssh.getSignShopName(),
										SaLE.getPlugin().getIFHEco().getAccount(ssh.getAccountId()).getCurrency().getUniqueName(), 
										iamount, costPerItem, 0, 0));
			} else
			{
				slv = (sub2.containsKey(b64) 
						? sub2.get(b64).addBuy(iamount, costPerItem) 
								: new ShopLogVar(ssh.getId(), ssh.getSignShopName(),
										SaLE.getPlugin().getVaultEco().currencyNamePlural(), 
										iamount, costPerItem, 0, 0));
			}
		} else 
		{
			if(SaLE.getPlugin() != null)
			{
				slv = (sub2.containsKey(b64) 
						? sub2.get(b64).addSell(iamount, costPerItem) 
								: new ShopLogVar(ssh.getId(), ssh.getSignShopName(),
										SaLE.getPlugin().getIFHEco().getAccount(ssh.getAccountId()).getCurrency().getUniqueName(), 
										0, 0, iamount, costPerItem));
			} else
			{
				slv = (sub2.containsKey(b64) 
						? sub2.get(b64).addSell(iamount, costPerItem) 
								: new ShopLogVar(ssh.getId(), ssh.getSignShopName(),
										SaLE.getPlugin().getVaultEco().currencyNamePlural(), 
										0, 0, iamount, costPerItem));
			}
		}
		sub2.put(b64, slv);
		sub.put(client, sub2);
		base.put(owner, sub);
	}
}