package com.example.user.services.UserImpl;
import com.example.user.exception.DuplicateResourceException;
import com.example.user.repository.UserRepo;
import com.example.user.services.UserService;
import com.example.user.entity.Users;
import com.example.user.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public Boolean exists(Long employeeId){

        boolean exist = false;
        exist = userRepo.existsById(employeeId);

        return exist;

    }

    @Override
    public List<Users> getAllEmployees() {
        return userRepo.findAll();
    }

    @Override
    public Users getEmployeeById(Long employeeId) {

        Users foundEmployee = userRepo.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee" , "EmployeeId" , employeeId.toString()));

        return foundEmployee;

    }

    @Override
    public Users createEmployee(Users employee) {

        Optional<Users> foundEmployee= userRepo.findByEmail(employee.getEmail());

        if(foundEmployee.isPresent()){
           throw new DuplicateResourceException("Employee already exists with this Email : " + employee.getEmail());
        }
        return userRepo.save(employee);
    }

    @Override
    public Users updateUser(Long id,Users user){

        Users userfound = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee ", "Employee_Id" , id.toString()));

        userfound.setEmail(user.getEmail());
        userfound.setF_name(user.getF_name());
        userfound.setL_name(user.getL_name());
        userfound.setHire_Date(user.getHire_Date());
        userfound.setManager_Id(user.getManager_Id());
        userfound.setPhone_Number(user.getPhone_Number());
        userfound.setRole(user.getRole());
        userfound.setDate_of_birth(user.getDate_of_birth());
        return userRepo.save(userfound);
    }

    @Override
    public boolean deleteEmployee(Long employeeId) {

        boolean isDeleted = false;

        Users user = userRepo.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId.toString()));

        if(user != null){
            userRepo.deleteById(employeeId);
            isDeleted= true;
        }
    return isDeleted;
    }
}
