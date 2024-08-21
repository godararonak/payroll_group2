package com.example.salary.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SalaryPerMonth")
@NoArgsConstructor
@Getter
@Setter
@Data
public class SalaryPerMonth {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column(name = "employee_id", nullable = false)
private Long employeeId;

@Column(name = "month",nullable = false)
private String month;

@Column(name = "year",nullable = false)
private Integer year;

@Column(name = "monthly_salary",nullable = false )
private double salary;


}
