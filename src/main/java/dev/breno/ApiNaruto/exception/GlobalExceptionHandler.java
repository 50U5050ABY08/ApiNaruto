package dev.breno.ApiNaruto.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * ============================================================================
 * TRATAMENTO GLOBAL DE EXCEÇÕES (SEGURANÇA E PADRONIZAÇÃO)
 * ============================================================================
 * 
 * [ANOTAÇÃO @RestControllerAdvice]
 * - O que faz: Centraliza o tratamento de erros de todos os Controllers da API.
 *   Qualquer exceção lançada em qualquer endpoint será capturada por esta classe.
 * - Segurança (Information Disclosure): Impede que o cliente receba stack traces
 *   completos do Java ou detalhes internos do banco de dados. Retornamos apenas
 *   mensagens limpas, seguras e padronizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura erros de validação de entrada (ex: @NotBlank, @Email, @Min).
     * Quando o usuário envia dados inválidos, o Spring lança MethodArgumentNotValidException.
     * 
     * @param ex Exceção de validação capturada.
     * @return Resposta estruturada com os campos inválidos e suas respectivas mensagens.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de Validação");
        
        // Mapeia cada campo inválido para sua respectiva mensagem de erro amigável
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        response.put("fields", errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Captura exceções do tipo ResponseStatusException (ex: 404 Not Found lançado nos Services).
     * 
     * @param ex Exceção capturada.
     * @return Resposta limpa com o status HTTP correspondente e a mensagem de erro.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getStatusCode().value());
        response.put("error", ex.getReason());
        
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    /**
     * Captura qualquer outra exceção genérica não tratada (Fallback de Segurança).
     * Evita que erros inesperados quebrem a API ou vazem informações do servidor.
     * 
     * @param ex Exceção genérica capturada.
     * @return Resposta genérica de erro 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.");
        
        // IMPORTANTE: Em produção, você deve logar o erro real no servidor para depuração,
        // mas NUNCA retornar o stack trace ou a mensagem real do erro para o cliente final!
        System.err.println("Erro inesperado capturado: " + ex.getMessage());
        ex.printStackTrace();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
