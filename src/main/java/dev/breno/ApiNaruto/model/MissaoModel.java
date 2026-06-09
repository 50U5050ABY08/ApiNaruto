/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.model;

import dev.breno.ApiNaruto.model.NinjaModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author brenorocha
 */
@Entity
@Table(name = "tb_missoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MissaoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    
    private String missão;
    
    
    
    private String ranking_da_missao;
    
   
    @OneToMany(mappedBy = "missoes") // um para todos.
    private List<NinjaModel> ninjas;
    
    
}
