/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.exception;

/**
 * ============================================================================
 * EXCEÇÃO DE CREDENCIAIS INVÁLIDAS
 * ============================================================================
 *
 * Lançada quando username ou senha estão incorretos.
 *
 * Traduções:
 *
 * Credentials = credenciais
 * Invalid = inválidas
 */
public class CredenciaisInvalidasException extends RuntimeException {

    public CredenciaisInvalidasException() {
        super("Usuário ou senha inválidos.");
    }
}