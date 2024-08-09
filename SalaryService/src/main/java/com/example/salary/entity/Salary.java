package com.example.salary.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO employee connection

@Entity(name = "Salary")    //TODO plural? convention?
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY //TODO IDENTITY or AUTO?
    )
    private long id;
    private double basic;
    private double hra;
    private double allowance;
    private double total_ctc;
}
