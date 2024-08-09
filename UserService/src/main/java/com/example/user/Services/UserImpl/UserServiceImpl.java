package com.example.user.Services.UserImpl;

import com.example.user.Repository.UserRepo;
import com.example.user.Services.UserService.UserService;
import com.example.user.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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
    public Users getEmployeeById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public Users saveEmployee(Users employee) {
        return userRepo.save(employee);
    }

    @Override
    public Boolean updateUser(Long id,Users user){

        Users userfound = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee "+ "Employee_Id" + id.toString()));

        if(userfound.equals(true)){
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
    public void deleteEmployee(Long id) {

        userRepo.deleteById(id);
    }
}
