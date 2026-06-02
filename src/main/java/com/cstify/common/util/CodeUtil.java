package com.cstify.common.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CodeUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String jti() {
        return UUID.randomUUID().toString();
    }

    public static String tokenKey(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String orderToken(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String orderCode(){
//        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        int randomNumber = Math.abs(UUID.randomUUID().hashCode()) % 100_000_000;
//        String randomPart = String.format("%08d", randomNumber);
//        return datePart + randomPart;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 8
        String time = String.format("%06d", System.currentTimeMillis() % 1_000_000L); // 6
        String rand = String.format("%06d", ThreadLocalRandom.current().nextInt(1_000_000)); // 6
        return date + time + rand; // 20자리
    }

    public static String productCode(){
        return "P" + generateDigitCode(10);
    }

    public static String partnerCode(){
        return "P" + generateDigitCode(9);
    }

    public static String tenantCode(){
        return "T" + generateDigitCode(9);
    }

    public static String virtualPinCode() {
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10)); // 0~9
        }
        return sb.toString();
    }

    public static String pinCode() {
        StringBuilder sb = new StringBuilder(18);
        for (int i = 0; i < 18; i++) {
            sb.append(random.nextInt(10)); // 0~9
        }
        return sb.toString();
    }

    public static String generateDigitCode(int length) {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();

        // 양수로 변환 후 10자리 숫자로 제한
        long positive = Math.abs(mostSignificantBits);
        String code = String.valueOf(positive);

        // 10자리로 잘라서 반환
        if (code.length() < length) {
            code = String.format("%0" + length + "d", positive); // 부족하면 0-padding
        } else {
            code = code.substring(0, length);
        }

        return code;
    }
}
