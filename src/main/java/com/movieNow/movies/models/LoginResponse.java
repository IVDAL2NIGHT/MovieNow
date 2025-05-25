package com.movieNow.movies.models;

/**
 * Representa la respuesta devuelta al cliente después de un intento exitoso de inicio de sesión.
 * Este registro encapsula información esencial para usuarios autenticados,
 * incluyendo un token y detalles básicos del usuario.
 *
 * Campos:
 * - token: El JWT o token de acceso emitido tras una autenticación exitosa, utilizado para
 * peticiones posteriores que requieran autorización.
 * - username: El nombre de usuario del usuario autenticado.
 * - email: La dirección de correo electrónico asociada a la cuenta del usuario autenticado.
 */

public record LoginResponse(String token, String username, String email) {
}
