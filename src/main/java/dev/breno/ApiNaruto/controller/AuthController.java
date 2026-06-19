/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.dto.AuthRequestDTO;
import dev.breno.ApiNaruto.dto.AuthResponseDTO;
import dev.breno.ApiNaruto.dto.UserRequestDTO;
import dev.breno.ApiNaruto.dto.UserResponseDTO;
import dev.breno.ApiNaruto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public AuthResponseDTO login(
        @Valid @RequestBody AuthRequestDTO request) {

        return userService.autenticar(request);
    }
    
    @PostMapping("/register")
    public UserResponseDTO cadastrarUsuario(
        @Valid @RequestBody UserRequestDTO userDTO) {
        
        return userService.cadastrarUsuario(userDTO);
    }
    
    private final UserService userService;
}