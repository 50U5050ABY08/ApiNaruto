/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.dto.AuthRequestDTO;
import dev.breno.ApiNaruto.dto.AuthResponseDTO;
import dev.breno.ApiNaruto.dto.UserRequestDTO;
import dev.breno.ApiNaruto.dto.UserResponseDTO;
import dev.breno.ApiNaruto.exception.CredenciaisInvalidasException;
import dev.breno.ApiNaruto.model.UserModel;
import dev.breno.ApiNaruto.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;



class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private UserService userService;

    @BeforeEach
    void prepararTeste() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);

        userService = new UserService(
                userRepository,
                passwordEncoder,
                jwtService
        );
    }

    @Test
    void deveCadastrarUsuarioComSenhaCriptografadaERoleUser() {

        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("usuario");
        request.setPassword("123456");

        when(passwordEncoder.encode("123456"))
                .thenReturn("senha-criptografada");

        when(userRepository.save(any(UserModel.class)))
                .thenAnswer(invocation -> {
                    UserModel user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        UserResponseDTO response =
                userService.cadastrarUsuario(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).isEqualTo("usuario");
        assertThat(response.getRole()).isEqualTo("ROLE_USER");

        verify(passwordEncoder).encode("123456");

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("usuario")
                && user.getPassword().equals("senha-criptografada")
                && user.getRole().equals("ROLE_USER")
        ));
    }

    @Test
    void deveAutenticarUsuarioComSenhaCorretaERetornarToken() {

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("breno");
        request.setPassword("123456");

        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("breno");
        user.setPassword("hash-bcrypt");
        user.setRole("ROLE_ADMIN");

        when(userRepository.findByUsername("breno"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "hash-bcrypt"))
                .thenReturn(true);

        when(jwtService.gerarToken("breno"))
                .thenReturn("jwt-token-gerado");

        AuthResponseDTO response =
                userService.autenticar(request);

        assertThat(response.getToken())
                .isEqualTo("jwt-token-gerado");

        verify(userRepository).findByUsername("breno");
        verify(passwordEncoder).matches("123456", "hash-bcrypt");
        verify(jwtService).gerarToken("breno");
    }

    @Test
    void deveLancarExcecaoQuandoSenhaForIncorreta() {

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("breno");
        request.setPassword("senhaErrada");

        UserModel user = new UserModel();
        user.setUsername("breno");
        user.setPassword("hash-bcrypt");

        when(userRepository.findByUsername("breno"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("senhaErrada", "hash-bcrypt"))
                .thenReturn(false);

        assertThatThrownBy(() -> userService.autenticar(request))
                .isInstanceOf(CredenciaisInvalidasException.class);

        verify(jwtService, never()).gerarToken(anyString());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistirNoLogin() {

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("invasor");
        request.setPassword("123456");

        when(userRepository.findByUsername("invasor"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.autenticar(request))
                .isInstanceOf(CredenciaisInvalidasException.class);

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());

        verify(jwtService, never())
                .gerarToken(anyString());
    }
}