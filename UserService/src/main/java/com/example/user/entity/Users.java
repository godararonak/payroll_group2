package com.example.user.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@Entity
@Data
@NoArgsConstructor

@Entity(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String f_name;
    private String l_name;
    private LocalDate date_of_birth;
    private LocalDate hire_Date;
    private String Email;
    private String Phone_Number;
    private String role;
    private long Manager_Id;
    private double total_ctc;
    private String password;


}
