/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ============================================================================
 * DTO DE RESPOSTA DE USUÁRIO
 * ============================================================================
 *
 * Representa os dados do usuário retornados pela API.
 *
 * A senha nunca deve ser retornada.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;
}