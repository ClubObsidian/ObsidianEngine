package com.clubobsidian.obsidianengine.task;

import java.util.ArrayList;

public class TaskThread extends Thread {
	
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
