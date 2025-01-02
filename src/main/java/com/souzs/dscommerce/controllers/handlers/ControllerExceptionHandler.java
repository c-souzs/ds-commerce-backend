package com.souzs.dscommerce.controllers.handlers;

import com.souzs.dscommerce.dto.CustomError;
import com.souzs.dscommerce.dto.ValidationError;
import com.souzs.dscommerce.services.exceptions.DatabaseException;
import com.souzs.dscommerce.services.exceptions.ForbiddenException;
import com.souzs.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// Quando as excecoes contidas nessa classe estouram no sistema
// o metodo que referencia a excecao é executado.
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Dados inválidos.", request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(errorField -> error.addError(errorField.getField(), errorField.getDefaultMessage()));

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
