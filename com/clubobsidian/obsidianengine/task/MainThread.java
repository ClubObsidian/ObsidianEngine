package com.clubobsidian.obsidianengine.task;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainThread extends Thread {
	
	private ConcurrentLinkedQueue<EngineRunnable> runnables = new ConcurrentLinkedQueue<EngineRunnable>();
	
	@Override
	public void run()
	{
		while(true)
		{
			for(EngineRunnable runnable : this.runnables)
			{
				runnable.run();
				if(!runnable.keepAlive())
				{
					runnables.remove(runnable);
				}
			}
		}
	}
	
	public void addRunnable(EngineRunnable runnable)
	{
		this.runnables.add(runnable);
	}
}
