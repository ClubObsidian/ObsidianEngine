package com.clubobsidian.obsidianengine.objects.tasks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TestTaskThread extends Thread {
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	@Override
	public void run()
	{
		double starting = System.currentTimeMillis();
		int amt = 1000000;
		for(int i = 0; i < amt; i++)
		{
			for(Task task : tasks)
			{
				task.call();
			}
		}
		double ending = System.currentTimeMillis();
		System.out.println("Time for " + amt + " iterations: " + (new BigDecimal((ending - starting) / 1000.0)).setScale(2, RoundingMode.UP) + "s");
		try 
		{
			Thread.sleep(1000L);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		this.run();
	}
	
	public void addTask(Task task)
	{
		this.tasks.add(task);
	}
}