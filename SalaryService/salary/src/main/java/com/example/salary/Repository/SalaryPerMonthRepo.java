package com.example.salary.Repository;

import com.example.salary.Entity.Salary;
import com.example.salary.Entity.SalaryPerMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryPerMonthRepo extends JpaRepository<SalaryPerMonth, Integer> {

    Optional<List<SalaryPerMonth>> findByemployeeId(Long employeeId);

}
