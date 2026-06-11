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
 * DATA TRANSFER OBJECT (DTO DE RESPOSTA)
 * ============================================================================
 *
 * Esta classe representa os dados que a API retorna ao cliente após
 * consultar ou cadastrar um Ninja.
 *
 * [OBJETIVO]
 * - Evitar expor toda a entidade NinjaModel.
 * - Retornar apenas as informações necessárias ao consumidor da API.
 * - Permitir personalizar a resposta sem alterar a estrutura do banco.
 *
 * Exemplo de resposta:
 *
 * {
 *    "id": 1,
 *    "nome": "Naruto Uzumaki",
 *    "email": "naruto@konoha.com",
 *    "idade": 17,
 *    "missao": "Exame Chunin"
 * }
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NinjaResponseDTO {

    /**
     * Identificador único do ninja.
     */
    private Long id;

    /**
     * Nome do ninja.
     */
    private String nome;

    /**
     * E-mail do ninja.
     */
    private String email;

    /**
     * Idade do ninja.
     */
    private int idade;

    /**
     * Nome da missão associada ao ninja.
     *
     * Nesta resposta retornamos apenas a descrição da missão,
     * evitando enviar todo o objeto MissaoModel.
     */
    private String missao;
}

