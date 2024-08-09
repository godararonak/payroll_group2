package com.example.salary.services;

import org.springframework.stereotype.Service;

@Service
public interface SalaryService {
    Double getSalary(Long empId);
    Double getBasic(Long empId);
    Double getHRA(Long empId);
    Double getAllowance(Long empId);
    Double getTotalCtc(Long empId);

}
