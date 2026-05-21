package org.ms.enseignantservice;

import org.ms.enseignantservice.entities.Enseignant;
import org.ms.enseignantservice.repository.EnseignantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnseignantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnseignantServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(EnseignantRepository enseignantRepository) {
        return args -> {
            enseignantRepository.save(new Enseignant("ENS001", "SAMI", "HADHRI", "Developp des applications mobiles natives multiplatformes"));
            enseignantRepository.save(new Enseignant("ENS002", "MOHAMED", "ZAYANI", "Micro Service"));
            enseignantRepository.save(new Enseignant("ENS003", "MOHAMED", "MANAA", "Big Data"));
        };
    }
}
