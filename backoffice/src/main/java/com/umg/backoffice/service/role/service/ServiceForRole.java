package com.umg.backoffice.service.role.service;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Role;
import com.umg.backoffice.repository.RoleRepository;
import com.umg.backoffice.service.role.interfaces.InterfaceForRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class ServiceForRole implements InterfaceForRole {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> getAllRoles() {
        return roleRepository.findByEstadoNotOrderByIdDesc(Constants.ESTADO_ELIMINADO);
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if(role != null){
            String randomString = UUID.randomUUID().toString();
            randomString = randomString.substring(0,6);
            role.setNombreRol(role.getNombreRol()+randomString);
            role.setEstado(Constants.ESTADO_ELIMINADO);
            roleRepository.save(role);
            return true;
        }
        return false;
    }
}
