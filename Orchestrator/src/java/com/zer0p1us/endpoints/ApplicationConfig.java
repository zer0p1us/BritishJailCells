package com.zer0p1us.endpoints;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author siman
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.zer0p1us.endpoints.Apply.class);
        resources.add(com.zer0p1us.endpoints.Cancel.class);
        resources.add(com.zer0p1us.endpoints.History.class);
        resources.add(com.zer0p1us.endpoints.Search.class);
    }
    
}
