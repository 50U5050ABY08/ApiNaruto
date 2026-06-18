package dev.breno.ApiNaruto.repository;

import dev.breno.ApiNaruto.model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

    @Repository
    public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {

    // Métodos como save(), findAll(), findById() e deleteById() já estão disponíveis automaticamente!

    /**
     * ============================================================================
     * BUSCAR NINJA POR NOME
     * ============================================================================
     *
     * O Spring Data JPA interpreta automaticamente o nome deste método
     * e gera a consulta SQL correspondente.
     *
     * Tradução:
     *
     * find = encontrar
     * by = por
     * nome = nome
     *
     * findByNome = encontrar por nome
     *
     * Exemplo de SQL gerado:
     *
     * SELECT * FROM tb_cadastro
     * WHERE nome = ?
     *
     * @param nome Nome do ninja.
     * @return Lista de ninjas encontrados.
     */
    List<NinjaModel> findByNome(String nome);
    
    /**
 * ============================================================================
 * BUSCAR NINJA POR TRECHO DO NOME
 * ============================================================================
 *
 * O Spring gera automaticamente uma consulta utilizando LIKE.
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * nome = nome
 * containing = contendo
 *
 * findByNomeContaining
 * ↓
 * Encontrar nomes contendo determinado texto.
 *
 * Exemplo:
 *
 * Naruto
 * ↓
 * Naruto Uzumaki
 *
 * SQL aproximado:
 *
 * SELECT * FROM tb_cadastro
 * WHERE nome LIKE '%Naruto%'
 *
 * @param nome Trecho do nome a ser pesquisado.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByNomeContaining(String nome);
    
    /**
 * ============================================================================
 * BUSCAR NINJA POR TRECHO DO NOME IGNORANDO MAIÚSCULAS E MINÚSCULAS
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * nome = nome
 * containing = contendo
 * ignore = ignorar
 * case = maiúsculas/minúsculas
 *
 * findByNomeContainingIgnoreCase
 *
 * Encontrar nomes contendo determinado texto sem diferenciar letras
 * maiúsculas de minúsculas.
 *
 * Exemplos:
 *
 * naruto
 * Naruto
 * NARUTO
 *
 * Todos encontram:
 *
 * Naruto Uzumaki
 *
 * @param nome Trecho do nome.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByNomeContainingIgnoreCase(String nome);
    
    
    /**
 * ============================================================================
 * BUSCAR NINJA POR E-MAIL
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * email = e-mail
 *
 * findByEmail
 *
 * Encontrar ninja através do e-mail.
 *
 * Como o e-mail deve ser único na aplicação,
 * esperamos retornar apenas um ninja.
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE email = ?
 *
 * @param email E-mail do ninja.
 * @return Ninja encontrado.
 */
    Optional<NinjaModel> findByEmail(String email);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS POR IDADE
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * idade = idade
 *
 * findByIdade
 *
 * Encontrar ninjas pela idade.
 *
 * Como vários ninjas podem ter a mesma idade,
 * o retorno deve ser uma lista.
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE idade = ?
 *
 * @param idade Idade dos ninjas.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByIdade(int idade);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MAIOR QUE
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * idade = idade
 * greater = maior
 * than = que
 *
 * findByIdadeGreaterThan
 *
 * Encontrar ninjas com idade superior ao valor informado.
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE idade > ?
 *
 * @param idade Idade utilizada como referência.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByIdadeGreaterThan(int idade);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MAIOR OU IGUAL
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * idade = idade
 * greater = maior
 * than = que
 * equal = igual
 *
 * findByIdadeGreaterThanEqual
 *
 * Encontrar ninjas com idade maior ou igual ao valor informado.
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE idade >= ?
 *
 * @param idade Idade utilizada como referência.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByIdadeGreaterThanEqual(int idade);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MENOR QUE
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * idade = idade
 * less = menor
 * than = que
 *
 * findByIdadeLessThan
 *
 * Encontrar ninjas com idade inferior ao valor informado.
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE idade < ?
 *
 * @param idade Idade utilizada como referência.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByIdadeLessThan(int idade);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MENOR OU IGUAL
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * idade = idade
 * less = menor
 * than = que
 * equal = igual
 *
 * findByIdadeLessThanEqual
 *
 * Encontrar ninjas com idade menor ou igual ao valor informado.
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE idade <= ?
 *
 * @param idade Idade utilizada como referência.
 * @return Lista de ninjas encontrados.
 */
    List<NinjaModel> findByIdadeLessThanEqual(int idade);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS MAIORES QUE UTILIZANDO JPQL
 * ============================================================================
 *
 * Esta consulta é escrita manualmente utilizando JPQL.
 *
 * Tradução:
 *
 * SELECT = selecionar
 * FROM = de
 * WHERE = onde
 *
 * n = apelido (alias) da entidade NinjaModel
 *
 * :idade = parâmetro nomeado
 *
 * @param idade Idade utilizada como referência.
 * @return Lista de ninjas encontrados.
 */
    @Query("""
           SELECT n
           FROM NinjaModel n
           WHERE n.idade > :idade
           """)
    List<NinjaModel> buscarNinjasMaioresQue (@Param("idade") int idade);
    
    /**
 * ============================================================================
 * BUSCAR NINJAS PELO ID DA MISSÃO
 * ============================================================================
 *
 * Tradução:
 *
 * find = encontrar
 * by = por
 * missao = missão
 * id = identificador
 *
 * findByMissaoId
 *
 * O Spring navega automaticamente pelo relacionamento:
 *
 * NinjaModel
 *     ↓
 * MissaoModel
 *     ↓
 * id
 *
 * SQL aproximado:
 *
 * SELECT *
 * FROM tb_cadastro
 * WHERE missoes_id = ?
 *
 * @param id ID da missão.
 * @return Lista de ninjas da missão.
 */
    List<NinjaModel> findByMissaoId(Long id);

}