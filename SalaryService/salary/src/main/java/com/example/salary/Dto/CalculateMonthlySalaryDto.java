package com.example.salary.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateMonthlySalaryDto {

    Long employeeId;
    int month;
    int year;
}
