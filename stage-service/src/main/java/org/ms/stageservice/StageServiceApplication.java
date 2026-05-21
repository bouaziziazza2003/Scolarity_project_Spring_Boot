package org.ms.stageservice;

import org.ms.stageservice.entities.Stage;
import org.ms.stageservice.repository.StageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class StageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StageServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(StageRepository stageRepository) {
        return args -> {
            stageRepository.save(new Stage("STG001", "Application Mobile Flutter", "ETU001", null, null, null, null));
            stageRepository.save(new Stage("STG002", "Developement Web Spring Boot", "ETU002", null, null, null, null));
            stageRepository.save(new Stage("STG003", "Data Science avec Python", "ETU003", null, null, null, null));
        };
    }
}
