package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.model.MissaoModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import dev.breno.ApiNaruto.dto.MissaoResponseDTO;
import dev.breno.ApiNaruto.dto.MissaoRequestDTO;
import dev.breno.ApiNaruto.exception.MissaoNaoEncontradaException;
import dev.breno.ApiNaruto.mapper.MissaoMapper;
import dev.breno.ApiNaruto.repository.MissaoRepository;
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
 * ============================================================================
 * SALVAR MISSÃO
 * ============================================================================
 *
 * Cadastra uma nova missão utilizando um DTO de requisição.
 *
 * O cliente não envia mais diretamente a entidade MissaoModel.
 * Ele envia um MissaoRequestDTO, contendo apenas os campos permitidos.
 *
 * Fluxo:
 *
 * Cliente
 *     ↓
 * MissaoRequestDTO
 *     ↓
 * Service
 *     ↓
 * MissaoModel
 *     ↓
 * Repository
 *     ↓
 * Banco de Dados
 *     ↓
 * MissaoMapper
 *     ↓
 * MissaoResponseDTO
 *
 * @param missaoDTO Dados enviados pelo cliente.
 * @return Missão cadastrada em formato DTO.
 */
    public MissaoResponseDTO salvarMissao(MissaoRequestDTO missaoDTO) {

        MissaoModel missao = new MissaoModel();

        missao.setId(null);
        missao.setMissao(missaoDTO.getMissao());
        missao.setRankingDaMissao(missaoDTO.getRankingDaMissao());

        MissaoModel missaoSalva = missaoRepository.save(missao);

        return MissaoMapper.toResponseDTO(missaoSalva);
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
    * ============================================================================
    * ATUALIZAR MISSÃO
    * ============================================================================
    *
    * Atualiza uma missão existente utilizando um DTO de requisição.
    *
    * O cliente envia apenas os dados permitidos através do
    * MissaoRequestDTO. O Service busca a entidade existente,
    * atualiza seus atributos, persiste no banco e converte o
    * resultado para um MissaoResponseDTO.
    *
    * @param id Identificador da missão.
    * @param missaoDTO Dados enviados para atualização.
    * @return Missão atualizada em formato DTO.
    */
   public MissaoResponseDTO atualizarMissao(
           Long id,
           MissaoRequestDTO missaoDTO) {

       /**
    * Busca a entidade existente no banco.
    */
   MissaoModel missaoExistente = buscarEntidadePorId(id);

   /**
    * Atualiza os dados da missão.
    */
   missaoExistente.setMissao(
           missaoDTO.getMissao());

   missaoExistente.setRankingDaMissao(
           missaoDTO.getRankingDaMissao());

   /**
    * Salva as alterações realizadas.
    */
   MissaoModel missaoSalva =
           missaoRepository.save(missaoExistente);

   /**
    * Converte para DTO de resposta.
    */
        return MissaoMapper.toResponseDTO(missaoSalva);
    
   }
    
    private MissaoModel buscarEntidadePorId(Long id) {

        return missaoRepository.findById(id)
            .orElseThrow(() -> new MissaoNaoEncontradaException(id));
    }
    
    
    
    /**
 * ============================================================================
 * BUSCAR MISSÕES POR RANKING
 * ============================================================================
 *
 * Busca missões utilizando o ranking como filtro.
 *
 * Fluxo:
 *
 * Controller
 *     ↓
 * Service
 *     ↓
 * Repository (findByRankingDaMissao)
 *     ↓
 * List<MissaoModel>
 *     ↓
 * MissaoMapper
 *     ↓
 * List<MissaoResponseDTO>
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * rankingDaMissao = ranking da missão
 *
 * findByRankingDaMissao = encontrar pelo ranking da missão
 *
 * @param ranking Ranking usado como filtro.
 * @return Lista de missões encontradas em formato DTO.
 */
    public List<MissaoResponseDTO> buscarPorRanking(String ranking) {

        return missaoRepository.findByRankingDaMissao(ranking)
            .stream()
            .map(MissaoMapper::toResponseDTO)
            .toList();
    }
    
    
    /**
 * ============================================================================
 * BUSCAR MISSÕES POR RANKING PARCIAL
 * ============================================================================
 *
 * Busca missões cujo ranking contenha o texto informado,
 * ignorando diferenças entre letras maiúsculas e minúsculas.
 *
 * Tradução:
 *
 * Containing = contendo
 * IgnoreCase = ignorar maiúsculas e minúsculas
 *
 * @param ranking Texto usado como filtro.
 * @return Lista de missões encontradas em formato DTO.
 */
    public List<MissaoResponseDTO> buscarPorRankingParcial(String ranking) {

        return missaoRepository.findByRankingDaMissaoContainingIgnoreCase(ranking)
                .stream()
                .map(MissaoMapper::toResponseDTO)
                .toList();
    }
    
    
}
