package com.clubobsidian.obsidianengine.task;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.event.TestEvent;

public class EventRunnable implements Runnable {

	@Override
	public void run() 
	{
		ObsidianEngine.getEventDispatcher().dispatchEvent(new TestEvent());
	}
}