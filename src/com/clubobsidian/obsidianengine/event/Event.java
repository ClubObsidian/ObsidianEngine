package com.clubobsidian.obsidianengine.event;

public class Event {

	private String name;
	private boolean cancelled = false;
	
	public String getName()
	{
		if(this.name == null)
		{
			this.name = this.getClass().getSimpleName();
		}
		return this.name;
	}
	
	public boolean isCancelled()
	{
		return this.cancelled;
	}
	
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Event))
			return false;
		
		Event event = (Event) obj;
		return event.getName().equals(this.getName());
	}
}