package com.at.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.File;

public class ConfigUtil {
    private static Properties properties;
    
    private static final String CONFIG_FILE = "E:\\AT\\VS Code Project One\\adrian\\config.properties";
    
    static {
        properties = new Properties();
        boolean loaded = false;
        
        try {
            File configFile = new File(CONFIG_FILE);
            if (configFile.exists()) {
                System.out.println("Loading config from: " + configFile.getAbsolutePath());
                try (FileInputStream input = new FileInputStream(configFile)) {
                    properties.load(input);
                    System.out.println("Successfully loaded properties");
                    loaded = true;
                }
            } else {
                System.err.println("Config file not found at: " + configFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            e.printStackTrace();
        }
        
        if (!loaded) {
            System.err.println("WARNING: Failed to load configuration, properties will be null");
        }
    }
    
    public static String getProperty(String key) {
        if (properties == null) {
            System.err.println("WARNING: Properties not initialized when getting " + key);
            return null;
        }
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("WARNING: Property not found: " + key);
        }
        return value;
    }
}