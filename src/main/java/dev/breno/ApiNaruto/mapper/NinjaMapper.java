/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.mapper;

import dev.breno.ApiNaruto.dto.NinjaResponseDTO;
import dev.breno.ApiNaruto.model.NinjaModel;

/**
 * ============================================================================
 * CAMADA DE CONVERSÃO (MAPPER)
 * ============================================================================
 *
 * O Mapper é responsável por converter objetos de um tipo para outro.
 *
 * Neste projeto ele será utilizado para transformar:
 *
 * NinjaModel  ----->  NinjaResponseDTO
 *
 * Dessa forma, a API não expõe diretamente a entidade do banco de dados,
 * seguindo uma boa prática de arquitetura e segurança.
 *
 * Futuramente também poderemos criar métodos para converter:
 *
 * NinjaRequestDTO ---> NinjaModel
 *
 * deixando toda a lógica de conversão centralizada nesta classe.
 */
public class NinjaMapper {

    /**
     * =========================================================================
     * CONVERTE MODEL PARA RESPONSE DTO
     * =========================================================================
     *
     * Recebe uma entidade NinjaModel e cria um novo objeto
     * NinjaResponseDTO contendo apenas os dados que desejamos
     * retornar ao cliente.
     *
     * @param ninja Entidade vinda do banco de dados.
     * @return DTO que será enviado na resposta da API.
     */
    public static NinjaResponseDTO toResponseDTO(NinjaModel ninja) {

        // Cria um novo objeto DTO
        NinjaResponseDTO dto = new NinjaResponseDTO();

        // Copia o ID
        dto.setId(ninja.getId());

        // Copia o nome
        dto.setNome(ninja.getNome());

        // Copia o e-mail
        dto.setEmail(ninja.getEmail());

        // Copia a idade
        dto.setIdade(ninja.getIdade());

        /**
         * Verifica se o ninja possui uma missão associada.
         *
         * Essa verificação evita um NullPointerException caso
         * o ninja ainda não tenha missão cadastrada.
         */
        if (ninja.getMissao() != null) {

            dto.setMissao(
                    ninja.getMissao().getMissao()
            );

        }

        // Retorna o DTO preenchido
        return dto;
    }

}
