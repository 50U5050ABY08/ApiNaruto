/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.service.NinjaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.breno.ApiNaruto.dto.NinjaResponseDTO;
import dev.breno.ApiNaruto.dto.NinjaRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ============================================================================
 * CAMADA DE ENTRADA (CONTROLLER DE NINJAS)
 * ============================================================================
 *
 * Responsável por receber as requisições HTTP relacionadas aos ninjas e
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
@RequestMapping("/ninjas")
@RequiredArgsConstructor
public class NinjaController {

    /**
     * Camada responsável pelas regras de negócio de ninjas.
     */
    private final NinjaService ninjaService;

    /**
     * GET /ninjas
     * Retorna todos os ninjas cadastrados.
     * 
     * @return Lista de ninjas com status 200 OK.
     */
    
    
    /**
     * ============================================================================
     * LISTAR NINJAS COM PAGINAÇÃO
     * ============================================================================
     *
     * Endpoint responsável por listar ninjas de forma paginada.
     *
     * A paginação evita retornar todos os registros de uma vez, melhorando
     * desempenho e organização da resposta.
     *
     * Exemplo:
     *
     * GET /ninjas?page=0&size=5
     *
     * @param pageable Objeto criado automaticamente pelo Spring a partir dos
     *                 parâmetros enviados na URL.
     * @return Página de ninjas em formato DTO.
     */
    @GetMapping
    public ResponseEntity<Page<NinjaResponseDTO>> listarNinjas(
            Pageable pageable) {

    Page<NinjaResponseDTO> ninjas =
            ninjaService.listarNinjas(pageable);

    return ResponseEntity.ok(ninjas);
}
    
 /**
 * ============================================================================
 * BUSCAR NINJAS POR NOME
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas utilizando o nome como filtro.
 *
 * Tradução:
 *
 * RequestParam:
 * Request = requisição
 * Param = parâmetro
 *
 * @RequestParam pega um valor enviado na URL.
 *
 * Exemplo:
 *
 * GET /ninjas/buscar?nome=Naruto
 *
 * Neste caso:
 *
 * nome = Naruto
 *
 * @param nome Nome usado como filtro de busca.
 * @return Lista de ninjas encontrados em formato DTO.
 */
    @GetMapping("/buscar")
    public ResponseEntity<List<NinjaResponseDTO>> buscarPorNome(
        @RequestParam String nome) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorNome(nome);

    return ResponseEntity.ok(ninjas);
}

/**
 * ============================================================================
 * BUSCAR NINJA POR E-MAIL
 * ============================================================================
 *
 * Endpoint responsável por buscar um ninja através do e-mail.
 *
 * Tradução:
 *
 * RequestParam:
 * Request = requisição
 * Param = parâmetro
 *
 * O e-mail será enviado como parâmetro na URL.
 *
 * Exemplo:
 *
 * GET /ninjas/email?email=naruto@folha.com
 *
 * @param email E-mail usado na busca.
 * @return Ninja encontrado em formato DTO.
 */
    @GetMapping("/email")
    public ResponseEntity<NinjaResponseDTO> buscarPorEmail(
        @RequestParam String email) {

    NinjaResponseDTO ninja =
            ninjaService.buscarPorEmail(email);

    return ResponseEntity.ok(ninja);
}


/**
 * ============================================================================
 * BUSCAR NINJAS POR IDADE
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas utilizando a idade como filtro.
 *
 * Como vários ninjas podem ter a mesma idade, retornamos uma lista
 * de NinjaResponseDTO.
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
 * GET /ninjas/idade?idade=16
 *
 * Neste caso:
 *
 * idade = 16
 *
 * @param idade Idade usada como filtro.
 * @return Lista de ninjas encontrados em formato DTO.
 */
    @GetMapping("/idade")
    public ResponseEntity<List<NinjaResponseDTO>> buscarPorIdade(
        @RequestParam int idade) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorIdade(idade);

    return ResponseEntity.ok(ninjas);
}

/**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MAIOR QUE
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas cuja idade seja maior
 * que o valor informado na URL.
 *
 * Tradução:
 *
 * greater = maior
 * than = que
 *
 * GreaterThan = maior que
 *
 * Exemplo:
 *
 * GET /ninjas/idade/maior?idade=18
 *
 * Neste caso, serão retornados ninjas com idade maior que 18.
 *
 * @param idade Idade usada como referência.
 * @return Lista de ninjas com idade superior ao valor informado.
 */
    @GetMapping("/idade/maior")
    public ResponseEntity<List<NinjaResponseDTO>> buscarPorIdadeMaiorQue(
        @RequestParam int idade) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorIdadeMaiorQue(idade);

    return ResponseEntity.ok(ninjas);
}


/**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MAIOR OU IGUAL
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas cuja idade seja maior ou igual
 * ao valor informado na URL.
 *
 * Tradução:
 *
 * greater = maior
 * than = que
 * equal = igual
 *
 * GreaterThanEqual = maior ou igual
 *
 * Exemplo:
 *
 * GET /ninjas/idade/maior-ou-igual?idade=18
 *
 * Neste caso, serão retornados ninjas com idade maior ou igual a 18.
 *
 * @param idade Idade usada como referência.
 * @return Lista de ninjas com idade maior ou igual ao valor informado.
 */
    @GetMapping("/idade/maior-ou-igual")
    public ResponseEntity<List<NinjaResponseDTO>> buscarPorIdadeMaiorOuIgual(
        @RequestParam int idade) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorIdadeMaiorOuIgual(idade);

    return ResponseEntity.ok(ninjas);
}
    
    /**
 * ============================================================================
 * BUSCAR NINJAS MAIORES QUE USANDO @QUERY
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas com idade maior que o valor informado,
 * utilizando uma consulta JPQL escrita manualmente no Repository com @Query.
 *
 * Tradução:
 *
 * Query = consulta
 *
 * @Query permite escrever a consulta manualmente quando o nome do método
 * começaria a ficar muito grande ou quando precisamos de mais controle.
 *
 * Exemplo:
 *
 * GET /ninjas/query/maiores?idade=18
 *
 * @param idade Idade usada como referência.
 * @return Lista de ninjas com idade superior ao valor informado.
 */
    @GetMapping("/query/maiores")
    public ResponseEntity<List<NinjaResponseDTO>> buscarMaioresQueComQuery(
        @RequestParam int idade) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarMaioresQueComQuery(idade);

    return ResponseEntity.ok(ninjas);
}


/**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MENOR QUE
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas cuja idade seja menor
 * que o valor informado na URL.
 *
 * Tradução:
 *
 * less = menor
 * than = que
 *
 * LessThan = menor que
 *
 * Exemplo:
 *
 * GET /ninjas/idade/menor?idade=18
 *
 * Neste caso, serão retornados ninjas com idade menor que 18.
 *
 * @param idade Idade usada como referência.
 * @return Lista de ninjas com idade inferior ao valor informado.
 */
    @GetMapping("/idade/menor")
    public ResponseEntity<List<NinjaResponseDTO>> buscarPorIdadeMenorQue(
        @RequestParam int idade) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorIdadeMenorQue(idade);

    return ResponseEntity.ok(ninjas);
}
    
    /**
 * ============================================================================
 * BUSCAR NINJAS COM IDADE MENOR OU IGUAL
 * ============================================================================
 *
 * Endpoint responsável por buscar ninjas cuja idade seja menor ou igual
 * ao valor informado na URL.
 *
 * Tradução:
 *
 * less = menor
 * than = que
 * equal = igual
 *
 * LessThanEqual = menor ou igual
 *
 * Exemplo:
 *
 * GET /ninjas/idade/menor-ou-igual?idade=18
 *
 * Neste caso, serão retornados ninjas com idade menor ou igual a 18.
 *
 * @param idade Idade usada como referência.
 * @return Lista de ninjas com idade menor ou igual ao valor informado.
 */
    @GetMapping("/idade/menor-ou-igual")
    public ResponseEntity<List<NinjaResponseDTO>> buscarPorIdadeMenorOuIgual(
        @RequestParam int idade) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorIdadeMenorOuIgual(idade);

    return ResponseEntity.ok(ninjas);
}

    /**
    * ============================================================================
    * BUSCAR NINJA POR ID
    * ============================================================================
    *
    * Endpoint responsável por localizar um ninja pelo seu ID.
    *
    * O Controller delega a regra de negócio ao Service e retorna um
    * NinjaResponseDTO ao cliente, evitando expor diretamente a entidade
    * do banco de dados.
    *
    * Endpoint:
    *
    * GET /ninjas/{id}
    *
    * @param id Identificador do ninja.
    * @return NinjaResponseDTO com status HTTP 200 (OK).
    */
   @GetMapping("/{id}")
   public ResponseEntity<NinjaResponseDTO> buscarNinjaPorId(
        @PathVariable Long id) {

    NinjaResponseDTO ninja = ninjaService.buscarNinjaPorId(id);

    return ResponseEntity.ok(ninja);

}
   
   /**
 * ============================================================================
 * BUSCAR NINJAS PELO ID DA MISSÃO
 * ============================================================================
 *
 * Endpoint responsável por buscar todos os ninjas vinculados
 * a uma missão específica.
 *
 * Este endpoint usa o relacionamento entre NinjaModel e MissaoModel.
 *
 * Tradução:
 *
 * missao = missão
 * id = identificador
 *
 * Exemplo:
 *
 * GET /ninjas/missao/1
 *
 * Neste caso, serão retornados todos os ninjas que possuem
 * a missão de ID 1.
 *
 * @param missaoId ID da missão.
 * @return Lista de ninjas vinculados à missão informada.
 */
@GetMapping("/missao/{missaoId}")
public ResponseEntity<List<NinjaResponseDTO>> buscarPorMissaoId(
        @PathVariable Long missaoId) {

    List<NinjaResponseDTO> ninjas =
            ninjaService.buscarPorMissaoId(missaoId);

    return ResponseEntity.ok(ninjas);
}

/**
 * ============================================================================
 * CADASTRAR NOVO NINJA
 * ============================================================================
 *
 * Recebe um NinjaRequestDTO enviado pelo cliente, encaminha para a
 * camada de serviço e retorna um NinjaResponseDTO.
 *
 * Dessa forma a API deixa de expor diretamente a entidade do banco,
 * seguindo boas práticas de arquitetura em camadas.
 *
 * Endpoint:
 *
 * POST /ninjas
 *
 * @param ninja Dados enviados pelo cliente.
 * @return Ninja cadastrado com status HTTP 201 (Created).
 */
    @PostMapping
    public ResponseEntity<NinjaResponseDTO> salvarNinja(
        @RequestBody @Valid NinjaRequestDTO ninja) {

    NinjaResponseDTO novoNinja = ninjaService.salvarNinja(ninja);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(novoNinja);
}

    /**
 * ============================================================================
 * ATUALIZAR NINJA
 * ============================================================================
 *
 * Endpoint responsável por atualizar um ninja já existente.
 *
 * Diferentemente da versão anterior, o Controller não recebe mais uma
 * entidade (NinjaModel), e sim um DTO de requisição (NinjaRequestDTO),
 * contendo apenas os dados que o cliente pode enviar.
 *
 * Após a atualização, o Service retorna um NinjaResponseDTO, evitando
 * expor diretamente a entidade do banco de dados para o cliente.
 *
 * Fluxo:
 *
 * Cliente
 *     ↓
 * NinjaRequestDTO
 *     ↓
 * Controller
 *     ↓
 * Service
 *     ↓
 * Banco de Dados
 *     ↓
 * NinjaModel
 *     ↓
 * NinjaMapper
 *     ↓
 * NinjaResponseDTO
 *
 * Endpoint:
 *
 * PUT /ninjas/{id}
 *
 * @param id Identificador do ninja.
 * @param ninjaDTO Dados enviados pelo cliente para atualização.
 * @return Ninja atualizado em formato DTO com status HTTP 200 (OK).
 */
    @PutMapping("/{id}")
    public ResponseEntity<NinjaResponseDTO> atualizarNinja(
        @PathVariable Long id,
        @RequestBody @Valid NinjaRequestDTO ninjaDTO) {

    NinjaResponseDTO ninjaAtualizado =
            ninjaService.atualizarNinja(id, ninjaDTO);

    return ResponseEntity.ok(ninjaAtualizado);
}

    /**
     * DELETE /ninjas/{id}
     * Deleta um ninja pelo ID de forma segura.
     * 
     * @param id ID do ninja a ser deletado.
     * @return Status 204 No Content indicando sucesso sem corpo de resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNinja(@PathVariable Long id) {
        ninjaService.deletarNinja(id);
        return ResponseEntity.noContent().build();
    }
}
