package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.dto.ProdutoRequestDTO;
import dev.breno.ApiNaruto.dto.ProdutoResponseDTO;
import dev.breno.ApiNaruto.exception.RegraDeNegocioException;
import dev.breno.ApiNaruto.mapper.ProdutoMapper;
import dev.breno.ApiNaruto.model.ProdutoModel;
import dev.breno.ApiNaruto.repository.ProdutoRepository;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<ProdutoResponseDTO> listarProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(ProdutoMapper::toResponseDTO);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return ProdutoMapper.toResponseDTO(buscarEntidadePorId(id));
    }

    public ProdutoResponseDTO buscarPorSku(String sku) {
        String skuNormalizado = normalizarSku(sku);
        ProdutoModel produto = produtoRepository.findBySkuIgnoreCase(skuNormalizado)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado com SKU: " + skuNormalizado));
        return ProdutoMapper.toResponseDTO(produto);
    }

    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        String nomeNormalizado = nome == null ? "" : nome.trim();
        return produtoRepository.findByNomeContainingIgnoreCase(nomeNormalizado)
                .stream()
                .map(ProdutoMapper::toResponseDTO)
                .toList();
    }

    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(ProdutoMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO dto) {
        String sku = normalizarSku(dto.getSku());
        validarSkuDisponivel(sku, null);

        ProdutoModel produto = ProdutoMapper.toModel(dto);
        produto.setSku(sku);
        produto.setNome(dto.getNome().trim());
        produto.setAtivo(true);

        ProdutoModel salvo = produtoRepository.save(produto);
        return ProdutoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        ProdutoModel produto = buscarEntidadePorId(id);
        String sku = normalizarSku(dto.getSku());
        validarSkuDisponivel(sku, id);

        ProdutoMapper.updateModel(dto, produto);
        produto.setSku(sku);
        produto.setNome(dto.getNome().trim());

        ProdutoModel salvo = produtoRepository.save(produto);
        return ProdutoMapper.toResponseDTO(salvo);
    }

    @Transactional
    public ProdutoResponseDTO desativarProduto(Long id) {
        ProdutoModel produto = buscarEntidadePorId(id);
        produto.setAtivo(false);
        return ProdutoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponseDTO ativarProduto(Long id) {
        ProdutoModel produto = buscarEntidadePorId(id);
        produto.setAtivo(true);
        return ProdutoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    private ProdutoModel buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado com ID: " + id));
    }

    private void validarSkuDisponivel(String sku, Long produtoIdAtual) {
        produtoRepository.findBySkuIgnoreCase(sku).ifPresent(produto -> {
            if (produtoIdAtual == null || !produto.getId().equals(produtoIdAtual)) {
                throw new RegraDeNegocioException("Já existe um produto cadastrado com o SKU: " + sku);
            }
        });
    }

    private String normalizarSku(String sku) {
        return sku.trim().toUpperCase(Locale.ROOT);
    }
}
