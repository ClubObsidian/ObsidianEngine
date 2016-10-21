package com.clubobsidian.obsidianengine.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.clubobsidian.obsidianengine.annotations.EventHandler;

public class EventDispatcher {

	public void dispatchEvent(final Event event)
	{
		for (Listener handler : EventRegistryHandler.getHandlers()) 
		{
			Method[] methods = handler.getClass().getMethods();
			for (int i = 0; i < methods.length; ++i) 
			{
				EventHandler eventHandler = methods[i].getAnnotation(EventHandler.class);
				if (eventHandler != null) 
				{
					Class<?>[] methodParams = methods[i].getParameterTypes();

					if (methodParams.length >= 1)
					{
						if (event.getClass().equals(methodParams[0]))
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