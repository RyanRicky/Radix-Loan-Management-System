package com.ryan.loan_payment_system.loan.service;


import com.ryan.loan_payment_system.loan.dto.CreateLoanRequest;
import com.ryan.loan_payment_system.loan.dto.LoanResponse;
import com.ryan.loan_payment_system.loan.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    LoanResponse createLoan(CreateLoanRequest request);
    LoanResponse getLoanById(Long loanId);
    Page<LoanResponse> getAllLoans(Pageable pageable);
    Loan getLoanEntityById(Long loanId);
    Loan saveLoan(Loan loan);

}
