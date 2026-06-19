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
import dev.breno.ApiNaruto.model.UserModel;
import dev.breno.ApiNaruto.repository.UserRepository;
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

    public UserModel buscarPorUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(username));  
    }
    
    private final PasswordEncoder passwordEncoder;
    
    public UserResponseDTO cadastrarUsuario(UserRequestDTO userDTO) {

    UserModel user = new UserModel();

    user.setUsername(userDTO.getUsername());

    user.setPassword(
            passwordEncoder.encode(userDTO.getPassword())
    );

    user.setRole(userDTO.getRole());

    UserModel userSalvo = userRepository.save(user);

    return new UserResponseDTO(
            userSalvo.getId(),
            userSalvo.getUsername(),
            userSalvo.getRole()
        );
    }
    
    public AuthResponseDTO autenticar(AuthRequestDTO authDTO) {

    UserModel user = userRepository.findByUsername(authDTO.getUsername())
            .orElseThrow(() -> new CredenciaisInvalidasException());

    boolean senhaCorreta = passwordEncoder.matches(
            authDTO.getPassword(),
            user.getPassword()
        );

        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException();
        }

        return new AuthResponseDTO("LOGIN_REALIZADO_COM_SUCESSO");
    }
}