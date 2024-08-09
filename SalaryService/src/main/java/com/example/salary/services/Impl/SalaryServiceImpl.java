package com.example.salary.services.Impl;

import com.example.salary.entity.Salary;
import com.example.salary.repository.SalaryRepo;
import com.example.salary.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;

public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private SalaryRepo salaryRepo;

    @Override
    public Long getHRA(Long empId) {

        var salaryData = salaryRepo.findById(empId);
        if(salaryData == null) {
            System.out.println("Employee salary data not found");
        }
        Salary s = new Salary();

        return 0l;
    }

    @Override
    public Long getAllowance(Long empId) {
        return 0L;
    }

    @Override
    public Long getTotalCtc(Long empId) {
        return 0L;
    }

    @Override
    public Long getSalary(Long empId) {
        return 1L;
    }

    @Override
    public Long getBasic(Long empId) {
        return 0L;
    }


}
