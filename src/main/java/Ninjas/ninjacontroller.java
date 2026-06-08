/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ninjas;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brenorocha
 */
@RestController
@RequestMapping
public class ninjacontroller {
    
    @GetMapping("/funfando")
    public String funfando() {
   
        return "Só pra saber se essa merda de rota ta funcionando, nesta merda";
    }
    
}
