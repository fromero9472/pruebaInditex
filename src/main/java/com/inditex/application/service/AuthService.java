package com.inditex.application.service;

import com.inditex.domain.port.in.ValidateCredential;
import com.inditex.domain.port.out.CredentialValidator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Primary
@Service
public class AuthService implements ValidateCredential {

    private final CredentialValidator credentialValidator;

    public AuthService(CredentialValidator credentialValidator) {
        this.credentialValidator = credentialValidator;
    }

    @Override
    public boolean validateCredentials(String user, String contrasena) {
        return credentialValidator.validate(user, contrasena);
    }
}
