package org.example.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream inputStream = ConfigLoader.class.getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException("config.properties file not found in classpath");
            }

            properties.load(inputStream);
            System.out.println("Configuration loaded successfully");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage(), e);
        }
    }

    /**
     * Get property value by key
     * @param key property key
     * @return property value or null if not found
     */
    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Warning: Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    /**
     * Get property value by key with default value
     * @param key property key
     * @param defaultValue default value if key not found
     * @return property value or default value
     */
    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get all loaded properties (for debugging)
     */
    public static Properties getAllProperties() {
        return new Properties(properties);
    }
}