package com.example.AuthServer.service;


import com.example.AuthServer.dto.JWTDto;
import com.example.AuthServer.dto.ResponseDto;
import com.example.AuthServer.entity.Role;
import com.example.AuthServer.entity.User;
import com.example.AuthServer.payload.LoginDTO;
import com.example.AuthServer.payload.RegisterDTO;
import com.example.AuthServer.payload.ResetPasswordDto;

import java.util.List;

public interface AuthService {
    JWTDto login(LoginDTO loginDto);
    Object register(RegisterDTO registerDto);
    ResponseDto resetPassword(ResetPasswordDto resetPasswordDto);
    void forgotPassword(ResetPasswordDto resetPasswordDto);
    void updateResetPasswordToken(String token, String email);
    User getByResetPasswordToken(String token);
    Role createRole(Role role);
    List<Role> getAllRole();
}
