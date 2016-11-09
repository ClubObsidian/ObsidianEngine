package com.clubobsidian.obsidianengine.task;

public interface EngineRunnable extends Runnable {

	boolean keepAlive = false;
	
	public void run();
	
	default public EngineRunnable setKeepAlive(boolean keepAlive)
	{
		keepAlive = false;
		return this;
	}
	
	default public boolean keepAlive()
	{
		return keepAlive;
	}
}