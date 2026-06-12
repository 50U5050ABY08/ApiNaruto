/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.mapper;

import dev.breno.ApiNaruto.dto.MissaoResponseDTO;
import dev.breno.ApiNaruto.model.MissaoModel;

/**
 * ============================================================================
 * CAMADA DE CONVERSÃO (MAPPER DE MISSÃO)
 * ============================================================================
 *
 * Responsável por converter uma entidade MissaoModel em
 * MissaoResponseDTO.
 *
 * Dessa forma evitamos expor diretamente a entidade do banco
 * de dados ao cliente da API.
 */
public class MissaoMapper {

    /**
     * =========================================================================
     * CONVERTE MODEL PARA RESPONSE DTO
     * =========================================================================
     *
     * @param missao Entidade vinda do banco.
     * @return DTO que será enviado ao cliente.
     */
    public static MissaoResponseDTO toResponseDTO(
            MissaoModel missao) {

        MissaoResponseDTO dto =
                new MissaoResponseDTO();

        dto.setId(missao.getId());

        dto.setMissao(
                missao.getMissao());

        dto.setRankingDaMissao(
                missao.getRankingDaMissao());

        return dto;
    }
}
