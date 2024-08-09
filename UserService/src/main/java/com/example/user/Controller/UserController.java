package com.example.user.Controller;


import com.example.user.Services.UserService.UserService;
import com.example.user.entity.Users;
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

    @GetMapping("/method-testing")
    public String getMethodTesting(){
        return this.userService.getMethodTesting();
    }

    @GetMapping()
    public List<Users> getAllEmployees(){

        return userService.getAllEmployees();

    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id){

        Users user= userService.getEmployeeById(id);

        return user;

    }

    @PostMapping("/tts")
    public Users createUsers(@RequestBody Users user){

        return userService.saveEmployee(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Users users){

    boolean isUpdated = userService.updateUser(id,users);
        if(isUpdated)
            return ResponseEntity.status(HttpStatus.OK).body("Employee details Updated Successfully");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "Failure to update Employee");
    }
    }

