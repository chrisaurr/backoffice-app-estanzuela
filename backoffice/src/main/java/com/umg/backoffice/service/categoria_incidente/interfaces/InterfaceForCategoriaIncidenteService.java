package com.umg.backoffice.service.categoria_incidente.interfaces;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;

import java.util.List;

public interface InterfaceForCategoriaIncidenteService {

    CategoriaIncidente saveNewCategoriaIncidente(CategoriaIncidente categoriaIncidente);

    List<CategoriaIncidente> getAllCategoriaIncidente(Integer estado);

    CategoriaIncidente getCategoriaIncidenteById(Long id, Integer estado);

    Boolean deleteCategoriaIncidenteById(Long id);

    List<CategoriaIncidente> getByNombreLike(String nombre, Integer estado);
}
