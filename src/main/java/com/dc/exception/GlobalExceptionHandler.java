package com.dc.exception;


import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
       Map<String,String> fieldErrors = new HashMap<>();

       //field level error
       e.getBindingResult().getFieldErrors().forEach(error->{
           fieldErrors.put(error.getField(),error.getDefaultMessage());
       });

       //class level errors
        e.getBindingResult().getGlobalErrors().forEach(error->{
            fieldErrors.put(error.getObjectName(),error.getDefaultMessage());
        });
       List<String> fieldMessages = fieldErrors.values().stream().toList();
       Map<String,Object> errors = new HashMap<>();
       errors.put("errors", fieldErrors);
       errors.put("errorMessages", fieldMessages);
       return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException e){
        Map<String,String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(
                error -> errors.put(error.getPropertyPath().toString(),error.getMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Map<String,Object>> handleCustomExceptions(GenericException e){
        Map<String,Object> errors = new HashMap<>();
        Map<String,String> fieldErrors = new HashMap<>();
        fieldErrors.put(e.getFieldName(),e.getMessage());
        errors.put("errors",fieldErrors);
        errors.put("errorMessages", fieldErrors.values().stream().toList());
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String,String>> handleNoResourceFoundException(NoResourceFoundException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("Invalid URL", e.getResourcePath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }


    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleRoleNotFoundException(RoleNotFoundException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("roleID", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }



    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Map<String,String>> handleLockedException(LockedException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("user", e.getMessage());
        return ResponseEntity.status(HttpStatus.LOCKED).body(errors);
    }


    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Map<String,String>> handleUserLoginException(InternalAuthenticationServiceException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleBadCredentialsException(BadCredentialsException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", "In correct Password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}
