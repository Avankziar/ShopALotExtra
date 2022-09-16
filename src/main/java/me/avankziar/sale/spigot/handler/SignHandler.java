package main.java.me.avankziar.sale.spigot.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.java.me.avankziar.sale.general.ChatApi;
import main.java.me.avankziar.sale.spigot.SaLE;
import main.java.me.avankziar.sale.spigot.database.MysqlHandler;
import main.java.me.avankziar.sale.spigot.objects.SignShop;

public class SignHandler
{
	private static SaLE plugin = SaLE.getPlugin();
	private static LinkedHashMap<UUID, Integer> toCheckMap = new LinkedHashMap<>(); //player, sshid
	
	public static Sign getSign(SignShop ssh)
	{
		if(!plugin.getServername().equals(ssh.getServer()))
		{
			return null;
		}
		Block b = Bukkit.getWorld(ssh.getWorld()).getBlockAt(ssh.getX(), ssh.getY(), ssh.getZ());
		if(!(b instanceof Sign))
		{
			return null;
		}
		return (Sign) b;
	}
	
	public static String getSignLine(int index, SignShop ssh)
	{
		switch(index)
		{
		default:
		case 0:
			return MaterialHandler.getMaterial(ssh.getMaterial());
		case 1:
			if(ssh.getBuyAmount() == null)
			{
				return plugin.getYamlHandler().getLang().getString("SignHandler.Line1").replace("%amount%", "/");
			}
			return plugin.getYamlHandler().getLang().getString("SignHandler.Line1").replace("%amount%", String.valueOf(ssh.getBuyAmount()));
		case 2:
			if(ssh.getSellAmount() == null)
			{
				return plugin.getYamlHandler().getLang().getString("SignHandler.Line2").replace("%amount%", "/");
			}
			return plugin.getYamlHandler().getLang().getString("SignHandler.Line2").replace("%amount%", String.valueOf(ssh.getSellAmount()));
		case 3:
			StringBuilder sb = new StringBuilder();
			String colorB = "";
			String colorS = "";
			long now = System.currentTimeMillis();
			long buy = ssh.getItemStorageCurrent();
			long sell = ssh.getItemStorageTotal()-ssh.getItemStorageCurrent();
			if(ssh.getDiscountStart() < now && ssh.getDiscountEnd() > now)
			{
				//DiscountpossibleBuy/sell
				if(ssh.getDiscountPossibleBuy() >= 0 || ssh.getDiscountPossibleSell() >= 0)
				{
					if(ssh.getDiscountPossibleBuy() >= 0)
					{
						colorB = getPercentColor(ssh.getItemStorageTotal(), ssh.getPossibleBuy());
						if(ssh.getDiscountPossibleBuy() > 99999)
						{
							sb.append(colorB+"99999+");
						} else
						{
							sb.append(colorB+String.valueOf(ssh.getDiscountPossibleBuy()));
						}
					} else
					{
						if(buy > 99999)
						{
							sb.append(colorB+"99999+");
						} else
						{
							sb.append(colorB+buy);
						}
					}
					sb.append(" &r/ ");
					if(ssh.getDiscountPossibleSell() >= 0)
					{
						colorS = getPercentColor(ssh.getItemStorageTotal(), ssh.getPossibleSell());
						if(ssh.getDiscountPossibleSell() > 99999)
						{
							sb.append(colorS+"99999+");
						} else
						{
							sb.append(colorS+String.valueOf(ssh.getDiscountPossibleSell()));
						}
					} else
					{
						if(sell > 99999)
						{
							sb.append(colorS+"99999+");
						} else
						{
							sb.append(colorS+sell);
						}
					}
					return sb.toString();
				} else
				{
					//Discount without possibleBuy/Sell
					if(buy > 99999)
					{
						sb.append(colorB+"99999+");
					} else
					{
						sb.append(colorB+buy);
					}
					sb.append(" &r/ ");
					if(sell > 99999)
					{
						sb.append(colorS+"99999+");
					} else
					{
						sb.append(colorS+sell);
					}
				}
			} else
			{
				//normal possibleBuy/sell
				if(ssh.getPossibleBuy() >= 0 || ssh.getPossibleSell() >= 0)
				{
					colorB = getPercentColor(ssh.getItemStorageTotal(), ssh.getPossibleBuy());
					if(ssh.getPossibleBuy() >= 0)
					{
						if(ssh.getPossibleBuy() > 99999)
						{
							sb.append(colorB+"99999+");
						} else
						{
							sb.append(colorB+String.valueOf(ssh.getPossibleBuy()));
						}
					} else
					{
						
					}
					sb.append(" &r/ ");
					if(ssh.getPossibleSell() >= 0)
					{
						colorS = getPercentColor(ssh.getItemStorageTotal(), ssh.getPossibleSell());
						if(ssh.getPossibleSell() > 99999)
						{
							sb.append(colorS+"99999+");
						} else
						{
							sb.append(colorS+String.valueOf(ssh.getPossibleSell()));
						}
					} else
					{
						if(sell > 99999)
						{
							sb.append(colorS+"99999+");
						} else
						{
							sb.append(colorS+sell);
						}
					}
					return sb.toString();
				} else
				{
					//normal without possibleBuy/Sell
					if(buy > 99999)
					{
						sb.append(colorB+"99999+");
					} else
					{
						sb.append(colorB+buy);
					}
					sb.append(" &r/ ");
					if(sell > 99999)
					{
						sb.append(colorS+"99999+");
					} else
					{
						sb.append(colorS+sell);
					}
				}
			}
			return sb.toString();
		}
	}
	
	public static String getPercentColor(long max, long actual)
	{
		double perc = ((double) actual)/((double) max) * 100.0;
		if(perc >= 100.0){return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.100AndAbove")+actual;}
		else if(perc < 100.0 && perc >= 75.0){return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.Between100And75")+actual;}
		else if(perc < 75.0 && perc >= 50.0){return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.Between75And50")+actual;}
		else if(perc < 50.0 && perc >= 25.0){return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.Between50And25")+actual;}
		else if(perc < 25.0 && perc >= 10.0){return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.Between25And10")+actual;}
		else if(perc < 10.0 && perc > 0.0){return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.Between10And0")+actual;}
		else {return plugin.getYamlHandler().getLang().getString("SignHandler.PercentColor.0AndLess")+actual;}
	}
	
	public static boolean isMember(SignShop ssh, UUID member)
	{
		return false; //TODO
	}
	
	public static boolean putInItemIntoShop(SignShop ssh, Player player, ItemStack toPutIn)
	{
		final boolean isShift = player.isSneaking();
		ItemStack c = toPutIn.clone();
		c.setAmount(1);
		if(ssh.getItemStack() == null || ssh.getItemStack().getType() == Material.AIR)
		{
			ssh.setItemStack(c);
			ItemMeta im = c.getItemMeta();
			if(im.hasDisplayName())
			{
				ssh.setDisplayName(im.getDisplayName());
			} else
			{
				ssh.setDisplayName(MaterialHandler.getMaterial(toPutIn.getType()));
			}
			ssh.setMaterial(c.getType());
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignHandler.AttachedNewItemStackToShop")
					.replace("%name%", ssh.getItemStack().getItemMeta().hasDisplayName() 
							? ssh.getItemStack().getItemMeta().getDisplayName() : MaterialHandler.getMaterial(ssh.getItemStack().getType()))
					.replace("%signshop%", ssh.getDisplayName())));
		}
		int amount = 0;
		if(isShift)
		{
			if(!ssh.getItemStack().toString().equals(c.toString()))
			{
				return false;
			}
			for(int i = 0; i < player.getInventory().getStorageContents().length; i++)
			{
				ItemStack is = player.getInventory().getStorageContents()[i];
				ItemStack cc = is.clone();
				cc.setAmount(1);
				if(is == null || is.getType() == Material.AIR)
				{
					continue;
				}
				if(!ssh.getItemStack().toString().equals(cc.toString()))
				{
					continue;
				}
				amount += is.getAmount();
				is = null;
			}
		} else
		{
			if(!ssh.getItemStack().toString().equals(c.toString()))
			{
				return false;
			}
			amount = toPutIn.getAmount();
			toPutIn = null;
			ssh.setItemStorageCurrent(ssh.getItemStorageCurrent()+((long) amount));
		}
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignHandler.ItemsAddedToShop")
				.replace("%amount%", String.valueOf(amount))
				.replace("%now%", String.valueOf(ssh.getItemStorageCurrent())+" / "+String.valueOf(ssh.getItemStorageTotal()))));
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
		return true;
	}
	
	public static void takeOutItemFromShop(SignShop ssh, Player player)
	{
		final boolean isShift = player.isSneaking();
		if(ssh.getItemStack() == null || ssh.getItemStack().getType() == Material.AIR)
		{
			player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignHandler.NoItemsIsSetUp")));
			return;
		}
		int amount = 0;
		if(isShift)
		{
			ArrayList<ItemStack> list = new ArrayList<>();
			for(int i = 0; i < player.getInventory().getStorageContents().length; i++)
			{
				ItemStack is = player.getInventory().getStorageContents()[i];
				if(is != null)
				{
					continue;
				}
				ItemStack out = ssh.getItemStack().clone();
				out.setAmount(out.getMaxStackSize());
				amount += out.getMaxStackSize();
				ssh.setItemStorageCurrent(ssh.getItemStorageCurrent()-((long) out.getMaxStackSize()));
			}
			for(ItemStack is : list)
			{
				player.getInventory().addItem(is);
			}
		} else
		{
			ItemStack out = ssh.getItemStack().clone();
			out.setAmount(out.getMaxStackSize());
			amount = out.getMaxStackSize();
			ssh.setItemStorageCurrent(ssh.getItemStorageCurrent()-((long) out.getMaxStackSize()));
			player.getInventory().setItemInMainHand(out);
		}
		player.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("SignHandler.ItemsRemovedToShop")
				.replace("%amount%", String.valueOf(amount))
				.replace("%now%", String.valueOf(ssh.getItemStorageCurrent())+" / "+String.valueOf(ssh.getItemStorageTotal()))));
		plugin.getMysqlHandler().updateData(MysqlHandler.Type.SIGNSHOP, ssh, "`id` = ?", ssh.getId());
	}
	
	public static boolean inCheck(UUID uuid)
	{
		return toCheckMap.containsKey(uuid);
	}
	
	public static void putToCheck(UUID uuid, int sshid)
	{
		toCheckMap.put(uuid, sshid);
	}
	
	public static void checkAfterAdministration(UUID uuid)
	{
		//TODO AccountID, ASHID
		toCheckMap.remove(uuid);
	}
	
	public static void updateSign()
	{
		//TODO Wenn update, dann auch den MaxStorageItem, falls VIP etc. ausläuft, nach jedem kauf.
		//TODO Beim Schließen des Administration GUI prüfen, ob ASH System dem Spieler gehört. Wenn nicht auf 0 setzten und dem Spieler benachrichtigen.
	}
}