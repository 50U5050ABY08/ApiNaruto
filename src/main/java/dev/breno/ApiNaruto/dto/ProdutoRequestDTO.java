package dev.breno.ApiNaruto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank(message = "O SKU é obrigatório")
    @Size(max = 60, message = "O SKU deve ter no máximo 60 caracteres")
    private String sku;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome;

    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
    private String descricao;

    @NotNull(message = "O preço de custo é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "O preço de custo não pode ser negativo")
    private BigDecimal precoCusto;

    @NotNull(message = "O preço de venda é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "O preço de venda não pode ser negativo")
    private BigDecimal precoVenda;
}
