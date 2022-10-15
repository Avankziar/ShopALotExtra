package main.java.me.avankziar.sale.spigot.objects;

public enum ClickFunctionType
{
	//Admin Button anzahl: 21
	//InfoItem
	//ADMINISTRATION_INFOITEM,
	//Toggelt An- & Verkauf
	ADMINISTRATION_TOGGLE_BUY,
	ADMINISTRATION_TOGGLE_SELL,
	//Neudefinierung des Items
	ADMINISTRATION_ITEM_CLEAR,
	//Einstellen des An- & Verkaufspreis
	ADMINISTRATION_NUMPAD_CANCEL,
	ADMINISTRATION_NUMPAD_COLON,
	ADMINISTRATION_NUMPAD_DECIMAL,
	ADMINISTRATION_NUMPAD_CLEAR,
	ADMINISTRATION_NUMPAD_REMOVEONCE,
	ADMINISTRATION_NUMPAD_0, //Setzt den KaufWertes auf die vorherige Zahl mit einem 0 am Ende. Bspw. 55 + 0 = 550
	ADMINISTRATION_NUMPAD_1,
	ADMINISTRATION_NUMPAD_2,
	ADMINISTRATION_NUMPAD_3,
	ADMINISTRATION_NUMPAD_4,
	ADMINISTRATION_NUMPAD_5,
	ADMINISTRATION_NUMPAD_6,
	ADMINISTRATION_NUMPAD_7,
	ADMINISTRATION_NUMPAD_8,
	ADMINISTRATION_NUMPAD_9,
	ADMINISTRATION_SETBUY_OPEN_NUMPAD,
	ADMINISTRATION_SETBUY_CLEAR, //Setzt den Preis auf 0 zurück.
	ADMINISTRATION_SETBUY_TAKEOVER, //Akzeptiert den eingetragen Betrag für den Wert
	ADMINISTRATION_SETSELL_OPEN_NUMPAD,
	ADMINISTRATION_SETSELL_CLEAR,
	ADMINISTRATION_SETSELL_TAKEOVER,
	//Erweiterung der Lagerräume, Zahl ist die Anzahl an Items, nicht Stacks
	ADMINISTRATION_ADDSTORAGE_1,
	ADMINISTRATION_ADDSTORAGE_8,
	ADMINISTRATION_ADDSTORAGE_16,
	ADMINISTRATION_ADDSTORAGE_32,
	ADMINISTRATION_ADDSTORAGE_64,
	ADMINISTRATION_ADDSTORAGE_576,
	ADMINISTRATION_ADDSTORAGE_1728,
	ADMINISTRATION_ADDSTORAGE_3456,
	ADMINISTRATION_ADDSTORAGE_6912,
	
	//Angabe eines Konto
	ADMINISTRATION_SETACCOUNT_OPEN_NUMPAD,
	ADMINISTRATION_SETACCOUNT_DEFAULT, //Setzt es auf den MAIN acc, wenn nicht existiert, auf 0
	ADMINISTRATION_SETACCOUNT_TAKEOVER,
	//ShopStatistik
	ADMINISTRATION_OPEN_SHOPLOG,
	//ASH
	ADMINISTRATION_SETASH_OPEN_NUMPAD,
	ADMINISTRATION_SETASH_CLEAR,
	ADMINISTRATION_SETASH_TAKEOVER,
	//Ver- & Ankaufslimit
	ADMINISTRATION_SETPOSSIBLE_BUY_OPEN_NUMPAD,
	ADMINISTRATION_SETPOSSIBLE_BUY_CLEAR, //Setzt auf -1, aka deaktiviert
	ADMINISTRATION_SETPOSSIBLE_BUY_TAKEOVER,
	ADMINISTRATION_SETPOSSIBLE_SELL_OPEN_NUMPAD,
	ADMINISTRATION_SETPOSSIBLE_SELL_CLEAR,
	ADMINISTRATION_SETPOSSIBLE_SELL_TAKEOVER,
	//Discount
	ADMINISTRATION_SETDISCOUNT_CLEAR, //Setzt Start und Ende auf 0
	ADMINISTRATION_SETDISCOUNT_START_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNT_START_TAKEOVER,
	ADMINISTRATION_SETDISCOUNT_START_WORLD_TAKEOVER,
	ADMINISTRATION_SETDISCOUNT_HOUR_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNT_HOUR_TAKEOVER,
	ADMINISTRATION_SETDISCOUNT_HOUR_WORLD_TAKEOVER,
	
	ADMINISTRATION_SETDISCOUNT_END_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNT_END_TAKEOVER,
	ADMINISTRATION_SETDISCOUNT_END_WORLD_TAKEOVER,
	//Discount Ver- & Ankaufslimit
	ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_CLEAR, //Setzt auf -1, aka deaktiviert
	ADMINISTRATION_SETDISCOUNTPOSSIBLE_BUY_TAKEOVER,
	ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_CLEAR,
	ADMINISTRATION_SETDISCOUNTPOSSIBLE_SELL_TAKEOVER,
	//Discount Ver- & Ankaufspreis
	ADMINISTRATION_SETDISCOUNTBUY_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNTBUY_CLEAR, //Setzt den Preis auf 0 zurück.
	ADMINISTRATION_SETDISCOUNTBUY_TAKEOVER,
	ADMINISTRATION_SETDISCOUNTSELL_OPEN_NUMPAD,
	ADMINISTRATION_SETDISCOUNTSELL_CLEAR,
	ADMINISTRATION_SETDISCOUNTSELL_TAKEOVER,
	//Löschbutton
	ADMINISTRATION_DELETE_WITHOUT_ITEMS_IN_STORAGE,
	ADMINISTRATION_DELETE_ALL,
	//OP SetUnlimited Toggle
	ADMINISTRATION_UNLIMITED_TOGGLE_BUY,
	ADMINISTRATION_UNLIMITED_TOGGLE_SELL,
	//SettingsLevel Change
	ADMINISTRATION_SETTINGSLEVEL_SETTO_BASE,
	ADMINISTRATION_SETTINGSLEVEL_SETTO_ADVANCED,
	ADMINISTRATION_SETTINGSLEVEL_SETTO_EXPERT,
	ADMINISTRATION_SETTINGSLEVEL_SETTO_MASTER,
	
	ADMINISTRATION_SETGLOWING,
	ADMINISTRATION_SETUNGLOWING,
	
	ADMINISTRATION_SETITEMHOLOGRAM_ACTIVE,
	ADMINISTRATION_SETITEMHOLOGRAM_DEACTIVE,
	
	ADMINISTRATION_KEYBOARD_CANCEL,
	ADMINISTRATION_KEYBOARD_REMOVEONCE,
	ADMINISTRATION_KEYBOARD_CLEAR,
	ADMINISTRATION_KEYBOARD_A_CAPITAL,
	ADMINISTRATION_KEYBOARD_A_SMALL,
	ADMINISTRATION_KEYBOARD_B_CAPITAL,
	ADMINISTRATION_KEYBOARD_B_SMALL,
	ADMINISTRATION_KEYBOARD_C_CAPITAL,
	ADMINISTRATION_KEYBOARD_C_SMALL,
	ADMINISTRATION_KEYBOARD_D_CAPITAL,
	ADMINISTRATION_KEYBOARD_D_SMALL,
	ADMINISTRATION_KEYBOARD_E_CAPITAL,
	ADMINISTRATION_KEYBOARD_E_SMALL,
	ADMINISTRATION_KEYBOARD_F_CAPITAL,
	ADMINISTRATION_KEYBOARD_F_SMALL,
	ADMINISTRATION_KEYBOARD_G_CAPITAL,
	ADMINISTRATION_KEYBOARD_G_SMALL,
	ADMINISTRATION_KEYBOARD_H_CAPITAL,
	ADMINISTRATION_KEYBOARD_H_SMALL,
	ADMINISTRATION_KEYBOARD_I_CAPITAL,
	ADMINISTRATION_KEYBOARD_I_SMALL,
	ADMINISTRATION_KEYBOARD_J_CAPITAL,
	ADMINISTRATION_KEYBOARD_J_SMALL,
	ADMINISTRATION_KEYBOARD_K_CAPITAL,
	ADMINISTRATION_KEYBOARD_K_SMALL,
	ADMINISTRATION_KEYBOARD_L_CAPITAL,
	ADMINISTRATION_KEYBOARD_L_SMALL,
	ADMINISTRATION_KEYBOARD_M_CAPITAL,
	ADMINISTRATION_KEYBOARD_M_SMALL,
	ADMINISTRATION_KEYBOARD_N_CAPITAL,
	ADMINISTRATION_KEYBOARD_N_SMALL,
	ADMINISTRATION_KEYBOARD_O_CAPITAL,
	ADMINISTRATION_KEYBOARD_O_SMALL,
	ADMINISTRATION_KEYBOARD_P_CAPITAL,
	ADMINISTRATION_KEYBOARD_P_SMALL,
	ADMINISTRATION_KEYBOARD_Q_CAPITAL,
	ADMINISTRATION_KEYBOARD_Q_SMALL,
	ADMINISTRATION_KEYBOARD_R_CAPITAL,
	ADMINISTRATION_KEYBOARD_R_SMALL,
	ADMINISTRATION_KEYBOARD_S_CAPITAL,
	ADMINISTRATION_KEYBOARD_S_SMALL,
	ADMINISTRATION_KEYBOARD_T_CAPITAL,
	ADMINISTRATION_KEYBOARD_T_SMALL,
	ADMINISTRATION_KEYBOARD_U_CAPITAL,
	ADMINISTRATION_KEYBOARD_U_SMALL,
	ADMINISTRATION_KEYBOARD_V_CAPITAL,
	ADMINISTRATION_KEYBOARD_V_SMALL,
	ADMINISTRATION_KEYBOARD_W_CAPITAL,
	ADMINISTRATION_KEYBOARD_W_SMALL,
	ADMINISTRATION_KEYBOARD_X_CAPITAL,
	ADMINISTRATION_KEYBOARD_X_SMALL,
	ADMINISTRATION_KEYBOARD_Y_CAPITAL,
	ADMINISTRATION_KEYBOARD_Y_SMALL,
	ADMINISTRATION_KEYBOARD_Z_CAPITAL,
	ADMINISTRATION_KEYBOARD_Z_SMALL,
	ADMINISTRATION_KEYBOARD_0,
	ADMINISTRATION_KEYBOARD_1,
	ADMINISTRATION_KEYBOARD_2,
	ADMINISTRATION_KEYBOARD_3,
	ADMINISTRATION_KEYBOARD_4,
	ADMINISTRATION_KEYBOARD_5,
	ADMINISTRATION_KEYBOARD_6,
	ADMINISTRATION_KEYBOARD_7,
	ADMINISTRATION_KEYBOARD_8,
	ADMINISTRATION_KEYBOARD_9,
	ADMINISTRATION_KEYBOARD__,
	
	//Setzen des ListedType
	ADMINISTRATION_SETLISTEDTYPE_ALL,
	ADMINISTRATION_SETLISTEDTYPE_WHITELIST,
	ADMINISTRATION_SETLISTEDTYPE_BLACKLIST,
	ADMINISTRATION_SETLISTEDTYPE_MEMBER,
	ADMINISTRATION_SETLISTEDTYPE_CUSTOM,
	
	ADMINISTRATION_SETSIGNSHOPNAME_OPENKEYBOARD,
	ADMINISTRATION_SETSIGNSHOPNAME_TAKEOVER,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_BLACKLIST,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_WHITELIST,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_MEMBER,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_OPENKEYBOARD_CUSTOM,
	
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_BLACKLIST, // Setzte einen Spieler für diesen Shop auf die Blackliste
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_BLACKLIST_WORLD, //Setzte einen Spieler für alle Shop in dieser Welt auf die Blackliste
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_BLACKLIST_REMOVE,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_BLACKLIST_REMOVE_WORLD,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_WHITELIST,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_WHITELIST_WORLD,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_WHITELIST_REMOVE,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_WHITELIST_REMOVE_WORLD,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_MEMBER,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_MEMBER_WORLD,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_MEMBER_REMOVE,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_MEMBER_REMOVE_WORLD,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_CUSTOM,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_CUSTOM_WORLD,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_CUSTOM_REMOVE,
	ADMINISTRATION_ADDLISTEDTYPE_PLAYER_CUSTOM_REMOVE_WORLD,
	
	//ShopGui Button Anzahl: 15
	//SHOP_ITEMSTACK_VIEW, //Einfache Ansicht des Items wie es ist. Return beim Klick.
	//SHOP_ITEMSTACK_INFO, //Zeigt alle weitere Infos, Haltbarkeit, ItemFlags etc.
	SHOP_TOGGLE_SUBSCRIBE, //Toggelt ob man dem Shop abbonieren möchte.
	SHOP_BUY_1,
	SHOP_BUY_8,
	SHOP_BUY_16,
	SHOP_BUY_32,
	SHOP_BUY_64,
	SHOP_BUY_576,
	SHOP_BUY_1728,
	SHOP_BUY_2304,
	SHOP_SELL_1,
	SHOP_SELL_8,
	SHOP_SELL_16,
	SHOP_SELL_32,
	SHOP_SELL_64,
	SHOP_SELL_576,
	SHOP_SELL_1728,
	SHOP_SELL_2304
}
