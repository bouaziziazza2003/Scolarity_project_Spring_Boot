package org.ms.scolariteservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ms.scolariteservice.model.Enseignant;
import org.ms.scolariteservice.model.Etudiant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Scolarite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String niveau;

    @Transient
    private String addresse;

    @Transient
    private String email;

    @Transient
    private Etudiant etudiant;

    @Transient
    private Enseignant enseignant;
}
