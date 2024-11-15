package com.gob.biblioteca_santa_fe.services;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.model.TipoUsuario;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Set<TipoUsuario> tipoUsuarios, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());
    
        // Convertir la Set de TipoUsuario a un formato serializable
        List<Map<String, Object>> tipoUsuariosMap = tipoUsuarios.stream()
            .map(tipo -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", tipo.getId());
                map.put("nombre", tipo.getNombre());
                // Agrega otros campos necesarios del TipoUsuario
                return map;
            })
            .collect(Collectors.toList());
    
        // Agregar tipoUsuarios a los claims
        extraClaims.put("tipoUsuarios", tipoUsuariosMap);
    
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> extractTipoUsuarios(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("tipoUsuarios", List.class);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            return (username.equals(userDetails.getUsername()) && !expiration.before(new Date()));
        } catch (ExpiredJwtException e) {

            System.out.println("tokex expirado");
            return false;
        } catch (Exception e) {

            System.out.println("Error en la validación del token JWT: " + e.getMessage());
            return false;
        }
    }
    private Key generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }






}
