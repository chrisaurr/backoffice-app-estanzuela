package com.umg.backoffice.service.area_de_trabajo.interfaces;

import com.umg.backoffice.modelo.entity.AreaTrabajo;
import com.umg.backoffice.modelo.entity.CategoriaIncidente;

import java.util.List;
import java.util.Set;

public interface InterfaceForAreaDeTrabajo {

    AreaTrabajo save(AreaTrabajo areaTrabajo);
    Set<AreaTrabajo> getAllAreaDeTrabajo();
    Boolean deleteAreaDeTrabajo(Long id);
}
