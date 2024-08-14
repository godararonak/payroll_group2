package com.example.salary.Services.Impl;
import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
import com.example.salary.Dto.UserDto;
import com.example.salary.Entity.Leaves;
import com.example.salary.Entity.Salary;
import com.example.salary.ExceptionHandling.DuplicateResourceException;
import com.example.salary.ExceptionHandling.ResourceNotFoundException;
import com.example.salary.Repository.LeavesRepo;
import com.example.salary.Repository.SalaryRepo;
import com.example.salary.Services.LeaveService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LeavesRepo leavesRepo;

    @Autowired
    SalaryRepo salaryRepo;

    @Autowired
    RestTemplate restTemplate;

    private Logger logger= LoggerFactory.getLogger(LeaveServiceImpl.class);

    @Override
    public Leave takeLeave(Leave leave) {

        // verify from user service that employee should exist

        // validation that there should not be any other leave in the same time period

        List<Leaves> leaves1 = leavesRepo.findByemployeeId(leave.getEmployeeId());

        for(Leaves l: leaves1){
            LocalDate s= l.getStartDate();
            LocalDate e=l.getEndDate();
            if(leave.getStartDate().isAfter(e) || leave.getEndDate().isBefore(s))
                continue;
            else{
                throw new DuplicateResourceException("Leave already present with date between "+leave.getStartDate()+" and "+leave.getEndDate());
            }
        }

        Leaves leaves=modelMapper.map(leave, Leaves.class);
        leaves.setStatus("Pending");
        leavesRepo.save(leaves);
        return modelMapper.map(leaves,Leave.class);
    }

    @Override
    public Leave updateLeave(Leave leave) {
        // verify employeeId from employee service


        Leaves leave1 = leavesRepo.findById(leave.getId())
                .orElseThrow(() -> new ResourceNotFoundException("leave","id", leave.getId()));
        leave1.setLeaveType(leave.getLeaveType());
        leave1.setStartDate(leave.getStartDate());
        leave1.setEndDate(leave.getEndDate());
        leave1.setStatus(leave.getStatus());
        return modelMapper.map(leavesRepo.save(leave1),Leave.class);
    }

    @Override
    public void changeLeaveStatus(Integer leaveId, String status) {
        Leaves leave1 = leavesRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("leave","id", leaveId));
        leave1.setStatus(status);
        leavesRepo.save(leave1);
    }

    @Override
    public List<LocalDate> findAllLeavesInMonth(Long employeeId, int month, int year) {

        // verify employeeId from employee service


        List<Leaves> leaves = leavesRepo.findByemployeeId(employeeId);
        List<LocalDate> ans= new ArrayList<>();
        for(Leaves l: leaves){
            LocalDate s=l.getStartDate();
            LocalDate e=l.getEndDate();
            if(year<s.getYear()||year>e.getYear())
                continue;
            if(month<s.getMonthValue() || month>e.getMonthValue())
                continue;

            //[m1 ....m....m2]
            while(!s.equals(e)){
                if(s.getMonthValue()==month && !s.getDayOfWeek().equals("SATURDAY") && !s.getDayOfWeek().equals("SUNDAY")){
                   ans.add(s);
                }
                s=s.plusDays(1);
            }
        }

        return ans;
    }

    @Override
    public List<LocalDate> findAllLeavesBetweenDates(Long employeeId, LocalDate startDate, LocalDate endDate) {

        // verify employeeId from employee service

        List<Leaves>leaves=leavesRepo.findByemployeeId(employeeId);

        List<LocalDate> ans= new ArrayList<>();
        for(Leaves l: leaves){
            LocalDate s=l.getStartDate();
            LocalDate e=l.getEndDate();
            // [s,e] , [start date, end date]
            // [start date, end date], [s,e]
            // check merge interval

            if(e.isBefore(startDate) || endDate.isBefore(s))  // no common day
                continue;

            //[m1 ....m....m2]
            while(!s.equals(e)){
                if(s.isBefore(startDate))
                    continue;
                if(s.isAfter(endDate))
                    break;
                if(!s.getDayOfWeek().equals("SATURDAY") && !s.getDayOfWeek().equals("SUNDAY")){
                    ans.add(s);
                }
                s=s.plusDays(1);
            }
        }
        return ans;
    }

    @Override
    public MonthlySalary calculateMonthlySalary(Long employeeId, int month, int year) {


        Salary salary = salaryRepo.findByemployeeId(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("employee","id", employeeId));

        List<LocalDate> leavesInMonth = findAllLeavesInMonth(employeeId, month, year);

        BigDecimal monthlySalary = salary.getTotalCtc().divide(new BigDecimal(12), BigDecimal.ROUND_HALF_UP);

        BigDecimal dailyRate = monthlySalary.divide(new BigDecimal(22), BigDecimal.ROUND_HALF_UP);

        int totalLeaveDays = leavesInMonth.size();

        BigDecimal deduction = dailyRate.multiply(new BigDecimal(totalLeaveDays));

        MonthlySalary monthlySalary1=new MonthlySalary();

        monthlySalary1.setLeaves(totalLeaveDays);

        monthlySalary1.setNetSalary(monthlySalary.subtract(deduction));

        monthlySalary1.setEmployeeId(employeeId);

        monthlySalary1.setTotalCtc(salary.getTotalCtc());

        monthlySalary1.setMonth(month);

        monthlySalary1.setSalaryDeducted(deduction);

        monthlySalary1.setMonthlySalary(monthlySalary);

        return monthlySalary1;
    }
}
