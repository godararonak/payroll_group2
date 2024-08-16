package com.example.AuthServer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EmployeeDto {

    private long id;

    private String f_name;

    private String l_name;

    private String email;

    private String role;

    private long manager_id;


}
