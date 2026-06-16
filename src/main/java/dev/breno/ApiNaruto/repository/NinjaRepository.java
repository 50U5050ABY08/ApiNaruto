package dev.breno.ApiNaruto.repository;

import dev.breno.ApiNaruto.model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

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

}