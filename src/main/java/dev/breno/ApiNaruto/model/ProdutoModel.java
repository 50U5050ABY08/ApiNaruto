package dev.breno.ApiNaruto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade que representa uma mercadoria comercializada pela distribuidora.
 *
 * SKU = Stock Keeping Unit = Unidade de Manutenção de Estoque.
 */
@Entity
@Table(name = "tb_produtos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String sku;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 2000)
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precoCusto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precoVenda;

    @Column(nullable = false)
    private Boolean ativo = true;
}
