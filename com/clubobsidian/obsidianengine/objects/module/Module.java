package com.clubobsidian.obsidianengine.objects.module;

import java.io.File;
import java.io.IOException;

import com.clubobsidian.obsidianengine.objects.yaml.FileConfiguration;

public class Module
{
	private ModuleLogger logger;
	private String name;
	private File file;
	private String main;
	private String version;
	private String[] authors;
	private String[] loadBefore;
	private String[] dependencies;
	private String[] softDependencies;
	private static File moduleFolder = new File("modules");
	
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
	
	public FileConfiguration getConfig()
	{
		return FileConfiguration.loadFile(new File(Module.moduleFolder.getAbsoluteFile(), this.name + File.separatorChar + "config.yml"));
	}
	
	public void saveDefaultConfig()
	{
		if(!Module.moduleFolder.exists())
		{
			try 
			{
				Module.moduleFolder.createNewFile();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		File moduleFolder = new File(Module.moduleFolder.getAbsolutePath(), this.name);
		if(!moduleFolder.exists())
		{
			moduleFolder.mkdir();
		}
		
		File configFile = new File(moduleFolder.getAbsolutePath(), "config.yml");
		this.getClass().getResourceAsStream("/config.yml");
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public File getFile()
	{
		return this.file;
	}
	
	public String getMain()
	{
		return this.main;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public String[] getAuthors()
	{
		return this.authors;
	}
	
	public String[] getLoadBefore()
	{
		return this.loadBefore;
	}
	
	public String[] getDependencies()
	{
		return this.dependencies;
	}
	
	public String[] getSoftDependencies()
	{
		return this.softDependencies;
	}
}