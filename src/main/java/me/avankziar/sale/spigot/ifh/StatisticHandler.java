package main.java.me.avankziar.sale.spigot.ifh;

import java.util.UUID;

import main.java.me.avankziar.sale.spigot.SaLE;

public class StatisticHandler
{
	private static SaLE plugin;
	/**
	 * How much material as client was bought.
	 */
	public static String CLIENT_BUY_AMOUNT_MATERIAL = "CLIENT_BUY_AMOUNT_MATERIAL";
	/**
     * How much material as client was sold.
     */
	public static String CLIENT_SELL_AMOUNT_MATERIAL = "CLIENT_SELL_AMOUNT_MATERIAL";
	/**
     * How much material as shopowner was bought.
     */
	public static String SHOPOWNER_BUY_AMOUNT_MATERIAL = "SHOPOWNER_BUY_AMOUNT_MATERIAL";
	/**
     * How much material as shopowner was sold.
     */
	public static String SHOPOWNER_SELL_AMOUNT_MATERIAL = "SHOPOWNER_SELL_AMOUNT_MATERIAL";
	/**
	 * How much was paid as client for what was bought.
	 */
	public static String CLIENT_BUY_AMOUNT_CURRENCY = "CLIENT_BUY_AMOUNT_CURRENCY";
	/**
     * How much was paid as client for what was sold.
     */
	public static String CLIENT_SELL_AMOUNT_CURRENCY = "CLIENT_SELL_AMOUNT_CURRENCY";
	/**
     *How much was paid as shopowner for what was bought.
     */
	public static String SHOPOWNER_BUY_AMOUNT_CURRENCY = "SHOPOWNER_BUY_AMOUNT_CURRENCY";
	/**
     * How much was paid as shopowner for what was sold.
     */
	public static String SHOPOWNER_SELL_AMOUNT_CURRENCY = "SHOPOWNER_SELL_AMOUNT_CURRENCY";
	
	public StatisticHandler(SaLE plugin)
	{
		StatisticHandler.plugin = plugin;
		me.avankziar.ifh.general.statistic.StatisticType.SortingType st = me.avankziar.ifh.general.statistic.StatisticType.SortingType.WITH_SUBSTATISTIC;
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(CLIENT_BUY_AMOUNT_MATERIAL, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(CLIENT_SELL_AMOUNT_MATERIAL, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(SHOPOWNER_BUY_AMOUNT_MATERIAL, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(SHOPOWNER_SELL_AMOUNT_MATERIAL, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(CLIENT_BUY_AMOUNT_CURRENCY, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(CLIENT_SELL_AMOUNT_CURRENCY, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(SHOPOWNER_BUY_AMOUNT_CURRENCY, st);
		me.avankziar.ifh.general.statistic.StatisticType.registerStatisticType(SHOPOWNER_SELL_AMOUNT_CURRENCY, st);
	}
	
	public static void addStatistic(UUID uuid, String statistic, String materialOrEntityType, double value)
	{
		if(plugin.getStatistic() == null)
		{
			return;
		}
		me.avankziar.ifh.general.statistic.StatisticType st = me.avankziar.ifh.general.statistic.StatisticType.valueOf(statistic);
		if(st == null)
		{
			return;
		}
		plugin.getStatistic().addStatisticValue(uuid, 
				me.avankziar.ifh.general.statistic.StatisticType.valueOf(statistic),
				materialOrEntityType, value);
	}
}