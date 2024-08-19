package com.example.AuthServer.service;


import com.example.AuthServer.entity.Role;
import com.example.AuthServer.entity.User;
import com.example.AuthServer.payload.LoginDTO;
import com.example.AuthServer.payload.RegisterDTO;
import com.example.AuthServer.payload.ResetPasswordDto;

import java.util.List;

public interface AuthService {
    String login(LoginDTO loginDto);
    String register(RegisterDTO registerDto);
    String resetPassword(ResetPasswordDto resetPasswordDto);
    String forgotPassword(ResetPasswordDto resetPasswordDto);
    void updateResetPasswordToken(String token, String email);
    User getByResetPasswordToken(String token);
    Role createRole(Role role);
    List<Role> getAllRole();
}
