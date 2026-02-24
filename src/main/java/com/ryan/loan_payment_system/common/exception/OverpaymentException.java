package com.ryan.loan_payment_system.common.exception;

public class OverpaymentException extends RuntimeException {
    public OverpaymentException(String message) {
        super(message);
    }
}
