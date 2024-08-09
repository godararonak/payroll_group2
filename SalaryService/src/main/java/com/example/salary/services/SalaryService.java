package com.example.salary.services;

import org.springframework.stereotype.Service;

@Service
public interface SalaryService {
    public Long getSalary(Long empId);
    public Long getBasic(Long empId);
    public Long getHRA(Long empId);
    public Long getAllowance(Long empId);
    public Long getTotalCtc(Long empId);

    Long getHRA();
}
