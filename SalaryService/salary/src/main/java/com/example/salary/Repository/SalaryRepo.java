package com.example.salary.Repository;
import com.example.salary.Entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepo extends JpaRepository<Salary,Integer> {

    Optional<Salary> findByemployeeId(Long employeeId);

}
