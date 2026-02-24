package com.ryan.loan_payment_system.common.loan.repository;

import com.ryan.loan_payment_system.common.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
