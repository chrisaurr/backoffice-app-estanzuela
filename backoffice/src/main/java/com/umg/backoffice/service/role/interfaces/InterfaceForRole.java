package com.umg.backoffice.service.role.interfaces;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Role;

import java.util.Set;

public interface InterfaceForRole {

    Role save(Role role);
    Set<Role> getAllRoles();
    Boolean delete(Long id);
    Role getRoleById(Long id, Integer estado);
}
