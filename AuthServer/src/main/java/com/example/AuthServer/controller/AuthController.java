package com.example.AuthServer.controller;
import com.example.AuthServer.GMailer;
import com.example.AuthServer.entity.Role;
import com.example.AuthServer.entity.User;
import com.example.AuthServer.payload.LoginDTO;
import com.example.AuthServer.payload.RegisterDTO;
import com.example.AuthServer.payload.ResetPasswordDto;
import com.example.AuthServer.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){
        String response =authService.resetPassword(resetPasswordDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/token_forgot_password")
    public ResponseEntity<String> processForgotPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        String email = resetPasswordDto.getUsername();
        String token = RandomString.make(30);

        try {
            authService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://frontendpage-forgotpassword.com/?token=hbhsbhs?token=" + token;
            sendEmail(email, resetPasswordLink);
            return ResponseEntity.ok("We have sent a reset password link to your email. Please check.");
        } catch (UnsupportedEncodingException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email");
        }
    }

    @PostMapping("/reset_forgotPassword")
    public ResponseEntity<String> processResetForgotPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        String token = resetPasswordDto.getToken();
        String password = resetPasswordDto.getNewPassword();

        try {
            User user = authService.getByResetPasswordToken(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
            } else {
                authService.forgotPassword(resetPasswordDto);
                return ResponseEntity.ok("You have successfully changed your password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request.");
        }
    }



    @Autowired
    GMailer gMailer;

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {

        String subject = "Here's the link to reset your password";

        String content = "<!DOCTYPE html>"
                + "<html><body>"
                + "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>"
                + "</body></html>";

        try {
            gMailer.sendMail(subject, content, recipientEmail);
        }catch (Exception e) {
            System.err.println("Unable to send mail");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto){
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role response = authService.createRole(role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRole(){
        List<Role> response = authService.getAllRole();
        return ResponseEntity.ok(response);
    }
}
