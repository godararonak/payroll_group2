package com.example.user.exception;
import com.example.user.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   // used for exception handling of whole controller, part of AOP

public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse resourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return  apiResponse;

    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ApiResponse duplicateResourceException(DuplicateResourceException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return  apiResponse;

    }


}
