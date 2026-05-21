package org.ms.scolariteservice;

import org.ms.scolariteservice.entities.Scolarite;
import org.ms.scolariteservice.repository.ScolariteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScolariteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScolariteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ScolariteRepository scolariteRepository) {
        return args -> {
            scolariteRepository.save(new Scolarite(null, "Ali", "Ben Salah", "L3"));
            scolariteRepository.save(new Scolarite(null, "Mariem", "Kacem", "M1"));
            scolariteRepository.save(new Scolarite(null, "Sami", "Trabelsi", "L3"));

            scolariteRepository.findAll().forEach(s -> {
                System.out.println(s.toString());
            });
        };
    }
}
