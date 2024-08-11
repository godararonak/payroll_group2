package com.example.user.Controller;


import com.example.user.Services.UserService.UserService;
import com.example.user.dto.ErrorResponseDto;
import com.example.user.entity.Users;
import jakarta.transaction.Transactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/va/employees")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/ping")
    public ResponseEntity<String> Ping() {
        System.out.println("ping is successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Server is up");
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<List<Users>> getAllEmployees(){
        System.out.println("fetch all successfull");
        List<Users> employeeList = userService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);

    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Users> getEmployeeById(@PathVariable Long employeeId){
        System.out.println("get mapping successfull by id");
        Users user = userService.getEmployeeById(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

//    @Operation(
//            summary = "Create Employee REST API",
//            description = "REST API to create new Employee in Payroll"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "201",
//                    description = "HTTP Status CREATED"
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "HTTP Status Internal Server Error",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            )
//    }
//    )

    @PostMapping("/createEmployee")
    public ResponseEntity<String> createUsers(@RequestBody Users user){
        System.out.println("user created successfull");
        userService.createEmployee(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new String("201"+ "Created Successfully"));

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Users users){
        System.out.println("update user successfull");
    boolean isUpdated = userService.updateUser(id,users);
        if(isUpdated)
            return ResponseEntity.status(HttpStatus.OK).body("Employee details Updated Successfully");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "Failure to update Employee");
    }


//    @Operation(
//            summary = "Delete Employee Details REST API",
//            description = "REST API to delete Employee details based on a Employee Id"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "HTTP Status OK"
//            ),
//            @ApiResponse(
//                    responseCode = "417",
//                    description = "Expectation Failed"
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "HTTP Status Internal Server Error",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            )
//    }
//    )
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        System.out.println("delete user succcessfull");
        boolean isDeleted = userService.deleteEmployee(id);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new String("204" + "Deleted Successfully"));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new String("500" + "Internal Server Error"));
        }

    }


    }

