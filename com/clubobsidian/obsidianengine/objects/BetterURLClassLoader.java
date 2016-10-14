package com.clubobsidian.obsidianengine.objects;

import java.net.URL;
import java.net.URLClassLoader;

public class BetterURLClassLoader extends URLClassLoader {
	
	public BetterURLClassLoader(URL[] urls, ClassLoader parent) 
	{
		super(urls, parent);
	}

	@Override
	public void addURL(URL url)
	{
		super.addURL(url);
	}
}
