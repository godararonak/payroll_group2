package com.example.salary.Controller;


import com.example.salary.Dto.CalculateMonthlySalaryDto;
import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
import com.example.salary.Dto.ResponseDto;
import com.example.salary.Entity.Leaves;
import com.example.salary.Entity.SalaryPerMonth;
import com.example.salary.ExceptionHandling.InvalidQueryParameterException;
import com.example.salary.Services.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/leaves")
public class LeavesController {

    @Autowired
    private LeaveService leaveService;

    // Endpoint for taking leave
    @PostMapping("/take")
    public ResponseEntity<Object> takeLeave(@RequestBody Leaves leave) {
        try {
            Leaves savedLeave = leaveService.takeLeave(leave);
            return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchAllPending")
    public ResponseEntity<Object> fetchAllLeaves(){
        // FOR ADMIN
        try {
            List<Leaves> leavesList = leaveService.fetchAllPending();

            return new ResponseEntity<>(leavesList, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



//    @PutMapping("/update")
//    public ResponseEntity<Leave> updateLeave(@RequestBody Leave leave) {
//        Leave updatedLeave = leaveService.updateLeave(leave);
//        return new ResponseEntity<>(updatedLeave,HttpStatus.OK);
//    }

//    Patch vs. Put: The changeLeaveStatus method uses @PatchMapping because
//    it updates only a specific field, while updateLeave
//    uses @PutMapping to replace or update the entire leave object.

    @PatchMapping("/status/approve/{leaveId}")
    public ResponseEntity<Object> changeLeaveStatusToApprove(@PathVariable Integer leaveId) {
        try {
            leaveService.changeLeaveStatusToApprove(leaveId);
            return new ResponseEntity<>(new ResponseDto("Leave Approved"),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/status/reject/{leaveId}")
    public ResponseEntity<Object> changeLeaveStatusToReject(@PathVariable Integer leaveId) {
        try {
            leaveService.changeLeaveStatusToReject(leaveId);
            return new ResponseEntity<>(new ResponseDto("Leave Rejected"),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for finding all leaves in a month
    @GetMapping("/month")
    public ResponseEntity<Object> findAllLeavesInMonth(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        try {
            if (employeeId == 0 || month == 0 || year == 0) throw new InvalidQueryParameterException();

            List<LocalDate> leaves = leaveService.findAllLeavesInMonth(employeeId, month, year);
            return new ResponseEntity<>(leaves, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> findLeavesByEmployeeId(@PathVariable Long employeeId){

        try {
            List<Leaves> list = leaveService.fetchAllLeavesByEmployeeId(employeeId);

            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Endpoint for finding all leaves between two dates
    @GetMapping("/betweenDates")
    public ResponseEntity<Object> findAllLeavesBetweenDates(
            @RequestParam Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<LocalDate> leaves = leaveService.findAllLeavesBetweenDates(employeeId, startDate, endDate);
            return new ResponseEntity<>(leaves, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Endpoint for calculating monthly salary
    @GetMapping("/salary")
    public ResponseEntity<Object> calculateMonthlySalary(
            CalculateMonthlySalaryDto calculateMonthlySalaryDto) {
        try {
            SalaryPerMonth monthlySalary = leaveService.generateSalary(calculateMonthlySalaryDto.getEmployeeId(),calculateMonthlySalaryDto.getMonth(),calculateMonthlySalaryDto.getYear());
            return new ResponseEntity<>(monthlySalary, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
