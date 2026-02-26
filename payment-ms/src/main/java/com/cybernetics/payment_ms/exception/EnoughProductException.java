package com.cybernetics.payment_ms.exception;

public class EnoughProductException extends RuntimeException {
    public EnoughProductException(String message) {
        super(message);
    }
}
