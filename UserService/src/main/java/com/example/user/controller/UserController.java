package com.example.user.controller;

import com.example.user.dto.ResponseDto;
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
        public ResponseEntity<ResponseDto> existsById(@PathVariable Long empId){

        Boolean ans = userService.exists(empId);

        if(ans){
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDto("User exists"));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("User does not exist"));
        }
}

        @GetMapping("/ping")
        public ResponseEntity<String> Ping() {
            System.out.println("ping is successfully");
            return ResponseEntity.status(HttpStatus.OK).body("Server is up");
        }

        @GetMapping("/fetch-all")
        public ResponseEntity<Object> getAllEmployees(){
            try {
                System.out.println("fetch all successfull");
                List<Users> employeeList = userService.getAllEmployees();
                return ResponseEntity.status(HttpStatus.OK).body(employeeList);
            }catch (Exception e){
                return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        @GetMapping("/fetchEmployee/{employeeId}")
        public ResponseEntity<Object> getEmployeeById(@PathVariable Long employeeId){
            try {
                System.out.println("get mapping successfull by id");
                Users user = userService.getEmployeeById(employeeId);
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }catch (Exception e){
                return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/createEmployee")
        public ResponseEntity<Object> createUsers(@RequestBody UserDto userDto){
            try {
                Users users = userService.createEmployee(modelMapper.map(userDto, Users.class));
                return new ResponseEntity<>(users, HttpStatus.CREATED);
            }catch(Exception e){
                return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity<Object>updateEmployee(@PathVariable Long id, @RequestBody Users users){
            try {
                Users user = userService.updateUser(id, users);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
            }
         }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Object> deleteEmployee(@PathVariable Long id){
            try {
                Users user = userService.getEmployeeById(id);
                user.setActive(false);
                userService.updateUser(id, user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(new ResponseDto(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
