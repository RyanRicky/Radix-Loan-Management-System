package com.ryan.loan_payment_system.payment.service;

import com.ryan.loan_payment_system.common.exception.LoanAlreadySettledException;
import com.ryan.loan_payment_system.common.exception.OverpaymentException;
import com.ryan.loan_payment_system.loan.entity.Loan;
import com.ryan.loan_payment_system.loan.entity.LoanStatus;
import com.ryan.loan_payment_system.loan.service.LoanService;
import com.ryan.loan_payment_system.payment.dto.CreatePaymentRequest;
import com.ryan.loan_payment_system.payment.dto.PaymentResponse;
import com.ryan.loan_payment_system.payment.entity.Payment;
import com.ryan.loan_payment_system.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final LoanService loanService;


    @Transactional
    @Override
    public PaymentResponse processPayment(CreatePaymentRequest request) {
        Objects.requireNonNull(request, "Payment request must not be null");
        Objects.requireNonNull(request.loanId(), "Loan ID must not be null");
        Objects.requireNonNull(request.paymentAmount(), "Payment amount must not be null");

        Loan loan = loanService.getLoanEntityById(request.loanId());

        if (loan.getStatus() == LoanStatus.SETTLED) {
            throw new LoanAlreadySettledException(
                    "Loan with ID " + request.loanId() + " is already settled. No further payments accepted."
            );
        }

        BigDecimal paymentAmount = request.paymentAmount();
        BigDecimal remaining = loan.getRemainingBalance();

        if (paymentAmount.compareTo(remaining) > 0) {
            throw new OverpaymentException(
                    "Payment amount " + paymentAmount + " exceeds remaining balance of " + remaining
            );
        }

        BigDecimal newBalance = remaining.subtract(paymentAmount);
        loan.setRemainingBalance(newBalance);

        if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus(LoanStatus.SETTLED);
        }

        loanService.saveLoan(loan);

        Payment payment = Payment.builder()
                .loan(loan)
                .paymentAmount(paymentAmount)
                .build();

        Payment saved = paymentRepository.save(payment);
        return PaymentResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPaymentsByLoanId(Long loanId, Pageable pageable) {
        Objects.requireNonNull(loanId, "Loan ID must not be null");

        // Validate loan existence
        loanService.getLoanEntityById(loanId);
        return paymentRepository.findAllByLoan_LoanId(loanId, pageable).map(PaymentResponse::from);
    }

    @Override
    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentResponse::from);
    }

}
