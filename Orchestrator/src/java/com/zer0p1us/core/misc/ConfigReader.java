package com.zer0p1us.core.misc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author zer0p1us
 */
public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) { 
            // Load the properties file 
            properties.load(input); 
            // Read properties 
            String propertyName = properties.getProperty("propertyName"); 
            System.out.println("Property Value: " + propertyName); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getDatabaseUrl() {
        return getProperty("db.url");
    }

    public String getDatabaseUser() {
        return getProperty("db.user");
    }

    public String getDatabasePassword() {
        return getProperty("db.password");
    }
    
    public String getDatabaseTimeout() {
        return getProperty("db.loginTimeout");
    }
}