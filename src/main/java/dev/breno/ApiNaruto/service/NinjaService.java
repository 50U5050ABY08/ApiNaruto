package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.model.NinjaModel;
import dev.breno.ApiNaruto.repository.NinjaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
     * Retorna todos os ninjas cadastrados.
     */
    public List<NinjaModel> listarNinjas() {
        return ninjaRepository.findAll();
    }

    /**
     * Busca um ninja pelo seu ID de forma segura.
     * 
     * @param id ID do ninja a ser buscado.
     * @return NinjaModel se encontrado.
     * @throws ResponseStatusException se o ninja não for encontrado (HTTP 404).
     */
    public NinjaModel buscarNinjaPorId(Long id) {
        return ninjaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ninja não encontrado com o ID: " + id));
    }

    /**
     * Salva um novo ninja no banco de dados.
     * 
     * @param ninja Objeto contendo os dados do ninja.
     * @return O ninja salvo com seu ID gerado.
     */
    public NinjaModel salvarNinja(NinjaModel ninja) {
        // Garantir que o ID seja nulo na criação para evitar que o cliente force a atualização de um registro existente (ID Spoofing)
        ninja.setId(null);
        return ninjaRepository.save(ninja);
    }

    /**
     * Deleta um ninja pelo ID de forma segura.
     * 
     * @param id ID do ninja a ser deletado.
     */
    public void deletarNinja(Long id) {
        // Verifica se o ninja existe antes de tentar deletar para evitar erros inesperados
        NinjaModel ninja = buscarNinjaPorId(id);
        ninjaRepository.delete(ninja);
    }

    /**
     * Atualiza um ninja existente de forma segura.
     * 
     * @param id ID do ninja a ser atualizado.
     * @param ninjaAtualizado Objeto contendo os novos dados.
     * @return O ninja atualizado e persistido.
     */
    public NinjaModel atualizarNinja(Long id, NinjaModel ninjaAtualizado) {
        // Verifica se o ninja existe no banco antes de atualizar
        NinjaModel ninjaExistente = buscarNinjaPorId(id);
        
        // Atualiza apenas os campos permitidos, mantendo a integridade do ID original
        ninjaExistente.setNome(ninjaAtualizado.getNome());
        ninjaExistente.setEmail(ninjaAtualizado.getEmail());
        ninjaExistente.setIdade(ninjaAtualizado.getIdade());
        ninjaExistente.setMissoes(ninjaAtualizado.getMissoes());
        
        return ninjaRepository.save(ninjaExistente);
    }
}
