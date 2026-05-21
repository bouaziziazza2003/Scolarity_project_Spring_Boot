package org.ms.etudiantservice;

import org.ms.etudiantservice.entities.Etudiant;
import org.ms.etudiantservice.repository.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EtudiantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EtudiantServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(EtudiantRepository etudiantRepository) {
        return args -> {
            etudiantRepository.save(new Etudiant("ETU001", "AZZA", "BOUAZIZI", "L3"));
            etudiantRepository.save(new Etudiant("ETU002", "NOUR", "BOUAZIZI", "M1"));
            etudiantRepository.save(new Etudiant("ETU003", "GAYTH", "BOUAZIZI", "L3"));
        };
    }
}
