package com.clubobsidian.obsidianengine.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
			else if(value instanceof HashMap)
			{
				HashMap<String, Object> map = (HashMap<String, Object>) value;
				file.values.put(key, map);
				for(Entry<String, Object> entry : map.entrySet())
				{
					recurHashMap(map, entry.getKey(), entry.getValue());
				}
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
	
	private static void recurHashMap(Object previous, String key, Object current)
	{
		LinkedHashMap<String, Object> map = ((LinkedHashMap<String, Object>) previous);
		if(current instanceof ArrayList)
		{
			ArrayList<?> list = (ArrayList<?>) current;
			Object[] ar = list.toArray(new Object[list.size()]);
			map.put(key, ar);
		}
		else if(current instanceof String)
		{
			String str = (String) current;
			map.put(key, ((str).toCharArray()));
		}
		else if(current instanceof LinkedHashMap)
		{
			map.put(key, current);
			
			LinkedHashMap<String, Object> currentMap = (LinkedHashMap<String, Object>) current;
			
			for(Entry<String, Object> entry : currentMap.entrySet())
			{
				recurHashMap(currentMap, entry.getKey(), entry.getValue());
			}
		}
		else
		{
			map.put(key, current);
		}
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
		Object[] objs = new CloneWrapper<Object[]>().clone(this.get(key));
		String[] strs = new String[objs.length];
		for(int i = 0; i < strs.length; i++)
		{
			strs[i] = (String) objs[i];
		}
		return strs;
	}

	public ArrayList<String> getStringList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.get(key));
		ArrayList<String> list = new ArrayList<String>();
		for(Object obj : ar)
		{
			list.add((String) obj);
		}
		return list;
	}
	
	public ArrayList<Integer> getIntegerList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.get(key));
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(Object obj : ar)
		{
			list.add((Integer) obj);
		}
		return list;
	}
	
	public ArrayList<Double> getDoubleList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.get(key));
		ArrayList<Double> list = new ArrayList<Double>();
		for(Object obj : ar)
		{
			list.add((Double) obj);
		}
		return list;
	}
	
	public ArrayList<Long> getLongList(String key)
	{
		Object[] ar = new CloneWrapper<Object[]>().clone(this.get(key));
		ArrayList<Long> list = new ArrayList<Long>();
		for(Object obj : ar)
		{
			list.add((Long) obj);
		}
		return list;
	}
	
	public String getString(String key)
	{
		return (String) this.get(key);
	}
	
	public Boolean getBoolean(String key)
	{
		return new CloneWrapper<Boolean>().clone(this.get(key));
	}
	
	public Integer getInt(String key)
	{
		return new CloneWrapper<Integer>().clone(this.get(key));
	}
	
	public Double getDouble(String key)
	{
		return new CloneWrapper<Double>().clone(this.get(key));
	}
	
	public Long getLong(String key)
	{
		return new CloneWrapper<Long>().clone(this.get(key));
	}
	
	public Object get(String key)
	{
		if(key.contains("."))
		{
			String[] split = key.split("\\.");
			Object current = this.values.get(split[0]);
			for(int i = 1; i < split.length + 1; i++)
			{
				if(current instanceof LinkedHashMap)
				{
					LinkedHashMap<String, Object> currentMap = (LinkedHashMap<String,Object>) current;
					current = currentMap.get(split[i]);
				}
				else if(current instanceof char[])
				{
					String str = String.valueOf(new CloneWrapper<char[]>().clone(current));
					return str;
				}
				else
				{
					return current;
				}
			}
			return null;
		}
		else
		{
			Object obj = this.values.get(key);
			if(obj instanceof char[])
			{
				return this.getString(key);
			}
			return new CloneWrapper<Object>().clone(obj);
		}
	}
	
	public boolean exists(String key)
	{
		return this.values.containsKey(key);
	}
	
	public void set(String key, Object object)
	{
		this.values.put(key, object);
	}
	
	public Set<String> getKeys()
	{
		return new CloneWrapper<Set<String>>().clone(this.values.keySet());
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