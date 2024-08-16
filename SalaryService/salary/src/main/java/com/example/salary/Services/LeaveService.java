package com.example.salary.Services;

import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public interface LeaveService {

    Leave takeLeave(Leave leave);

    Leave updateLeave(Leave leave);


    void changeLeaveStatusToApprove(Integer leaveId);

    void changeLeaveStatusToReject(Integer leaveId);

    List<LocalDate> findAllLeavesInMonth(Long employeeId, int month, int year);

    List<LocalDate> findAllLeavesBetweenDates(Long employeeId, LocalDate startDate, LocalDate endDate);

//    MonthlySalary calculateMonthlySalary(Long employeeId, int month, int year);

    ResponseEntity<String> generateSalary(long empId, int month, int year);

}
