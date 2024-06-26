package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Set<Role> findByEstadoNotOrderByIdDesc(Integer estado);

    Role findByIdAndEstadoNot(Long id, Integer estado);

}
