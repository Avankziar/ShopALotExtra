package main.java.me.avankziar.sale.spigot.cmdtree;

public enum CommandExecuteType
{
	SALE,
	SALE_SHOP, //Überbefehl für die statistischen Befehle
	//SALE_GUI, //Ruft ein Gui auf, zum öffnen von den SignShop Guis. ?Eventuell?
	SALE_DELETE, //Admincmd zum delete
	SALE_SHOP_TOGGLE, //Toggle um in AdminGui
	SALE_SHOP_BREAKTOGGLE, //Breaktoggle um schilder schnell abzubauen.
	/*
	 * Aufrufen des globalen GUI, das es ermöglich alle Shops aller Spieler aus der Ferne zu erreichen.
	 * Auch soll man hier nach allen Shops filtern können, welches x material anbieten. (Tastaturpad a la NumPad gui?)
	 * Dazu soll das auktionsystem erreichbar sein.
	 */
	//TODO Befehl um alle Spieler auf den ListedType aufzulisten Vielleicht?
	//TODO Worldguard negativ flag einstellen, um zu verbieten das man shop erstellen darf.
	//TODO Worldguard shop search. mit angabe des Spielername und des grundstücknamens.
	SALE_SHOP_LOG,
	SALE_SHOP_DAILYLOG,
	SALE_SHOPPING,
	SALE_SHOPPING_LOG,
	SALE_SHOPPING_DAILYLOG,
}