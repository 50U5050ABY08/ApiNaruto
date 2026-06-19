/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ============================================================================
 * DTO DE REQUISIÇÃO DE MISSÃO
 * ============================================================================
 *
 * Representa os dados enviados pelo cliente para criar ou atualizar
 * uma missão.
 *
 * Este DTO impede que o cliente manipule diretamente a entidade
 * MissaoModel.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MissaoRequestDTO {

    @NotBlank(message = "A descrição da missão é obrigatória")
    private String missao;

    @NotBlank(message = "O ranking da missão é obrigatório")
    private String rankingDaMissao;

}
