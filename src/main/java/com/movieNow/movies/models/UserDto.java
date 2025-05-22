package com.movieNow.movies.models;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la información de un usuario.
 * Este registro se utiliza para encapsular el nombre de usuario, correo electrónico y contraseña
 * de un usuario como datos ligeros e inmutables.

 * Campos:
 * - username: El nombre de usuario único del usuario.
 * - email: La dirección de correo electrónico asociada al usuario.
 * - password: La contraseña asociada a la cuenta del usuario.
 */

public record UserDto (String username,String email,String password){
}
