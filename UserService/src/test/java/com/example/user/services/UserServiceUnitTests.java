package com.example.user.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.user.entity.Users;
import com.example.user.repository.UserRepo;

import com.example.user.services.UserImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.modelmapper.internal.util.Assert;

import java.sql.SQLOutput;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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



}
