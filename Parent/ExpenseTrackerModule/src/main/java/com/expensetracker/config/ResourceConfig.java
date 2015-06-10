package com.expensetracker.config;

import com.expensetracker.manager.ResourceManager;

public class ResourceConfig {
	
    private static ResourceConfig instance = new ResourceConfig();
	private String GLOBAL_CONFIG_PROPS = "GlobalConfig.properties";

	/**
     * Stores global resource.
     */
    private transient Resource globalResource;
	
    /**
     * Prevent instantiation from external code.
     */
    private ResourceConfig() {
        init();
    }

	private void init() {
        synchronized (ResourceConfig.class) {
            // Load the global configurations from bbassist.properties.
            this.loadGlobalProperties();
        }
	}

	private void loadGlobalProperties() {
		// TODO Auto-generated method stub
		this.globalResource = ResourceManager.get(GLOBAL_CONFIG_PROPS);
	}
	
	
    public void setProperty(final String key, final String value) {

        validateProperty(key);

        synchronized (this) {
            this.globalResource.putValue(key, value);
        }
    }
	
    /**
     * Returns a <code>GlobalConfig</code> instance.
     * 
     * @return A <code>GlobalConfig</code> instance.
     */
    public static ResourceConfig getInstance() {
        return instance;
    }

    /**
     * Resets the global configuration map.
     */
    public void reset() {
        globalResource.reset();
        init();
    }

    private static void validateProperty(final String key) {

        try {
            ResourceConfigKey.valueOf(key);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Unknown property " + key);
        }
    }
    
    public long getJobTimerPeriod() {
    	return Long.valueOf(this.globalResource.getValue(ResourceConfigKey.JOB_TIMER_INTERVAL.name()));
    }
    
    public String getLogFilePath() {
    	return this.globalResource.getValue(ResourceConfigKey.LOG_FILE_PATH.name());
    }
    
    public String getLogFileName() {
    	return this.globalResource.getValue(ResourceConfigKey.LOG_FILE_NAME.name());
    }
    
    public String getDriverName() {
    	return this.globalResource.getValue(ResourceConfigKey.DRIVER_NAME.name());
    }
    
    public String getDBConnectionURL() {
    	return this.globalResource.getValue(ResourceConfigKey.URL.name());
    }
    
    public String getDBUsername() {
    	return this.globalResource.getValue(ResourceConfigKey.USERNAME.name());
    }
    
    public String getDBPassword() {
    	return this.globalResource.getValue(ResourceConfigKey.PASSWORD.name());
    }
}
