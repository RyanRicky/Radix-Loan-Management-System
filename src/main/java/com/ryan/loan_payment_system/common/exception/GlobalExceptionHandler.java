package com.ryan.loan_payment_system.common.exception;

import com.ryan.loan_payment_system.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.bad(ex.getMessage()));
    }

    @ExceptionHandler(OverpaymentException.class)
    public ResponseEntity<ApiResponse> handleOverpayment(OverpaymentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.bad(ex.getMessage()));
    }

    @ExceptionHandler(LoanAlreadySettledException.class)
    public ResponseEntity<ApiResponse> handleLoanAlreadySettled(LoanAlreadySettledException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.bad(ex.getMessage()));
    }

}
