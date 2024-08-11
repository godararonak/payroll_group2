package com.example.salary.Dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Leave {

    Integer Id;

    Long employeeId;

    String leaveType;

    LocalDate startDate;

    LocalDate endDate;

    String status;

}
