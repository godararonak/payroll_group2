package com.example.salary.ExceptionHandling;
import com.example.salary.Dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




    @ExceptionHandler(MethodArgumentNotValidException.class)

//    MethodArgumentNotValidException this is bydeafult exception class that throw error while validation failled
// from where I get its name ??? (making postman request, error messgae seens, from there i know about this class)

    public ResponseEntity<?> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){

        Map<String,String> resp=new HashMap<>();
        // field => error message (e.g name => Username must be min of 4 characters)

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : errors) {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                resp.put(fieldName, message);
        }

        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);

    }

}
