package com.movieNow.movies.controllers;

import com.movieNow.movies.models.LoginDto;
import com.movieNow.movies.models.LoginResponse;
import com.movieNow.movies.models.User;
import com.movieNow.movies.models.UserDto;
import com.movieNow.movies.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * UserController es un controlador REST que proporciona endpoints de API para la autenticación y registro de usuarios.
 * Esta clase maneja las peticiones HTTP relacionadas con el registro e inicio de sesión de usuarios, delegando la lógica
 * de negocio a la clase UserService.
 *
 * Anotaciones:
 * - @RestController: Indica que esta clase es un controlador REST para manejar peticiones HTTP y devolver respuestas HTTP.
 * - @RequestMapping("/api/v1/user"): Especifica la ruta base para todos los endpoints en este controlador.
 * - @AllArgsConstructor: Genera un constructor con todos los argumentos para las dependencias de la clase.
 *
 * Endpoints:
 * - POST /signup: Maneja el registro de usuarios creando un nuevo usuario en el sistema.
 * - POST /signing: Autentica un usuario existente, devolviendo una respuesta de inicio de sesión con un token tras una autenticación exitosa.
 *
 * Dependencias:
 * - UserService: La clase de servicio responsable de manejar la lógica de negocio relacionada con usuarios, como el registro y la autenticación.
 */

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        UserDto registerUserDto = new UserDto(username, email, password);
        User registeredUser = userService.registerUser(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signing")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
        var loginResponse = userService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}
