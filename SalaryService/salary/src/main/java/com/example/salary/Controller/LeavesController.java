package com.example.salary.Controller;


import com.example.salary.Dto.Leave;
import com.example.salary.Dto.MonthlySalary;
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
    public ResponseEntity<Leave> takeLeave(@RequestBody Leave leave) {
        Leave savedLeave = leaveService.takeLeave(leave);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Leave> updateLeave(@RequestBody Leave leave) {
        Leave updatedLeave = leaveService.updateLeave(leave);
        return new ResponseEntity<>(updatedLeave,HttpStatus.OK);
    }

//    Patch vs. Put: The changeLeaveStatus method uses @PatchMapping because
//    it updates only a specific field, while updateLeave
//    uses @PutMapping to replace or update the entire leave object.

    @PatchMapping("/status/{leaveId}")
    public ResponseEntity<Void> changeLeaveStatus(@PathVariable Integer leaveId, @RequestParam String status) {
        leaveService.changeLeaveStatus(leaveId, status);
        return ResponseEntity.ok().build();
    }

    // Endpoint for finding all leaves in a month
    @GetMapping("/month")
    public ResponseEntity<List<LocalDate>> findAllLeavesInMonth(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        List<LocalDate> leaves = leaveService.findAllLeavesInMonth(employeeId, month, year);
        return new ResponseEntity<>(leaves,HttpStatus.OK);
    }

    // Endpoint for finding all leaves between two dates
    @GetMapping("/between-dates")
    public ResponseEntity<List<LocalDate>> findAllLeavesBetweenDates(
            @RequestParam Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<LocalDate> leaves = leaveService.findAllLeavesBetweenDates(employeeId, startDate, endDate);
        return new ResponseEntity<>(leaves,HttpStatus.OK);
    }


    // Endpoint for calculating monthly salary
    @GetMapping("/salary")
    public ResponseEntity<MonthlySalary> calculateMonthlySalary(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {
        MonthlySalary monthlySalary = leaveService.calculateMonthlySalary(employeeId, month, year);
        return new ResponseEntity<>(monthlySalary,HttpStatus.OK);
    }

}
