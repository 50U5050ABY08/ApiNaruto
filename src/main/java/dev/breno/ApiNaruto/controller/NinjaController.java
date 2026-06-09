/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brenorocha
 */
@RestController /* Rest = transferência de estado representacional (padrão de APIs)
                   Controller = controlador
"Com esse Anotations essa classe vai receber requisições HTTP e retornar respostas."
*/


@RequestMapping("/ninjas") /* Request = requisição
                              Mapping = mapeamento

"Significa que toda rota desta classe começará com:"
/ninjas

e ai vamos ter:
GET /ninjas
POST /ninjas
PUT /ninjas
DELETE /ninjas
*/






public class NinjaController {
           //final = não poderá receber outra referência depois da construção do objeto
    private final NinjaService ninjaService; 
    //Porque o uso de final? Achei uma boa estrutura o Spring apenas injetar o Service uma vez.
    
    public NinjaController(NinjaService ninjaService) {
         this.ninjaService = ninjaService;
         //this = este objeto
         //this.ninjaService = ninjaService; O ATRIBUTO DA CLASSE RECEBE O PARÂMETRO RECEBIDO PELO CONSTRUTOR.
         
    }
    
}
