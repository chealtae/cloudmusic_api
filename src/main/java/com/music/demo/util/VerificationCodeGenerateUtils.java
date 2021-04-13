package com.music.demo.util;

import java.security.SecureRandom;
import java.util.Random;

public class VerificationCodeGenerateUtils {

    private static final String SYMBOLS = "123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String getVerificationCode() {
        char[] tempChars = new char[6];
        for (int i = 0; i < tempChars.length; i++) {
            tempChars[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(tempChars);
    }

}
