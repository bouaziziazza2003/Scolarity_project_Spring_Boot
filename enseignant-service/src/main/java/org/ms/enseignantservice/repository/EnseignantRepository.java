package org.ms.enseignantservice.repository;

import org.ms.enseignantservice.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, String> {
}
