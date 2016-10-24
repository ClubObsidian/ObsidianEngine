package com.clubobsidian.obsidianengine.event;

public class Event {

	private String name;
	
	public String getName()
	{
		if(this.name == null)
		{
			this.name = this.getClass().getSimpleName();
		}
		return this.name;
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