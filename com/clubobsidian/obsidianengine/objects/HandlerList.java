package com.clubobsidian.obsidianengine.objects;

import java.util.List;

public class HandlerList {

	private List<Listener> registeredListeners;
	
	public List<Listener> getRegisteredListeners()
	{
		return this.registeredListeners;
	}
	
}
