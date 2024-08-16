package com.example.salary.ExceptionHandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidQueryParameterException extends RuntimeException {

   long EmployeeId;
    int month;
    int year;

    public InvalidQueryParameterException() {

        super(String.format(" INVALID DATA ,PASS THE CORRECT DATA! "));

    }

}
