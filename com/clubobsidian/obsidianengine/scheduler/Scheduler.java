package com.clubobsidian.obsidianengine.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.task.EngineRunnable;

public class Scheduler {

	private ExecutorService service = Executors.newCachedThreadPool();

	public void callSynchronously(final EngineRunnable runnable)
	{
		ObsidianEngine.getMainThread().addRunnable(runnable.setKeepAlive(false));
	}
	
	public Future<?> scheduleSyncDelayedTask(final EngineRunnable runnable, final Long delay)
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

	public Future<?> scheduleSyncRepeatingTask(final EngineRunnable runnable, Long delayStarting, Long delayBetweenExecutions)
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

	public Future<?> scheduleAsyncDelayedTask(final EngineRunnable runnable, Long delayStarting)
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
	
	public Future<?> scheduleAsyncRepeatingTask(final EngineRunnable runnable, Long delay, Long delayBetweenExecutions)
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