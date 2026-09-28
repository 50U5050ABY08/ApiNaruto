package dev.breno.ApiNaruto.mapper;

import dev.breno.ApiNaruto.dto.ProdutoRequestDTO;
import dev.breno.ApiNaruto.dto.ProdutoResponseDTO;
import dev.breno.ApiNaruto.model.ProdutoModel;

public class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static ProdutoModel toModel(ProdutoRequestDTO dto) {
        ProdutoModel produto = new ProdutoModel();
        updateModel(dto, produto);
        produto.setAtivo(true);
        return produto;
    }

    public static void updateModel(ProdutoRequestDTO dto, ProdutoModel produto) {
        produto.setSku(dto.getSku());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPrecoCusto(dto.getPrecoCusto());
        produto.setPrecoVenda(dto.getPrecoVenda());
    }

    public static ProdutoResponseDTO toResponseDTO(ProdutoModel produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setSku(produto.getSku());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPrecoCusto(produto.getPrecoCusto());
        dto.setPrecoVenda(produto.getPrecoVenda());
        dto.setAtivo(produto.getAtivo());
        return dto;
    }
}
