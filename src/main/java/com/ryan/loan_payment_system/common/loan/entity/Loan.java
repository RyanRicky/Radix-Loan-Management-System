package com.ryan.loan_payment_system.common.loan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal remainingBalance;

    @Column(nullable = false)
    private Integer term;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;
}