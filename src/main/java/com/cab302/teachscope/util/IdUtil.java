package com.cab302.teachscope.util;

import java.util.UUID;

public class IdUtil {
    public static String generateIdString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
