package com.example.user.Controller;


import com.example.user.Services.UserService.UserService;
import com.example.user.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/va/employees")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<Users> getAllEmployees(){

        return userService.getAllEmployees();

    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id){

        Users user= userService.getEmployeeById(id);

        return user;

    }

    @PostMapping()
    public Users createUsers(@RequestBody Users user){

        return userService.saveEmployee(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateEmployee(@PathVariable Long id, @RequestBody Users users){

        if(userService.getEmployeeById(id)!=null){
           return ResponseEntity.notFound().build();
        }

        return null;
    }

}
