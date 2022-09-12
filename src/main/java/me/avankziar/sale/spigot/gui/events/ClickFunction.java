package main.java.me.avankziar.sale.spigot.gui.events;

public class ClickFunction
{
	private ClickFunctionType clickFunctionType;
	private String function;
	
	public ClickFunction(ClickFunctionType clickFunctionType, String function)
	{
		setClickFunctionType(clickFunctionType);
		setFunction(function);
	}

	public ClickFunctionType getClickFunctionType()
	{
		return clickFunctionType;
	}

	public void setClickFunctionType(ClickFunctionType clickFunctionType)
	{
		this.clickFunctionType = clickFunctionType;
	}

	public String getFunction()
	{
		return function;
	}

	public void setFunction(String function)
	{
		this.function = function;
	}
	
	@Override
	public String toString()
	{
		return clickFunctionType.toString()+";"+function;
	}
}
