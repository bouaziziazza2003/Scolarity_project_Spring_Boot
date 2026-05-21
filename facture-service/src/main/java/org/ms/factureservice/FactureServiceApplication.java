package org.ms.factureservice;

import org.ms.factureservice.entities.Facture;
import org.ms.factureservice.entities.FactureLigne;
import org.ms.factureservice.feign.ClientServiceClient;
import org.ms.factureservice.feign.ProduitServiceClient;
import org.ms.factureservice.model.Client;
import org.ms.factureservice.model.Produit;
import org.ms.factureservice.repository.FactureLigneRepository;
import org.ms.factureservice.repository.FactureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class FactureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FactureServiceApplication.class, args);
    }

    // ⚠️ SAFE VERSION: only runs if other services are available
    @Bean
    CommandLineRunner start(
            FactureRepository factureRepository,
            FactureLigneRepository factureLigneRepository,
            ClientServiceClient clientServiceClient,
            ProduitServiceClient produitServiceClient
    ) {
        return args -> {

            try {
                // Get client remotely
                Client client = clientServiceClient.findClientById(1L);

                if (client == null) {
                    System.out.println("⚠️ Client not found, skipping facture creation");
                    return;
                }

                // Create facture
                Facture facture = new Facture();
                 facture.setClient(client);
                facture.setClientID(client.getId());

                facture = factureRepository.save(facture);

                // Get products remotely
                List<Produit> produits = produitServiceClient.getAllProduits();

                if (produits == null || produits.isEmpty()) {
                    System.out.println("⚠️ No products found");
                    return;
                }

                for (Produit p : produits) {
                    FactureLigne ligne = new FactureLigne();
                    ligne.setProduitID(p.getId());
                    ligne.setPrice(p.getPrice());
                    ligne.setQuantity(1 + new Random().nextInt(10));
                    ligne.setFacture(facture);

                    factureLigneRepository.save(ligne);
                }

                System.out.println("✅ Facture created successfully");

            } catch (Exception e) {
                // 🚨 IMPORTANT: prevents app from crashing
                System.out.println("❌ Startup initialization failed: " + e.getMessage());
            }
        };
    }
}