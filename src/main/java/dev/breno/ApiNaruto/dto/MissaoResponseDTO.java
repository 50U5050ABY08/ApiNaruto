/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ============================================================================
 * DTO DE RESPOSTA DE MISSÃO
 * ============================================================================
 *
 * Representa os dados que serão enviados ao cliente.
 *
 * Observe que não retornaremos a lista completa de ninjas.
 * Isso evita referências circulares e mantém a API enxuta.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MissaoResponseDTO {

    private Long id;

    private String missao;

    private String rankingDaMissao;

}
