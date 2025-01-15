package com.inditex.domain.port.in;

/**
 * Interfaz que define los métodos del servicio de autenticación, utilizado para la validación de credenciales de usuario.
 * Esta interfaz es parte de la capa de dominio y se encuentra en el puerto de entrada (Input Port) de la arquitectura hexagonal.
 * El propósito de este puerto es proporcionar una abstracción para la validación de las credenciales de un usuario
 * sin depender de la implementación específica de la autenticación.
 *
 * <p>Implementaciones de esta interfaz deben proporcionar la lógica para verificar si las credenciales proporcionadas
 * (usuario y contraseña) son correctas y, por lo tanto, determinar si el usuario está autorizado para acceder al sistema.</p>
 */
public interface AuthServicePort {

    /**
     * Método que valida las credenciales proporcionadas (usuario y contraseña).
     *
     * @param user     El nombre de usuario que se desea validar.
     * @param contrasena La contraseña correspondiente al nombre de usuario.
     * @return true si las credenciales son válidas, false si son inválidas.
     *
     * <p>Este método es utilizado en el proceso de autenticación para verificar si los datos ingresados por el usuario
     * son correctos y coinciden con los registrados en el sistema.</p>
     */
    boolean validateCredentials(String user, String contrasena);
}
