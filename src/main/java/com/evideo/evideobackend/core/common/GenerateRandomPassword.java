package com.evideo.evideobackend.core.common;

import java.security.SecureRandom;
import java.util.UUID;

public class GenerateRandomPassword {

    public static String generateRandomPassword(int len)
    {
        UUID uuid = UUID.randomUUID();

        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ-"+uuid.toString()+"-0123456789-abcdefghijklmnopqrstuvwxyz";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static String key()
    {
        UUID uuid = UUID.randomUUID();

        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ-"+uuid.toString()+"-0123456789-abcdefghijklmnopqrstuvwxyz";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < 15; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        String str = sb.toString().replaceAll("-","");
        return str.toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(GenerateRandomPassword.generateRandomPassword(20));
    }
}
