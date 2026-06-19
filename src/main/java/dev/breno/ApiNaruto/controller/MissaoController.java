package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.service.MissaoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.breno.ApiNaruto.dto.MissaoResponseDTO;
import dev.breno.ApiNaruto.dto.MissaoRequestDTO;

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
    public ResponseEntity<List<MissaoResponseDTO>> listarMissoes() {
    List<MissaoResponseDTO> missoes = missaoService.listarMissoes();
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
    public ResponseEntity<MissaoResponseDTO> buscarMissaoPorId(@PathVariable Long id) {
    MissaoResponseDTO missao = missaoService.buscarMissaoPorId(id);
    return ResponseEntity.ok(missao);
}
    
    /**
 * ============================================================================
 * BUSCAR MISSÕES POR RANKING
 * ============================================================================
 *
 * Endpoint responsável por buscar missões utilizando o ranking como filtro.
 *
 * Tradução:
 *
 * RequestParam:
 * Request = requisição
 * Param = parâmetro
 *
 * @RequestParam captura o valor enviado na URL.
 *
 * Exemplo:
 *
 * GET /missoes/ranking?ranking=Rank A
 *
 * Neste caso:
 *
 * ranking = Rank A
 *
 * @param ranking Ranking usado como filtro.
 * @return Lista de missões encontradas em formato DTO.
 */
@GetMapping("/ranking")
public ResponseEntity<List<MissaoResponseDTO>> buscarPorRanking(
        @RequestParam String ranking) {

    List<MissaoResponseDTO> missoes =
            missaoService.buscarPorRanking(ranking);

    return ResponseEntity.ok(missoes);
}

/**
 * ============================================================================
 * BUSCAR MISSÕES POR RANKING PARCIAL
 * ============================================================================
 *
 * Endpoint responsável por buscar missões cujo ranking contenha
 * o texto informado, ignorando letras maiúsculas e minúsculas.
 *
 * Tradução:
 *
 * Containing = contendo
 * IgnoreCase = ignorar maiúsculas e minúsculas
 *
 * Exemplo:
 *
 * GET /missoes/ranking/parcial?ranking=a
 *
 * Neste caso, serão retornadas missões cujo ranking contenha "a",
 * como "A", "Rank A" ou "rank a".
 *
 * @param ranking Texto usado como filtro.
 * @return Lista de missões encontradas em formato DTO.
 */
    @GetMapping("/ranking/parcial")
    public ResponseEntity<List<MissaoResponseDTO>> buscarPorRankingParcial(
        @RequestParam String ranking) {

    List<MissaoResponseDTO> missoes =
            missaoService.buscarPorRankingParcial(ranking);

        return ResponseEntity.ok(missoes);
    }

    /**
     * POST /missoes
     * Cria uma nova missão de forma segura.
     * 
     * @param missaoDTO
     * @return Missão criada com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<MissaoResponseDTO> salvarMissao(
        @RequestBody @Valid MissaoRequestDTO missaoDTO) {

    MissaoResponseDTO novaMissao =
            missaoService.salvarMissao(missaoDTO);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(novaMissao);
    }

    /**
     * PUT /missoes/{id}
     * Atualiza uma missão existente de forma segura.
     * 
     * @param id ID da missão a ser actualizada.
     * @param missaoDTO
     * @return Missão atualizada com status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MissaoResponseDTO> atualizarMissao(
        @PathVariable Long id,
        @RequestBody @Valid MissaoRequestDTO missaoDTO) {

    MissaoResponseDTO missaoAtualizada =
            missaoService.atualizarMissao(id, missaoDTO);

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