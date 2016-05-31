package me.virustotal.obsidianengine.objects;

public class Module
{
	private String name;
	public Module()
	{
		this.onModulePreload();
	}
	
	public void onModulePreload()
	{
		
	}
	
	public void onModuleEnable() 
	{
		
	}

	public void onModuleDisable() 
	{
		
	}
	
	public String getName()
	{
		return this.name;
	}
}