package com.clubobsidian.obsidianengine.objects;

import com.clubobsidian.obsidianengine.annotations.EventHandler;
import com.clubobsidian.obsidianengine.objects.events.TestEvent;

public class TestListener implements Listener {
	
	@EventHandler
	public void test(TestEvent e)
	{
		System.out.println("asdf");
	}
}