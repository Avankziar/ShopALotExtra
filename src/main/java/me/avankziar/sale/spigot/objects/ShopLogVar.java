package main.java.me.avankziar.sale.spigot.objects;

public class ShopLogVar
{
	public int shopID;
	public String shopname;
	public String currency;
	public long itemAmount;
	public double costTotal;
	
	public ShopLogVar(int shopID, String shopname, String currency, long itemAmount, double costPerItem)
	{
		this.shopID = shopID;
		this.shopname = shopname;
		this.currency = currency;
		this.itemAmount = itemAmount;
		this.costTotal = itemAmount*costPerItem;
	}
	
	public ShopLogVar add(long itemAmount, double costPerItem)
	{
		this.itemAmount += itemAmount;
		this.costTotal += itemAmount*costPerItem;
		return this;
	}
}