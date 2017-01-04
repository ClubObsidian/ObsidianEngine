package com.clubobsidian.obsidianengine.task;

public class RunnableWrapper {

	private Runnable runnable;
	private boolean keepAlive;
	
	public RunnableWrapper(Runnable runnable, boolean keepAlive)
	{
		this.runnable = runnable;
		this.keepAlive = keepAlive;
	}
	
	public Runnable getRunnable()
	{
		return this.runnable;
	}
	
	public boolean getKeepAlive()
	{
		return this.keepAlive;
	}	
}