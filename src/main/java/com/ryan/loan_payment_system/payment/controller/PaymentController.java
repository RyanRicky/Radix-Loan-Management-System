package com.ryan.loan_payment_system.payment.controller;

import com.ryan.loan_payment_system.common.dto.ApiResponse;
import com.ryan.loan_payment_system.payment.dto.CreatePaymentRequest;
import com.ryan.loan_payment_system.payment.dto.PaymentResponse;
import com.ryan.loan_payment_system.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse> processPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPayments(
            @PageableDefault(size = 10, sort = "paymentId") Pageable pageable) {
        Page<PaymentResponse> page = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(ApiResponse.ok(page));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<ApiResponse> getPaymentsByLoan(
            @PathVariable Long loanId,
            @PageableDefault(size = 10, sort = "paymentId") Pageable pageable) {
        Page<PaymentResponse> page = paymentService.getPaymentsByLoanId(loanId, pageable);
        return ResponseEntity.ok(ApiResponse.ok(page));
    }
}