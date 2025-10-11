package com.cab302.teachscope.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for password functionality.
 */
public class PasswordUtils {

    private static final String tsEmailAddress = "teachscope.serviceteam@gmail.com";
    private static final String tsEmailPass = "lafs rwgq pcjt weyt";

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

    public static void sendResetCode(String userEmail, String resetCode) throws MessagingException {
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be null or empty.");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(tsEmailAddress, tsEmailPass);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(tsEmailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Your Password Reset Code");
            message.setText("Your password reset code is: " + resetCode);

            Transport.send(message);

            System.out.println("Reset code sent successfully to " + userEmail);

        } catch (AddressException ae) {
            System.err.println("Invalid email address: " + userEmail);
            throw ae;
        } catch (AuthenticationFailedException afe) {
            System.err.println("SMTP authentication failed. Check your Gmail App Password.");
            throw afe;
        } catch (MessagingException me) {
            System.err.println("Failed to send email to " + userEmail);
            me.printStackTrace();
            throw me;
        }
    }
 


}
