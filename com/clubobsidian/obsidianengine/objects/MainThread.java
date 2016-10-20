package com.clubobsidian.obsidianengine.objects;

import java.util.ArrayList;

public class MainThread extends Thread {
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	@Override
	public void run()
	{
		while(true)
		{
			for(Task task : tasks)
			{
				task.call();
			}
		}
	}
	
	public void addTask(Task task)
	{
		this.tasks.add(task);
	}
}
