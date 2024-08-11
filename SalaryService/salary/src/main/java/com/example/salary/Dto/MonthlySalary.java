package com.example.salary.Dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MonthlySalary {

    Long employeeId;

    BigDecimal totalCtc;

    Integer leaves;

    Integer month;

    BigDecimal salaryDeducted;

    BigDecimal netSalary;

    BigDecimal monthlySalary;

}
