package org.ms.stageservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ms.stageservice.model.Enseignant;
import org.ms.stageservice.model.Etudiant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Stage {
    @Id
    private String code;
    private String sujet;

    private String etudiantMatricule;

    private String enseignantCode;

    @Transient
    private String addresse;

    @Transient
    private Etudiant etudiant;

    @Transient
    private Enseignant enseignant;
}
