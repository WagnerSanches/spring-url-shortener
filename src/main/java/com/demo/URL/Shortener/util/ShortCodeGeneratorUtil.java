package com.demo.URL.Shortener.util;

import java.util.Random;

public class ShortCodeGeneratorUtil {

    private static final int SHORT_CODE_SIZE = 6;
    private static final char[] characters = new char[] {
            // Lowercase letters
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            // Uppercase letters
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            // Numbers
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            // Special characters accepted in URLs
            '_', '@', '$',
    }; // 107 billion possibilities of combinations. =)

    public static String generate() {
        StringBuilder code = new StringBuilder(SHORT_CODE_SIZE);
        Random random = new Random();

        for (int i = 0; i < SHORT_CODE_SIZE; i++)
            code.append(characters[random.nextInt(characters.length)]);

        return code.toString();
    }

}
