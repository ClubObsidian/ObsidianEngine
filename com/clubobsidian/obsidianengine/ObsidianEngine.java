package com.clubobsidian.obsidianengine;

import java.lang.reflect.Field;
import java.net.URL;

import com.clubobsidian.obsidianengine.classloader.BetterURLClassLoader;
import com.clubobsidian.obsidianengine.event.EventDispatcher;
import com.clubobsidian.obsidianengine.event.EventRegistry;
import com.clubobsidian.obsidianengine.manager.JarManager;
import com.clubobsidian.obsidianengine.manager.ModuleManager;
import com.clubobsidian.obsidianengine.module.Module;
import com.clubobsidian.obsidianengine.module.ModuleLogger;
import com.clubobsidian.obsidianengine.module.ModuleStack;
import com.clubobsidian.obsidianengine.task.ConsoleThread;
import com.clubobsidian.obsidianengine.task.EventTask;
import com.clubobsidian.obsidianengine.task.TaskThread;


public class ObsidianEngine {
	
	private static Module engineModule = new Module();
	private static ModuleManager moduleManager = new ModuleManager();
	private static JarManager jarManager = new JarManager();
	private static BetterURLClassLoader loader;
	private static TaskThread taskThread;
	private static EventDispatcher eventDispatcher;
	private static EventRegistry eventRegistry;
	
	public static void main(final String[] args)
	{
		//for(String str : args)
		//{
		//	System.out.println(str);
		//}

		//FileConfiguration file = FileConfiguration.loadFile(new File("test.yml"));
		//System.out.println(new File("test.yml").getAbsolutePath());

		ObsidianEngine.loader = new BetterURLClassLoader(new URL[0], ObsidianEngine.class.getClassLoader());
		
		
		ObsidianEngine.eventRegistry = new EventRegistry();
		ObsidianEngine.eventDispatcher = new EventDispatcher();
		ObsidianEngine.setupEngineModule();
		ObsidianEngine.getLogger().info("Starting ObsidianEngine...");
		ObsidianEngine.moduleManager.preLoadModules();
		ObsidianEngine.jarManager.checkStandAlone(args);
		ObsidianEngine.moduleManager.loadModules();
		ObsidianEngine.jarManager.loadJar(args);
		ObsidianEngine.moduleManager.enableModules();

		ObsidianEngine.taskThread = new TaskThread();
		if(ObsidianEngine.jarManager.getStandalone())
		{
			new ConsoleThread().start();
			ObsidianEngine.taskThread.setDaemon(false);
			//ObsidianEngine.taskThread.addTask(new EventTask());
			//ObsidianEngine.eventRegistry.register(new TestListener());
		}
		ObsidianEngine.taskThread.addTask(new EventTask());
		ObsidianEngine.taskThread.start();
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
	
	public static EventDispatcher getEventDispatcher()
	{
		return ObsidianEngine.eventDispatcher;
	}
	
	public static EventRegistry getEventRegistry()
	{
		return ObsidianEngine.eventRegistry;
	}
	
	public static void tryToinjectClass(String clazz)
	{
		//TODO
	}
}