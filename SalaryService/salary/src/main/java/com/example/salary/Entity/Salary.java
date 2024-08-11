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

    @Column(name = "basic", precision = 10, scale = 2)
    private BigDecimal basic;

    @Column(name = "hra", precision = 10, scale = 2)
    private BigDecimal hra;

    @Column(name = "allowance", precision = 10, scale = 2)
    private BigDecimal allowance;

    @Column(name = "total_ctc", precision = 10, scale = 2)
    private BigDecimal totalCtc;

}
