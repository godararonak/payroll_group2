package com.example.salary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity(name = "leaves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    //TODO Are enums necessary?
    public enum LeaveType {
        PAID,
        UNPAID
    }

    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private long id;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private long numOfDays;
    private LeaveStatus leaveStatus;

}
