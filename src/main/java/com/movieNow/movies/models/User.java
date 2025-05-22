package com.movieNow.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representa una entidad de usuario almacenada en la colección "user" en una base de datos MongoDB.
 * Esta clase implementa la interfaz UserDetails de Spring Security,
 * permitiendo su uso para propósitos de autenticación y autorización.

 * Anotaciones:
 * - @Document: Mapea esta clase a la colección "user" en la base de datos.
 * - @Data: Genera automáticamente los métodos getters, setters, equals, hashCode y toString.
 * - @AllArgsConstructor: Crea un constructor con todos los campos como parámetros.
 * - @NoArgsConstructor: Crea un constructor sin argumentos.

 * Campos:
 * - id: El identificador único para el usuario en MongoDB.
 * - username: El nombre de usuario del usuario.
 * - email: La dirección de correo electrónico del usuario.
 * - password: La contraseña del usuario.
 * - role: Una lista de roles o autoridades asignadas al usuario, usada para autorización.

 * Constructores:
 * - User(String username): Inicializa un usuario con el nombre de usuario dado y una lista vacía de roles.

 * Métodos:
 * - getAuthorities(): Devuelve una colección de roles, convertidos a objetos GrantedAuthority.
 * - getUsername(): Recupera el nombre de usuario del usuario.
 * - isAccountNonExpired(): Indica si la cuenta del usuario no ha expirado (siempre verdadero).
 * - isAccountNonLocked(): Indica si la cuenta del usuario no está bloqueada (siempre verdadero).
 * - isCredentialsNonExpired(): Indica si las credenciales del usuario no han expirado (siempre verdadero).
 * - isEnabled(): Indica si el usuario está habilitado o activo (siempre verdadero).
 */

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    
    @Id
    private ObjectId id;

    private String username;
    private String email;
    private String password;
    private List<String> role;
    
    public User(String username) {
        this.username = username;
        this.role = new ArrayList<>();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(SimpleGrantedAuthority::new).toList();
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}