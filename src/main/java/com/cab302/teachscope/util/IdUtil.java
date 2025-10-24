package com.cab302.teachscope.util;

import java.util.UUID;

/**
 * Utility class for IDs
 */
public class IdUtil {
    /**
     * Generates a new UUID string
     * @return UUID string
     */
    public static String generateIdString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
