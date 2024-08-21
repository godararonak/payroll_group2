package com.example.AuthServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String f_name;


    private String l_name;

    private LocalDate date_of_birth;

    private LocalDate hire_Date;

    private String email;

    private String Phone_Number;

    private String role;

    private long Manager_Id;

    private double total_ctc;

    private String password;


}
