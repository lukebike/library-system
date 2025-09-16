package com.storedemo.librarysystem.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");
        if(statusCode == HttpStatus.NOT_FOUND.value()){
            message = "The requested resource was not found";
        }
         else if(statusCode == HttpStatus.UNAUTHORIZED.value()){
            message = "You are not authorized to access this resource";
        }
        return ResponseEntity.status(statusCode != null ? statusCode : 500).body(message != null ? message : "An unexpected error occurred");
    }
}
