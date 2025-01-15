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
 * Servicio encargado de la validación de credenciales utilizando un sistema de encriptación RSA.
 * Este servicio utiliza una clave privada para desencriptar las credenciales almacenadas (usuario y contraseña)
 * y compararlas con los valores proporcionados durante el proceso de autenticación.
 *
 * <p>Este servicio implementa la interfaz {@link AuthServicePort}, lo que permite su integración con el resto
 * de la aplicación.</p>
 */
@Primary
@Service
@Slf4j  // Anotación de Lombok para habilitar logging en la clase.
public class AuthService implements AuthServicePort {

    // Propiedades inyectadas desde el archivo de configuración, conteniendo las credenciales encriptadas
    @Value("${jwt.user}")
    private String jwtUser;

    @Value("${jwt.password}")
    private String jwtPassword;

    /**
     * Valida las credenciales de un usuario comparando los valores proporcionados con los valores desencriptados
     * almacenados en las propiedades configuradas (jwt.user y jwt.password).
     *
     * @param user      El nombre de usuario proporcionado para la autenticación.
     * @param contrasena La contraseña proporcionada para la autenticación.
     * @return {@code true} si las credenciales proporcionadas coinciden con las credenciales desencriptadas,
     *         {@code false} si no coinciden o si ocurre algún error durante el proceso.
     */
    @Override
    public boolean validateCredentials(String user, String contrasena) {
        try {
            log.info("Iniciando validación de credenciales para el usuario: {}", user);

            // Cargar clave privada desde resources
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/key/private_key.pem"));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            log.debug("Clave privada cargada correctamente.");

            // Desencriptar jwt.user utilizando la clave privada
            String decryptedUser = decryptWithPrivateKey(jwtUser, privateKey);
            log.debug("Usuario desencriptado: {}", decryptedUser);

            // Desencriptar jwt.password utilizando la clave privada
            String decryptedPassword = decryptWithPrivateKey(jwtPassword, privateKey);
            log.debug("Contraseña desencriptada: {}", decryptedPassword);

            // Comparar las credenciales desencriptadas con las proporcionadas
            boolean credentialsValid = user.equals(decryptedUser) && contrasena.equals(decryptedPassword);
            log.info("Credenciales validadas: {}", credentialsValid);

            return credentialsValid;
        } catch (Exception e) {
            // Imprimir error y retornar false si ocurre una excepción durante la validación
            log.error("Error durante la validación de credenciales: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * Desencripta un dato encriptado utilizando la clave privada proporcionada.
     *
     * @param encryptedData El dato encriptado que se desea desencriptar (en formato Base64).
     * @param privateKey    La clave privada utilizada para desencriptar el dato.
     * @return El dato desencriptado como una cadena de texto.
     * @throws Exception Si ocurre un error durante el proceso de desencriptado.
     */
    private String decryptWithPrivateKey(String encryptedData, PrivateKey privateKey) throws Exception {
        log.debug("Iniciando desencriptación de los datos.");

        // Inicializar el objeto Cipher para desencriptar con RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Decodificar el dato encriptado desde Base64
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

        // Desencriptar el dato
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Convertir el dato desencriptado a String y retornarlo
        String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);
        log.debug("Datos desencriptados exitosamente: {}", decryptedString);

        return decryptedString;
    }
}
