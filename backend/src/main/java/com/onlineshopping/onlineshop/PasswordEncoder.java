package com.onlineshopping.onlineshop;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class PasswordEncoder {

    public static String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validatePassword(String password, String hashedPassword) {
        String newlyHashedPassword = encode(password);
        return hashedPassword.equals(newlyHashedPassword);
    }

    public static String generateToken(String username) {
        String tokenData = username + "-" + Instant.now().toString();
        String encodedToken = Base64.getEncoder().encodeToString(tokenData.getBytes());
        return encodedToken;
    }
}
