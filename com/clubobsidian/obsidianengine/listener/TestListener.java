package com.clubobsidian.obsidianengine.listener;

import com.clubobsidian.obsidianengine.event.EventHandler;
import com.clubobsidian.obsidianengine.event.TestEvent;

public class TestListener implements Listener {
	
	@EventHandler
	public void test(TestEvent e)
	{
		//System.out.println("test");
	}
}