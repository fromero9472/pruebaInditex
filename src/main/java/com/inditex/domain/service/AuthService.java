package com.inditex.domain.service;

import com.inditex.domain.port.in.AuthServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * Servicio para validar credenciales de usuario con encriptación RSA.
 */
@Primary
@Service
@Slf4j
public class AuthService implements AuthServicePort {

    @Value("${jwt.user}")
    private String jwtUser;

    @Value("${jwt.password}")
    private String jwtPassword;

    /**
     * Valida las credenciales comparando las proporcionadas con las almacenadas (desencriptadas).
     *
     * @param user     Nombre de usuario.
     * @param contrasena Contraseña.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    @Override
    public boolean validateCredentials(String user, String contrasena) {
        try {
            // Cargar clave privada para desencriptación
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/key/private_key.pem"));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Desencriptar y validar credenciales
            String decryptedUser = decryptWithPrivateKey(jwtUser, privateKey);
            String decryptedPassword = decryptWithPrivateKey(jwtPassword, privateKey);

            return user.equals(decryptedUser) && contrasena.equals(decryptedPassword);
        } catch (Exception e) {
            log.error("Error validando credenciales", e);
            return false;
        }
    }

    /**
     * Desencripta datos encriptados usando una clave privada RSA.
     *
     * @param encryptedData Dato encriptado en Base64.
     * @param privateKey    Clave privada RSA.
     * @return Dato desencriptado.
     * @throws Exception Si ocurre un error en el proceso de desencriptación.
     */
    private String decryptWithPrivateKey(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
