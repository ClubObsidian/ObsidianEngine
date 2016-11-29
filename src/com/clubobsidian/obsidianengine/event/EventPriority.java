package com.clubobsidian.obsidianengine.event;

public enum EventPriority {

	LOWEST(0), LOW(1), NORMAL(2), HIGH(3), HIGHEST(4), MONITOR(5);
	
	private int priority = 0;
	EventPriority(int priority)
	{
		this.priority = priority;
	}
	
	public int getPriority()
	{
		return this.priority;
	}
}
