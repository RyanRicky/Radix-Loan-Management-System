package com.ryan.loan_payment_system.payment.repository;

import com.ryan.loan_payment_system.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAllByLoan_LoanId(Long loanId, Pageable pageable);
}
