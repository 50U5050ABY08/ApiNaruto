package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.dto.NinjaRequestDTO;
import dev.breno.ApiNaruto.model.MissaoModel;
import dev.breno.ApiNaruto.model.NinjaModel;
import dev.breno.ApiNaruto.repository.MissaoRepository;
import dev.breno.ApiNaruto.repository.NinjaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import dev.breno.ApiNaruto.dto.NinjaResponseDTO;
import dev.breno.ApiNaruto.mapper.NinjaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * ============================================================================
 * CAMADA DE NEGÓCIO (SERVICE DE NINJAS)
 * ============================================================================
 * 
 * [ANOTAÇÃO @Service]
 * - O que faz: Marca esta classe como um componente de serviço do Spring. É aqui que
 *   fica toda a lógica de negócios, validações e regras do sistema.
 * - Segurança (Isolamento): O Service funciona como um "pedágio" ou "barreira de segurança".
 *   O Controller nunca acessa o banco diretamente. Se precisarmos validar se um usuário
 *   tem permissão para ver ou alterar um ninja, essa validação é feita aqui, impedindo
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
public class NinjaService {

    /**
     * Dependência responsável pelo acesso ao banco de dados de ninjas.
     * Injetada de forma segura via construtor pelo Lombok (@RequiredArgsConstructor).
     */
    private final NinjaRepository ninjaRepository;
    
/**
     * Dependência responsável pelo acesso ao banco de dados de missao.
     * Injetada de forma segura via construtor pelo Lombok (@RequiredArgsConstructor).
     */
    private final MissaoRepository missaoRepository;
    
    
    /**
     * Retorna todos os ninjas cadastrados.
     * @return 
     */
    
/**
 * ============================================================================
 * LISTAR NINJAS COM PAGINAÇÃO
 * ============================================================================
 *
 * Busca os ninjas cadastrados no banco de forma paginada.
 *
 * A paginação evita retornar todos os registros de uma vez, o que melhora
 * desempenho, organização da resposta e escalabilidade da API.
 *
 * Exemplo de uso:
 *
 * GET /ninjas?page=0&size=5
 *
 * page = número da página, começando em 0
 * size = quantidade de registros por página
 *
 * @param pageable Objeto criado automaticamente pelo Spring com os dados
 *                 de paginação enviados na URL.
 * @return Página de NinjaResponseDTO.
 */
public Page<NinjaResponseDTO> listarNinjas(Pageable pageable) {

    return ninjaRepository.findAll(pageable)
            .map(NinjaMapper::toResponseDTO);
}

/**
 * ============================================================================
 * BUSCAR NINJA POR NOME
 * ============================================================================
 *
 * Busca ninjas cadastrados utilizando o nome como filtro.
 *
 * Fluxo:
 *
 * Cliente
 *    ↓
 * Nome informado
 *    ↓
 * Repository (findByNome)
 *    ↓
 * List<NinjaModel>
 *    ↓
 * NinjaMapper
 *    ↓
 * List<NinjaResponseDTO>
 *    ↓
 * Cliente
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * nome = nome
 *
 * findByNome = encontrar por nome
 *
 * @param nome Nome do ninja.
 * @return Lista de ninjas encontrados.
 */
public List<NinjaResponseDTO> buscarPorNome(String nome) {

    return ninjaRepository.findByNome(nome)
            .stream()
            .map(NinjaMapper::toResponseDTO)
            .toList();
}

/**
 * ============================================================================
 * BUSCA INTERNA DA ENTIDADE
 * ============================================================================
 *
 * Método utilizado apenas pelo Service para recuperar a entidade
 * NinjaModel diretamente do banco de dados.
 *
 * Diferente do método buscarNinjaPorId(), este retorna a entidade
 * e não um DTO, sendo utilizado em operações internas como UPDATE
 * e DELETE.
 *
 * @param id ID do ninja.
 * @return NinjaModel encontrado.
 */
private NinjaModel buscarEntidadePorId(Long id) {

    return ninjaRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Ninja não encontrado com ID: " + id));
}

    /**
 * ============================================================================
 * BUSCAR NINJA POR ID
 * ============================================================================
 *
 * Busca um ninja pelo seu identificador e converte a entidade para um
 * DTO antes de retornar ao Controller.
 *
 * Utilizamos DTO para evitar expor diretamente a estrutura da entidade
 * do banco de dados para o cliente da API.
 *
 * Fluxo:
 *
 * Banco
 *    ↓
 * NinjaModel
 *    ↓
 * NinjaMapper
 *    ↓
 * NinjaResponseDTO
 *
 * @param id ID do ninja.
 * @return NinjaResponseDTO.
 */
public NinjaResponseDTO buscarNinjaPorId(Long id) {

    // Busca o ninja ou lança exceção caso não exista
    NinjaModel ninja = ninjaRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Ninja não encontrado com ID: " + id));

    // Converte para DTO
    return NinjaMapper.toResponseDTO(ninja);

}

    /**
     * Salva um novo ninja no banco de dados.
     * 
     * @param ninja Objeto contendo os dados do ninja.
     * @return O ninja salvo com seu ID gerado.
     */
    public NinjaResponseDTO salvarNinja(NinjaRequestDTO ninjaDTO) {
        
        // Cria uma nova entidade que será persistida no banco
        NinjaModel ninja = new NinjaModel();
        
        // Copia os dados enviados pelo cliente para a entidade
        ninja.setNome(ninjaDTO.getNome());
        ninja.setEmail(ninjaDTO.getEmail());
        ninja.setIdade(ninjaDTO.getIdade());
        // Garantir que o ID seja nulo na criação para evitar que o cliente force a atualização de um registro existente (ID Spoofing)
        ninja.setId(null);

        MissaoModel missao = missaoRepository.findById(
        ninjaDTO.getMissaoId())
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Missão não encontrada."
        ));
        
        ninja.setMissao(missao);
        
        // Salva no banco
        NinjaModel ninjaSalvo = ninjaRepository.save(ninja);
        
        return NinjaMapper.toResponseDTO(ninjaSalvo);
    }

    /**
     * Deleta um ninja pelo ID de forma segura.
     * 
     * @param id ID do ninja a ser deletado.
     */
    public void deletarNinja(Long id) {
        // Verifica se o ninja existe antes de tentar deletar para evitar erros inesperados
        NinjaModel ninja = buscarEntidadePorId(id);
        ninjaRepository.delete(ninja);
    }

            /**
         * ============================================================================
         * ATUALIZAR NINJA
         * ============================================================================
         *
         * Atualiza um ninja existente utilizando um DTO de requisição.
         *
         * O cliente envia apenas os dados permitidos através do
         * NinjaRequestDTO. O Service busca a entidade existente,
         * atualiza seus atributos, persiste no banco e converte o
         * resultado para um NinjaResponseDTO.
         *
         * Fluxo:
         *
         * Cliente
         *     ↓
         * NinjaRequestDTO
         *     ↓
         * Service
         *     ↓
         * NinjaModel
         *     ↓
         * Banco de Dados
         *     ↓
         * NinjaMapper
         *     ↓
         * NinjaResponseDTO
         *
         * @param id Identificador do ninja.
         * @param ninjaDTO Dados enviados para atualização.
         * @return Ninja atualizado em formato DTO.
         */
        public NinjaResponseDTO atualizarNinja(Long id,NinjaRequestDTO ninjaDTO) {
        
        /**
        * Busca a entidade existente no banco.
        *
        * Não utilizamos buscarNinjaPorId(), pois esse método retorna
        * um NinjaResponseDTO destinado ao cliente da API.
        *
        * Para atualizar um registro, precisamos da entidade
        * (NinjaModel), que será modificada e salva novamente.
        */
       NinjaModel ninjaExistente = buscarEntidadePorId(id);
       /**
        * Atualiza os dados básicos do ninja.
        *
        * Copiamos os valores enviados no DTO para a entidade que já
        * existe no banco de dados. Dessa forma preservamos seu ID
        * original e modificamos apenas os campos permitidos.
        */
       ninjaExistente.setNome(ninjaDTO.getNome());
       ninjaExistente.setEmail(ninjaDTO.getEmail());
       ninjaExistente.setIdade(ninjaDTO.getIdade());
       
       /**
        * Busca a missão informada pelo cliente.
        *
        * O DTO envia apenas o ID da missão. Por isso precisamos
        * localizar a entidade no banco antes de associá-la ao ninja.
        *
        * Caso a missão não exista, retornamos um erro HTTP 404,
        * impedindo a criação de um relacionamento inválido.
        */
       MissaoModel missao = missaoRepository.findById(
               ninjaDTO.getMissaoId())
               .orElseThrow(() -> new ResponseStatusException(
                       HttpStatus.NOT_FOUND,
                       "Missão não encontrada."
               ));

       /**
        * Associa a missão encontrada ao ninja.
        */
       ninjaExistente.setMissao(missao);
       
       
       NinjaModel ninjaSalvo = ninjaRepository.save(ninjaExistente);
       
       return NinjaMapper.toResponseDTO(ninjaSalvo);
    }
}
