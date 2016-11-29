package com.clubobsidian.obsidianengine.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.clubobsidian.obsidianengine.ObsidianEngine;

public class Scheduler {

	private ExecutorService service = Executors.newCachedThreadPool();

	public void callSynchronously(final Runnable runnable)
	{
		ObsidianEngine.getMainThread().addRunnable(runnable, false);
	}
	
	public Future<?> scheduleSyncDelayedTask(final Runnable runnable, final Long delay)
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
			private boolean ran = false;
			
			@Override
			public void run()
			{
				try 
				{
					if(!this.ran)
					{
						TimeUnit.MILLISECONDS.sleep(delayStarting);
						this.ran = true;
					}

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

	public Future<?> scheduleAsyncDelayedTask(final Runnable runnable, Long delayStarting)
	{
		return this.service.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try 
				{
					TimeUnit.MILLISECONDS.sleep(delayStarting);
					runnable.run();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public Future<?> scheduleAsyncRepeatingTask(final Runnable runnable, Long delay, Long delayBetweenExecutions)
	{
		return this.service.submit(new Runnable()
		{
			private boolean ran = false;
			
			@Override
			public void run()
			{
				try 
				{
					if(!this.ran)
					{
						TimeUnit.MILLISECONDS.sleep(delay);
						this.ran = true;
					}
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