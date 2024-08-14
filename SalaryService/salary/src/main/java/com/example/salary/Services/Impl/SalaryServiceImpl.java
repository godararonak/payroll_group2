package com.example.salary.Services.Impl;
import com.example.salary.Dto.AllEmployeeSalary;
import com.example.salary.Entity.Salary;
import com.example.salary.ExceptionHandling.DuplicateResourceException;
import com.example.salary.ExceptionHandling.ResourceNotFoundException;
import com.example.salary.Repository.LeavesRepo;
import com.example.salary.Repository.SalaryRepo;
import com.example.salary.Services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    SalaryRepo salaryRepo;

    @Autowired
    LeavesRepo leavesRepo;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public Salary saveSalary(Salary salary) {

        // verify that employee present in DB from user service




        // verify that employee salary not already in DB
        Optional<Salary> existingSalary = salaryRepo.findByemployeeId(salary.getEmployeeId());

        if (existingSalary.isPresent()) {
            throw new DuplicateResourceException("employee","id", salary.getEmployeeId());
        }

        return salaryRepo.save(salary);
    }

    @Override
    public Salary updateSalary(Long employeeId,Salary salary) {

        // verify employee from user service


        //

       Salary salary1 =salaryRepo.findByemployeeId(employeeId)
                       .orElseThrow(()->new ResourceNotFoundException("employee","id", employeeId));

       salary1.setHra(salary.getHra());
       salary1.setBasic(salary.getBasic());
       salary1.setTotalCtc(salary.getTotalCtc());
       salary1.setAllowance(salary.getAllowance());
       return salaryRepo.save(salary1);
    }

    @Override
    public void deleteSalary(Long userId) {
        Salary salary1 =salaryRepo.findByemployeeId(userId)
                .orElseThrow(()->new ResourceNotFoundException("employee","id", userId));
        salaryRepo.delete(salary1);
    }

    @Override
    public Salary getSalary(Long userId) {
        Salary salary1 =salaryRepo.findByemployeeId(userId)
                .orElseThrow(()->new ResourceNotFoundException("employee","id", userId));

        return salary1;
    }

    @Override
    public AllEmployeeSalary getAllSalary(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        // using pagination and sorting

        Sort sort=null;

        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else{
            sort=Sort.by(sortBy).descending();
        }

        Pageable p= PageRequest.of(pageNumber,pageSize, sort);  // pageable object

        Page<Salary> pagePost= this.salaryRepo.findAll(p); // passing pageable object to findAll

        List<Salary> allSalary = pagePost.getContent();

        AllEmployeeSalary modifiedSalaryPayload= new AllEmployeeSalary();
        modifiedSalaryPayload.setContent(allSalary);
        modifiedSalaryPayload.setPageNumber(pagePost.getNumber());
        modifiedSalaryPayload.setPageSize(pagePost.getSize());
        modifiedSalaryPayload.setTotalElements(pagePost.getTotalElements());
        modifiedSalaryPayload.setLastPage(pagePost.isLast());
        modifiedSalaryPayload.setTotalPages(pagePost.getTotalPages());
        return modifiedSalaryPayload;

    }
}
