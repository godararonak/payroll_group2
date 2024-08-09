package com.example.salary.services.Impl;

import com.example.salary.repository.SalaryRepo;
import com.example.salary.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;

public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private SalaryRepo salaryRepo;

    @Override
    public Long getHRA(Long empId) {

        return 1L;
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
