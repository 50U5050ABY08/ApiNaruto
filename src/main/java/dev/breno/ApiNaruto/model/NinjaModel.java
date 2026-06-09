/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author brenorocha
 */
@Entity
@Table(name ="tb_cadastro")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class NinjaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    private Long id;
    
    private String nome;
    
    private String email;
    
    private int idade;
    
    @ManyToOne // Um ninja tem uma unica missao
    @JoinColumn(name = "missoes_id") // Foreing Key (Chave Estrangeira).
    private MissaoModel missoes;
    
}