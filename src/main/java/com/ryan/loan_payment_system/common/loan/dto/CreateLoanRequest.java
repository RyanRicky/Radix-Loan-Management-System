package com.ryan.loan_payment_system.common.loan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateLoanRequest(
        @NotNull(message = "Loan amount is required")
        @Positive(message = "Loan amount must be positive")
        BigDecimal loanAmount,

        @NotNull(message = "Term is required")
        @Min(value = 1, message = "Term must be at least 1 month")
        Integer term
) {}
