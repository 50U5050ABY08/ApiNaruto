/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dev.breno.ApiNaruto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ============================================================================
 * DATA TRANSFER OBJECT (DTO DE REQUISIÇÃO)
 * ============================================================================
 *
 * Esta classe representa os dados que o cliente envia para a API quando
 * deseja cadastrar ou atualizar um Ninja.
 *
 * [POR QUE USAR DTO?]
 * - Evita expor diretamente a entidade do banco de dados (NinjaModel).
 * - Permite controlar exatamente quais informações podem ser recebidas.
 * - Facilita futuras alterações sem impactar a estrutura do banco.
 * - É uma boa prática utilizada em projetos profissionais com Spring Boot.
 *
 * Exemplo de JSON esperado:
 *
 * {
 *    "nome": "Naruto Uzumaki",
 *    "email": "naruto@konoha.com",
 *    "idade": 17,
 *    "missaoId": 2
 * }
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NinjaRequestDTO {

    /**
     * Nome do ninja.
     * Não pode ser vazio ou composto apenas por espaços.
     */
    @NotBlank(message = "O nome do ninja é obrigatório")
    private String nome;

    /**
     * E-mail do ninja.
     * Deve possuir um formato válido.
     */
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail fornecido deve ser válido")
    private String email;

    /**
     * Idade do ninja.
     * Deve ser maior que zero.
     */
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 1, message = "A idade deve ser maior que zero")
    private Integer idade;

    /**
     * ID da missão que será associada ao ninja.
     *
     * Observação:
     * Enviamos apenas o ID da missão, e não o objeto completo,
     * tornando a comunicação da API mais simples e eficiente.
     */
    @NotNull(message = "O ID da missão é obrigatório")
    private Long missaoId;
}


