package org.ms.enseignantservice.web;

import org.ms.enseignantservice.entities.Enseignant;
import org.ms.enseignantservice.repository.EnseignantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enseignants")
public class EnseignantRestController {

    private final EnseignantRepository enseignantRepository;

    public EnseignantRestController(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }

    @GetMapping
    public List<Enseignant> getAll() {
        return enseignantRepository.findAll();
    }

    @GetMapping("/{code}")
    public Enseignant getById(@PathVariable String code) {
        return enseignantRepository.findById(code).orElseThrow();
    }

    @PostMapping
    public Enseignant save(@RequestBody Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    @PutMapping("/{code}")
    public Enseignant update(@PathVariable String code, @RequestBody Enseignant enseignant) {
        enseignant.setCode(code);
        return enseignantRepository.save(enseignant);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) {
        enseignantRepository.deleteById(code);
    }
}
