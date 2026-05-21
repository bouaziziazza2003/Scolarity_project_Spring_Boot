package org.ms.scolariteservice.feign;

import org.ms.scolariteservice.model.Etudiant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ETUDIANT-SERVICE")
public interface EtudiantServiceClient {

    @GetMapping("/etudiants")
    List<Etudiant> getAllEtudiants();

    @GetMapping("/etudiants/{matricule}")
    Etudiant getEtudiantById(@PathVariable("matricule") String matricule);
}
