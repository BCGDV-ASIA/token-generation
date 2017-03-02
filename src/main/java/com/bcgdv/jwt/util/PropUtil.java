/*
 * PropUtil
 */
package com.bcgdv.jwt.util;

/**
 * Get props from the environment or java args.
 */
public class PropUtil {

    /**
     * Fetch value as Java property or environment variable.
     * @param key they key
     * @return value as String
     */
    public static String getEnvOrProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }
}
