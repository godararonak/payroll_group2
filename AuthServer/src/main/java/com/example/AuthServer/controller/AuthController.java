package com.example.AuthServer.controller;
import com.example.AuthServer.GMailer;
import com.example.AuthServer.dto.JWTDto;
import com.example.AuthServer.dto.ResponseDto;
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

@CrossOrigin
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDTO registerDto) {
        try {
            ResponseDto response = authService.register(registerDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){
        try {
            ResponseDto response = authService.resetPassword(resetPasswordDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/token_forgot_password")
    public ResponseEntity<ResponseDto> processForgotPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        String email = resetPasswordDto.getUsername();
        String token = RandomString.make(30);

        try {
            authService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:4200/forgot-password/" + token;
            sendEmail(email, resetPasswordLink);
            ResponseDto responseDto=new ResponseDto();
            responseDto.setResponse("We have sent a reset password link to your email. Please check.");
            return ResponseEntity.ok(responseDto);
        } catch (UnsupportedEncodingException | MessagingException e) {
            ResponseDto responseDto=new ResponseDto();
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset_forgotPassword")
    public ResponseEntity<ResponseDto> processResetForgotPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        String token = resetPasswordDto.getToken();
        String password = resetPasswordDto.getNewPassword();

        try {
            User user = authService.getByResetPasswordToken(token);
            if (user == null) {
                return new ResponseEntity<>(new ResponseDto("Invalid Token"),HttpStatus.BAD_REQUEST);
            } else {
                authService.forgotPassword(resetPasswordDto);
                return new ResponseEntity<>(new ResponseDto("You have successfully changed your password."),HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto){
        try {
            JWTDto response = authService.login(loginDto);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> createRole(@RequestBody Role role){
        try {
            Role response = authService.createRole(role);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRole(){
        List<Role> response = authService.getAllRole();
        return ResponseEntity.ok(response);
    }
}
