package org.ms.scolariteservice;

import org.ms.scolariteservice.entities.Scolarite;
import org.ms.scolariteservice.repository.ScolariteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ScolariteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScolariteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ScolariteRepository scolariteRepository) {
        return args -> {
            scolariteRepository.save(new Scolarite(null, "AZZA", "BOUAZIZI", "L3", null, null, null, null));
            scolariteRepository.save(new Scolarite(null, "NOUR", "BOUAZIZI", "M1", null, null, null, null));
            scolariteRepository.save(new Scolarite(null, "GAYTH", "BOUAZIZI", "L3", null, null, null, null));

            scolariteRepository.findAll().forEach(s -> {
                System.out.println(s.toString());
            });
        };
    }
}
