package com.expensetracker.manager;

import java.util.HashMap;
import java.util.Map;

import com.expensetracker.config.Resource;

public class ResourceManager {
	
    /**
     * The table of all Resources keyed by 'id'.
     */
    private static Map<String, Resource> bundles = new HashMap<String, Resource>();
    
    
	
    private ResourceManager() {
        // Exists only to defeat instantiation.
    }
    
    public static void put(final String identifier, final Resource resource) {
        bundles.put(identifier, resource);
    }
    
    /**
     * Get a Resource object.
     * 
     * @param identifier The file path of the Resource to be returned.
     * @return The Resource object.
     */
    public static Resource get(final String identifier) {
        Resource resource = null;
        if (bundles.containsKey(identifier)) {
            resource = bundles.get(identifier);
        } else {
            resource = new Resource(identifier);
            bundles.put(identifier, resource);
        }
        return resource;
    }
}
