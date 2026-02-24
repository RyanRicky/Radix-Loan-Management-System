package com.ryan.loan_payment_system.loan.service;

import com.ryan.loan_payment_system.loan.dto.CreateLoanRequest;
import com.ryan.loan_payment_system.loan.dto.LoanResponse;
import com.ryan.loan_payment_system.loan.entity.Loan;
import com.ryan.loan_payment_system.loan.entity.LoanStatus;
import com.ryan.loan_payment_system.loan.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplementationTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImplementation loanService;

    private Loan mockLoan;

    @BeforeEach
    void setUp() {
        mockLoan = Loan.builder()
                .loanId(1L)
                .loanAmount(new BigDecimal("5000.00"))
                .remainingBalance(new BigDecimal("5000.00"))
                .term(12)
                .status(LoanStatus.ACTIVE)
                .build();
    }

    @Test
    void createLoan_ShouldSuccessfullyCreateAndReturnLoan() {
        CreateLoanRequest request = new CreateLoanRequest(new BigDecimal("5000.00"), 12);

        when(loanRepository.save(any(Loan.class))).thenReturn(mockLoan);

        LoanResponse response = loanService.createLoan(request);

        assertThat(response).isNotNull();
        assertThat(response.loanId()).isEqualTo(1L);
        assertThat(response.loanAmount()).isEqualByComparingTo("5000.00");
        assertThat(response.remainingBalance()).isEqualByComparingTo("5000.00");
        assertThat(response.term()).isEqualTo(12);
        assertThat(response.status()).isEqualTo("ACTIVE");
    }

    @Test
    void createLoan_ShouldSetRemainingBalanceEqualToLoanAmount() {
        CreateLoanRequest request = new CreateLoanRequest(new BigDecimal("10000.00"), 24);

        Loan savedLoan = Loan.builder()
                .loanId(2L)
                .loanAmount(new BigDecimal("10000.00"))
                .remainingBalance(new BigDecimal("10000.00"))
                .term(24)
                .status(LoanStatus.ACTIVE)
                .build();

        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);

        LoanResponse response = loanService.createLoan(request);

        assertThat(response.remainingBalance()).isEqualByComparingTo(response.loanAmount());
    }

    @Test
    void getLoanById_ShouldReturnLoan_WhenExists() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(mockLoan));

        LoanResponse response = loanService.getLoanById(1L);

        assertThat(response).isNotNull();
        assertThat(response.loanId()).isEqualTo(1L);
    }




}