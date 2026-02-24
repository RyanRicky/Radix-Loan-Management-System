package com.ryan.loan_payment_system.common.loan.service;


import com.ryan.loan_payment_system.common.loan.dto.CreateLoanRequest;
import com.ryan.loan_payment_system.common.loan.dto.LoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    LoanResponse createLoan(CreateLoanRequest request);
    LoanResponse getLoanById(Long loanId);
    Page<LoanResponse> getAllLoans(Pageable pageable);

}
