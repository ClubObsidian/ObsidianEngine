package com.clubobsidian.obsidianengine.objects;

import java.util.ArrayList;
import java.util.List;

public class EventRegistryHandler {

	private static ArrayList<Listener> handlers = new ArrayList<Listener>();

    public static void register(Listener listener) 
    {
        EventRegistryHandler.handlers.add(listener);
    }

    public static void unregister(Listener listener)
    {
    	EventRegistryHandler.handlers.remove(listener);
    }
    
    public static List<Listener> getHandlers() 
    {
        return EventRegistryHandler.handlers;
    }
}