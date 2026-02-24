package com.ryan.loan_payment_system.common.dto;

public record ApiResponse(
        boolean success,
        Object data,
        String result
) {
    public static ApiResponse ok(Object data) {
        return new ApiResponse(true, data, "OK");
    }

    public static ApiResponse bad(Object data) {
        return new ApiResponse(false, data, "BAD");
    }
}