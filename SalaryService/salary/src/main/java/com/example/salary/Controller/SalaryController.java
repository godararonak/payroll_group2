package com.example.salary.Controller;
import com.example.salary.Dto.AllEmployeeSalary;
import com.example.salary.Dto.ApiResponse;
import com.example.salary.Dto.ResponseDto;
import com.example.salary.Entity.Salary;
import com.example.salary.Services.SalaryService;
import com.example.salary.config.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/salary")
public class SalaryController {

  @Autowired
    SalaryService salaryService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Object> createSalary(@RequestBody Salary salary) {
        try {
            Salary createdSalary = salaryService.saveSalary(salary);
            return new ResponseEntity<>(createdSalary, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateById/{employeeId}")
    public ResponseEntity<Object> updateSalary(@PathVariable Long employeeId, @RequestBody Salary salary) {
        try {
            Salary updatedSalary = salaryService.updateSalary(employeeId, salary);
            return new ResponseEntity<>(updatedSalary, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("deleteBYId/{employeeId}")
    public ResponseEntity<ApiResponse> deleteSalary(@PathVariable Long employeeId) {
        salaryService.deleteSalary(employeeId);
        return new ResponseEntity<>(new ApiResponse("Employee Deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("fetchById/{employeeId}")
    public ResponseEntity<Object> getSalary(@PathVariable Long employeeId) {
        try {
            Salary salary = salaryService.getSalary(employeeId);
            return new ResponseEntity<>(salary, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAll")
    private ResponseEntity<Object> getAllPosts(@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pgNo,
                                                          @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pgSize,
                                                          @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                          @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        AllEmployeeSalary salary = this.salaryService.getAllSalary(pgNo,pgSize,sortBy,sortDir);
        try {
            return new ResponseEntity<>(salary,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
