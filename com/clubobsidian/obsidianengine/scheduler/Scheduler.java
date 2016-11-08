package com.clubobsidian.obsidianengine.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.clubobsidian.obsidianengine.ObsidianEngine;

public class Scheduler {

	private ExecutorService service = Executors.newCachedThreadPool();

	public void callSynchronously(Runnable runnable)
	{
		ObsidianEngine.getMainThread().addRunnable(runnable);
	}
	
	public Future<?> scheduleSyncDelayedTask(final Runnable runnable, Long delay)
	{
		return this.service.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try 
				{
					TimeUnit.MILLISECONDS.sleep(delay);
					callSynchronously(runnable);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Future<?> scheduleSyncRepeatingTask(final Runnable runnable, Long delayStarting, Long delayBetweenExecutions)
	{
		return this.service.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try 
				{
					TimeUnit.MILLISECONDS.sleep(delayStarting);
					callSynchronously(runnable);
					TimeUnit.MILLISECONDS.sleep(delayBetweenExecutions);
					this.run();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Future<?> scheduleAsyncTask(final Runnable runnable)
	{
		return this.service.submit(runnable);
	}

	public Future<?> scheduleAsyncDelayedTask(final Runnable runnable, Long delay)
	{
		return this.service.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try 
				{
					TimeUnit.MILLISECONDS.sleep(delay);
					runnable.run();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	public Future<?> scheduleAsyncDelayedTask(final Runnable runnable, Long delay, Long delayBetweenExecutions)
	{
		return this.service.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try 
				{
					TimeUnit.MILLISECONDS.sleep(delay);
					runnable.run();
					TimeUnit.MILLISECONDS.sleep(delayBetweenExecutions);
					this.run();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}