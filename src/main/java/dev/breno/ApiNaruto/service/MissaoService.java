package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.model.MissaoModel;
import dev.breno.ApiNaruto.repository.MissaoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import dev.breno.ApiNaruto.dto.MissaoResponseDTO;
import dev.breno.ApiNaruto.mapper.MissaoMapper;

/**
 * ============================================================================
 * CAMADA DE NEGÓCIO (SERVICE DE MISSÕES)
 * ============================================================================
 * 
 * [ANOTAÇÃO @Service]
 * - O que faz: Marca esta classe como um componente de serviço do Spring. É aqui que
 *   fica toda a lógica de negócios, validações e regras do sistema.
 * - Segurança (Isolamento): O Service funciona como um "pedágio" ou "barreira de segurança".
 *   O Controller nunca acessa o banco diretamente. Se precisarmos validar se um usuário
 *   tem permissão para ver ou alterar uma missão, essa validação é feita aqui, impedindo
 *   acessos indevidos ao banco de dados.
 * 
 * [SEGURANÇA DA API - TRATAMENTO DE ERROS]
 * - Ao buscar ou deletar recursos, se o ID não existir, lançamos uma exceção controlada
 *   (ResponseStatusException) com o status HTTP 404 (Not Found). Isso evita que o sistema
 *   retorne um erro interno 500 (Internal Server Error) com stack traces detalhados,
 *   o que seria uma vulnerabilidade de segurança (Information Disclosure / Vazamento de Informação).
 */
@Service
@RequiredArgsConstructor
public class MissaoService {

    /**
     * Dependência responsável pelo acesso ao banco de dados de missões.
     * Injetada de forma segura via construtor pelo Lombok (@RequiredArgsConstructor).
     */
    private final MissaoRepository missaoRepository;

    /**
     * Retorna todas as missões cadastradas.
     * @return 
     */
    public List<MissaoResponseDTO> listarMissoes() {

    List<MissaoModel> missoes = missaoRepository.findAll();

    return missoes.stream()
            .map(MissaoMapper::toResponseDTO)
            .toList();
}

    /**
     * Busca uma missão pelo seu ID de forma segura.
     * 
     * @param id ID da missão a ser buscada.
     * @return MissaoModel se encontrada.
     * @throws ResponseStatusException se a missão não for encontrada (HTTP 404).
     */
    public MissaoResponseDTO buscarMissaoPorId(Long id) {

    MissaoModel missao = missaoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Missão não encontrada com o ID: " + id));

    return MissaoMapper.toResponseDTO(missao);
}

    /**
     * Salva uma nova missão no banco de dados.
     * 
     * @param missao Objeto contendo os dados da missão.
     * @return A missão salva com seu ID gerado.
     */
    public MissaoModel salvarMissao(MissaoModel missao) {
        // Garantir que o ID seja nulo na criação para evitar que o cliente force a atualização de um registro existente (ID Spoofing)
        missao.setId(null);
        return missaoRepository.save(missao);
    }

    /**
     * Deleta uma missão pelo ID de forma segura.
     * 
     * @param id ID da missão a ser deletada.
     */
    public void deletarMissao(Long id) {
    MissaoModel missao = buscarEntidadePorId(id);
    missaoRepository.delete(missao);
}

    /**
     * Atualiza uma missão existente de forma segura.
     * 
     * @param id ID da missão a ser atualizada.
     * @param missaoAtualizada Objeto contendo os novos dados.
     * @return A missão atualizada e persistida.
     */
    public MissaoModel atualizarMissao(Long id, MissaoModel missaoAtualizada) {

    MissaoModel missaoExistente = buscarEntidadePorId(id);

    missaoExistente.setMissao(missaoAtualizada.getMissao());
    missaoExistente.setRankingDaMissao(missaoAtualizada.getRankingDaMissao());

    return missaoRepository.save(missaoExistente);
}
    
    private MissaoModel buscarEntidadePorId(Long id) {

    return missaoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Missão não encontrada com o ID: " + id));
}
    
    
}
