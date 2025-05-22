package com.movieNow.movies.models;

/**
 * Objeto de Transferencia de Datos (DTO) para manejar credenciales de inicio de sesión.
 * Este registro se utiliza para encapsular el nombre de usuario y la contraseña proporcionados
 * por un usuario durante el proceso de autenticación.
 */
public record LoginDto(String username, String password) {
}
