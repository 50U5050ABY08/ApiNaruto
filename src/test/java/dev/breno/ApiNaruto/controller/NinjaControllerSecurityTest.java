/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.config.SecurityConfig;
import dev.breno.ApiNaruto.repository.UserRepository;
import dev.breno.ApiNaruto.security.JwtAuthenticationFilter;
import dev.breno.ApiNaruto.service.JwtService;
import dev.breno.ApiNaruto.service.NinjaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request
        .SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;

@WebMvcTest(controllers = NinjaController.class)
@Import({
    SecurityConfig.class,
    JwtAuthenticationFilter.class
})
class NinjaControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // Simula o service utilizado pelo controller.
    @MockitoBean
    private NinjaService ninjaService;

    // Dependências utilizadas pelo JwtAuthenticationFilter.
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    void prepararTeste() {

        when(ninjaService.listarNinjas(any(Pageable.class)))
                .thenReturn(Page.empty());
    }

    @Test
    void deveRetornar401AoListarNinjasSemAutenticacao()
            throws Exception {

        mockMvc.perform(
                get("/ninjas")
        )
                .andExpect(
                        status().isUnauthorized()
                );
    }

    @Test
    void devePermitirListagemParaUsuarioAutenticado()
            throws Exception {

        mockMvc.perform(
                get("/ninjas")
                        .with(
                                user("usuario")
                                        .authorities(
                                                new SimpleGrantedAuthority(
                                                        "ROLE_USER"
                                                )
                                        )
                        )
        )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    void deveRetornar403AoExcluirComRoleUser()
            throws Exception {

        mockMvc.perform(
                delete("/ninjas/999")
                        .with(
                                user("usuario")
                                        .authorities(
                                                new SimpleGrantedAuthority(
                                                        "ROLE_USER"
                                                )
                                        )
                        )
        )
                .andExpect(
                        status().isForbidden()
                );
    }

    @Test
    void devePermitirExclusaoComRoleAdmin()
            throws Exception {

        mockMvc.perform(
                delete("/ninjas/999")
                        .with(
                                user("breno")
                                        .authorities(
                                                new SimpleGrantedAuthority(
                                                        "ROLE_ADMIN"
                                                )
                                        )
                        )
        )
                .andExpect(
                        status().is2xxSuccessful()
                );
    }
}
