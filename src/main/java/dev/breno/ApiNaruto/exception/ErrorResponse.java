/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ============================================================================
 * DTO DE RESPOSTA DE ERRO
 * ============================================================================
 *
 * Representa o formato padrão das mensagens de erro retornadas pela API.
 *
 * Em vez de devolver erros diferentes em cada endpoint, centralizamos
 * a estrutura da resposta para facilitar manutenção, depuração e consumo
 * da API pelo cliente.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Momento em que o erro ocorreu.
     */
    private LocalDateTime timestamp;

    /**
     * Código HTTP do erro.
     *
     * Exemplo:
     * 400, 404, 500.
     */
    private int status;

    /**
     * Nome resumido do erro.
     *
     * Exemplo:
     * Bad Request, Not Found, Internal Server Error.
     */
    private String error;

    /**
     * Mensagem amigável explicando o erro.
     */
    private String message;

    /**
     * Caminho da requisição onde o erro ocorreu.
     *
     * Exemplo:
     * /ninjas/99
     */
    private String path;
}
