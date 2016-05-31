package me.virustotal.obsidianengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.virustotal.obsidianengine.objects.Module;

public class ObsidianEngine {

	private static File moduleFolder = new File("modules");
	private static ArrayList<Module> modules = new ArrayList<Module>();
	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args)
	{
		ObsidianEngine.loadModules();
		ObsidianEngine.loadJar(args);
		ObsidianEngine.loadModulesRuntime();	
	}

	private static void loadModules()
	{
		if(!ObsidianEngine.moduleFolder.exists())
		{
			ObsidianEngine.moduleFolder.mkdir();
		}

		for(File f : moduleFolder.listFiles())
		{
			if(f.getName() != null)
			{
				if(f.getName().toLowerCase().endsWith(".jar"))
				{
					//Crawls the jar file, finds classes associated with the module, creates a new instance via reflection
					try 
					{
						JarFile jarFile = new JarFile(f);
						Enumeration<JarEntry> entries = jarFile.entries();
						while(entries.hasMoreElements())
						{
							JarEntry entry = entries.nextElement();
							String entryName = entry.getName().replace("/", ".");
							if(entryName.endsWith(".class"))
							{
								entryName = entryName.substring(0, entryName.lastIndexOf("."));
								URLClassLoader loader = new URLClassLoader(new URL[] {f.toURI().toURL()}, ObsidianEngine.class.getClassLoader());
								Thread.currentThread().setContextClassLoader(loader);
								Class<?> cl = loader.loadClass(entryName);
								Object obj = cl.newInstance();
								if(obj instanceof Module)
								{
									ObsidianEngine.modules.add((Module) obj);
								}
								loader.close();
							}
						}
						jarFile.close();
					} 
					catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void loadJar(final String[] args)
	{
		if(args.length > 0)
		{
			ObsidianEngine.logger.log(Level.INFO, "Arguments found for obsidian engine, attempting to start.");
			File jar = new File(args[0]);
			ObsidianEngine.loadJarViaLoader(jar, Arrays.copyOfRange(args, 1, args.length));	
		}
		else
		{
			ObsidianEngine.logger.log(Level.INFO, "Cannot enable ObsidianEngine, no arguments given stopping....");
			System.exit(1);
		}
	}

	private static void loadModulesRuntime()
	{
		for(Module m : ObsidianEngine.modules)
		{
			m.onModuleEnable();
		}
	}

	private static String getJarMain(final File jar)
	{
		String main = null;
		try 
		{
			FileInputStream fileStream = new FileInputStream(jar);
			JarInputStream  jarStream = new JarInputStream(fileStream);
			main = jarStream.getManifest().getMainAttributes().getValue("Main-Class");
			fileStream.close();
			jarStream.close();
		} 
		catch (IOException e) 
		{
			ObsidianEngine.logger.log(Level.SEVERE, "IOException occured while reading jar manifest, stopping...");
			e.printStackTrace();
			System.exit(1);
		} 
		
		return main;
	}

	private static void loadJarViaLoader(final File jar,final String[] args)
	{
		ObsidianEngine.logger.log(Level.INFO, "Attempting to load jar via loader.");

		try 
		{
			URLClassLoader jarLoader = new URLClassLoader(new URL[] {jar.toURI().toURL()}, ObsidianEngine.getClassLoader());
			Thread.currentThread().setContextClassLoader(jarLoader);
			final Class<?> theClass = Class.forName(ObsidianEngine.getJarMain(jar), true, jarLoader);
			final Method m = theClass.getMethod("main", String[].class);
			m.invoke(null, new Object[] {args});
			//jarLoader.close(); -> If you close the loader some libraries such as log4j will throw a NoClassDefFoundError 
		} 
		catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			ObsidianEngine.logger.log(Level.SEVERE, "Could not load jar, stopping...");
			e.printStackTrace();
			System.exit(1);
		}		
	}

	public static ClassLoader getClassLoader()
	{
		return ObsidianEngine.class.getClassLoader();
	}

	public static ArrayList<Module> getModules()
	{
		return ObsidianEngine.modules;
	}
}