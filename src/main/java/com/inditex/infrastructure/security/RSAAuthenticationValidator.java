package com.inditex.infrastructure.security;

import com.inditex.application.exception.DecryptionException;
import com.inditex.domain.port.out.CredentialValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
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
            // Cargar la clave privada desde el archivo
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/key/private_key.pem"));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Desencriptar usuario y contraseña con la clave privada
            String decryptedUser = decryptWithPrivateKey(jwtUser, privateKey);
            String decryptedPassword = decryptWithPrivateKey(jwtPassword, privateKey);

            // Validar si el usuario y la contraseña coinciden
            return user.equals(decryptedUser) && password.equals(decryptedPassword);
        } catch (DecryptionException e) {
            // Lanza la excepción personalizada DecryptionException si ocurre un error en la desencriptación
            throw new DecryptionException("Error al desencriptar los datos de usuario o contraseña.", e);
        } catch (IOException e) {
            // Lanza una excepción personalizada si ocurre un error al leer el archivo de la clave privada
            throw new DecryptionException("Error al leer el archivo de clave privada.", e);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Lanza una excepción personalizada si ocurre un error al generar la clave privada
            throw new DecryptionException("Error al generar la clave privada desde el archivo.", e);
        }
    }

    // Método para desencriptar los datos con la clave privada
    private String decryptWithPrivateKey(String encryptedData, PrivateKey privateKey) {
        try {
            // Crear el objeto Cipher para RSA con el esquema OAEP y SHA-256
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decodificar el texto cifrado desde Base64
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

            // Desencriptar
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException e) {
            // Lanzar la excepción personalizada si ocurre algún error durante el proceso
            throw new DecryptionException("Error al desencriptar los datos.");
        }
    }
}
