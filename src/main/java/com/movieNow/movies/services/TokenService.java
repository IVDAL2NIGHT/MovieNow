package com.movieNow.movies.services;
import com.movieNow.movies.models.User;
import com.movieNow.movies.config.JwtConfig;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * Clase de servicio responsable de manejar funcionalidades relacionadas con la generación de JWT (JSON Web Token).
 * Utiliza configuraciones de JwtConfig y gestiona la creación de tokens para usuarios autenticados.

 * Responsabilidades:
 * - Generar un JWT utilizando la información de un usuario autenticado.
 * - Obtener y utilizar la clave secreta y el algoritmo de firma desde JwtConfig para firmar el token.
 * - Incluir claims como nombre de usuario, correo electrónico, roles e ID de usuario en la carga útil del token.

 * Se crea un SignedJWT interno con un encabezado, claims y firma.
 * El JWT resultante se serializa y se devuelve como una cadena de texto.

 * Dependencias:
 * - JwtConfig: Proporciona el algoritmo y la clave secreta para crear JWTs firmados.
 * - User: Representa la información del usuario autenticado, como nombre de usuario, correo electrónico e ID.
 * - Authentication: Proporcionado por Spring Security para recuperar detalles del usuario actualmente autenticado.

 * Lanza:
 * - RuntimeException si hay un error durante la firma del JWT.
 */

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtConfig jwtConfig;

    public String generateToken(Authentication authentication) {
        // header + payload/claims + signature
        var header = new JWSHeader.Builder(jwtConfig.getAlgorithm())
                .type(JOSEObjectType.JWT)
                .build();
        Instant now = Instant.now();
        var roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        var builder = new JWTClaimsSet.Builder()
                .issuer("MovieNow")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plus(1, java.time.temporal.ChronoUnit.HOURS)));
        builder.claim("roles", roles);
        var user = (User) authentication.getPrincipal();
        builder.claim("username", authentication.getName());
        builder.claim("email", user.getEmail());
        builder.claim("id",user.getId().toString());
        var claims = builder.build();

        var key = jwtConfig.getSecretKey();

        var jwt = new SignedJWT(header, claims);

        try {
            var signer = new MACSigner(key);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating JWT",e);
        }
        return jwt.serialize();
    }
}