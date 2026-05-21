package org.ms.scolariteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Etudiant {
    private String matricule;
    private String nom;
    private String prenom;
    private String niveau;
}
