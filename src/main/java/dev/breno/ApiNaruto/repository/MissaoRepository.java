package dev.breno.ApiNaruto.repository;

import dev.breno.ApiNaruto.model.MissaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ============================================================================
 * CAMADA DE PERSISTÊNCIA (REPOSITÓRIO DE MISSÕES)
 * ============================================================================
 * 
 * [ANOTAÇÃO @Repository]
 * - O que faz: Indica ao Spring que esta interface é um componente de acesso a dados (DAO).
 * - Segurança: Ativa a tradução automática de exceções do banco de dados. Se o PostgreSQL
 *   der um erro de conexão ou violação de chave, o Spring traduz isso em uma exceção amigável
 *   (DataAccessException), impedindo que detalhes técnicos internos do banco sejam expostos
 *   para o cliente final (o que seria uma falha de segurança por vazamento de informação).
 * 
 * [EXTENSÃO JpaRepository<MissaoModel, Long>]
 * - O que faz: Herda todos os métodos de CRUD (Create, Read, Update, Delete) prontos do Spring Data JPA.
 * - Segurança (SQL Injection): O Spring Data JPA usa Prepared Statements por baixo dos panos.
 *   Isso significa que qualquer parâmetro enviado pelo usuário é tratado estritamente como um valor,
 *   e nunca como código SQL executável. Você está 100% protegido contra SQL Injection aqui!
 */
@Repository
public interface MissaoRepository extends JpaRepository<MissaoModel, Long> {
    // Métodos como save(), findAll(), findById() e deleteById() já estão disponíveis automaticamente!
}
