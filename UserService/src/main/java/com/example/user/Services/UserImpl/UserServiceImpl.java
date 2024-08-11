package com.example.user.Services.UserImpl;

import com.example.user.Repository.UserRepo;
import com.example.user.Services.UserService.UserService;
import com.example.user.entity.Users;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.exception.ResourceNotFoundException;
//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Users> getAllEmployees() {
        return userRepo.findAll();
    }

    @Override
    public Users getEmployeeById(Long employeeId) {

        Users foundEmployee = userRepo.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee" , "EmployeeId" , employeeId.toString())
        );

        return foundEmployee;

    }

    @Override
    public void createEmployee(Users employee) {

        Optional<Users> foundEmployee= userRepo.findByEmail(employee.getEmail());

        if(foundEmployee.isPresent()){
           throw new RuntimeException("Employee already exists with this Email- " + employee.getEmail());
        }
        userRepo.save(employee);


    }

    @Override
    public Boolean updateUser(Long id,Users user){

        Users userfound = userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee ", "Employee_Id" , id.toString())
        );

        if(userfound != null){
        userRepo.deleteById(id);
        userRepo.save(user);

        }
        return true;
    }

    @Override
    public String getMethodTesting() {
        return "Method Testing Passed!";
    }

    @Override
    public boolean deleteEmployee(Long employeeId) {

        boolean isDeleted = false;

        Users user = userRepo.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId.toString())
        );

        if(user != null){
            userRepo.deleteById(employeeId);
            isDeleted= true;
        }
    return isDeleted;
    }
}
