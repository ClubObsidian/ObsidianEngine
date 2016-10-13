package com.clubobsidian.obsidianengine.objects.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.clubobsidian.obsidianengine.snakeyaml.Yaml;

public class FileConfiguration {

	/* Class: FileConfiguration
	 * Description: Wrapper class for SnakeYaml.
	 * All values are immutable as they are cloned on access.
	 * Class provides accessor methods for different object types.
	 */
	
	private Yaml yaml = new Yaml();
	protected HashMap<String, Object> values = new HashMap<String, Object>();
	
	@SuppressWarnings("unchecked")
	public static FileConfiguration loadStream(InputStream stream)
	{
		FileConfiguration file = new FileConfiguration();
		Yaml yaml = file.getYaml();
		Map<String, Object> values = (Map<String, Object>)(yaml.load(stream));
		for(String key : values.keySet())
		{
			Object value = values.get(key);
			if(value instanceof ArrayList)
			{
				ArrayList<?> list = (ArrayList<?>) value;
				Object[] ar = list.toArray(new Object[list.size()]);
				file.values.put(key, ar);
			}
			else if(value instanceof String)
			{
				file.values.put(key, (((String) value).toCharArray()));
			}
			else
			{
				file.values.put(key, value);
			}
		}
		try 
		{
			stream.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return file;
	}
	
	public static FileConfiguration loadFile(File file)
	{
		try 
		{
			return FileConfiguration.loadStream(new FileInputStream(file));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
		
	protected Yaml getYaml()
	{
		return this.yaml;
	}
	
	public String[] getStringArray(String key)
	{
		Object[] objs = new CloneWrapper<Object[]>().clone(this.values.get(key));
		String[] strs = new String[objs.length];
		for(int i = 0; i < strs.length; i++)
		{
			strs[i] = (String) objs[i];
		}
		return strs;
	}

	public ArrayList<String> getStringList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.values.get(key));
		ArrayList<String> list = new ArrayList<String>();
		for(Object obj : ar)
		{
			list.add((String) obj);
		}
		return list;
	}
	
	public ArrayList<Integer> getIntegerList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.values.get(key));
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(Object obj : ar)
		{
			list.add((Integer) obj);
		}
		return list;
	}
	
	public ArrayList<Double> getDoubleList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.values.get(key));
		ArrayList<Double> list = new ArrayList<Double>();
		for(Object obj : ar)
		{
			list.add((Double) obj);
		}
		return list;
	}
	
	public String getString(String key)
	{
		
		return String.valueOf(new CloneWrapper<char[]>().clone(this.values.get(key)));
	}
	
	public Boolean getBoolean(String key)
	{
		return new CloneWrapper<Boolean>().clone(this.values.get(key));
	}
	
	public Integer getInt(String key)
	{
		return new CloneWrapper<Integer>().clone(this.values.get(key));
	}
	
	public Double getDouble(String key)
	{
		return new CloneWrapper<Double>().clone(this.values.get(key));
	}
	
	public Object get(String key)
	{
		return new CloneWrapper<Object>().clone((this.values.get(key)));
	}
	
	public boolean exists(String key)
	{
		return this.values.containsKey(key);
	}
	
	public void set(String key, Object object)
	{
		this.values.put(key, object);
	}
	
	private class CloneWrapper<T>
	{
		@SuppressWarnings("unchecked")
		protected T clone(Object obj)
		{
			try 
			{
				Method cloneMethod = obj.getClass().getMethod("clone");
				cloneMethod.setAccessible(true);
				return (T) cloneMethod.invoke(obj);
			} 
			catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
			{
				e.printStackTrace();
				return null;
			}
			catch(NoSuchMethodException ex)
			{
				//Work around for interfaces
				return (T) obj;
			}
		}
	}
}