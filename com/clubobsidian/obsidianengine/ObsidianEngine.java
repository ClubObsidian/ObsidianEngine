package com.clubobsidian.obsidianengine;

import java.lang.reflect.Field;
import java.net.URL;

import com.clubobsidian.obsidianengine.objects.BetterURLClassLoader;
import com.clubobsidian.obsidianengine.objects.MainThread;
import com.clubobsidian.obsidianengine.objects.ModuleStack;
import com.clubobsidian.obsidianengine.objects.managers.JarManager;
import com.clubobsidian.obsidianengine.objects.managers.ModuleManager;
import com.clubobsidian.obsidianengine.objects.module.Module;
import com.clubobsidian.obsidianengine.objects.module.ModuleLogger;
import com.clubobsidian.obsidianengine.objects.tasks.ReadConsoleTask;


public class ObsidianEngine {
	
	private static Module engineModule = new Module();
	private static ModuleManager moduleManager = new ModuleManager();
	private static JarManager jarManager = new JarManager();
	private static BetterURLClassLoader loader;
	private static MainThread mainThread;
	
	public static void main(final String[] args)
	{
		//for(String str : args)
		//{
		//	System.out.println(str);
		//}

		//FileConfiguration file = FileConfiguration.loadFile(new File("test.yml"));
		//System.out.println(new File("test.yml").getAbsolutePath());

		ObsidianEngine.loader = new BetterURLClassLoader(new URL[0], ObsidianEngine.class.getClassLoader());
		ObsidianEngine.setupEngineModule();
		ObsidianEngine.getLogger().info("Starting ObsidianEngine...");
		ObsidianEngine.moduleManager.loadModules();
		ObsidianEngine.moduleManager.preloadModules();
		ObsidianEngine.jarManager.loadJar(args);
		ObsidianEngine.moduleManager.enableModules();

		if(ObsidianEngine.jarManager.getStandalone())
		{
			ObsidianEngine.mainThread = new MainThread();
			ObsidianEngine.mainThread.setDaemon(false);
			ObsidianEngine.mainThread.addTask(new ReadConsoleTask());
			ObsidianEngine.mainThread.start();
		}
		
	}
	
	private static void setupEngineModule()
	{
		try 
		{
			Field name = Module.class.getDeclaredField("name");
			name.setAccessible(true);
			name.set(ObsidianEngine.engineModule, "ObsidianEngine");
		
			Field logger = Module.class.getDeclaredField("logger");
			logger.setAccessible(true);
			logger.set(ObsidianEngine.engineModule, new ModuleLogger(ObsidianEngine.engineModule));
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
	
	public static BetterURLClassLoader getClassLoader()
	{
		return ObsidianEngine.loader;
	}

	public static ModuleStack getModules()
	{
		return ObsidianEngine.moduleManager.getModules();
	}
}