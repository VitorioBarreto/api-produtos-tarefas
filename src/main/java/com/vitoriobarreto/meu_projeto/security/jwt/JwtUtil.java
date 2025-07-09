package com.vitoriobarreto.meu_projeto.security.jwt;

import io.jsonwebtoken.Claims; // Importe esta classe
import io.jsonwebtoken.Jwts; // Importe esta classe
import io.jsonwebtoken.SignatureAlgorithm; // Importe esta classe
import io.jsonwebtoken.io.Decoders; // Importe esta classe
import io.jsonwebtoken.security.Keys; // Importe esta classe
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value; // Importe esta anotação
import org.springframework.security.core.userdetails.UserDetails; // Importe esta classe
import org.springframework.stereotype.Component; // Importe esta anotação

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component // 1. Marca esta classe como um componente Spring
public class JwtUtil {


    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class); // Crie um logger


    // 2. Chave secreta para assinar e verificar o JWT (coloque uma chave forte e segura!)
    @Value("${jwt.secret}") // Use uma chave forte!
    private String secret;

    // 3. Tempo de expiração do token em milissegundos (ex: 24 horas)
    @Value("${jwt.expiration.ms}") // 24 horas
    private long expirationMs;

    // ADICIONE ESTE MÉTODO
    @PostConstruct
    public void init() {
        logger.info("A chave secreta JWT REALMENTE EM USO: {}", secret);
    }


    // 4. Extrai o nome de usuário do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 5. Extrai a data de expiração do token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 6. Extrai uma "claim" específica do token (ex: subject, expiration)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 7. Extrai todas as "claims" (dados) do token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    // 8. Verifica se o token expirou
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 9. Gera um token para um usuário
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Adicione os papéis (roles) do usuário como uma claim personalizada
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(java.util.stream.Collectors.toList()));
        return createToken(claims, userDetails.getUsername());
    }

    // 10. Cria o token JWT
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Dados do token
                .setSubject(subject) // O assunto (geralmente o nome de usuário)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // Data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinatura com a chave secreta e algoritmo
                .compact(); // Constrói o token
    }

    // 11. Valida um token JWT
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 12. Obtém a chave de assinatura decodificada
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}