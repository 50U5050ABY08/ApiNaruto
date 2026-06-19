/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.exception;

/**
 *==============================================================================
 * EXCEÇÃO DE MISSÃO NÃO ENCONTRADA
 * =============================================================================
 * 
 * Exceção lançada quando uma missão não é encontrada no banco de dados.
 * 
 * Tradução:
 * 
 * Exception = exceção
 * Not Found = não encontrado
 * @author root
 */
public class MissaoNaoEncontradaException extends RuntimeException { 
    
    
    public MissaoNaoEncontradaException(Long id) {
        super("Missão não encontrada com o ID: " + id);
    }
}
