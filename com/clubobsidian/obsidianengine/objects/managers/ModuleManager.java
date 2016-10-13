package com.clubobsidian.obsidianengine.objects.managers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.objects.module.Module;
import com.clubobsidian.obsidianengine.snakeyaml.Yaml;

public class ModuleManager {

	private File moduleFolder = new File("modules");
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	//Module format example
	//name: TestModule
	//main: me.virustotal.testmodule.TestModule
	//version: 1.0
	//author: VirusTotal
	//loadbefore: [AnotherTestModule]
	//depend: [PaperModule]
	//softdepend: [OtherTestModule]
	
	public void loadModules()
	{
		if(!this.moduleFolder.exists())
		{
			this.moduleFolder.mkdir();
		}

		for(File f : moduleFolder.listFiles())
		{
			if(f.getName() != null)
			{
				if(f.getName().toLowerCase().endsWith(".jar"))
				{
					//Crawls the jar file, finds classes associated with the module, creates a new instance via reflection
					//Going to be updated to have a module.yml
					
					
					try 
					{
						JarFile jarFile = new JarFile(f);
						Enumeration<JarEntry> entries = jarFile.entries();
						boolean hasModuleFile = false;
						String name = null;
						String mainClass = null;
						String version = null;
						String[] loadBefore = null;
						String[] dependencies = null;
						String[] softDependencies = null;
						
						while(entries.hasMoreElements())
						{
							JarEntry entry = entries.nextElement();
							String entryName = entry.getName();//.replace("/", ".");
							/*if(entryName.endsWith(".class"))
							{
								entryName = entryName.substring(0, entryName.lastIndexOf("."));
								URLClassLoader loader = new URLClassLoader(new URL[] {f.toURI().toURL()}, ObsidianEngine.class.getClassLoader());
								Thread.currentThread().setContextClassLoader(loader);
								Class<?> cl = loader.loadClass(entryName);
								Object obj = cl.newInstance();
								if(obj instanceof Module)
								{
									Module module = (Module) obj;
									try 
									{
										ModuleManager.setModuleName(module, cl.getSimpleName()); //->To be done
									} 
									catch (NoSuchFieldException | SecurityException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) 
									{
										e.printStackTrace();
									}
									module.onModulePreload();
									this.modules.add(module);
								
								}
								loader.close();
								break;
							}*/
							if(entryName.equals("module.yml"))
							{
								
								hasModuleFile = true;
								//entry.
								//Load in vars
							}
						}
						
						if(hasModuleFile)
						{
							Yaml yaml = new Yaml();
							
						}
						else
						{
							
						}
						
						jarFile.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void loadModulesRuntime()
	{
		for(Module m : this.modules)
		{
			m.onModuleEnable();
		}
	}
	
	public ArrayList<Module> getModules()
	{
		return this.modules;
	}
	
	private static void setModuleName(Module module, String name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Field field = Module.class.getDeclaredField("name");
		field.setAccessible(true);
		field.set(module, name);
	}
}
