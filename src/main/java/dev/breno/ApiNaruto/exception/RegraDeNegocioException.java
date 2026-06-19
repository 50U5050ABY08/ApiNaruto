/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.exception;

/**
 * ============================================================================
 * EXCEÇÃO DE REGRA DE NEGÓCIO
 * ============================================================================
 *
 * Exceção lançada quando uma operação viola uma regra do sistema.
 *
 * Exemplo:
 *
 * Ninja menor de 18 anos tentando participar de missão Rank A.
 *
 * Tradução:
 *
 * Business Rule = Regra de Negócio
 * Exception = Exceção
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}