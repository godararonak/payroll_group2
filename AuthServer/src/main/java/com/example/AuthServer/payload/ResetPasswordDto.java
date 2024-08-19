package com.example.AuthServer.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    String username;
    String newPassword;
    String conformPassword;
    String token;
}
