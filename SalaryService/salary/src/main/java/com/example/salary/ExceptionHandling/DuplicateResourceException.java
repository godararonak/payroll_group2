package com.example.salary.ExceptionHandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DuplicateResourceException extends RuntimeException{

    String resourceName;
    String fieldName;
    long fieldValue;

    public DuplicateResourceException(String resourceName, String fieldName, long fieldValue) {

        super(String.format("%s already present with %s : %s",resourceName,fieldName,fieldValue));

        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;

    }

    public DuplicateResourceException(String resourceName) {
        super(String.format(resourceName));
        this.resourceName=resourceName;
    }


}
