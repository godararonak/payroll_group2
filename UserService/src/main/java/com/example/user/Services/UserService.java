package com.example.user.services;

import com.example.user.entity.Users;

import java.util.List;

public interface UserService {

    Boolean exists(Long id);

    List<Users> getAllEmployees();

    Users getEmployeeById(Long id);

    Users createEmployee(Users employee);

//    boolean deleteEmployee(Long id);

    Users updateUser(Long id,Users users);
}
