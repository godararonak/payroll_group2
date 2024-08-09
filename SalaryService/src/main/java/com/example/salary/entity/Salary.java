package com.example.salary.entity;


import jakarta.persistence.*;
import lombok.*;

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

    public double getBasic() {
        return basic;
    }

    public double getHra() {
        return hra;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public double getAllowance() {
        return allowance;
    }

    public double getTotalCtc() {
        return totalCtc;
    }
}
