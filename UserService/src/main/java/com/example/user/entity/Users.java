package com.example.user.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.antlr.v4.runtime.misc.NotNull;
//import javax.validation.constraints.NotNull;
import jakarta.validation.constraints.NotNull;
import com.example.user.enums.Role;

import java.time.LocalDate;

//@Entity
@Data
@NoArgsConstructor
@Entity
@Table(name = "Employee")

//@Entity(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull(message = "First Name is Required")
    private String f_name;

    @NotNull(message = "Last Name is Required")
    private String l_name;

    @Past(message = "Date of Birth Should be valid")
    private LocalDate date_of_birth;

    @NotNull(message = "First Name is Required")
    private LocalDate hire_Date;

    @NotNull(message = "First Name is Required")
    @Email(message = "Email should be in a proper format")
    private String Email;

    @NotNull(message = "Phone Number is Required")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number should have ten digits")
    private String Phone_Number;

    @NotNull(message ="Assign a role to Employee")
    @Enumerated(EnumType.STRING)
    private String role;

    @NotNull(message = "Manager Id is required")
    private long Manager_Id;

    @NotNull(message = "Required Field")
    private double total_ctc;

    @NotNull(message = "Set a temporary Password")
    private String password;

}
