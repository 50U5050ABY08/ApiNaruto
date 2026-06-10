/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.model.NinjaModel;
import dev.breno.ApiNaruto.service.NinjaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * CAMADA DE ENTRADA (CONTROLLER DE NINJAS)
 * ============================================================================
 *
 * Responsável por receber as requisições HTTP relacionadas aos ninjas e
 * encaminhá-las para a camada de negócio (Service).
 *
 * [SEGURANÇA DA API - BOAS PRÁTICAS]
 * 1. Uso de ResponseEntity: Permite retornar o status HTTP correto para cada operação
 *    (ex: 201 Created para criação, 204 No Content para deleção, 200 OK para sucesso).
 * 2. Isolamento de Dados: O Controller nunca acessa o repositório diretamente.
 * 3. Tratamento de Exceções: Erros de "não encontrado" são tratados no Service e
 *    retornados como 404 de forma limpa, sem expor stack traces do servidor.
 */
@RestController
@RequestMapping("/ninjas")
@RequiredArgsConstructor
public class NinjaController {

    /**
     * Camada responsável pelas regras de negócio de ninjas.
     */
    private final NinjaService ninjaService;

    /**
     * GET /ninjas
     * Retorna todos os ninjas cadastrados.
     * 
     * @return Lista de ninjas com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<NinjaModel>> listarNinjas() {
        List<NinjaModel> ninjas = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjas);
    }

    /**
     * GET /ninjas/{id}
     * Busca um ninja específico pelo ID.
     * 
     * @param id ID do ninja.
     * @return Ninja encontrado com status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NinjaModel> buscarNinjaPorId(@PathVariable Long id) {
        NinjaModel ninja = ninjaService.buscarNinjaPorId(id);
        return ResponseEntity.ok(ninja);
    }

    /**
     * POST /ninjas
     * Cria um novo ninja de forma segura.
     * 
     * @param ninja Dados do ninja a ser criado.
     * @return Ninja criado com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<NinjaModel> salvarNinja(@RequestBody NinjaModel ninja) {
        NinjaModel novoNinja = ninjaService.salvarNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoNinja);
    }

    /**
     * PUT /ninjas/{id}
     * Atualiza um ninja existente de forma segura.
     * 
     * @param id ID do ninja a ser atualizado.
     * @param ninja Dados atualizados do ninja.
     * @return Ninja atualizado com status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NinjaModel> atualizarNinja(
            @PathVariable Long id, 
            @RequestBody NinjaModel ninja) {
        NinjaModel ninjaAtualizado = ninjaService.atualizarNinja(id, ninja);
        return ResponseEntity.ok(ninjaAtualizado);
    }

    /**
     * DELETE /ninjas/{id}
     * Deleta um ninja pelo ID de forma segura.
     * 
     * @param id ID do ninja a ser deletado.
     * @return Status 204 No Content indicando sucesso sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNinja(@PathVariable Long id) {
        ninjaService.deletarNinja(id);
        return ResponseEntity.noContent().build();
    }
}
