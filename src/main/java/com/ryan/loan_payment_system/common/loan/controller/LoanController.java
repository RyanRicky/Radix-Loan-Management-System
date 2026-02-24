package com.ryan.loan_payment_system.common.loan.controller;

import com.ryan.loan_payment_system.common.dto.ApiResponse;
import com.ryan.loan_payment_system.common.loan.dto.CreateLoanRequest;
import com.ryan.loan_payment_system.common.loan.dto.LoanResponse;
import com.ryan.loan_payment_system.common.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<ApiResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
        LoanResponse response = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse> getLoan(@PathVariable Long loanId) {
        LoanResponse response = loanService.getLoanById(loanId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllLoans(
            @PageableDefault(size = 10, sort = "loanId") Pageable pageable) {
        Page<LoanResponse> page = loanService.getAllLoans(pageable);
        return ResponseEntity.ok(ApiResponse.ok(page));
    }
}
