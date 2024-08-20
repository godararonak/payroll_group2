package com.example.AuthServer.service.impl;

import com.example.AuthServer.dto.EmployeeDto;
import com.example.AuthServer.dto.JWTDto;
import com.example.AuthServer.dto.ResponseDto;
import com.example.AuthServer.entity.Role;
import com.example.AuthServer.entity.User;
import com.example.AuthServer.payload.LoginDTO;
import com.example.AuthServer.payload.RegisterDTO;
import com.example.AuthServer.payload.ResetPasswordDto;
import com.example.AuthServer.repository.RoleRepository;
import com.example.AuthServer.repository.UserRepository;
import com.example.AuthServer.security.JwtTokenProvider;
import com.example.AuthServer.service.AuthService;
import com.example.AuthServer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailService emailService;


    @Override
    public JWTDto login(LoginDTO loginDto) {
        // if login using one time accessible password :-
        String pwd=loginDto.getPassword();
        if(userRepository.existsByUsername(loginDto.getUsername())){
            User user=userRepository.findByUsername(loginDto.getUsername()).get();
                String tempPwd=user.getId()+user.getFirstName().toLowerCase()+user.getLastName().toLowerCase();
                if(tempPwd.equals(pwd) && user.isFirstLogin()){
//                    user.setFirstLogin(false);
                    userRepository.save(user);
                    JWTDto jwtDto=new JWTDto();
                    jwtDto.setFirstLogin(true);
                    // forward user to reset password page
                    return jwtDto;
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByUsername(loginDto.getUsername()).get();
        Set<Role> roles = user.getRoles();
        roles=user.getRoles();
        String role = roles.iterator().next().getName();
        JWTDto jwtDto= new JWTDto();
        jwtDto.setJwt(token);
        jwtDto.setRole(role);
        jwtDto.setFirstLogin(false);
        return jwtDto;
    }

    @Override
    public ResponseDto register(RegisterDTO registerDto) {

        ResponseDto responseDto=new ResponseDto();

        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new RuntimeException("Username already exist");
        }

        // add check for email exists in database
//        if(userRepository.existsByEmail(registerDto.getEmail())){
//            throw new RuntimeException("email is already exists");
//        }

        // assign a role to the user
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        long currentTimestamp = Instant.now().toEpochMilli();

        String tempPassword = currentTimestamp + registerDto.getFirstName() + registerDto.getLastName();

//        user.setEmail(registerDto.getEmail());
        user.setPassword(tempPassword);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(registerDto.getRoleName()).get();
        roles.add(userRole);
        user.setRoles(roles);

        // save the user in the database
//        emailService.sendTemporaryPasswordEmail(user.getUsername(),tempPassword);
        User savedUser=userRepository.save(user);
        String tempPasswordToMail = savedUser.getId() + registerDto.getFirstName().toLowerCase() + registerDto.getLastName().toLowerCase();
        user.setPassword(tempPasswordToMail);
        user.setFirstLogin(true);
        userRepository.save(user);
        emailService.sendTemporaryPasswordEmail(user.getUsername(),tempPasswordToMail);

        RestTemplate restTemplate = new RestTemplate();

        // Prepare the DTO for the employee service
        EmployeeDto employeeDTO = new EmployeeDto();
        employeeDTO.setF_name(registerDto.getFirstName());
        employeeDTO.setL_name(registerDto.getLastName());
        employeeDTO.setEmail(registerDto.getUsername());
        employeeDTO.setRole(registerDto.getRoleName());
        employeeDTO.setManager_Id(1);

        String url = "http://localhost:8181/api/v1/employees/createEmployee";

        // Make the POST request to the employee service
        ResponseEntity<String> response = restTemplate.postForEntity(url, employeeDTO, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            throw new RuntimeException("User registered successfully");
        } else {
            throw new RuntimeException("Failed to register employee in the employee service");
        }
        // call above post url of employee service to save that user as employee
    }

    @Override
    public ResponseDto resetPassword(ResetPasswordDto resetPasswordDto) {
        String password=resetPasswordDto.getNewPassword();
        String conformPassword=resetPasswordDto.getConformPassword();
        if(!password.equals(conformPassword)){
            throw new RuntimeException("Password not matches");
        }
        if(!userRepository.existsByUsername(resetPasswordDto.getUsername())){
            throw new RuntimeException("Username not registered");
        }

        if (!isPasswordValid(password)) {
            throw new RuntimeException("Password does not meet the required criteria");
        }

        User user=userRepository.findByUsername(resetPasswordDto.getUsername()).get();
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getConformPassword()));
        user.setFirstLogin(false);
        userRepository.save(user);
        ResponseDto responseDto=new ResponseDto();
        responseDto.setResponse("Password reset done");
        return responseDto;
        // forward user to login page;
    }

    @Override
    public void forgotPassword(ResetPasswordDto resetPasswordDto) {
        String password=resetPasswordDto.getNewPassword();
        String conformPassword=resetPasswordDto.getConformPassword();
        if(!password.equals(conformPassword)){
            throw new RuntimeException("Password not matches");
        }
        if(!userRepository.existsByUsername(resetPasswordDto.getUsername())){
            throw new RuntimeException("Username not registered");
        }

        if (!isPasswordValid(password)) {
            throw new RuntimeException("Password does not meet the required criteria");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(resetPasswordDto.getConformPassword());
        User user=userRepository.findByUsername(resetPasswordDto.getUsername()).get();
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email)  {
        User user = userRepository.findByUsername(email).get();
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Username not registered");
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    private boolean isPasswordValid(String password) {
        String MIN_LENGTH = "8";
        String MAX_LENGTH = "50";
        boolean SPECIAL_CHAR_NEEDED = true;  // Set to true if special characters are required

        String ONE_DIGIT = "(?=.*[0-9])";
        String LOWER_CASE = "(?=.*[a-z])";
        String UPPER_CASE = "(?=.*[A-Z])";
        String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[@#$%^&+=])" : "";
        String NO_SPACE = "(?=\\S+$)";
        String MIN_MAX_CHAR = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";

        String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;

        return password.matches(PATTERN);
    }


    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
