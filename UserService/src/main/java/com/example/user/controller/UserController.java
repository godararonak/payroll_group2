package com.example.user.controller;

import com.example.user.dto.UserDto;
import com.example.user.entity.Users;
import com.example.user.services.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


   @CrossOrigin
    @RestController
    @RequestMapping("/api/v1/employees")

    public class UserController {

        @Autowired
        UserService userService;

        @Autowired
        ModelMapper modelMapper;

        @GetMapping("/exist/{id}")
        public ResponseEntity<String> existsById(@PathVariable Long empId){

        Boolean ans = userService.exists(empId);

        if(ans){
            return ResponseEntity.status(HttpStatus.FOUND).body("User exists");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        }

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

        @GetMapping("/fetchEmployee/{employeeId}")
        public ResponseEntity<Users> getEmployeeById(@PathVariable Long employeeId){
            System.out.println("get mapping successfull by id");
            Users user = userService.getEmployeeById(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        @PostMapping("/createEmployee")
        public ResponseEntity<Users> createUsers(@RequestBody UserDto userDto){
            Users users=userService.createEmployee(modelMapper.map(userDto,Users.class));
            return new ResponseEntity<>(users,HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity<Users>updateEmployee(@PathVariable Long id, @RequestBody Users users){
            Users user = userService.updateUser(id,users);
            return new ResponseEntity<>(user,HttpStatus.OK);
         }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Users> deleteEmployee(@PathVariable Long id){

            Users user = userService.getEmployeeById(id);
            user.setActive(false);
            userService.updateUser(id, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
}
