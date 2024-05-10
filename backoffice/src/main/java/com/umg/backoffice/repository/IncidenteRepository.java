package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Ciudadano;
import com.umg.backoffice.modelo.entity.Incidente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente,Long> {

    List<Incidente> findByDescripcionContainingIgnoreCaseAndEstadoNot(String descripcion, Integer estado);

    //List<Incidente> findByEstadoNotOrderByIdDesc(Integer estado, Pageable pageable);
    Page<Incidente> findByEstadoNotOrderByIdDesc(Integer estado, Pageable pageable);

    List<Incidente> findByDireccionContainingIgnoreCaseAndEstadoNot(String direccion, Integer estado);

    List<Incidente> findByReferenciaDeDireccionContainingIgnoreCaseAndEstadoNot(String referencia, Integer estado);

    List<Incidente> findByFechaBetweenAndEstadoNot(Instant fechaInicial, Instant fechaFinal, Integer estado);

    List<Incidente> findByEstado(Integer estado);

    List<Incidente> findByIdCategoriaAndEstadoNot(CategoriaIncidente categoriaIncidente, Integer estado);

    List<Incidente> findByIdCiudadanoAndEstadoNot(Ciudadano ciudadano, Integer estado);

    Incidente findByIdAndEstadoNot(Long id, Integer estado);

    @Query("SELECT p FROM Incidente p WHERE (:descripcion IS NULL OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))) AND " +
            "(:direccion IS NULL OR LOWER(p.direccion) LIKE LOWER(CONCAT('%', :direccion, '%'))) AND " +
            "(:estado IS NULL OR p.estado = :estado) AND " +
            "(:categoria IS NULL OR p.idCategoria = :categoria) AND " +
            "(:fechaInicial IS NULL OR p.fecha >= :fechaInicial) AND " +
            "(:fechaFinal IS NULL OR p.fecha <= :fechaFinal) " +
            "ORDER BY p.id DESC")
    Page<Incidente> busquedaCompuesta(
            @Param("descripcion") String descripcion,
            @Param("direccion") String direccion,
            @Param("estado") Integer estado,
            @Param("categoria") CategoriaIncidente categoriaIncidente,
            @Param("fechaInicial") Instant fechaInicial,
            @Param("fechaFinal") Instant fechaFinal,
            Pageable pageable
    );
}
