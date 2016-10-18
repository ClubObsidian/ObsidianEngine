package com.clubobsidian.obsidianengine.objects.module;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.clubobsidian.obsidianengine.apachecommons.logging.impl.SimpleLog;

public class ModuleLogger implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5591488545151958082L;

	private Object logger;
	private String name;
	
	public ModuleLogger(Module module) 
	{
		this.logger = new SimpleLog(module.getName());
		this.name = module.getName();
	}
	
	public void setLogger(Object logger)
	{
		this.logger = logger;
	}
	
	public String getModuleName()
	{
		return this.name;
	}
	
	public void debug(Object message)
	{
		if(this.logger instanceof SimpleLog)
		{
			((SimpleLog)this.logger).debug(message);
		}
		else if(this.logger instanceof Logger)
		{
			((Logger)this.logger).log(Level.FINEST, message.toString());
		}
		else
		{
			try 
			{
				throw new Exception("That logger is not supported");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void error(Object message)
	{
		if(this.logger instanceof SimpleLog)
		{
			((SimpleLog)this.logger).error(message);
		}
		else if(this.logger instanceof Logger)
		{
			((Logger)this.logger).log(Level.WARNING, message.toString());
		}
		else
		{
			try 
			{
				throw new Exception("That logger is not supported");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void fatal(Object message)
	{
		if(this.logger instanceof SimpleLog)
		{
			((SimpleLog)this.logger).fatal(message);
		}
		else if(this.logger instanceof Logger)
		{
			((Logger)this.logger).log(Level.SEVERE, message.toString());
		}
		else
		{
			try 
			{
				throw new Exception("That logger is not supported");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void info(Object message)
	{
		if(this.logger instanceof SimpleLog)
		{
			((SimpleLog)this.logger).info(message);
		}
		else if(this.logger instanceof Logger)
		{
			((Logger)this.logger).log(Level.INFO, message.toString());
		}
		else
		{
			try 
			{
				throw new Exception("That logger is not supported");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}