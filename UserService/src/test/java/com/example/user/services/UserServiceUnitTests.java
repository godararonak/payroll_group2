package com.example.user.services;

import com.example.user.entity.Users;
import com.example.user.exception.DuplicateResourceException;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.repository.UserRepo;

import com.example.user.services.UserImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetEmployeeById() {
        Users user = new Users();
        user.setId(1L);
        user.setF_name("parush_ronak_shivam_nandini_rajdeep_aghnash");
        when(userRepo.findById(1l)).thenReturn(Optional.of(user));

        Users dataFromService = userService.getEmployeeById(1l);
        System.out.println(dataFromService.getF_name());
        assertEquals(dataFromService.getF_name(), user.getF_name());
    }

    @Test
    public void testExists_ShouldReturnTrue() {
        Long employeeId = 1L;

        when(userRepo.existsById(employeeId)).thenReturn(true);

        Boolean result = userService.exists(employeeId);

        assertTrue(result);
        verify(userRepo, times(1)).existsById(employeeId);
    }

    @Test
    public void testExists_ShouldReturnFalse() {
        Long employeeId = 1L;

        when(userRepo.existsById(employeeId)).thenReturn(false);

        Boolean result = userService.exists(employeeId);

        assertFalse(result);
        verify(userRepo, times(1)).existsById(employeeId);
    }

    @Test
    public void testGetAllEmployees_ShouldReturnListOfEmployees() {
        List<Users> mockUsers = Arrays.asList(new Users(), new Users());

        when(userRepo.findAll()).thenReturn(mockUsers);

        List<Users> result = userService.getAllEmployees();

        assertEquals(2, result.size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void testGetEmployeeById_ShouldReturnEmployee() {
        Long employeeId = 1L;
        Users mockUser = new Users();
        mockUser.setId(employeeId);

        when(userRepo.findById(employeeId)).thenReturn(Optional.of(mockUser));

        Users result = userService.getEmployeeById(employeeId);

        assertEquals(mockUser, result);
        verify(userRepo, times(1)).findById(employeeId);
    }

    @Test
    public void testGetEmployeeById_ShouldThrowException() {
        Long employeeId = 1L;

        when(userRepo.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getEmployeeById(employeeId));
        verify(userRepo, times(1)).findById(employeeId);
    }

    @Test
    public void testCreateEmployee_ShouldSaveEmployee() {
        Users newEmployee = new Users();
        newEmployee.setEmail("test@example.com");

        when(userRepo.findByEmail(newEmployee.getEmail())).thenReturn(Optional.empty());
        when(userRepo.save(newEmployee)).thenReturn(newEmployee);

        Users result = userService.createEmployee(newEmployee);

        assertEquals(newEmployee, result);
        verify(userRepo, times(1)).findByEmail(newEmployee.getEmail());
        verify(userRepo, times(1)).save(newEmployee);
    }

    @Test
    public void testCreateEmployee_ShouldThrowException() {
        Users newEmployee = new Users();
        newEmployee.setEmail("test@example.com");

        when(userRepo.findByEmail(newEmployee.getEmail())).thenReturn(Optional.of(new Users()));

        assertThrows(DuplicateResourceException.class, () -> userService.createEmployee(newEmployee));
        verify(userRepo, times(1)).findByEmail(newEmployee.getEmail());
        verify(userRepo, times(0)).save(any());
    }

    @Test
    public void testUpdateUser_ShouldUpdateEmployee() {
        Long employeeId = 1L;
        Users existingUser = new Users();
        Users updatedUser = new Users();
        updatedUser.setEmail("updated@example.com");

        when(userRepo.findById(employeeId)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(existingUser)).thenReturn(existingUser);

        Users result = userService.updateUser(employeeId, updatedUser);

        assertEquals(existingUser, result);
        verify(userRepo, times(1)).findById(employeeId);
        verify(userRepo, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUser_ShouldThrowException() {
        Long employeeId = 1L;
        Users updatedUser = new Users();

        when(userRepo.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(employeeId, updatedUser));
        verify(userRepo, times(1)).findById(employeeId);
    }




}
