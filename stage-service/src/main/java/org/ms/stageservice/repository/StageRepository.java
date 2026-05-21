package org.ms.stageservice.repository;

import org.ms.stageservice.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, String> {
}
