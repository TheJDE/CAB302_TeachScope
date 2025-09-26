package com.cab302.teachscope.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for password functionality.
 */
public class PasswordUtils {

    private final String tsEmailAddress = "teachscope.serviceteam@gmail.com";
    private final String tsEmailPass = "lafs rwgq pcjt weyt";

    private static final SecureRandom random = new SecureRandom();

    /**
     * Takes a password and returns an SHA-256 hash of it.
     * @param password The password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b: hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generatePasswordResetCode(int length) {
        StringBuilder resetCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            resetCode.append(random.nextInt(10)); // digit 0-9
        }
        return resetCode.toString();
    }

    public static String hashResetCode(String resetCode) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(resetCode.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResetCode(String userEmail, String resetCode) {
        // 1. Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 2. Create session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(tsEmailAddress, tsEmailPass);
            }
        });
        try {
            // 3. Build message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(tsEmailAddress));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(userEmail)
            );
            message.setSubject("Your Password Reset Code");
            message.setText("Your reset code is: " + resetCode );

            // 4. Send
            Transport.send(message);

            System.out.println("resetCode sent successfully to " + tsEmailAddress);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }



}
