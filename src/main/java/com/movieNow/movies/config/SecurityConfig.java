package com.movieNow.movies.config;

import com.movieNow.movies.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.SessionManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig es una clase de configuración que define la configuración de seguridad de la aplicación.
 * Utiliza Spring Security para implementar autenticación, autorización y gestión de sesiones.
 * Además, integra soporte para JWT (JSON Web Token) para manejar la autenticación sin estado.
 *
 * Esta clase proporciona las siguientes configuraciones:
 *
 * 1. Configuración de un bean SecurityFilterChain para deshabilitar la protección CSRF, definir reglas de
 * autorización de solicitudes, gestionar políticas de creación de sesiones y habilitar el soporte para
 * servidores de recursos usando JWT.
 *
 * 2. Configuración de un bean JwtDecoder para decodificar tokens JWT utilizando una clave secreta
 * obtenida de JwtConfig.
 *
 * 3. Definición de un bean BCryptPasswordEncoder para codificar contraseñas.
 *
 * 4. Configuración de un bean AuthenticationProvider con integración DAO (Objeto de Acceso a Datos).
 * Utiliza un UserDetailsService personalizado para cargar detalles del usuario y un BCryptPasswordEncoder
 * para la comparación de contraseñas.
 *
 * 5. Configuración de un bean AuthenticationManager para autenticar usuarios utilizando el
 * AuthenticationProvider definido.
 *
 * 6. Definición de un bean UserDetailsService para recuperar detalles del usuario desde un repositorio
 * MongoDB (UserRepository) dado un nombre de usuario. Si no se encuentra el usuario, lanza una
 * UsernameNotFoundException.
 *
 * Esta configuración asegura un mecanismo de seguridad sin estado, seguro y modular para la aplicación.
 */

@Configuration
@AllArgsConstructor

public class SecurityConfig {

    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/v1/user/**").permitAll()
                        .requestMatchers("/api/v1/movies/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(jwtConfig.getSecretKey()).build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }





}
