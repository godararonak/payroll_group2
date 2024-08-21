package com.example.salary.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateMonthlySalaryDto {

    private Long employeeId;
    private int month;
    private int year;
}
