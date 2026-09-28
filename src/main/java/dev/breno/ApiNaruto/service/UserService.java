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
import dev.breno.ApiNaruto.exception.UsuarioNaoEncontradoException;
import dev.breno.ApiNaruto.exception.RegraDeNegocioException;
import dev.breno.ApiNaruto.model.UserModel;
import dev.breno.ApiNaruto.repository.UserRepository;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ============================================================================
 * SERVICE DE USUÁRIOS
 * ============================================================================
 *
 * Responsável pelas regras e operações relacionadas aos usuários do sistema.
 *
 * Traduções:
 *
 * User = usuário
 * Service = serviço / camada de negócio
 * Username = nome de usuário
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserModel buscarPorUsername(String username) {
        return userRepository.findByUsername(normalizarUsername(username))
                .orElseThrow(
                        () -> new UsuarioNaoEncontradoException(username)
                );
    }



    public UserResponseDTO cadastrarUsuario(UserRequestDTO userDTO) {

    String username = normalizarUsername(userDTO.getUsername());

    if (userRepository.findByUsername(username).isPresent()) {
        throw new RegraDeNegocioException("Username já está em uso.");
    }

    UserModel user = new UserModel();

    user.setUsername(username);

    user.setPassword(
            passwordEncoder.encode(userDTO.getPassword())
    );

// Todo cadastro realizado pelo administrador recebe perfil comum por padrão.
    user.setRole("ROLE_USER");

    UserModel userSalvo = userRepository.save(user);

    return new UserResponseDTO(
            userSalvo.getId(),
            userSalvo.getUsername(),
            userSalvo.getRole()
        );
    }

    public AuthResponseDTO autenticar(AuthRequestDTO authDTO) {

    UserModel user = userRepository.findByUsername(normalizarUsername(authDTO.getUsername()))
            .orElseThrow(() -> new CredenciaisInvalidasException());

    boolean senhaCorreta = passwordEncoder.matches(
            authDTO.getPassword(),
            user.getPassword()
        );

        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtService.gerarToken(user.getUsername());

        return new AuthResponseDTO(token, user.getRole());
    }


    private String normalizarUsername(String username) {
        return username.trim().toLowerCase(Locale.ROOT);
    }

}