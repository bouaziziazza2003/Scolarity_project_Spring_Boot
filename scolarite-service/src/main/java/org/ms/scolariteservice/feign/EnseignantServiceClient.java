package org.ms.scolariteservice.feign;

import org.ms.scolariteservice.model.Enseignant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ENSEIGNANT-SERVICE")
public interface EnseignantServiceClient {

    @GetMapping("/enseignants")
    List<Enseignant> getAllEnseignants();

    @GetMapping("/enseignants/{code}")
    Enseignant getEnseignantById(@PathVariable("code") String code);
}
