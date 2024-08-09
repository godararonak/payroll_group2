package com.example.salary.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO employee connection

@Entity(name = "salaries")    //TODO plural? convention?
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY //TODO IDENTITY or AUTO?
    )
    private long id;

    @Column(name = "employee_id")
    private long employeeId;

    private double basic;
    private double hra;
    private double allowance;

    @Column(name = "total_ctc")
    private double totalCtc;
}
