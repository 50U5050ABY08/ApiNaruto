/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ============================================================================
 * ENTIDADE DE USUÁRIO DO SISTEMA
 * ============================================================================
 *
 * Representa o usuário responsável pela autenticação na API.
 *
 * Esta entidade é separada de NinjaModel porque autenticação
 * é uma responsabilidade diferente da regra de negócio dos ninjas.
 *
 * Tradução:
 *
 * User = usuário
 * Username = nome de usuário
 * Password = senha
 * Role = papel/função/permissão
 */
@Entity
@Table(name = "tb_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O username é obrigatório")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @NotBlank(message = "A role é obrigatória")
    private String role;
}
