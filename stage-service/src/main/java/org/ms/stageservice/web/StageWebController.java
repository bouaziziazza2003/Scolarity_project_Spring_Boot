package org.ms.stageservice.web;

import org.ms.stageservice.entities.Stage;
import org.ms.stageservice.feign.EnseignantServiceClient;
import org.ms.stageservice.feign.EtudiantServiceClient;
import org.ms.stageservice.model.Enseignant;
import org.ms.stageservice.model.Etudiant;
import org.ms.stageservice.repository.StageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import feign.FeignException;
import java.util.*;

@Controller
@RequestMapping("/web/stages")
@RefreshScope
public class StageWebController {

    private final StageRepository stageRepository;
    private final EtudiantServiceClient etudiantServiceClient;
    private final EnseignantServiceClient enseignantServiceClient;

    @Value("${global.addresse}")
    private String addresse;

    public StageWebController(StageRepository stageRepository,
                              EtudiantServiceClient etudiantServiceClient,
                              EnseignantServiceClient enseignantServiceClient) {
        this.stageRepository = stageRepository;
        this.etudiantServiceClient = etudiantServiceClient;
        this.enseignantServiceClient = enseignantServiceClient;
    }

    @GetMapping
    public String listStages(Model model) {
        List<Stage> stages = stageRepository.findAll();
        List<Etudiant> etudiants = new ArrayList<>();
        List<Enseignant> enseignants = new ArrayList<>();
        try {
            etudiants = etudiantServiceClient.getAllEtudiants();
        } catch (FeignException e) {
            model.addAttribute("errorEtudiant", "Service ETUDIANT-SERVICE indisponible");
        }
        try {
            enseignants = enseignantServiceClient.getAllEnseignants();
        } catch (FeignException e) {
            model.addAttribute("errorEnseignant", "Service ENSEIGNANT-SERVICE indisponible");
        }

        Map<String, Etudiant> etudiantMap = new HashMap<>();
        Map<String, Enseignant> enseignantMap = new HashMap<>();
        for (Etudiant e : etudiants) etudiantMap.put(e.getMatricule(), e);
        for (Enseignant e : enseignants) enseignantMap.put(e.getCode(), e);

        for (Stage s : stages) {
            s.setEtudiant(etudiantMap.get(s.getEtudiantMatricule()));
            s.setEnseignant(enseignantMap.get(s.getEnseignantCode()));
            s.setAddresse(addresse);
        }

        model.addAttribute("stages", stages);
        return "stages";
    }

    @GetMapping("/{code}")
    public String editStage(@PathVariable String code, Model model) {
        Stage stage = stageRepository.findById(code).orElseThrow();
        if (stage.getEtudiantMatricule() != null) {
            try {
                stage.setEtudiant(etudiantServiceClient.getEtudiantById(stage.getEtudiantMatricule()));
            } catch (FeignException e) {
                model.addAttribute("errorEtudiant", "Service ETUDIANT-SERVICE indisponible");
            }
        }
        if (stage.getEnseignantCode() != null) {
            try {
                stage.setEnseignant(enseignantServiceClient.getEnseignantById(stage.getEnseignantCode()));
            } catch (FeignException e) {
                model.addAttribute("errorEnseignant", "Service ENSEIGNANT-SERVICE indisponible");
            }
        }
        stage.setAddresse(addresse);

        List<Enseignant> enseignants = new ArrayList<>();
        try {
            enseignants = enseignantServiceClient.getAllEnseignants();
        } catch (FeignException e) {
            model.addAttribute("errorEnseignant", "Service ENSEIGNANT-SERVICE indisponible");
        }

        model.addAttribute("stage", stage);
        model.addAttribute("enseignants", enseignants);
        return "stage-form";
    }

    @PostMapping("/{code}")
    public String updateStage(@PathVariable String code,
                              @RequestParam String sujet,
                              @RequestParam String enseignantCode) {
        Stage stage = stageRepository.findById(code).orElseThrow();
        stage.setSujet(sujet);
        stage.setEnseignantCode(enseignantCode);
        stageRepository.save(stage);
        return "redirect:/web/stages";
    }
}
