package com.hardik.SpringSecurity.SecurityApplication.advice;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ApiError {

    private LocalDateTime localDateTime;

    private String error;

    private HttpStatus statusCode;

    public ApiError(){
        this.localDateTime=LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus statusCode){
        this();
        this.error=error;
        this.statusCode=statusCode;
    }
}
