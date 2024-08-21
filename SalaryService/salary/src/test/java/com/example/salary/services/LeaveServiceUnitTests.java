package com.example.salary.services;

import com.example.salary.Dto.Leave;
import com.example.salary.Dto.UserDto;
import com.example.salary.Entity.Leaves;
import com.example.salary.Entity.Salary;
import com.example.salary.Entity.SalaryPerMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.salary.Repository.LeavesRepo;
import com.example.salary.Repository.SalaryPerMonthRepo;
import com.example.salary.Repository.SalaryRepo;
import com.example.salary.Services.Impl.LeaveServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import org.modelmapper.ModelMapper;



@ExtendWith(MockitoExtension.class)
public class LeaveServiceUnitTests {
        @InjectMocks
        private LeaveServiceImpl leaveService;

        @Mock
        private LeavesRepo leavesRepo;

        @Mock
        private ModelMapper modelMapper;

        @Mock
        private RestTemplate restTemplate;

        @Mock
        private SalaryPerMonthRepo spmRepo;

        @Mock
        private SalaryRepo salaryRepo;

        // Test for takeLeave
        @Test
        public void testTakeLeave() {
            Leaves leave = new Leaves();
            leave.setEmployeeId(1L);
            leave.setStartDate(LocalDate.of(2023, 8, 1));
            leave.setEndDate(LocalDate.of(2023, 8, 5));

            UserDto userDto = new UserDto();
            userDto.setEmail("test@example.com");

            when(restTemplate.getForObject(anyString(), eq(UserDto.class))).thenReturn(userDto);
            when(leavesRepo.findByemployeeId(1L)).thenReturn(Collections.emptyList());
            when(modelMapper.map(any(Leaves.class), eq(Leaves.class))).thenReturn(leave);
            when(leavesRepo.save(any(Leaves.class))).thenReturn(leave);

            Leaves result = leaveService.takeLeave(leave);

            assertNotNull(result);
            verify(restTemplate).getForObject(anyString(), eq(UserDto.class));
            verify(leavesRepo).findByemployeeId(1L);
            verify(leavesRepo).save(any(Leaves.class));
        }

        // Test for fetchAllPending
        @Test
        public void testFetchAllPending() {
            Leaves leave1 = new Leaves();
            leave1.setStatus("Pending");

            Leaves leave2 = new Leaves();
            leave2.setStatus("Approved");

            when(leavesRepo.findAll()).thenReturn(Arrays.asList(leave1, leave2));

            List<Leaves> pendingLeaves = leaveService.fetchAllPending();

            assertEquals(1, pendingLeaves.size());
            assertEquals("Pending", pendingLeaves.get(0).getStatus());
            verify(leavesRepo).findAll();
        }

        // Test for fetchAllLeavesByEmployeeId
        @Test
        public void testFetchAllLeavesByEmployeeId() {
            Leaves leave1 = new Leaves();
            leave1.setEmployeeId(1L);
            Leaves leave2 = new Leaves();
            leave2.setEmployeeId(2L);

            when(leavesRepo.findAll()).thenReturn(Arrays.asList(leave1, leave2));

            List<Leaves> leaves = leaveService.fetchAllLeavesByEmployeeId(1L);

            assertEquals(1, leaves.size());
            assertEquals(1L, leaves.get(0).getEmployeeId().longValue());
            verify(leavesRepo).findAll();
        }

        // Test for updateLeave
        @Test
        public void testUpdateLeave() {
            Leaves existingLeave = new Leaves();
            existingLeave.setId(1L);
            existingLeave.setLeaveType("Sick Leave");

            Leave updatedLeave = new Leave();
            updatedLeave.setId(1);
            updatedLeave.setLeaveType("Casual Leave");

            when(leavesRepo.findById(1)).thenReturn(Optional.of(existingLeave));
            when(leavesRepo.save(any(Leaves.class))).thenReturn(existingLeave);
            when(modelMapper.map(existingLeave, Leave.class)).thenReturn(updatedLeave);

            Leave result = leaveService.updateLeave(updatedLeave);

            assertNotNull(result);
            assertEquals("Casual Leave", result.getLeaveType());
            verify(leavesRepo).findById(1);
            verify(leavesRepo).save(any(Leaves.class));
        }

        // Test for changeLeaveStatusToApprove
        @Test
        public void testChangeLeaveStatusToApprove() {
            Leaves leave = new Leaves();
            leave.setId(1L);
            leave.setStatus("Pending");

            when(leavesRepo.findById(1)).thenReturn(Optional.of(leave));

            leaveService.changeLeaveStatusToApprove(1);

            assertEquals("Approve", leave.getStatus());
            verify(leavesRepo).findById(1);
            verify(leavesRepo).save(leave);
        }

        // Test for findAllLeavesInMonth
        @Test
        public void testFindAllLeavesInMonth() {
            Leaves leave = new Leaves();
            leave.setStartDate(LocalDate.of(2023, 8, 1));
            leave.setEndDate(LocalDate.of(2023, 8, 5));
            leave.setEmployeeId(1L);

            when(leavesRepo.findByemployeeId(1L)).thenReturn(Collections.singletonList(leave));

            List<LocalDate> result = leaveService.findAllLeavesInMonth(1L, 8, 2023);

            assertEquals(4, result.size());
            verify(leavesRepo).findByemployeeId(1L);
        }

        // Test for generateSalary
        @Test
        public void testGenerateSalary() {
            Salary salary = new Salary();
            salary.setTotalCtc(1200000.0);

            when(salaryRepo.findByemployeeId(1L)).thenReturn(Optional.of(salary));
            when(spmRepo.save(any(SalaryPerMonth.class))).thenReturn(new SalaryPerMonth());

            SalaryPerMonth result = leaveService.generateSalary(1L, 8, 2023);

            assertNotNull(result);
            verify(salaryRepo).findByemployeeId(1L);
            verify(spmRepo).save(any(SalaryPerMonth.class));
        }

}
