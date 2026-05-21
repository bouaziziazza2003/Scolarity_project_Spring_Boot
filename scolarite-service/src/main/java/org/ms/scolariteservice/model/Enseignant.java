package org.ms.scolariteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Enseignant {
    private String code;
    private String nom;
    private String prenom;
    private String specialite;
}
