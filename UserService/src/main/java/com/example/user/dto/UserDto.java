package com.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private long id;

    private String f_name;

    private String l_name;

    private LocalDate date_of_birth;

    private LocalDate hire_Date;

    private String email;

    private String phone_Number;

    private String role;

    private long Manager_Id;

    private String address;

    private String about;

    private String password;

}
