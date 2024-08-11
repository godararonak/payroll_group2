package com.example.salary.Services;

import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public interface LeaveService {

    Leave takeLeave(Leave leave);

    Leave updateLeave(Leave leave);

    void changeLeaveStatus(Integer leaveId, String status);

    List<LocalDate> findAllLeavesInMonth(Long employeeId, int month, int year);

    List<LocalDate> findAllLeavesBetweenDates(Long employeeId, LocalDate startDate, LocalDate endDate);

    MonthlySalary calculateMonthlySalary(Long employeeId, int month, int year);

}
