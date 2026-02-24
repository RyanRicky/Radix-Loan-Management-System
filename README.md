# Loan Payment System Guide

# Project Structure

```

#Business Logic
src/main/java/com.ryan.loan_payment_system/
├── LoanPaymentSystemApplication.java
├── common/
│   ├── dto/
│   │   └── ApiResponse.java          # Universal API response record
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       ├── LoanAlreadySettledException.java
│       ├── OverpaymentException.java
│       └── ResourceNotFoundException.java
├── loan/
│   ├── controller/LoanController.java
│   ├── dto/LoanDtos.java
│   ├── entity/Loan.java
│   ├── entity/LoanStatus.java
│   ├── repository/LoanRepository.java
│   └── service/LoanService.java
└── payment/
    ├── controller/PaymentController.java
    ├── dto/PaymentDtos.java
    ├── entity/Payment.java
    ├── repository/PaymentRepository.java
    └── service/PaymentService.java
    
#TESTS    
src/main/java/test/java/com.ryan.loan_payment_system/
├── LoanPaymentSystemApplicationTests.java
├── loan.service/
│       ├── LoanServiceImplementationTest.java
├── payment.service/
│       ├── PaymentServiceImplementationTest.java


```

---

# Build & Run

```
mvn spring-boot:run
```

The application starts on 'http://localhost:8080'


---

# API Reference

All responses use the universal `ApiResponse` wrapper with JSON responses:

```
Success
{
  "success": true,
  "data": { ... },
  "result": "OK"
}
```

```
Failure
{
  "success": false,
  "data": "Error message",
  "result": "BAD"
}
```



# TESTING THE API


# Create a Loan

```
curl -X POST http://localhost:8080/loans \
  -H "Content-Type: application/json" \
  -d '{
    "loanAmount": 5000.00,
    "term": 12
  }'
```


# Get Loan by ID

```
curl http://localhost:8080/loans/1
```

---

# Get All Loans (Paginated), You can also test without Pagination

```
curl "http://localhost:8080/loans?page=0&size=10&sort=loanId,asc"
```


## Payment Endpoints

# Record/Create a Payment

```
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{
    "loanId": 1,
    "paymentAmount": 500.00
  }'
```



# Get All Payments (Paginated)

```
curl "http://localhost:8080/payments?page=0&size=10"
```

# Get Payments for a Loan (Paginated)

```
curl "http://localhost:8080/payments/loan/1?page=0&size=10"
```


# All list endpoints support standard Spring pagination query params but you can also request without pagination:


## UNIT TEST
You can run the tests using the command below
```
mvn test
```

Or navigate to the classes below and run each test manually or the entire test ckass:
```
src/main/java/test/java/com.ryan.loan_payment_system/
├── LoanPaymentSystemApplicationTests.java
├── loan.service/
│       ├── LoanServiceImplementationTest.java
├── payment.service/
│       ├── PaymentServiceImplementationTest.java
```
