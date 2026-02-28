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

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();
       e.getBindingResult().getFieldErrors().forEach(
               error->errors.put(error.getField(),error.getDefaultMessage()));
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
    public ResponseEntity<Map<String,String>> handleCustomExceptions(GenericException e){
        Map<String,String> errors = new HashMap<>();
        errors.put(e.getFieldName(), e.getMessage());
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
        errors.put("email", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleBadCredentialsException(BadCredentialsException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", "In correct Email or Password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        Map<String,String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}
