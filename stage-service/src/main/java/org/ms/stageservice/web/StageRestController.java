package org.ms.stageservice.web;

import org.ms.stageservice.entities.Stage;
import org.ms.stageservice.feign.EnseignantServiceClient;
import org.ms.stageservice.feign.EtudiantServiceClient;
import org.ms.stageservice.model.Enseignant;
import org.ms.stageservice.model.Etudiant;
import org.ms.stageservice.repository.StageRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/stages")
@RefreshScope
public class StageRestController {

    private final StageRepository stageRepository;
    private final EtudiantServiceClient etudiantServiceClient;
    private final EnseignantServiceClient enseignantServiceClient;

    @Value("${global.addresse}")
    private String addresse;

    public StageRestController(StageRepository stageRepository,
                               EtudiantServiceClient etudiantServiceClient,
                               EnseignantServiceClient enseignantServiceClient) {
        this.stageRepository = stageRepository;
        this.etudiantServiceClient = etudiantServiceClient;
        this.enseignantServiceClient = enseignantServiceClient;
    }

    @GetMapping
    public List<Stage> getAll() {
        List<Stage> stages = stageRepository.findAll();
        List<Etudiant> etudiants = new ArrayList<>();
        List<Enseignant> enseignants = new ArrayList<>();
        try {
            etudiants = etudiantServiceClient.getAllEtudiants();
        } catch (FeignException ignored) {}
        try {
            enseignants = enseignantServiceClient.getAllEnseignants();
        } catch (FeignException ignored) {}

        Map<String, Etudiant> etudiantMap = new HashMap<>();
        Map<String, Enseignant> enseignantMap = new HashMap<>();
        for (Etudiant e : etudiants) etudiantMap.put(e.getMatricule(), e);
        for (Enseignant e : enseignants) enseignantMap.put(e.getCode(), e);

        for (Stage s : stages) {
            s.setEtudiant(etudiantMap.get(s.getEtudiantMatricule()));
            s.setEnseignant(enseignantMap.get(s.getEnseignantCode()));
            s.setAddresse(addresse);
        }
        return stages;
    }

    @GetMapping("/{code}")
    public Stage getById(@PathVariable String code) {
        Stage stage = stageRepository.findById(code).orElseThrow();
        if (stage.getEtudiantMatricule() != null) {
            try {
                stage.setEtudiant(etudiantServiceClient.getEtudiantById(stage.getEtudiantMatricule()));
            } catch (FeignException ignored) {}
        }
        if (stage.getEnseignantCode() != null) {
            try {
                stage.setEnseignant(enseignantServiceClient.getEnseignantById(stage.getEnseignantCode()));
            } catch (FeignException ignored) {}
        }
        stage.setAddresse(addresse);
        return stage;
    }

    @PutMapping("/{code}")
    public Stage update(@PathVariable String code, @RequestBody Stage updated) {
        Stage stage = stageRepository.findById(code).orElseThrow();

        stage.setSujet(updated.getSujet());
        stage.setEnseignantCode(updated.getEnseignantCode());

        Stage saved = stageRepository.save(stage);

        if (saved.getEtudiantMatricule() != null) {
            try {
                saved.setEtudiant(etudiantServiceClient.getEtudiantById(saved.getEtudiantMatricule()));
            } catch (FeignException ignored) {}
        }
        if (saved.getEnseignantCode() != null) {
            try {
                saved.setEnseignant(enseignantServiceClient.getEnseignantById(saved.getEnseignantCode()));
            } catch (FeignException ignored) {}
        }
        saved.setAddresse(addresse);

        return saved;
    }
}
