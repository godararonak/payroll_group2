package com.example.salary.Services;

import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
import com.example.salary.Entity.Leaves;
import com.example.salary.Entity.SalaryPerMonth;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public interface LeaveService {

    Leaves takeLeave(Leaves leave);

    Leave updateLeave(Leave leave);


    void changeLeaveStatusToApprove(Integer leaveId);

    void changeLeaveStatusToReject(Integer leaveId);

    List<LocalDate> findAllLeavesInMonth(Long employeeId, int month, int year);

    List<LocalDate> findAllLeavesBetweenDates(Long employeeId, LocalDate startDate, LocalDate endDate);

//    MonthlySalary calculateMonthlySalary(Long employeeId, int month, int year);

    SalaryPerMonth generateSalary(Long empId, int month, int year);

    List<Leaves> fetchAllPending();

    List<Leaves> fetchAllLeavesByEmployeeId(Long employeeId);
}
