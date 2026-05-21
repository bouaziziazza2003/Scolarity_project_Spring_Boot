package org.ms.factureservice.feign;

import org.ms.factureservice.model.Produit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="PRODUIT-SERVICE")
public interface ProduitServiceClient {

    @GetMapping("/produits")
    List<Produit> getAllProduits();

    @GetMapping("/produits/{id}")
    Produit findProductById(@PathVariable Long id);
}