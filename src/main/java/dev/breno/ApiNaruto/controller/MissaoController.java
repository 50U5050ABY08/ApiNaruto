package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.model.MissaoModel;
import dev.breno.ApiNaruto.service.MissaoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * CAMADA DE ENTRADA (CONTROLLER DE MISSÕES)
 * ============================================================================
 *
 * Responsável por receber as requisições HTTP relacionadas às missões e
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
@RequestMapping("/missoes")
@RequiredArgsConstructor
public class MissaoController {

    /**
     * Camada responsável pelas regras de negócio de missões.
     */
    private final MissaoService missaoService;

    /**
     * GET /missoes
     * Retorna todas as missões cadastradas.
     * 
     * @return Lista de missões com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<MissaoModel>> listarMissoes() {
        List<MissaoModel> missoes = missaoService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    /**
     * GET /missoes/{id}
     * Busca uma missão específica pelo ID.
     * 
     * @param id ID da missão.
     * @return Missão encontrada com status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MissaoModel> buscarMissaoPorId(@PathVariable Long id) {
        MissaoModel missao = missaoService.buscarMissaoPorId(id);
        return ResponseEntity.ok(missao);
    }

    /**
     * POST /missoes
     * Cria uma nova missão de forma segura.
     * 
     * @param missao Dados da missão a ser criada.
     * @return Missão criada com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<MissaoModel> salvarMissao(@RequestBody @Valid MissaoModel missao) {
        MissaoModel novaMissao = missaoService.salvarMissao(missao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMissao);
    }

    /**
     * PUT /missoes/{id}
     * Atualiza uma missão existente de forma segura.
     * 
     * @param id ID da missão a ser actualizada.
     * @param missao Dados atualizados da missão.
     * @return Missão atualizada com status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MissaoModel> atualizarMissao(
            @PathVariable Long id, 
            @RequestBody @Valid MissaoModel missao) {
        MissaoModel missaoAtualizada = missaoService.atualizarMissao(id, missao);
        return ResponseEntity.ok(missaoAtualizada);
    }

    /**
     * DELETE /missoes/{id}
     * Deleta uma missão pelo ID de forma segura.
     * 
     * @param id ID da missão a ser deletada.
     * @return Status 204 No Content indicando sucesso sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMissao(@PathVariable Long id) {
        missaoService.deletarMissao(id);
        return ResponseEntity.noContent().build();
    }
}
