package com.example.salary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "working_days")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDays {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long Id;
    private LocalDate month;
    private Integer totalWorkingDays;
}
