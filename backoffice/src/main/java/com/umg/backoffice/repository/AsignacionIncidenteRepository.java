package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.AsignacionIncidente;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.modelo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AsignacionIncidenteRepository extends JpaRepository<AsignacionIncidente,Long> {

    Set<AsignacionIncidente> findByIdUsuarioAndEstadoOrderByIdDesc(Usuario idUsuario, Integer estado);

    Set<AsignacionIncidente> findByIdIncidenteOrderByIdDesc(Incidente incidente);

    @Query(value = "UPDATE asignacion_incidente SET estado = 0 WHERE id_incidente = :idIncidente", nativeQuery = true)
    Object updateEstadoAsignacionesIncidentesToZero(@Param("idIncidente") Long idIncidente);

}
