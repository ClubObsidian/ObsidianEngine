package com.clubobsidian.obsidianengine.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.listener.Listener;

public class EventDispatcher {

	public void dispatchEvent(final Event event)
	{
		for (Listener handler : ObsidianEngine.getEventRegistry().getHandlers()) 
		{
			Method[] methods = handler.getClass().getMethods();
			for (int i = 0; i < methods.length; ++i) 
			{
				EventHandler eventHandler = methods[i].getAnnotation(EventHandler.class);
				if (eventHandler != null) 
				{
					Class<?>[] params = methods[i].getParameterTypes();

					if (params.length == 1)
					{
						if (event.getClass().equals(params[0]))
						{
							try 
							{
								methods[i].invoke(handler.getClass().newInstance(), event);
							} 
							catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) 
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}