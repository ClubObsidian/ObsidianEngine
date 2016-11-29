package com.clubobsidian.obsidianengine.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import com.clubobsidian.obsidianengine.yaml.FileConfiguration;

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
	
	public void onLoad()
	{
		
	}
	
	public void onEnable() 
	{
		
	}

	public void onDisable() 
	{
		
	}
	
	public void saveDefaultConfig(boolean replace)
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
		if(!configFile.exists() || replace) //If the config file doesn't exist or if replace is true
		{
			if(configFile.exists())
				configFile.delete();
			
			InputStream stream = this.getClass().getResourceAsStream("/config.yml");
			System.out.println(stream == null);
			try 
			{
				Files.copy(stream, configFile.toPath());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				try 
				{
					stream.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public ModuleLogger getLogger()
	{		
		return this.logger;
	}
	
	public FileConfiguration getConfig()
	{
		return FileConfiguration.loadFile(new File(Module.moduleFolder.getAbsoluteFile(), this.name + File.separatorChar + "config.yml"));
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public File getJarFile()
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