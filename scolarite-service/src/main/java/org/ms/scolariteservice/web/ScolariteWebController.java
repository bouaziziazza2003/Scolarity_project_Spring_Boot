package org.ms.scolariteservice.web;

import org.ms.scolariteservice.entities.Scolarite;
import org.ms.scolariteservice.feign.EnseignantServiceClient;
import org.ms.scolariteservice.feign.EtudiantServiceClient;
import org.ms.scolariteservice.model.Enseignant;
import org.ms.scolariteservice.model.Etudiant;
import org.ms.scolariteservice.repository.ScolariteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import feign.FeignException;
import java.util.*;

@Controller
@RequestMapping("/web/scolarites")
@RefreshScope
public class ScolariteWebController {

    private final ScolariteRepository scolariteRepository;
    private final EtudiantServiceClient etudiantServiceClient;
    private final EnseignantServiceClient enseignantServiceClient;

    @Value("${adresse:Campus Universitaire}")
    private String addresse;

    @Value("${email:scolarite@universite.tn}")
    private String email;

    public ScolariteWebController(ScolariteRepository scolariteRepository,
                                  EtudiantServiceClient etudiantServiceClient,
                                  EnseignantServiceClient enseignantServiceClient) {
        this.scolariteRepository = scolariteRepository;
        this.etudiantServiceClient = etudiantServiceClient;
        this.enseignantServiceClient = enseignantServiceClient;
    }

    @GetMapping
    public String listScolarites(Model model) {
        List<Scolarite> scolarites = scolariteRepository.findAll();

        List<Etudiant> etudiants = new ArrayList<>();
        List<Enseignant> enseignants = new ArrayList<>();
        try { etudiants = etudiantServiceClient.getAllEtudiants(); }
        catch (FeignException ignored) {}
        try { enseignants = enseignantServiceClient.getAllEnseignants(); }
        catch (FeignException ignored) {}

        for (Scolarite s : scolarites) {
            s.setAddresse(addresse);
            s.setEmail(email);
        }

        model.addAttribute("scolarites", scolarites);
        model.addAttribute("addresse", addresse);
        model.addAttribute("email", email);
        return "scolarites";
    }

    @GetMapping("/{id}")
    public String editScolarite(@PathVariable Long id, Model model) {
        Scolarite scolarite = scolariteRepository.findById(id).orElseThrow();
        scolarite.setAddresse(addresse);
        scolarite.setEmail(email);

        model.addAttribute("scolarite", scolarite);
        return "scolarite-form";
    }

    @PostMapping("/{id}")
    public String updateScolarite(@PathVariable Long id,
                                  @RequestParam String nom,
                                  @RequestParam String prenom,
                                  @RequestParam String niveau) {
        Scolarite scolarite = scolariteRepository.findById(id).orElseThrow();
        scolarite.setNom(nom);
        scolarite.setPrenom(prenom);
        scolarite.setNiveau(niveau);
        scolariteRepository.save(scolarite);
        return "redirect:/web/scolarites";
    }
}
