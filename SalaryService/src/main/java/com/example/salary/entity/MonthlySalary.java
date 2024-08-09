package com.example.salary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private double netSalary;
    //TODO employeeId and salary month mapping
}
