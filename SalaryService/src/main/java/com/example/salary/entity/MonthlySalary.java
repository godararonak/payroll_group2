package com.example.salary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity(name = "monthly_salaries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalary {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long id;

    @Column(name = "employee_id")
    private long employeeId;

    private double netSalary;

    @Column(name = "salary_month")
    private LocalDate salaryMonth;
}
