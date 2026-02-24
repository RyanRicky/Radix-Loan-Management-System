package com.ryan.loan_payment_system.payment.service;

import com.ryan.loan_payment_system.payment.dto.CreatePaymentRequest;
import com.ryan.loan_payment_system.payment.dto.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentResponse processPayment(CreatePaymentRequest request);
    Page<PaymentResponse> getPaymentsByLoanId(Long loanId, Pageable pageable);
    Page<PaymentResponse> getAllPayments(Pageable pageable);
}
