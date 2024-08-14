package com.example.user.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "employee")
@NoArgsConstructor
@Getter
@Setter
@Data

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull(message = "First Name is Required")
    @Column(name = "First_Name")
    private String f_name;

    @NotNull(message = "Last Name is Required")
    @Column(name = "Last_Name")
    private String l_name;

    @Past(message = "Date of Birth Should be valid")
    @Column(name = "Date_OF_Birth")
    private LocalDate date_of_birth;

    @NotNull(message = "First Name is Required")
    @Column(name = "Hire_Date")
    private LocalDate hire_Date;

    @NotNull(message = "First Name is Required")
    @Email(message = "Email should be in a proper format")
    @Column(name="email")
    private String email;

    @NotNull(message = "Phone Number is Required")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number should have ten digits")
    @Column(name="phone_number")
    private String Phone_Number;

    @NotNull(message ="Assign a role to Employee")
//    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private String role;

    @NotNull(message = "Manager Id is required")
    @Column(name="Manager_id")
    private long Manager_Id;

    @NotNull(message = "Required Field")
    @Column(name = "Total_CTC")
    private double total_ctc;

    @NotNull(message = "Set a temporary Password")
    @Column(name = "Password")
    private String password;

}
