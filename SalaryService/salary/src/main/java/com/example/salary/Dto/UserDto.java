package com.example.salary.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class UserDto {

    private long id;

    private String f_name;

    private String l_name;

    private LocalDate date_of_birth;

    private LocalDate hire_Date;

    private String email;

    private String phone_number;

    private String role;

    private long manager_id;

    private double total_ctc;

    private String password;

}
