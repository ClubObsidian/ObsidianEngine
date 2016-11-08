package com.clubobsidian.obsidianengine.task;

import java.util.ArrayList;

public class MainThread extends Thread {
	
	private ArrayList<Runnable> runnables = new ArrayList<Runnable>();
	
	@Override
	public void run()
	{
		while(true)
		{
			for(Runnable runnable : this.runnables)
			{
				runnable.run();
			}
		}
	}
	
	public void addRunnable(Runnable runnable)
	{
		this.runnables.add(runnable);
	}
}
