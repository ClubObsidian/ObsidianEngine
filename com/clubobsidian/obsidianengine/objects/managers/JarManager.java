package com.clubobsidian.obsidianengine.objects.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.jar.JarInputStream;

import com.clubobsidian.obsidianengine.ObsidianEngine;

public class JarManager {
	
	private boolean standalone = true;
	
	private String getJarMain(final File jar)
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
			ObsidianEngine.getLogger().error("IOException occured while reading jar manifest, stopping...");
			e.printStackTrace();
			System.exit(1);
		} 
		
		return main;
	}

	public void loadJar(final String[] args)
	{
		if(args.length > 0)
		{
			if(args[0].toLowerCase().endsWith(".jar"))
			{
				this.standalone = false;
				ObsidianEngine.getLogger().info("Arguments found for obsidian engine, attempting to start.");
				File jar = new File(args[0]);
				this.loadJarViaLoader(jar, Arrays.copyOfRange(args, 1, args.length));	
				return;
			}
		}
		
		ObsidianEngine.getLogger().info("No main jar found to target, enabling ObsidianEngine in standalone mode.");
		this.standalone = true;
	}

	public boolean getStandalone()
	{
		return this.standalone;
	}
	
	private void loadJarViaLoader(final File jar,final String[] args)
	{
		ObsidianEngine.getLogger().info("Attempting to load jar via loader.");

		try 
		{
			Thread.currentThread().setContextClassLoader(ObsidianEngine.getClassLoader());
			ObsidianEngine.getClassLoader().addURL(jar.toURI().toURL());
			final Class<?> theClass = ObsidianEngine.getClassLoader().loadClass((this.getJarMain(jar)));
			final Method m = theClass.getMethod("main", String[].class);
			m.invoke(null, new Object[] {args});
		} 
		catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			ObsidianEngine.getLogger().info("Could not load jar, stopping...");
			e.printStackTrace();
			System.exit(1);
		}		
	}
}