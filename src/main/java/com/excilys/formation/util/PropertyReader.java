package com.excilys.formation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	private static Properties properties;
	static InputStream input ;
	private static final PropertyReader PROPERTYREADER ;
	
	static {
	    PROPERTYREADER = new PropertyReader();
	}
	
	public static PropertyReader getInstance()
	{
	    return PROPERTYREADER;
	}
	
	 public static Properties readProperties(InputStream pInputStream) {
	        if (pInputStream == null) {
	            return null;
	        }
	        properties = new Properties();
	        try {
	            properties.load(pInputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (pInputStream != null) {
	                try {
	                    pInputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return properties;
	    }
	 
	 public Properties readProperties(String pInputStream) {
         input = getClass().getClassLoader().getResourceAsStream(pInputStream);
	     
	     if (input == null) {
             return null;
         }
         properties = new Properties();
         try {
             properties.load(input);
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             if (pInputStream != null) {
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
