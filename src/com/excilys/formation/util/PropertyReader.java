package com.excilys.formation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	private Properties properties;
	InputStream input ;
	
	public PropertyReader(InputStream inputStream)
	{
		this.input = inputStream;
		this.properties = new Properties();
	}
	
	public Properties readProperties ()
	{
		if (input == null)
		{
			return properties;
		}
		try {
		properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return properties;
	}
}
