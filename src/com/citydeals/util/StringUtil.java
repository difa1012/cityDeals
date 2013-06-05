package com.citydeals.util;

public class StringUtil {

	public static String[] splitLine(String line)
	{
		String[] split = line.split("~");			
		return split;
	}
	
}
