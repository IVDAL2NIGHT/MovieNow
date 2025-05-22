package com.movieNow.movies.services;

import com.movieNow.movies.models.LoginDto;
import com.movieNow.movies.models.LoginResponse;
import com.movieNow.movies.models.User;
import com.movieNow.movies.models.UserDto;
import com.movieNow.movies.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Clase de servicio responsable de gestionar funcionalidades relacionadas con usuarios.
 * Proporciona métodos para registrar nuevos usuarios, autenticar usuarios existentes,
 * y recuperar detalles de usuario basados en el nombre de usuario.

 * Esta clase utiliza varias dependencias como `UserRepository` para operaciones de base de datos,
 * `PasswordEncoder` para asegurar contraseñas, `AuthenticationManager` para autenticación de usuarios,
 * y `TokenService` para generar tokens de autenticación.
 */

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    //registrar usuario
    public User registerUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setId(new ObjectId());
        user.setRole(List.of("ROLE_USER"));

        return userRepository.save(user);
    }

    public LoginResponse authenticate(LoginDto input){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.username(), input.password()));
        var user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(authentication);
        return new LoginResponse(token, user.getUsername(), user.getEmail());
    }

    //buscar usuario por username
    public Optional<User> singleUser(String username){
        return userRepository.findByUsername(username);
    }
}