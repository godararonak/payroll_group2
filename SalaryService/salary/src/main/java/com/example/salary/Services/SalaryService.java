package com.example.salary.Services;

import com.example.salary.Dto.AllEmployeeSalary;
import com.example.salary.Entity.Salary;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public interface SalaryService {

   Salary saveSalary(Salary salary);

   Salary updateSalary(Long employeeId,Salary salary);

   void deleteSalary(Long userId);

   Salary getSalary(Long userId);

   AllEmployeeSalary getAllSalary(Integer pgNo, Integer pgSize, String sortBy, String sortDir);

}
