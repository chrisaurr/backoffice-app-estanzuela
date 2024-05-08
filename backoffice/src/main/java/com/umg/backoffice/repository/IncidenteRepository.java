package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Ciudadano;
import com.umg.backoffice.modelo.entity.Incidente;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente,Long> {

    List<Incidente> findByDescripcionContainingIgnoreCase(String descripcion);

    List<Incidente> findAllByOrderByIdDesc();

    List<Incidente> findByDireccionContainingIgnoreCase(String direccion);

    List<Incidente> findByReferenciaDeDireccionContainingIgnoreCase(String referencia);

    List<Incidente> findByFechaBetween(Instant fechaInicial, Instant fechaFinal);

    List<Incidente> findByEstado(Integer estado);

    List<Incidente> findByIdCategoria(CategoriaIncidente categoriaIncidente);

    List<Incidente> findByIdCiudadano(Ciudadano ciudadano);


}
