package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long>  {

    List<Notificacion> findByDescripcionContainingIgnoreCaseAndEstadoNot(String descripcion, Integer estado);

    List<Notificacion> findByEstadoNotOrderByIdDesc(Integer estado);

    Notificacion findByIdAndEstadoNot(Long id, Integer estado);

    List<Notificacion> findByIdIncidente_IdAndEstado(Long incidenteId, Integer estado);

    List<Notificacion> findByIdIncidente_Id(Long incidenteId);

}
