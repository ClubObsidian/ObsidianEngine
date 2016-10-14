package com.clubobsidian.obsidianengine;

import java.lang.reflect.Field;

import com.clubobsidian.obsidianengine.objects.ModuleStack;
import com.clubobsidian.obsidianengine.objects.managers.JarManager;
import com.clubobsidian.obsidianengine.objects.managers.ModuleManager;
import com.clubobsidian.obsidianengine.objects.module.Module;
import com.clubobsidian.obsidianengine.objects.module.ModuleLogger;


public class ObsidianEngine {
	
	private static Module engineModule = new Module();
	private static ModuleManager moduleManager = new ModuleManager();
	private static JarManager jarManager = new JarManager();
	
	public static void main(String[] args)
	{
		//for(String str : args)
		//{
		//	System.out.println(str);
		//}
		
		//FileConfiguration file = FileConfiguration.loadFile(new File("test.yml"));
		//System.out.println(new File("test.yml").getAbsolutePath());
		
		ObsidianEngine.setupEngineModule();
		ObsidianEngine.getLogger().info("Starting ObsidianEngine...");
		ObsidianEngine.moduleManager.loadModules();
		ObsidianEngine.moduleManager.preloadModules();
		ObsidianEngine.jarManager.loadJar(args);
		ObsidianEngine.moduleManager.enableModules();	
	}
	
	private static void setupEngineModule()
	{
		try 
		{
			Field field = Module.class.getDeclaredField("name");
			field.setAccessible(true);
			field.set(ObsidianEngine.engineModule, "ObsidianEngine");
		} 
		catch (NoSuchFieldException | SecurityException| IllegalArgumentException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ModuleLogger getLogger()
	{
		return ObsidianEngine.engineModule.getLogger();
	}

	public static ClassLoader getClassLoader()
	{
		return ObsidianEngine.class.getClassLoader();
	}

	public static ModuleStack getModules()
	{
		return ObsidianEngine.moduleManager.getModules();
	}
}