/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.config;

import dev.breno.ApiNaruto.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

    http
            .csrf(AbstractHttpConfigurer::disable)

            // Não cria sessão no servidor.
            // Cada requisição deve enviar seu próprio JWT.
            .sessionManagement(session -> session
                    .sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            .authorizeHttpRequests(auth -> auth

                    // Cadastro e login continuam públicos.
                    .requestMatchers("/auth/**").permitAll()

                    // Somente administradores podem excluir ninjas.
                    .requestMatchers(
                            HttpMethod.DELETE,
                            "/ninjas/**"
                    ).hasAuthority("ROLE_ADMIN")

                    // Qualquer outra rota exige autenticação.
                    .anyRequest().authenticated()
            )

            // Não usaremos login por formulário nem HTTP Basic.
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

            return http.build();
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}