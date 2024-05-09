package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Ciudadano;
import com.umg.backoffice.modelo.entity.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente,Long> {

    List<Incidente> findByDescripcionContainingIgnoreCaseAndEstadoNot(String descripcion, Integer estado);

    List<Incidente> findByEstadoNotOrderByIdDesc(Integer estado);

    List<Incidente> findByDireccionContainingIgnoreCaseAndEstadoNot(String direccion, Integer estado);

    List<Incidente> findByReferenciaDeDireccionContainingIgnoreCaseAndEstadoNot(String referencia, Integer estado);

    List<Incidente> findByFechaBetweenAndEstadoNot(Instant fechaInicial, Instant fechaFinal, Integer estado);

    List<Incidente> findByEstado(Integer estado);

    List<Incidente> findByIdCategoriaAndEstadoNot(CategoriaIncidente categoriaIncidente, Integer estado);

    List<Incidente> findByIdCiudadanoAndEstadoNot(Ciudadano ciudadano, Integer estado);

    Incidente findByIdAndEstadoNot(Long id, Integer estado);
}
