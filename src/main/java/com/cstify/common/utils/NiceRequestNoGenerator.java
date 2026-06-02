package com.cstify.common.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NiceRequestNoGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyMMddHHmmss");

    private NiceRequestNoGenerator() {}

    /**
     * SNC + timestamp + random = 20자리
     */
    public static String generate() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        int random = RANDOM.nextInt(100_000); // 0 ~ 99999

        return "SNC" + timestamp + String.format("%05d", random);
    }
}
