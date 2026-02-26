package com.chathura.lapmart.product_service_api.advisor;


import com.chathura.lapmart.product_service_api.exception.EntryNotFoundException;
import com.chathura.lapmart.product_service_api.util.StandardResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponseDto> handleEntryNotFoundException(EntryNotFoundException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(404, e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponseDto> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(
                new StandardResponseDto(400, "Validation Failed!", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponseDto> handleGeneralException(Exception e) {
        return new ResponseEntity<>(
                new StandardResponseDto(500, "Internal Server Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}