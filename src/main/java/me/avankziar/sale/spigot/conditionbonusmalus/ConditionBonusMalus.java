package main.java.me.avankziar.sale.spigot.conditionbonusmalus;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import main.java.me.avankziar.sale.spigot.assistance.MatchApi;
import main.java.me.avankziar.sale.spigot.cmdtree.BaseConstructor;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler;
import main.java.me.avankziar.sale.spigot.handler.ConfigHandler.CountType;

public class ConditionBonusMalus
{
	public static boolean hasPermission(Player player, BaseConstructor bc)
	{
		if(BaseConstructor.getPlugin().getCondition() != null)
		{
			String[] ss = BaseConstructor.getPlugin().getCondition().getConditionEntry(
					player.getUniqueId(),
					bc.getConditionPath(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
			if(ss == null)
			{
				if(BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Condition.ConditionOverrulePermission", false))
				{
					return false;
				} else
				{
					return player.hasPermission(bc.getPermission());
				}
			}
			int t = 0;
			int f = 0;
			for(String s : ss)
			{
				if(MatchApi.isBoolean(s))
				{
					if(MatchApi.getBoolean(s).booleanValue())
					{
						t++;
					} else
					{
						f++;
					}
				}
			}
			if(BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Condition.ConditionOverrulePermission", false))
			{
				if((t > 0 && t > f))
				{
					return true;
				}
			} else
			{
				if((t > 0 && t > f) || player.hasPermission(bc.getPermission()))
				{
					return true;
				}
			}
			return false;
		}
		return player.hasPermission(bc.getPermission());
	}
	
	public static boolean hasPermission(Player player, Bypass.Permission bypassPermission)
	{
		if(BaseConstructor.getPlugin().getCondition() != null)
		{
			String[] ss = BaseConstructor.getPlugin().getCondition().getConditionEntry(
					player.getUniqueId(),
					bypassPermission.getCondition(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
			if(ss == null)
			{
				if(BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Condition.ConditionOverrulePermission", false))
				{
					return false;
				} else
				{
					return player.hasPermission(Bypass.get(bypassPermission));
				}
			}
			int t = 0;
			int f = 0;
			for(String s : ss)
			{
				if(MatchApi.isBoolean(s))
				{
					if(MatchApi.getBoolean(s).booleanValue())
					{
						t++;
					} else
					{
						f++;
					}
				}
			}
			if(BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Condition.ConditionOverrulePermission", false))
			{
				if((t > 0 && t > f))
				{
					return true;
				}
			} else
			{
				if((t > 0 && t > f) || player.hasPermission(Bypass.get(bypassPermission)))
				{
					return true;
				}
			}
			return false;
		}
		return player.hasPermission(Bypass.get(bypassPermission));
	}
	
	public static boolean hasPermission(Player player, Bypass.Permission bypassPermission, String addition)
	{
		if(BaseConstructor.getPlugin().getCondition() != null)
		{
			String[] ss = BaseConstructor.getPlugin().getCondition().getConditionEntry(
					player.getUniqueId(),
					bypassPermission.getCondition(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
			if(ss == null)
			{
				if(BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Condition.ConditionOverrulePermission", false))
				{
					return false;
				} else
				{
					return player.hasPermission(Bypass.get(bypassPermission)+addition);
				}
			}
			int t = 0;
			int f = 0;
			for(String s : ss)
			{
				if(MatchApi.isBoolean(s))
				{
					if(MatchApi.getBoolean(s).booleanValue())
					{
						t++;
					} else
					{
						f++;
					}
				}
			}
			if(BaseConstructor.getPlugin().getYamlHandler().getConfig().getBoolean("Condition.ConditionOverrulePermission", false))
			{
				if((t > 0 && t > f))
				{
					return true;
				}
			} else
			{
				if((t > 0 && t > f) || player.hasPermission(Bypass.get(bypassPermission)+addition))
				{
					return true;
				}
			}
			return false;
		}
		return player.hasPermission(Bypass.get(bypassPermission));
	}
	
	public static int getResult(@NonNull Player player, Bypass.Counter countPermission)
	{
		return getResult(player, 0.0, countPermission);
	}
	
	public static int getResult(@NonNull Player player, double value, Bypass.Counter countPermission)
	{
		if(player.hasPermission(Bypass.get(countPermission)+"*"))
		{
			return Integer.MAX_VALUE;
		}
		int possibleAmount = 0;
		CountType ct = new ConfigHandler().getCountPermType();
		switch(ct)
		{
		case ADDUP:
			for(int i = 1000; i >= 0; i--)
			{
				if(player.hasPermission(Bypass.get(countPermission)+i))
				{
					possibleAmount += i;
				}
			}
			break;
		case HIGHEST:
			for(int i = 1000; i >= 0; i--)
			{
				if(player.hasPermission(Bypass.get(countPermission)+i))
				{
					possibleAmount = i;
					break;
				}
			}
			break;
		}
		possibleAmount += (int) value;
		if(BaseConstructor.getPlugin().getBonusMalus() != null)
		{
			return (int) BaseConstructor.getPlugin().getBonusMalus().getResult(
					player.getUniqueId(),
					possibleAmount,
					countPermission.getBonusMalus(),
					BaseConstructor.getPlugin().getServername(),
					player.getWorld().getName());
		}
		return possibleAmount;
	}
	
	public static double getResult(UUID uuid, double value, Bypass.Counter countPermission)
	{
		double possibleAmount = value;
		if(BaseConstructor.getPlugin().getBonusMalus() != null)
		{
			return BaseConstructor.getPlugin().getBonusMalus().getResult(
					uuid,
					possibleAmount,
					countPermission.getBonusMalus());
		}
		return possibleAmount;
	}
}