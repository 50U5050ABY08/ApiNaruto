package dev.breno.ApiNaruto.repository;

import dev.breno.ApiNaruto.model.ProdutoModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    Optional<ProdutoModel> findBySkuIgnoreCase(String sku);

    boolean existsBySkuIgnoreCase(String sku);

    List<ProdutoModel> findByNomeContainingIgnoreCase(String nome);

    List<ProdutoModel> findByAtivoTrue();
}
