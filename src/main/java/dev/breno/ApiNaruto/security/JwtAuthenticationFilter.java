/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.security;

import dev.breno.ApiNaruto.model.UserModel;
import dev.breno.ApiNaruto.repository.UserRepository;
import dev.breno.ApiNaruto.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Não existe token ou o header não começa com "Bearer ".
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Evita autenticar novamente um usuário já autenticado.
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Remove os sete caracteres de "Bearer ".
            String token = authHeader.substring(7);

            // Usa o objeto jwtService para chamar o método.
            String username = jwtService.extrairUsername(token);

            // Usa o objeto userRepository para pesquisar no banco.
            UserModel user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // Verifica username e expiração do token.
            if (!jwtService.tokenValido(token, user.getUsername())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority(user.getRole())
            );

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException exception) {

            SecurityContextHolder.clearContext();

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );
        }
    }
}