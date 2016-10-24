package com.clubobsidian.obsidianengine.objects.tasks;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.objects.events.TestEvent;

public class EventTask implements Task {

	@Override
	public void call() 
	{
		ObsidianEngine.getEventDispatcher().dispatchEvent(new TestEvent());
	}
}