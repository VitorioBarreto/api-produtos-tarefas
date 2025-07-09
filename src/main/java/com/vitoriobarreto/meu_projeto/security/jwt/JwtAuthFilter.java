// src/main/java/com/vitoriobarreto/meu_projeto.security.jwt/JwtAuthFilter.java
package com.vitoriobarreto.meu_projeto.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Adicione esses imports para logging e exceções específicas
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        // Tenta extrair o token do cabeçalho
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);

                logger.info("Token JWT recebido: {}", jwt);
                logger.info("Usuário extraído do token: {}", username);
            } catch (ExpiredJwtException e) {
                logger.warn("JWT token has expired: {}", e.getMessage());
                logger.error("Token expirado: {}", e.getMessage());
                // Não há necessidade de lançar a exceção aqui, apenas não autenticar
            } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
                // Não há necessidade de lançar a exceção aqui, apenas não autenticar
            }
        }

        // Se o nome de usuário for encontrado E NÃO HOUVER autenticação prévia
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                userDetails = this.userDetailsService.loadUserByUsername(username);
            } catch (Exception e) {
                logger.error("Error loading UserDetails for username {}: {}", username, e.getMessage());
                // Em caso de erro ao carregar UserDetails, não autenticar
            }

            if (userDetails != null && jwt != null) { // Adicionei jwt != null para garantir que um token foi extraído
                try {
                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken); // Define a autenticação
                    }
                } catch (Exception e) {
                    logger.warn("JWT token validation failed for user {}: {}", username, e.getMessage());
                    // Não há necessidade de lançar a exceção aqui, a autenticação falhará
                }
            }
        }

        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }
}