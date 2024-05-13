package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.AreaTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AreaTrabajoRepository extends JpaRepository<AreaTrabajo,Long> {

    Set<AreaTrabajo> findByEstadoNotOrderByIdDesc(Integer estado);


}
