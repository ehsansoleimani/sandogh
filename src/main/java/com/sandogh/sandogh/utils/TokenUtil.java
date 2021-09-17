package com.sandogh.sandogh.utils;


import java.util.Random;

public class TokenUtil {
    public static String generateRandomToken() {
        StringBuilder stringBuilder = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        int charslength = chars.length();
        int numberslength = numbers.length();

        for (int i = 0; i < 40; i++) {
            int charsIndex = new Random().nextInt(charslength);
            int numbersIndex = new Random().nextInt(numberslength);
            stringBuilder.append(chars.charAt(charsIndex));
            stringBuilder.append(numbers.charAt(numbersIndex));
        }
        return stringBuilder.toString();
    }

}


