package com.target_india.digitize_time_table.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ExceptionModel> InvalidRole(InvalidRoleException ex){
        ExceptionModel response = new ExceptionModel(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ExceptionModel> InvalidDateException(InvalidDateException ex){
        ExceptionModel response = new ExceptionModel(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionModel> ResourceNotFound(ResourceNotFoundException ex){
        ExceptionModel response = new ExceptionModel(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionModel> ResourceAlreadyExist(ResourceAlreadyExistException ex){
        ExceptionModel response = new ExceptionModel(HttpStatus.CONFLICT.value(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
