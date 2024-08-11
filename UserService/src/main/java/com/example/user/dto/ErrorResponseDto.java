package com.example.user.dto;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class ErrorResponseDto {

    private String errorMessage;
    private String apiPath;
    private LocalDateTime errorTime;
    private HttpStatus statusCode;

}
