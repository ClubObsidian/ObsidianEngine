package com.clubobsidian.obsidianengine.module;

import java.util.Stack;

public class ModuleStack extends Stack<Module> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2330746198534891930L;

	public boolean insertBefore(String beforeModule, Module moduleToInsert)
	{
		int index = -1;
		for(int i = 0; i < this.size(); i++)
		{
			if(this.get(i).getName().equals(beforeModule))
			{
				index = i;
				this.insertElementAt(moduleToInsert, index);
				break;
			}
		}
		return index != -1;
	}	
	
	public boolean insertAfter(String afterModule, Module moduleToInsert)
	{
		int index = -1;
		for(int i = 0; i < this.size(); i++)
		{
			if(this.get(i).getName().equals(afterModule))
			{
				index = i + 1;
				this.insertElementAt(moduleToInsert, index);
				break;
			}
		}
		return index != -1;
	}
	
	public int getIndexOfModule(String module)
	{
		for(int i = 0; i < this.size(); i++)
		{
			if(this.get(i).getName().equals(module))
			{
				return i;
			}
		}
		return -1;
	}
}