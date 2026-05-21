package org.ms.scolariteservice.web;

import org.ms.scolariteservice.entities.Scolarite;
import org.ms.scolariteservice.repository.ScolariteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scolarites")
public class ScolariteRestController {

    private final ScolariteRepository scolariteRepository;

    public ScolariteRestController(ScolariteRepository scolariteRepository) {
        this.scolariteRepository = scolariteRepository;
    }

    @GetMapping
    public List<Scolarite> getAll() {
        return scolariteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Scolarite getById(@PathVariable Long id) {
        return scolariteRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Scolarite save(@RequestBody Scolarite scolarite) {
        return scolariteRepository.save(scolarite);
    }

    @PutMapping("/{id}")
    public Scolarite update(@PathVariable Long id, @RequestBody Scolarite scolarite) {
        scolarite.setId(id);
        return scolariteRepository.save(scolarite);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scolariteRepository.deleteById(id);
    }
}
