package com.api.todoapi.exceptions;

import com.api.todoapi.exceptions.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponse> handleException(Exception ex) {
        var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro não identificado");
        if (this.printStackTrace)
            errorResponse.setStack(ExceptionUtils.getStackTrace(ex));

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.CONFLICT, ex.getMostSpecificCause().getMessage());
        if (this.printStackTrace)
            errorResponse.setStack(ExceptionUtils.getStackTrace(ex));

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage());
        if (this.printStackTrace)
            errorResponse.setStack(ExceptionUtils.getStackTrace(ex));

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        if (this.printStackTrace)
            errorResponse.setStack(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
