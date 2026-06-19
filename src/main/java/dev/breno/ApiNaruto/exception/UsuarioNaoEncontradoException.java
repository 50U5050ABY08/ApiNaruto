/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.exception;

/**
 * ============================================================================
 * EXCEÇÃO DE USUÁRIO NÃO ENCONTRADO
 * ============================================================================
 *
 * Exceção lançada quando um usuário não é encontrado no banco de dados.
 *
 * Traduções:
 *
 * User = usuário
 * Not Found = não encontrado
 * Exception = exceção
 */
public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String username) {
        super("Usuário não encontrado: " + username);
    }
}