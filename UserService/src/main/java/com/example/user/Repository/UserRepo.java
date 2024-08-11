package com.example.user.Repository;

import com.example.user.entity.Users;
//import org.apache.catalina.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Long> {

   Optional<Users> findByEmail(String email);
   Optional<Users> findByEmployeeId(Long employeeId);
}
