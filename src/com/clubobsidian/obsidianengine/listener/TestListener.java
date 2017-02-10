package com.clubobsidian.obsidianengine.listener;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.event.EventHandler;
import com.clubobsidian.obsidianengine.event.TestEvent;

public class TestListener implements Listener {
	
	private Long start = -1L;
	private int fired = 0;
	
	@EventHandler
	public void test(TestEvent e)
	{
		this.fired += 1;
		System.out.println("Fired: " + this.fired);
		if(this.fired == 1)
		{
			this.start = System.currentTimeMillis();
		}
		else
		{
			this.fired += 1;
			if(this.fired == 1000000)
			{
				ObsidianEngine.getLogger().info("Time for 1 million events: " + (System.currentTimeMillis() - this.start) + "ms");
			}
		}
	}
}