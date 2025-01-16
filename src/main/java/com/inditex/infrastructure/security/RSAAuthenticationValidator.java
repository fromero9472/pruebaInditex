package com.inditex.infrastructure.security;

import com.inditex.domain.port.out.CredentialValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAAuthenticationValidator implements CredentialValidator {

    @Value("${jwt.user}")
    private String jwtUser;
    @Value("${jwt.password}")
    private String jwtPassword;

    @Override
    public boolean validate(String user, String password) {
        try {
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/key/private_key.pem"));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            String decryptedUser = decryptWithPrivateKey(jwtUser, privateKey);
            String decryptedPassword = decryptWithPrivateKey(jwtPassword, privateKey);

            return user.equals(decryptedUser) && password.equals(decryptedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    private String decryptWithPrivateKey(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
