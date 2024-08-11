package com.example.salary.Repository;

import com.example.salary.Entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface LeavesRepo extends JpaRepository<Leaves,Integer> {

    List<Leaves> findByemployeeId(Long employeeId);
}
