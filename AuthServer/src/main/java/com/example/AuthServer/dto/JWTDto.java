package com.example.AuthServer.dto;


import com.example.AuthServer.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JWTDto {

    String jwt;

    String role;

    boolean firstLogin;

    Long userId;

}
