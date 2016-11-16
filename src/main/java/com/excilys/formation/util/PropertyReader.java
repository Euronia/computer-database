package com.excilys.formation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	private static Properties properties;
	InputStream input ;
	
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
}
