package com.clubobsidian.obsidianengine.objects.managers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.objects.ModuleStack;
import com.clubobsidian.obsidianengine.objects.module.Module;
import com.clubobsidian.obsidianengine.objects.yaml.FileConfiguration;

public class ModuleManager {

	private File moduleFolder = new File("modules");
	private ModuleStack modules = new ModuleStack();
	
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
					String simpleName = f.getName().replace(".jar", "");
					
					try 
					{
						JarFile jarFile = new JarFile(f);
						Enumeration<JarEntry> entries = jarFile.entries();
						InputStream moduleStream = null;
						String name = null;
						String mainClass = null;
						String version = null;
						String[] authors = null;
						String[] loadBefore = null;
						String[] dependencies = null;
						String[] softDependencies = null;
						ArrayList<String> moduleEntries = new ArrayList<String>();
						
						while(entries.hasMoreElements())
						{
							ZipEntry entry = entries.nextElement();
							String entryName = entry.getName().replace("/", ".");
							if(entryName.contains(".class"))
							{
								entryName = entryName.replace(".class", "");
							}
						
							moduleEntries.add(entryName);
							if(entryName.equals("module.yml"))
							{
								moduleStream = jarFile.getInputStream(entry);
							}
						}
						
						if(moduleStream != null)
						{
							FileConfiguration moduleYml = FileConfiguration.loadStream(moduleStream);
							
							if(moduleYml.exists("name"))
							{
								name = moduleYml.getString("name");
							}
							else
							{
								ObsidianEngine.getLogger().fatal("Module " + simpleName + " does not have a name set in the module configuration, the module will not be loaded in!");
								continue;
							}
							
							if(moduleYml.exists("main"))
							{
								mainClass = moduleYml.getString("main");
								if(!moduleEntries.contains(mainClass))
								{
									ObsidianEngine.getLogger().fatal("The main class" + mainClass + " for module " + name + " does not exist, the module will not be loaded in!");
									continue;
								}
							}
							else
							{
								ObsidianEngine.getLogger().fatal("Module " + name + " does not have a main class set in the module configuration, the module will not be loaded in!");
								continue;
							}
							
							if(moduleYml.exists("version"))
							{
								version = moduleYml.getString("version");
							}
							else
							{
								ObsidianEngine.getLogger().fatal("Module " + name + " does not have a version set in the module configuration, the module will not be loaded in!");
								continue;
							}
							
							if(moduleYml.exists("authors"))
							{
								authors = moduleYml.getStringArray("authors");
							}
							else
							{
								authors = new String[0];
							}
							
							if(moduleYml.exists("loadbefore"))
							{
								loadBefore = moduleYml.getStringArray("loadbefore");
							}
							else
							{
								loadBefore = new String[0];
							}
							
							if(moduleYml.exists("depend"))
							{
								moduleYml.getStringArray("depend");
							}
							else
							{
								dependencies = new String[0];
							}
							
							if(moduleYml.exists("softdepend"))
							{
								softDependencies = moduleYml.getStringArray("softdepend");
							}
							else
							{
								softDependencies = new String[0];
							}
							
							Module module = new Module();
							ModuleManager.setField(module, "name", name);
							ModuleManager.setField(module, "file", f);
							ModuleManager.setField(module, "main", mainClass);
							ModuleManager.setField(module, "version", version);
							ModuleManager.setField(module, "authors", authors);
							ModuleManager.setField(module, "loadBefore", loadBefore);
							ModuleManager.setField(module, "dependencies", dependencies);
							ModuleManager.setField(module, "softDependencies", softDependencies);
							this.modules.add(module);
							moduleStream.close();
						}
						else
						{
							ObsidianEngine.getLogger().fatal("Module " + simpleName + " does not have a module configuration file, the module will not be loaded in!");
						}
						
						jarFile.close();
					} 
					catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		this.orderModules();
	}

	private void orderModules()
	{
		Iterator<Module> iterator = this.modules.iterator();
		while(iterator.hasNext())
		{
			Module mod = iterator.next();
			String[] loadBefore = mod.getLoadBefore();
			String[] dependencies = mod.getDependencies();
			String[] softDependencies = mod.getSoftDependencies();
			
			int indexOfCurrentModule = this.modules.getIndexOfModule(mod.getName());
			if(loadBefore.length > 0)
			{
				for(String str : loadBefore)
				{
					int indexOfFindModule = this.modules.getIndexOfModule(str);
					
					if(indexOfFindModule != -1 && indexOfCurrentModule > indexOfFindModule) //If module is found and index is greater than the searched module
					{
						this.modules.insertBefore(str, mod);
					}
				}
			}
			
			if(dependencies.length > 0)
			{
				for(String str : dependencies)
				{
					int indexOfFindModule = this.modules.getIndexOfModule(str);
					if(indexOfFindModule != -1 && indexOfCurrentModule < indexOfFindModule) //If the module is found and the index is less than the searched module
					{
						this.modules.insertAfter(str, mod);
					}
					else if(indexOfFindModule == -1)
					{
						ObsidianEngine.getLogger().fatal("Cannot load module " + mod.getName() + " because the dependency " + str + " is not found!");
						iterator.remove(); //Remove module from stack since it is no longer in use, may want to switch to disabled state later
					}
				}
			}
			
			if(softDependencies.length > 0)
			{
				for(String str : softDependencies)
				{
					int indexOfFindModule = this.modules.getIndexOfModule(str);
					if(indexOfFindModule != -1 && indexOfCurrentModule < indexOfFindModule) //If the module is found and the index is less than the searched module
					{
						this.modules.insertAfter(str, mod);
					}
					//Like dependencies but do not fail
				}
			}
		}
	}
	
	public void preloadModules()
	{
		for(Module m : this.modules)
		{	
			try
			{
				String name = m.getName();
				System.out.println(name);
				File file = m.getFile();
				String mainClass = m.getMain();
				String version = m.getVersion();
				String[] authors = m.getAuthors();
				String[] loadBefore = m.getLoadBefore();
				String[] dependencies = m.getDependencies();
				String[] softDependencies = m.getSoftDependencies();

				URLClassLoader loader = new URLClassLoader(new URL[] {file.toURI().toURL()}, ObsidianEngine.class.getClassLoader());
				Thread.currentThread().setContextClassLoader(loader);
				Class<?> cl = loader.loadClass(mainClass);
				Object obj = cl.newInstance();
				Module module = null;
				if(obj instanceof Module)
				{
					module = (Module) obj;
				}
				
				ModuleManager.setField(module, "name", name);
				ModuleManager.setField(module, "file", file);
				ModuleManager.setField(module, "main", mainClass);
				ModuleManager.setField(module, "version", version);
				ModuleManager.setField(module, "authors", authors);
				ModuleManager.setField(module, "loadBefore", loadBefore);
				ModuleManager.setField(module, "dependencies", dependencies);
				ModuleManager.setField(module, "softDependencies", softDependencies);
				m = module;
				module.onModulePreload();
			}
			catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | InstantiationException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void enableModules()
	{
		for(Module m : this.modules)
		{
			m.onModuleEnable();
		}
	}
	
	public ModuleStack getModules()
	{
		return this.modules;
	}
	
	private static void setField(Module module, String fieldName, Object fieldValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Field field = Module.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(module, fieldValue);
	}
}
