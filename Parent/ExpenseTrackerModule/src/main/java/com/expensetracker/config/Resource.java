package com.expensetracker.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class Resource {

	private String fileName;
	private HashMap<String, String> bundleMap;

	Properties prop;

	public Resource(final String aFileName) {
		this.fileName = aFileName;
		prop = new Properties();

		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	    	InputStream is = classloader.getResourceAsStream(aFileName);
			//FileInputStream is = new FileInputStream(aFileName);
			
			prop.load(is);
			populateMap(prop);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateMap(Properties bundle) {
		HashMap<String, String> map = null;
		if (bundle != null) {
			/*
			 * final Enumeration<String> keys = bundle.getKeys(); String key =
			 * ""; while (keys.hasMoreElements()) { key = keys.nextElement(); if
			 * (map == null) { map = new HashMap<String, String>(); }
			 * map.put(key, bundle.getString(key)); }
			 */
			if (map == null) {
				map = new HashMap<String, String>();
			}
			for (String key : bundle.stringPropertyNames()) {
				String value = bundle.getProperty(key);
				map.put(key, value);
			}
		}
		this.bundleMap = map;
	}

	/**
	 * Gets the value.
	 * 
	 * @param key
	 *            The key.
	 * @return String The value.
	 */
	public final String getValue(final String key) {
		String val = null;
		if (bundleMap != null) {
			val = bundleMap.get(key);
			if (val != null) {
				val = val.trim();
			}
		}
		return val;
	}

	/**
	 * Puts the key-value pair.
	 * 
	 * @param key
	 *            The key.
	 * @param value
	 *            The value.
	 */
	public final void putValue(final String key, final String value) {
		if (bundleMap != null) {
			bundleMap.put(key, value);
		}
	}

	/**
	 * Resets the resource by reloading it.
	 */
	public final void reset() {
		if (bundleMap != null) {
			bundleMap.clear();
			// populateMap(ResourceBundle.getBundle(fileName));
		}
	}
}
