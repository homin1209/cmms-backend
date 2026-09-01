package com.homin.cmms.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EquipmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEquipmentNotFoundException(EquipmentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
        ));
    }

    @ExceptionHandler(DuplicateEquipmentCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEquipmentCodeException(DuplicateEquipmentCodeException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message
        ));
    }

    @ExceptionHandler(InspectionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInspectionNotFoundException(InspectionNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(FailureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFailureNotFoundException(FailureNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(MaintenanceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMaintenanceNotFoundException(MaintenanceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(InvalidFailureStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFailureStatusTransition(InvalidFailureStatusTransitionException exception) {
        String message = exception.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message
                ));
    }

    @ExceptionHandler(InvalidMaintenanceStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMaintenanceStatusTransition(InvalidMaintenanceStatusTransitionException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(DuplicateUserEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserEmailException(DuplicateUserEmailException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginException(InvalidLoginException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        exception.getMessage()
                ));
    }
}
