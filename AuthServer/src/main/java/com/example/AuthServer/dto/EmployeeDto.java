package com.example.AuthServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {

    private String f_name;

    private String l_name;

    private String email;

    private String role;

    private long Manager_Id;


}
