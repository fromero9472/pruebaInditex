package com.inditex.domain.port.in;

/**
 * Interfaz que define los métodos para la validación de credenciales de usuario.
 * Es parte de la capa de dominio y corresponde al puerto de entrada en una arquitectura hexagonal.
 */
public interface ValidateCredential {

    /**
     * Valida las credenciales de un usuario (usuario y contraseña).
     *
     * @param user     El nombre de usuario.
     * @param contrasena La contraseña asociada al usuario.
     * @return true si las credenciales son válidas, false si no lo son.
     */
    boolean validateCredentials(String user, String contrasena);
}
