package main.java.me.avankziar.sale.spigot.cmdtree;

public enum CommandExecuteType
{
	SALE,
	SALE_SHOP, //Überbefehl für die statistischen Befehle
	//SALE_GUI, //Ruft ein Gui auf, zum öffnen von den SignShop Guis. ?Eventuell?
	SALE_DEBUG, //Only for Debugging Purpose
	SALE_SHOP_DELETE, //Admincmd zum delete
	SALE_SHOP_TOGGLE, //Toggle um in AdminGui
	SALE_SHOP_BREAKTOGGLE, //Breaktoggle um schilder schnell abzubauen.
	SALE_SHOP_SEARCHBUY,
	SALE_SHOP_SEARCHSELL,
	SALE_SHOP_TOGGLESEARCHALL,

	SALE_SHOP_LOG,
	SALE_SHOP_DAILYLOG,
	SALE_CLIENT,
	SALE_CLIENT_LOG,
	SALE_CLIENT_DAILYLOG,
	
	SALE_SERVERACCOUNT,
	SALE_SERVERACCOUNT_CREATE,
	
	SALE_SUBSCRIBED,
}