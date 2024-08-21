package com.example.user.services;

import com.example.user.entity.Users;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {

    Boolean exists(Long id);

    Users exists(String username);

    List<Users> getAllEmployees();

    Users getEmployeeById(Long id);

    Users createEmployee(Users employee);

//    boolean deleteEmployee(Long id);

    Users updateUser(Long id,Users users);
}
