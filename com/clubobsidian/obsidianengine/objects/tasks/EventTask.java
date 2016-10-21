package com.clubobsidian.obsidianengine.objects.tasks;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.objects.Task;
import com.clubobsidian.obsidianengine.objects.events.TestEvent;

public class EventTask implements Task {

	@Override
	public void call() 
	{
		ObsidianEngine.dispatchEvent(new TestEvent());
	}
}