package org.ms.etudiantservice.web;

import org.ms.etudiantservice.entities.Etudiant;
import org.ms.etudiantservice.repository.EtudiantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantRestController {

    private final EtudiantRepository etudiantRepository;

    public EtudiantRestController(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @GetMapping
    public List<Etudiant> getAll() {
        return etudiantRepository.findAll();
    }

    @GetMapping("/{matricule}")
    public Etudiant getById(@PathVariable String matricule) {
        return etudiantRepository.findById(matricule).orElseThrow();
    }

    @PostMapping
    public Etudiant save(@RequestBody Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @PutMapping("/{matricule}")
    public Etudiant update(@PathVariable String matricule, @RequestBody Etudiant etudiant) {
        etudiant.setMatricule(matricule);
        return etudiantRepository.save(etudiant);
    }

    @DeleteMapping("/{matricule}")
    public void delete(@PathVariable String matricule) {
        etudiantRepository.deleteById(matricule);
    }
}
