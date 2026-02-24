package com.ryan.loan_payment_system.payment.dto;

import com.ryan.loan_payment_system.payment.entity.Payment;

import java.math.BigDecimal;

public record PaymentResponse(
        Long paymentId,
        Long loanId,
        BigDecimal paymentAmount,
        BigDecimal remainingLoanBalance,
        String loanStatus
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getLoan().getLoanId(),
                payment.getPaymentAmount(),
                payment.getLoan().getRemainingBalance(),
                payment.getLoan().getStatus().name()
        );
    }
}
