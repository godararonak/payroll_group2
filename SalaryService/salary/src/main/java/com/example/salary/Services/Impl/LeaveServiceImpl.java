package com.example.salary.Services.Impl;
import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
import com.example.salary.Dto.UserDto;
import com.example.salary.Entity.Leaves;
import com.example.salary.Entity.Salary;
import com.example.salary.Entity.SalaryPerMonth;
import com.example.salary.ExceptionHandling.DuplicateResourceException;
import com.example.salary.ExceptionHandling.ResourceNotFoundException;
import com.example.salary.Repository.LeavesRepo;
import com.example.salary.Repository.SalaryPerMonthRepo;
import com.example.salary.Repository.SalaryRepo;
import com.example.salary.Services.LeaveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LeavesRepo leavesRepo;

    @Autowired
    SalaryPerMonthRepo spmRepo;

    @Autowired
    SalaryRepo salaryRepo;

//    @Autowired
//    RestTemplate restTemplate;


    String[] montharray ={"","Jan","Feb", "Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};

    @Override
    public Leaves takeLeave(Leaves leave) {

        // verify from user service that employee should exist
//
        RestTemplate restTemplate = new RestTemplate();
        Long empId = leave.getEmployeeId();
//
//        String url = "http://localhost:8080/api/v1/employees/fetchEmployee/" + empId;
//
//        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
//



        UserDto userDto1 =  restTemplate.getForObject("http://localhost:8181/api/v1/employees/fetchEmployee/"+leave.getEmployeeId(),UserDto.class);

        if (userDto1.getEmail() == null || userDto1.getEmail().trim().isEmpty()) {
            throw new ResourceNotFoundException("Employee","Id",empId);
        }


//        logger.info("{}",userDto1);

        // validation that there should not be any other leave in the same time period

        List<Leaves> leaves1 = leavesRepo.findByemployeeId(leave.getEmployeeId());

        for (Leaves l : leaves1) {
            LocalDate s = l.getStartDate();
            LocalDate e = l.getEndDate();
            if (leave.getStartDate().isAfter(e) || leave.getEndDate().isBefore(s))
                continue;
            else {
                throw new DuplicateResourceException("Leave already present with date between " + leave.getStartDate() + " and " + leave.getEndDate());
            }
        }

        Leaves leaves = modelMapper.map(leave, Leaves.class);
        leaves.setStatus("Pending");
        leavesRepo.save(leaves);
        return leaves;
                //modelMapper.map(leaves, Leave.class);
    }

    @Override
    public List<Leaves> fetchAllPending(){

        List<Leaves> list = leavesRepo.findAll();
        List<Leaves> pendingList = new ArrayList<>();

        for(Leaves leave: list){

            if(leave.getStatus().equals("Pending")) pendingList.add(leave);

        }

        return pendingList;

    }

    @Override
   public List<Leaves> fetchAllLeavesByEmployeeId(Long employeeId){

        List<Leaves> list = leavesRepo.findAll();
        List<Leaves> employeeLeaves = new ArrayList<>();

        for(Leaves leave: list){

            if(leave.getEmployeeId().equals(employeeId)) employeeLeaves.add(leave);

        }

        return employeeLeaves;

    }

    @Override
    public Leave updateLeave(Leave leave) {
        // verify employeeId from employee service


        Leaves leave1 = leavesRepo.findById(leave.getId())
                .orElseThrow(() -> new ResourceNotFoundException("leave", "id", leave.getId()));
        leave1.setLeaveType(leave.getLeaveType());
        leave1.setStartDate(leave.getStartDate());
        leave1.setEndDate(leave.getEndDate());
        leave1.setStatus(leave.getStatus());
        return modelMapper.map(leavesRepo.save(leave1), Leave.class);
    }

    @Override
    public void changeLeaveStatusToApprove(Integer leaveId) {
        Leaves leave1 = leavesRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("leave", "id", leaveId));
        leave1.setStatus("Approve");
        leavesRepo.save(leave1);

//        Long empId = leave1.getId();
//
//        Optional<Salary> salary = salaryRepo.findByemployeeId(empId);
//
//        LocalDate start = leave1.getStartDate();
//        LocalDate end = leave1.getEndDate();
//
//        long leaves = ChronoUnit.DAYS.between(start, end);
//
//        if (salary.isPresent()) {
//            Double ctc = salary.get().getTotalCtc();
//
//            Double monthly_salary = ((ctc / 12) / 22) * (22 - leaves);
//            SalaryPerMonth salaryPerMonth = new SalaryPerMonth();
//            salaryPerMonth.setEmployeeId(empId);
//            salaryPerMonth.setSalary(monthly_salary);
//            salaryPerMonth.setMonth(end.getMonth().toString());
//            salaryPerMonth.setYear(end.getYear());
//            spmRepo.save(salaryPerMonth);
//        }
//        return ResponseEntity.of(Optional.of("Salary data not found;"));

    }

    @Override
    public void changeLeaveStatusToReject(Integer leaveId) {
        Leaves leave1 = leavesRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("leave", "id", leaveId));
        leave1.setStatus("Reject");
        leavesRepo.save(leave1);
    }


    @Override
    public List<LocalDate> findAllLeavesInMonth(Long employeeId, int month, int year) {

        // verify employeeId from employee service



        RestTemplate restTemplate=new RestTemplate();

        String url = "http://localhost:8181/api/v1/employees/fetchEmployee/" + employeeId;

        ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);

//        if(response.equals())

//        UserDto userDto = response.getBody();


        List<Leaves> leaves = leavesRepo.findByemployeeId(employeeId);
        List<LocalDate> ans = new ArrayList<>();
        for (Leaves l : leaves) {
            LocalDate s = l.getStartDate();
            LocalDate e = l.getEndDate();
            if (year < s.getYear() || year > e.getYear())
                continue;
            if (month < s.getMonthValue() || month > e.getMonthValue())
                continue;

            //[m1....m....m2]
            while (!s.equals(e)) {
                if (s.getMonthValue() == month && !s.getDayOfWeek().equals("SATURDAY") && !s.getDayOfWeek().equals("SUNDAY")) {
                    ans.add(s);
                }
                s = s.plusDays(1);
            }
        }

        return ans;
    }

    @Override
    public List<LocalDate> findAllLeavesBetweenDates(Long employeeId, LocalDate startDate, LocalDate endDate) {

        // verify employeeId from employee service

        List<Leaves> leaves = leavesRepo.findByemployeeId(employeeId);

        List<LocalDate> ans = new ArrayList<>();
        for (Leaves l : leaves) {
            LocalDate s = l.getStartDate();
            LocalDate e = l.getEndDate();
            // [s,e] , [start date, end date]
            // [start date, end date], [s,e]
            // check merge interval

            if (e.isBefore(startDate) || endDate.isBefore(s))  // no common day
                continue;

            //[m1 ....m....m2]
            while (!s.equals(e)) {
                if (s.isBefore(startDate))
                    continue;
                if (s.isAfter(endDate))
                    break;
                if (!s.getDayOfWeek().equals("SATURDAY") && !s.getDayOfWeek().equals("SUNDAY")) {
                    ans.add(s);
                }
                s = s.plusDays(1);
            }
        }
        return ans;
    }

    @Override
    public SalaryPerMonth generateSalary(Long empId, int month, int year) {
        int leaves = findAllLeavesInMonth(empId, month, year).size();
        SalaryPerMonth salaryPerMonth = new SalaryPerMonth();
        Optional<Salary> salary = salaryRepo.findByemployeeId(empId);

        if (salary.isPresent()) {
            Double ctc = salary.get().getTotalCtc();

            Double monthly_salary = ((ctc / 12) / 22) * (22 - leaves);

            salaryPerMonth.setEmployeeId(empId);
            salaryPerMonth.setSalary(monthly_salary);
            salaryPerMonth.setMonth(montharray[month]);
            salaryPerMonth.setYear(year);
            spmRepo.save(salaryPerMonth);

        }else{
            throw new ResourceNotFoundException("Employee","id",empId);
        }
        return salaryPerMonth;

    }



}

//    @Override
//    public MonthlySalary calculateMonthlySalary(Long employeeId, int month, int year) {
//
//
//        Salary salary = salaryRepo.findByemployeeId(employeeId)
//                .orElseThrow(()->new ResourceNotFoundException("employee","id", employeeId));
//
//        List<LocalDate> leavesInMonth = findAllLeavesInMonth(employeeId, month, year);
//
//        Double monthlySalary = salary.getTotalCtc().divide(new BigDecimal(12), BigDecimal.ROUND_HALF_UP);
//
//        Double dailyRate = monthlySalary.divide(new BigDecimal(22), BigDecimal.ROUND_HALF_UP);
//
//        int totalLeaveDays = leavesInMonth.size();
//
//        BigDecimal deduction = dailyRate.multiply(new BigDecimal(totalLeaveDays));
//
//        MonthlySalary monthlySalary1=new MonthlySalary();
//
//        monthlySalary1.setLeaves(totalLeaveDays);
//
//        monthlySalary1.setNetSalary(monthlySalary.subtract(deduction));
//
//        monthlySalary1.setEmployeeId(employeeId);
//
//        monthlySalary1.setTotalCtc(salary.getTotalCtc());
//
//        monthlySalary1.setMonth(month);
//
//        monthlySalary1.setSalaryDeducted(deduction);
//
//        monthlySalary1.setMonthlySalary(monthlySalary);
//
//        return monthlySalary1;
//    }

