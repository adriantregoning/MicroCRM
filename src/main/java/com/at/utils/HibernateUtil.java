package com.at.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;  
    private static final String CONFIG_FILE_PATH = "E:\\AT\\MicroCRM\\config.properties";   
    // NB NB NB change to your location for config.properties file ABOVE <<<<<<<<<<<<<<<<<<<<<<<<<<<<
    
    static {
        System.out.println("HibernateUtil.init - class loading stuff here");
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create configuration
            Configuration configuration = new Configuration();
            
            // Load properties from the absolute path
            File configFile = new File(CONFIG_FILE_PATH);
            System.out.println("Looking for config.properties at: " + configFile.getAbsolutePath());
            System.out.println("config.properties exists: " + configFile.exists());
            
            if (configFile.exists()) {
                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    props.load(fis);
                    
                    // Get database properties
                    String dbUrl = props.getProperty("db.url");
                    String dbUsername = props.getProperty("db.username");
                    String dbPassword = props.getProperty("db.password");
                    
                    // Set Hibernate properties
                    configuration.setProperty("hibernate.connection.url", dbUrl);
                    configuration.setProperty("hibernate.connection.username", dbUsername);
                    configuration.setProperty("hibernate.connection.password", dbPassword);
                }
            } else {
                throw new RuntimeException("Config file not found at: " + CONFIG_FILE_PATH);
            }
            
            // Configure with hibernate.cfg.xml after setting the properties
            configuration.configure();
            
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            getSessionFactory().close();
        }
    }
}