package com.example.salary.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

import com.example.salary.Dto.UserDto;
import com.example.salary.Entity.Salary;
import com.example.salary.ExceptionHandling.DuplicateResourceException;
import com.example.salary.ExceptionHandling.ResourceNotFoundException;
import com.example.salary.Repository.LeavesRepo;
import com.example.salary.Repository.SalaryRepo;
import com.example.salary.Services.Impl.SalaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class SalaryServiceUnitTests {

    @Mock
    private SalaryRepo salaryRepo;

    @Mock
    private LeavesRepo leavesRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSalary_Success() {
        Salary salary = new Salary();
        salary.setEmployeeId(1L);
        salary.setBasic(1000.0);

        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.empty());
        when(restTemplate.getForEntity(anyString(), eq(UserDto.class))).thenReturn(new ResponseEntity<>(userDto, HttpStatus.OK));
        when(salaryRepo.save(any(Salary.class))).thenReturn(salary);

        Salary result = salaryService.saveSalary(salary);

        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
        verify(salaryRepo).findByemployeeId(1L);
        verify(salaryRepo).save(any(Salary.class));
    }


    @Test
    public void testSaveSalary_ResourceNotFoundException() {
        Salary salary = new Salary();
        salary.setEmployeeId(1L);

        UserDto userDto = new UserDto();
        userDto.setId(2L); // mismatch employeeId

        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.empty());
        when(restTemplate.getForEntity(anyString(), eq(UserDto.class))).thenReturn(new ResponseEntity<>(userDto, HttpStatus.OK));

        assertThrows(ResourceNotFoundException.class, () -> salaryService.saveSalary(salary));
    }

    @Test
    public void testUpdateSalary_Success() {
        Salary salary = new Salary();
        salary.setEmployeeId(1L);
        salary.setBasic(2000.0);

        UserDto userDto = new UserDto();
        userDto.setId(1L);

        Salary existingSalary = new Salary();
        existingSalary.setEmployeeId(1L);

        when(restTemplate.getForEntity(anyString(), eq(UserDto.class))).thenReturn(new ResponseEntity<>(userDto, HttpStatus.OK));
        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.of(existingSalary));
        when(salaryRepo.save(any(Salary.class))).thenReturn(existingSalary);

        Salary result = salaryService.updateSalary(1L, salary);

        assertNotNull(result);
        assertEquals(2000.0, result.getBasic());
        verify(salaryRepo).findByemployeeId(1L);
        verify(salaryRepo).save(any(Salary.class));
    }

    @Test
    public void testDeleteSalary_Success() {
        Salary salary = new Salary();
        salary.setEmployeeId(1L);

        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.of(salary));

        salaryService.deleteSalary(1L);

        verify(salaryRepo).findByemployeeId(1L);
        verify(salaryRepo).delete(salary);
    }

    @Test
    public void testDeleteSalary_ResourceNotFoundException() {
        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> salaryService.deleteSalary(1L));
    }

    @Test
    public void testGetSalary_Success() {
        Salary salary = new Salary();
        salary.setEmployeeId(1L);

        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.of(salary));

        Salary result = salaryService.getSalary(1L);

        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
        verify(salaryRepo).findByemployeeId(1L);
    }

    @Test
    public void testGetSalary_ResourceNotFoundException() {
        when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> salaryService.getSalary(1L));
    }
}
