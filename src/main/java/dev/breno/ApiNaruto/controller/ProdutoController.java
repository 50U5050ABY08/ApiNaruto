package dev.breno.ApiNaruto.controller;

import dev.breno.ApiNaruto.dto.ProdutoRequestDTO;
import dev.breno.ApiNaruto.dto.ProdutoResponseDTO;
import dev.breno.ApiNaruto.service.ProdutoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutos(
            @ParameterObject
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(produtoService.listarProdutos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @GetMapping("/sku")
    public ResponseEntity<ProdutoResponseDTO> buscarPorSku(@RequestParam String sku) {
        return ResponseEntity.ok(produtoService.buscarPorSku(sku));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(produtoService.listarAtivos());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvarProduto(
            @RequestBody @Valid ProdutoRequestDTO dto) {

        ProdutoResponseDTO produto = produtoService.salvarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequestDTO dto) {

        return ResponseEntity.ok(produtoService.atualizarProduto(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<ProdutoResponseDTO> desativarProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.desativarProduto(id));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<ProdutoResponseDTO> ativarProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.ativarProduto(id));
    }
}
