package com.clubobsidian.obsidianengine.event;

import com.clubobsidian.obsidianengine.event4j.EventBus;
import com.clubobsidian.obsidianengine.event4j.EventExecutor;

public class EventManager {

	private final EventBus<Event, Object> bus;
	public EventManager()
	{
		this.bus = EventBus.builder()
				.eventClass(Event.class)
				.executorFactory(EventExecutor.Factory.getDefault())
				.build();
	}
	
	public void dispatchEvent(final Event event)
	{
		this.bus.fire(event);
	}
	
	public void register(Object listener)
	{
		this.bus.register(listener);
	}
	
	public void unregister(Object listener)
	{
		this.bus.unregister(listener);
	}
}