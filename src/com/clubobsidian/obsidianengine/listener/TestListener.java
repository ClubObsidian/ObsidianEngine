package com.clubobsidian.obsidianengine.listener;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.event.EventHandler;
import com.clubobsidian.obsidianengine.event.EventPriority;
import com.clubobsidian.obsidianengine.event.TestEvent;

public class TestListener implements Listener {
	
	private Long start = -1L;
	private int fired = 0;
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void test(TestEvent e)
	{
		this.fired += 1;
		if(this.fired == 1)
		{
			this.start = System.currentTimeMillis();
		}
		else
		{
			if(this.fired == 1000000)
			{
				ObsidianEngine.getLogger().info("Time for 1mil events: " + (System.currentTimeMillis() - this.start) + "ms");
			}
		}
	}
}