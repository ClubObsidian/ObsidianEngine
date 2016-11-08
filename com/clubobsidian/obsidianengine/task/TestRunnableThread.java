package com.clubobsidian.obsidianengine.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TestRunnableThread extends Thread {
	
	private ArrayList<Runnable> runnables = new ArrayList<Runnable>();
	
	@Override
	public void run()
	{
		double starting = System.currentTimeMillis();
		Long amt = 10000000000L;
		for(long i = 0; i < amt; i++)
		{
			for(Runnable task : runnables)
			{
				task.run();
			}
		}
		double ending = System.currentTimeMillis();
		System.out.println("Time for " + amt + " iterations: " + (ending - starting) + " nanoseconds");
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
}