package com.example.salary.Controller;


import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
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

@RestController
@RequestMapping("/api/v1/leaves")
public class LeavesController {

    @Autowired
    private LeaveService leaveService;

    // Endpoint for taking leave
    @PostMapping("/take")
    public ResponseEntity<Leaves> takeLeave(@RequestBody Leaves leave) {
        Leaves savedLeave = leaveService.takeLeave(leave);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }

    @GetMapping("/fetchAllPending")
    public ResponseEntity<List<Leaves>> fetchAllLeaves(){
        // FOR ADMIN
        List<Leaves> leavesList = leaveService.fetchAllPending();

        return new ResponseEntity<>(leavesList, HttpStatus.CREATED);

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
    public ResponseEntity<Void> changeLeaveStatusToApprove(@PathVariable Integer leaveId) {
        leaveService.changeLeaveStatusToApprove(leaveId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status/reject/{leaveId}")
    public ResponseEntity<Void> changeLeaveStatusToReject(@PathVariable Integer leaveId) {
        leaveService.changeLeaveStatusToReject(leaveId);
        return ResponseEntity.ok().build();
    }

    // Endpoint for finding all leaves in a month
    @GetMapping("/leaves")
    public ResponseEntity<List<LocalDate>> findAllLeavesInMonth(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        if(employeeId ==0 || month ==0 || year == 0) throw new InvalidQueryParameterException();

        List<LocalDate> leaves = leaveService.findAllLeavesInMonth(employeeId, month, year);
        return new ResponseEntity<>(leaves,HttpStatus.OK);
    }

    @GetMapping("/leaves/{employeeId}")
    public ResponseEntity<List<Leaves>> findLeavesByEmployeeId(@PathVariable Long employeeId){

        List<Leaves> list = leaveService.fetchAllLeavesByEmployeeId(employeeId);

        return new ResponseEntity<>(list,HttpStatus.OK);

    }


    // Endpoint for finding all leaves between two dates
    @GetMapping("/betweenDates")
    public ResponseEntity<List<LocalDate>> findAllLeavesBetweenDates(
            @RequestParam Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<LocalDate> leaves = leaveService.findAllLeavesBetweenDates(employeeId, startDate, endDate);
        return new ResponseEntity<>(leaves,HttpStatus.OK);
    }


    // Endpoint for calculating monthly salary
    @GetMapping("/salary")
    public ResponseEntity<SalaryPerMonth> calculateMonthlySalary(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        SalaryPerMonth monthlySalary = leaveService.generateSalary(employeeId, month, year);
        return new ResponseEntity<>(monthlySalary,HttpStatus.OK);
    }

}
