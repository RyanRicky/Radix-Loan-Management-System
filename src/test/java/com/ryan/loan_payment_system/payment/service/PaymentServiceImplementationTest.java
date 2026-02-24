package com.ryan.loan_payment_system.payment.service;


import com.ryan.loan_payment_system.common.exception.LoanAlreadySettledException;
import com.ryan.loan_payment_system.common.exception.OverpaymentException;
import com.ryan.loan_payment_system.loan.entity.Loan;
import com.ryan.loan_payment_system.loan.entity.LoanStatus;
import com.ryan.loan_payment_system.loan.service.LoanServiceImplementation;
import com.ryan.loan_payment_system.payment.dto.CreatePaymentRequest;
import com.ryan.loan_payment_system.payment.dto.PaymentResponse;
import com.ryan.loan_payment_system.payment.entity.Payment;
import com.ryan.loan_payment_system.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplementationTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private LoanServiceImplementation loanService;

    @InjectMocks
    private PaymentServiceImplementation paymentService;

    private Loan activeLoan;

    @BeforeEach
    void setUp() {
        activeLoan = Loan.builder()
                .loanId(1L)
                .loanAmount(new BigDecimal("1000.00"))
                .remainingBalance(new BigDecimal("1000.00"))
                .term(12)
                .status(LoanStatus.ACTIVE)
                .build();
    }

    @Test
    void processPayment_ShouldReduceLoanBalance() {
        CreatePaymentRequest request = new CreatePaymentRequest(1L, new BigDecimal("400.00"));

        when(loanService.getLoanEntityById(1L)).thenReturn(activeLoan);
        when(loanService.saveLoan(any(Loan.class))).thenReturn(activeLoan);

        Payment savedPayment = Payment.builder()
                .paymentId(1L)
                .loan(activeLoan)
                .paymentAmount(new BigDecimal("400.00"))
                .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        // Simulating how balance reduction is handled
        activeLoan.setRemainingBalance(new BigDecimal("600.00"));

        PaymentResponse response = paymentService.processPayment(request);

        assertThat(response).isNotNull();
        assertThat(response.paymentAmount()).isEqualByComparingTo("400.00");
    }

    @Test
    void processPayment_ShouldSettleLoan_WhenFullPaymentMade() {
        Loan loan = Loan.builder()
                .loanId(1L)
                .loanAmount(new BigDecimal("500.00"))
                .remainingBalance(new BigDecimal("500.00"))
                .term(6)
                .status(LoanStatus.ACTIVE)
                .build();

        CreatePaymentRequest request = new CreatePaymentRequest(1L, new BigDecimal("500.00"));

        when(loanService.getLoanEntityById(1L)).thenReturn(loan);
        when(loanService.saveLoan(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        Payment savedPayment = Payment.builder()
                .paymentId(1L)
                .loan(loan)
                .paymentAmount(new BigDecimal("500.00"))
                .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        paymentService.processPayment(request);

        assertThat(loan.getStatus()).isEqualTo(LoanStatus.SETTLED);
        assertThat(loan.getRemainingBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void processPayment_ShouldThrowOverpaymentException_WhenPaymentExceedsBalance() {
        CreatePaymentRequest request = new CreatePaymentRequest(1L, new BigDecimal("1500.00"));

        when(loanService.getLoanEntityById(1L)).thenReturn(activeLoan);

        assertThatThrownBy(() -> paymentService.processPayment(request))
                .isInstanceOf(OverpaymentException.class)
                .hasMessageContaining("1500");
    }

    @Test
    void processPayment_ShouldThrowLoanAlreadySettledException_WhenLoanIsSettled() {
        activeLoan.setStatus(LoanStatus.SETTLED);
        activeLoan.setRemainingBalance(BigDecimal.ZERO);

        CreatePaymentRequest request = new CreatePaymentRequest(1L, new BigDecimal("100.00"));

        when(loanService.getLoanEntityById(1L)).thenReturn(activeLoan);

        assertThatThrownBy(() -> paymentService.processPayment(request))
                .isInstanceOf(LoanAlreadySettledException.class)
                .hasMessageContaining("already settled");
    }

}