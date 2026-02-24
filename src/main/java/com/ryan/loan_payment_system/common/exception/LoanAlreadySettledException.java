package com.ryan.loan_payment_system.common.exception;

public class LoanAlreadySettledException extends RuntimeException {
    public LoanAlreadySettledException(String message) {
        super(message);
    }
}