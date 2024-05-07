package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente,Long> {
}
