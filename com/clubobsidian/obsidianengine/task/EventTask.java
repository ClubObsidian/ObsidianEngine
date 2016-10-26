package com.clubobsidian.obsidianengine.task;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.event.TestEvent;

public class EventTask implements Task {

	@Override
	public void call() 
	{
		ObsidianEngine.getEventDispatcher().dispatchEvent(new TestEvent());
	}
}