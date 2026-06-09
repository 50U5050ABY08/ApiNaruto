/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.model.NinjaModel;
import dev.breno.ApiNaruto.service.NinjaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================================
 * CAMADA DE ENTRADA (CONTROLLER)
 * ============================================================================
 *
 * Responsável por receber as requisições HTTP e encaminhá-las
 * para a camada de negócio (Service).
 *
 * Fluxo:
 *
 * Cliente
 *    ↓
 * Controller
 *    ↓
 * Service
 *    ↓
 * Repository
 *    ↓
 * Banco de Dados
 */

@RestController

/**
 * @RequestMapping
 *
 * Tradução:
 * Request = Requisição
 * Mapping = Mapeamento
 *
 * Define a rota base deste Controller.
 *
 * Todas as rotas começarão com:
 *
 * /ninjas
 */
@RequestMapping("/ninjas")

/**
 * @RequiredArgsConstructor (Lombok)
 *
 * Tradução:
 * Required = Obrigatório
 * Args = Argumentos
 * Constructor = Construtor
 *
 * Gera automaticamente um construtor contendo todos
 * os atributos marcados como final.
 *
 * Assim, não precisamos escrever manualmente:
 *
 * public NinjaController(NinjaService ninjaService){
 *     this.ninjaService = ninjaService;
 * }
 *
 * Essa abordagem é considerada uma boa prática no
 * Spring Boot moderno para Injeção de Dependência.
 */
@RequiredArgsConstructor
public class NinjaController {

    /**
     * Camada responsável pelas regras de negócio.
     */
    private final NinjaService ninjaService;

    /**
     * @GetMapping
     *
     * Tradução:
     * GET = Buscar
     *
     * Endpoint:
     *
     * GET /ninjas
     *
     * Retorna todos os ninjas cadastrados.
     */
    @GetMapping
    public List<NinjaModel> listarNinjas() {

        return ninjaService.listarNinjas();

    }

    /**
     * @PostMapping
     *
     * Tradução:
     * POST = Criar
     *
     * Endpoint:
     *
     * POST /ninjas
     *
     * Recebe um Ninja enviado no corpo da requisição
     * e encaminha para o Service realizar a persistência.
     *
     * Futuramente utilizaremos DTO + @Valid para
     * validar os dados de entrada.
     */
    @PostMapping
    public NinjaModel salvarNinja(@RequestBody NinjaModel ninja) {

        return ninjaService.salvarNinja(ninja);

    }

}