package com.inditex.domain.port.out;

public interface CredentialValidator {
    boolean validate(String user, String password);
}