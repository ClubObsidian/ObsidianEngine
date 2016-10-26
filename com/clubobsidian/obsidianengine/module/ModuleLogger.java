package com.clubobsidian.obsidianengine.module;

import com.clubobsidian.obsidianengine.apachecommons.logging.impl.SimpleLog;

public class ModuleLogger extends SimpleLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5591488545151958082L;
	
	public ModuleLogger(Module module) 
	{
		super(module.getName());
	}	
}