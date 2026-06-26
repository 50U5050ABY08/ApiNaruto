/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.config;

import dev.breno.ApiNaruto.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

    http
            .cors(Customizer.withDefaults())
            
            
            .csrf(AbstractHttpConfigurer::disable)

            // Não cria sessão no servidor.
            // Cada requisição deve enviar seu próprio JWT.
            .sessionManagement(session -> session
                    .sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

                        .authorizeHttpRequests(auth -> auth

                    // Login e cadastro são públicos.
                    .requestMatchers("/auth/**").permitAll()

                    // Documentação Swagger é pública.
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**"
                    ).permitAll()

                    // Somente administradores podem excluir ninjas.
                    .requestMatchers(
                            HttpMethod.DELETE,
                            "/ninjas/**"
                    ).hasAuthority("ROLE_ADMIN")

                    // As demais rotas precisam de JWT.
                    .anyRequest().authenticated()
            )
            
            .exceptionHandling(exception -> exception

        // Sem autenticação ou token:
        .authenticationEntryPoint(
                (request, response, authException) ->
                        response.setStatus(
                                HttpServletResponse.SC_UNAUTHORIZED
                        )
        )

        // Autenticado, mas sem permissão:
        .accessDeniedHandler(
                (request, response, accessDeniedException) ->
                        response.setStatus(
                                HttpServletResponse.SC_FORBIDDEN
                        )
        )
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
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(
            List.of("http://localhost:5173")
    );

    configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
    );

    configuration.setAllowedHeaders(
            List.of("Authorization", "Content-Type")
    );

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration(
            "/**",
            configuration
    );

    return source;
    }
}