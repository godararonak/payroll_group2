package com.example.salary.Dto;

import com.example.salary.Entity.Salary;
import lombok.Data;

import java.util.List;

@Data

public class AllEmployeeSalary {

    private List<Salary> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElements;

    private boolean isLastPage;

    private Integer totalPages;

}
