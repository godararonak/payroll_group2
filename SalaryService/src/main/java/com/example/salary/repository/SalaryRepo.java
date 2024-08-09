package com.example.salary.repository;


import com.example.salary.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepo extends JpaRepository<Salary, Long> {

}
