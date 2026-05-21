package org.ms.factureservice.web;

import lombok.RequiredArgsConstructor;
import org.ms.factureservice.entities.Facture;
import org.ms.factureservice.entities.FactureLigne;
import org.ms.factureservice.feign.ClientServiceClient;
import org.ms.factureservice.feign.ProduitServiceClient;
import org.ms.factureservice.model.Client;
import org.ms.factureservice.model.Produit;
import org.ms.factureservice.repository.FactureLigneRepository;
import org.ms.factureservice.repository.FactureRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/factures")
public class FactureRestController {

    private final FactureRepository factureRepository;
    private final FactureLigneRepository factureLigneRepository;
    private final ClientServiceClient clientServiceClient;
    private final ProduitServiceClient produitServiceClient;

    @GetMapping
    public Iterable<Facture> getAll() {
        return factureRepository.findAll();
    }

    @GetMapping("/{id}")
    public Facture getById(@PathVariable Long id) {
        Facture facture = factureRepository.findById(id).orElseThrow();
        Client client = clientServiceClient.findClientById(facture.getClientID());
        facture.setClient(client);
        return facture;
    }

    @GetMapping("/full-facture/{id}")
    public Facture getFullFacture(@PathVariable Long id) {
        Facture facture = factureRepository.findById(id).orElseThrow();
        Client client = clientServiceClient.findClientById(facture.getClientID());
        facture.setClient(client);
        facture.getFactureLignes().forEach(fl -> {
            Produit produit = produitServiceClient.findProductById(fl.getProduitID());
            fl.setProduit(produit);
        });
        return facture;
    }

    @GetMapping("/{id}/lignes")
    public Collection<FactureLigne> getLignes(@PathVariable Long id) {

        Collection<FactureLigne> lignes = factureLigneRepository.findByFactureId(id);

        lignes.forEach(l -> {
            Produit p = produitServiceClient.findProductById(l.getProduitID());
            l.setProduit(p);
        });

        return lignes;
    }
}