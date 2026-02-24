package com.ryan.loan_payment_system.common.loan.service;

import com.ryan.loan_payment_system.common.exception.ResourceNotFoundException;
import com.ryan.loan_payment_system.common.loan.dto.CreateLoanRequest;
import com.ryan.loan_payment_system.common.loan.dto.LoanResponse;
import com.ryan.loan_payment_system.common.loan.entity.Loan;
import com.ryan.loan_payment_system.common.loan.entity.LoanStatus;
import com.ryan.loan_payment_system.common.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanServiceImplementation implements LoanService{
    private final LoanRepository loanRepository;

    @Override
    public LoanResponse createLoan(CreateLoanRequest request) {
        Objects.requireNonNull(request, "Create loan request must not be null");
        Objects.requireNonNull(request.loanAmount(), "Loan amount must not be null");
        Objects.requireNonNull(request.term(), "Term must not be null");

        Loan loan = Loan.builder()
                .loanAmount(request.loanAmount())
                .remainingBalance(request.loanAmount())
                .term(request.term())
                .status(LoanStatus.ACTIVE)
                .build();

        Loan saved = loanRepository.save(loan);
        return LoanResponse.from(saved);
    }

    @Override
    public LoanResponse getLoanById(Long loanId) {
        Objects.requireNonNull(loanId, "Loan ID must not be null");

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

        return LoanResponse.from(loan);
    }

    @Override
    public Page<LoanResponse> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable).map(LoanResponse::from);
    }

}
