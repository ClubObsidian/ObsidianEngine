package com.clubobsidian.obsidianengine.task;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MainThread extends Thread {
	
	private ConcurrentLinkedQueue<RunnableWrapper> runnables = new ConcurrentLinkedQueue<RunnableWrapper>();
	
	@Override
	public void run()
	{
		while(true)
		{
			for(RunnableWrapper runnable : this.runnables)
			{
				runnable.getRunnable().run();
				if(!runnable.getKeepAlive())
				{
					this.runnables.remove(runnable);
				}
			}
		}
	}
	
	public void addRunnable(Runnable runnable, boolean keepAlive)
	{
		this.runnables.add(new RunnableWrapper(runnable, keepAlive));
	}
}
