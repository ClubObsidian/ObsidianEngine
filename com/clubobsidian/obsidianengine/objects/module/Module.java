package com.clubobsidian.obsidianengine.objects.module;

public class Module
{
	private String name;
	private ModuleLogger logger;
	private String version;
	private String[] authors;
	
	public void onModulePreload()
	{
		
	}
	
	public void onModuleEnable() 
	{
		
	}

	public void onModuleDisable() 
	{
		
	}
	
	public ModuleLogger getLogger()
	{
		if(this.logger == null)
			this.logger = new ModuleLogger(this);
		
		return this.logger;
	}
	
	private void setName(String name)
	{
		this.name = name;
	}
	
	private void setVersion(String version)
	{
		this.version = version;
	}
	
	private void setAuthors(String... authors)
	{
		this.authors = authors;
	}
	
	public String getName()
	{
		return this.name;
	}
}