package com.ryan.loan_payment_system.loan.dto;

import com.ryan.loan_payment_system.loan.entity.Loan;

import java.math.BigDecimal;

public record LoanResponse(
        Long loanId,
        BigDecimal loanAmount,
        BigDecimal remainingBalance,
        Integer term,
        String status
) {
    public static LoanResponse from(Loan loan) {
        return new LoanResponse(
                loan.getLoanId(),
                loan.getLoanAmount(),
                loan.getRemainingBalance(),
                loan.getTerm(),
                loan.getStatus().name()
        );
    }
}
