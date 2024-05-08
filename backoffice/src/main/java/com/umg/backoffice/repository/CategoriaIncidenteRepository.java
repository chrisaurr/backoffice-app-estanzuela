package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaIncidenteRepository extends JpaRepository<CategoriaIncidente,Long> {

    List<CategoriaIncidente> findByNombreContainingIgnoreCaseAndEstadoNot(String nombre, Integer estado);

    List<CategoriaIncidente> findByEstadoNotOrderByIdDesc(Integer estado);

    CategoriaIncidente findByIdAndEstadoNot(Long id, Integer estado);
}
