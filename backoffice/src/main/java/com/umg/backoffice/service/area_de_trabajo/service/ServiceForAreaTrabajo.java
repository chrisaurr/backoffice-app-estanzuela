package com.umg.backoffice.service.area_de_trabajo.service;

import com.umg.backoffice.modelo.entity.AreaTrabajo;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.repository.AreaTrabajoRepository;
import com.umg.backoffice.service.area_de_trabajo.interfaces.InterfaceForAreaDeTrabajo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class ServiceForAreaTrabajo implements InterfaceForAreaDeTrabajo {

    @Autowired
    AreaTrabajoRepository areaTrabajoRepository;

    @Transactional
    @Override
    public AreaTrabajo save(AreaTrabajo areaTrabajo) {
        return areaTrabajoRepository.save(areaTrabajo);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<AreaTrabajo> getAllAreaDeTrabajo() {
        return areaTrabajoRepository.findByEstadoNotOrderByIdDesc(Constants.ESTADO_ELIMINADO);
    }

    @Override
    public Boolean deleteAreaDeTrabajo(Long id) {
        AreaTrabajo areaTrabajo = areaTrabajoRepository.findById(id).orElse(null);
        if(areaTrabajo != null){
            String randomString = UUID.randomUUID().toString();
            randomString = randomString.substring(0,6);
            areaTrabajo.setNombre(areaTrabajo.getNombre()+randomString);
            areaTrabajo.setEstado(Constants.ESTADO_ELIMINADO);
            areaTrabajoRepository.save(areaTrabajo);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public AreaTrabajo getAreaTrabajoById(Long id, Integer estado) {
        return areaTrabajoRepository.findByIdAndEstadoNot(id, estado);
    }

}
