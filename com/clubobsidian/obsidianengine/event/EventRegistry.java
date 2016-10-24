package com.clubobsidian.obsidianengine.event;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.obsidianengine.listener.Listener;

public class EventRegistry {

	private static ArrayList<Listener> handlers = new ArrayList<Listener>();

    public void register(Listener listener) 
    {
        EventRegistry.handlers.add(listener);
    }

    public void unregister(Listener listener)
    {
    	EventRegistry.handlers.remove(listener);
    }
    
    public List<Listener> getHandlers() 
    {
        return EventRegistry.handlers;
    }
}