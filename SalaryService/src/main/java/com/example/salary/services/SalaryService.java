package com.example.salary.services;

import org.springframework.stereotype.Service;

@Service
public interface SalaryService {
    Long getSalary(Long empId);
    Long getBasic(Long empId);
    Long getHRA(Long empId);
    Long getAllowance(Long empId);
    Long getTotalCtc(Long empId);

}
