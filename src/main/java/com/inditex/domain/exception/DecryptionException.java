package com.inditex.domain.exception;

public class DecryptionException extends RuntimeException {
    public DecryptionException(String message, Exception e) {
        super(message);
    }
}
