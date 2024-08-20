package com.example.salary.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "salary")
@NoArgsConstructor
@Getter
@Setter
@Data

public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "basic", nullable = false)
    private Double basic;

    @Column(name = "hra", nullable = false)
    private Double hra;

    @Column(name = "allowance", nullable = false)
    private Double allowance;

    @Column(name = "total_ctc", nullable = false)
    private Double totalCtc;

}
