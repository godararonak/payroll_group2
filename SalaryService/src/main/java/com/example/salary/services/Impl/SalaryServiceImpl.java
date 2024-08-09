package com.example.salary.services.Impl;

import com.example.salary.entity.Salary;
import com.example.salary.repository.SalaryRepo;
import com.example.salary.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private SalaryRepo salaryRepo;

    @Override
    public Double getHRA(Long empId) {
        //ToDo check for exception
        return salaryRepo.findById(empId).orElseThrow(()-> new RuntimeException("Error in getting HRA with id:" + empId)).getHra();
    }

    @Override
    public Double getAllowance(Long empId) {
        return salaryRepo.findById(empId).orElseThrow(()-> new RuntimeException("Error in getting Allowance with id:" + empId)).getAllowance();
    }

    @Override
    public Double getTotalCtc(Long empId) {
        return salaryRepo.findById(empId).orElseThrow(()-> new RuntimeException("Error in getting TotalCTC with id:" + empId)).getTotalCtc();
    }

    @Override
    public Double getSalary(Long empId) {



        return salaryRepo.findById(empId).orElseThrow(()-> new RuntimeException("Error in getting Salary with id:" + empId)).getTotalCtc() - deductions;
    }

    @Override
    public Double getBasic(Long empId) {
        return salaryRepo.findById(empId).orElseThrow(()-> new RuntimeException("Error in getting basic with id:" + empId)).getBasic();
    }


}
