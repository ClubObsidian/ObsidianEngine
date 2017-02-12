package com.clubobsidian.obsidianengine;

import java.lang.reflect.Field;
import java.net.URL;

import com.clubobsidian.obsidianengine.classloader.BetterURLClassLoader;
import com.clubobsidian.obsidianengine.command.CommandDispatcher;
import com.clubobsidian.obsidianengine.event.EventManager;
import com.clubobsidian.obsidianengine.event.TestEvent;
import com.clubobsidian.obsidianengine.listener.TestListener;
import com.clubobsidian.obsidianengine.manager.JarManager;
import com.clubobsidian.obsidianengine.manager.ModuleManager;
import com.clubobsidian.obsidianengine.module.Module;
import com.clubobsidian.obsidianengine.module.ModuleLogger;
import com.clubobsidian.obsidianengine.module.ModuleStack;
import com.clubobsidian.obsidianengine.scheduler.Scheduler;
import com.clubobsidian.obsidianengine.threads.ConsoleThread;
import com.clubobsidian.obsidianengine.user.ConsoleUser;


public class ObsidianEngine {
	
	private static Module engineModule = new Module();
	private static ModuleManager moduleManager = new ModuleManager();
	private static JarManager jarManager = new JarManager();
	private static BetterURLClassLoader loader;
	private static EventManager eventDispatcher;
	private static ConsoleUser consoleUser = new ConsoleUser();
	private static CommandDispatcher commandDispatcher = new CommandDispatcher();
	private static Scheduler scheduler = new Scheduler();
	
	public static String asdf = "sometestvalue";
	
	public static void main(final String[] args)
	{
		ObsidianEngine.loader = new BetterURLClassLoader(new URL[0], ObsidianEngine.class.getClassLoader());
		ObsidianEngine.eventDispatcher = new EventManager();
		ObsidianEngine.setupEngineModule();
		ObsidianEngine.getLogger().info("Starting ObsidianEngine...");
		ObsidianEngine.moduleManager.preLoadModules();
		ObsidianEngine.jarManager.checkStandAlone(args);
		ObsidianEngine.moduleManager.loadModules();
		ObsidianEngine.jarManager.loadJar(args);
		ObsidianEngine.moduleManager.enableModules();

		if(ObsidianEngine.jarManager.getStandalone())
		{
			ConsoleThread consoleThread = new ConsoleThread();
			consoleThread.setDaemon(false);
			consoleThread.start();
		}
		
		if(args.length > 0)
		{
			if(args[0].equals("test"))
			{
				ObsidianEngine.getEventManager().register(new TestListener());
				for(int i = 0; i < 1000000; i++)
				{
					ObsidianEngine.getEventManager().dispatchEvent(new TestEvent());
				}
			}
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
	
	public static EventManager getEventManager()
	{
		return ObsidianEngine.eventDispatcher;
	}
	
	public static ConsoleUser getConsoleUser()
	{
		return ObsidianEngine.consoleUser;
	}
	
	public static CommandDispatcher getCommandDispatcher() 
	{
		return ObsidianEngine.commandDispatcher;
	}
	
	public static Scheduler getScheduler()
	{
		return ObsidianEngine.scheduler;
	}
	
	public static void tryToinjectClass(String clazz)
	{
		//TODO
	}
}