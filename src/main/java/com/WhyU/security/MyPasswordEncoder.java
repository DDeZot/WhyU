package com.WhyU.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyPasswordEncoder extends BCryptPasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        String raw = rawPassword.toString();
        String salt;

        if (raw.endsWith("0")) {
            salt = "$2a$10$uCqisnNx3j/f7zg4ly3IDO";
        } else if (raw.endsWith("1")) {
            salt = "$2a$10$YMyiuaSzSd6DwirmkYYYl.";
        } else if (raw.endsWith("2")) {
            salt = "$2a$10$i03xOHeekqnZEPcb7G1AX.";
        } else if (raw.endsWith("3")) {
            salt = "$2a$10$oiS/dGiDbw2jzF7EJkpgHu";
        } else if (raw.endsWith("4")) {
            salt = "$2a$10$VPYzHpq/HhU9oDCeUMCDNO";
        } else if (raw.endsWith("5")) {
            salt = "$2a$10$QiOQl1mpNJcklGmsYaSaSu";
        } else if (raw.endsWith("6")) {
            salt = "$2a$10$fzRH3kKHkmf5lYctrjVJmO";
        } else if (raw.endsWith("7")) {
            salt = "$2a$10$H/jqed3ZrVJCoRUqjGsBKe";
        } else if (raw.endsWith("8")) {
            salt = "$2a$10$GN3GMR53cChx4HgHAiFWT.";
        } else if (raw.endsWith("9")) {
            salt = "$2a$10$RgI7RnrrGpLgtpNKpAAIAO";
        } else {
            throw new IllegalArgumentException("Invalid password format. Password must end with a digit.");
        }

        return BCrypt.hashpw(rawPassword.toString(), salt);
    }
}