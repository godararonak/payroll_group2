package com.example.AuthServer.service.impl;

import com.example.AuthServer.GMailer;
import com.example.AuthServer.service.EmailService;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    GMailer gMailer;

    @Override
    public void sendTemporaryPasswordEmail(String to, String temporaryPassword) {
        String subject="Your Temporary Password";
        String message=buildEmailBody(temporaryPassword);
        try {
            gMailer.sendMail(subject, message, to);
        }catch (Exception e) {
                System.err.println("Unable to send mail");
        }
    }

    private String buildEmailBody(String temporaryPassword) {
        String loginPageUrl = "http://yourdomain.com/login";

        return "Dear User,\n\n"
                + "Welcome! Your account has been successfully created. "
                + "Here is your temporary one time accessible password: " + temporaryPassword + "\n\n"
                + "Please use this password to log in to your account for the first time. "
                + "You will be required to reset your password upon your first login.\n\n"
                + "Login here: " + loginPageUrl + "\n\n"
                + "Best regards,\n"
                + "UKG";
    }
}
