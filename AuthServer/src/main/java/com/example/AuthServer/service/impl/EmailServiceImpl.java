package com.example.AuthServer.service.impl;

import com.example.AuthServer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendTemporaryPasswordEmail(String to, String temporaryPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Temporary Password");
        message.setText(buildEmailBody(temporaryPassword));
        message.setFrom("payroll@ukg.com");
                // Send the email
//        mailSender.send(message);
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
