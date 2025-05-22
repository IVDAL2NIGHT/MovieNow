package com.movieNow.movies.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * Clase de configuración para propiedades y utilidades de JWT (JSON Web Token).
 * Esta clase es responsable de cargar y gestionar las propiedades de configuración
 * relacionadas con JWT, como la clave secreta, el tiempo de expiración y el algoritmo.
 * Proporciona métodos para obtener una SecretKey y JWSAlgorithm basados en la configuración
 * cargada.

 * La clave secreta y el algoritmo configurados se utilizan en varias partes de la
 * aplicación para firmar y verificar tokens JWT, asegurando una comunicación segura.
 */

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    @Value("${security.jwt.algorithm}")
    private String algorithm;

    public SecretKey getSecretKey() {
        var key =new OctetSequenceKey.Builder(secretKey.getBytes())
                .algorithm(new JWSAlgorithm(algorithm))
                .build();
        return key.toSecretKey();
    }

    public JWSAlgorithm getAlgorithm() {
        return new JWSAlgorithm(algorithm);
    }
}
