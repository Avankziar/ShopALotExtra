package main.java.me.avankziar.sale.spigot.cmdtree;

public enum CommandExecuteType
{
	SALE,
	SALE_SHOP,
	SALE_GUI,
	/*
	 * Aufrufen des globalen GUI, das es ermöglich alle Shops aller Spieler aus der Ferne zu erreichen.
	 * Auch soll man hier nach allen Shops filtern können, welches x material anbieten. (Tastaturpad a la NumPad gui?)
	 * Dazu soll das auktionsystem erreichbar sein.
	 */
	//TODO Befehl um alle Spieler auf den ListedType aufzulisten Vielleicht?
	//TODO Worldguard negativ flag einstellen, um zu verbieten das man shop erstellen darf.
	//TODO Worldguard shop search. mit angabe des Spielername und des grundstücknamens.
	//TODO shop delete befehle, mit var wie player:xxx && server:xxx oder world:xxx etc.
	//TODO Shop break delete toggle befehl, zum toggle um shopschilder direkt abschlagen zu können
	//TODO Shop toggle um ins administrationsgui anderer shops zu kommen
	SHOPLOG,
	SHOPDAILYLOG,
	SHOPPPINGLOG,
	SHOPPINGDAILYLOG,
}
