package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.model.NinjaModel;
import dev.breno.ApiNaruto.repository.NinjaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * ============================================================================
 * CAMADA DE NEGÓCIO (SERVICE)
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
 * [ANOTAÇÃO @Autowired] (apenas para motivos de consultas futuras)
     * - O que faz: Realiza a Injeção de Dependência. O Spring busca o NinjaRepository
     *   e o injeta automaticamente aqui no construtor.
     * - Segurança (Injeção via Construtor): Esta é a forma mais segura de injetar dependências.
     *   Ela garante que a classe nunca seja instanciada sem as suas dependências obrigatórias,
     *   evitando erros de ponteiro nulo (NullPointerException) e facilitando testes unitários.
 */

/////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * Histórico:
 *
 * Antes utilizávamos @Autowired para injeção de dependência.
 *
 * Atualmente optamos por @RequiredArgsConstructor,
 * que gera automaticamente o construtor para atributos
 * final e mantém a injeção por construtor, considerada
 * uma boa prática no Spring Boot moderno.
 */

//////////////////////////////////////////////////////////////////////////////////////


/**
 * @RequiredArgsConstructor (Lombok)
 *
 * Tradução:
 * Required = Obrigatório
 * Args = Argumentos
 * Constructor = Construtor
 *
 * O Lombok gera automaticamente um construtor contendo
 * todos os atributos marcados como "final".
 *
 * Neste caso, ele cria internamente:
 *
 * public NinjaService(NinjaRepository ninjaRepository){
 *     this.ninjaRepository = ninjaRepository;
 * }
 *
 * Isso elimina código repetitivo e mantém a injeção de
 * dependência por construtor, considerada uma boa prática
 * no Spring Boot moderno.
 */


@Service
@RequiredArgsConstructor
public class NinjaService {

    /**
     * Dependência responsável pelo acesso ao banco.
     */
    private final NinjaRepository ninjaRepository;

    public List<NinjaModel> listarNinjas() {
        return ninjaRepository.findAll();
    }

    public NinjaModel salvarNinja(NinjaModel ninja) {
        return ninjaRepository.save(ninja);
    }

}