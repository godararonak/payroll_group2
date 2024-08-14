package com.example.salary.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
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
