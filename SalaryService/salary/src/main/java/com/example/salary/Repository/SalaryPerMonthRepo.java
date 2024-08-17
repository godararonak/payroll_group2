package com.example.salary.Repository;

import com.example.salary.Entity.SalaryPerMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryPerMonthRepo extends JpaRepository<SalaryPerMonth, Integer> {

}
