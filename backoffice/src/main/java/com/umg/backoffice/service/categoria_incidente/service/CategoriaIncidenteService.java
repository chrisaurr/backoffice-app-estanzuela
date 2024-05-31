package com.umg.backoffice.service.categoria_incidente.service;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.repository.CategoriaIncidenteRepository;
import com.umg.backoffice.service.categoria_incidente.interfaces.InterfaceForCategoriaIncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaIncidenteService implements InterfaceForCategoriaIncidenteService {

    @Autowired
    CategoriaIncidenteRepository categoriaIncidenteRepository;

    @Transactional
    @Override
    public CategoriaIncidente saveNewCategoriaIncidente(CategoriaIncidente categoriaIncidente) {
        return categoriaIncidenteRepository.save(categoriaIncidente);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaIncidente> getAllCategoriaIncidente(Integer estado) {
        return categoriaIncidenteRepository.findByEstadoNotOrderByIdDesc(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoriaIncidente getCategoriaIncidenteById(Long id, Integer estado) {
        return categoriaIncidenteRepository.findByIdAndEstadoNot(id, estado);
    }

    @Transactional
    @Override
    public Boolean deleteCategoriaIncidenteById(Long id) {
        CategoriaIncidente categoriaIncidente = categoriaIncidenteRepository.findById(id).orElse(null);
        if(categoriaIncidente == null)return true;
        categoriaIncidente.setEstado(0);
        String randomString = UUID.randomUUID().toString();
        randomString = randomString.substring(0,6);
        categoriaIncidente.setNombre(categoriaIncidente.getNombre()+randomString);
        CategoriaIncidente confirmarCategoriaIncidente = categoriaIncidenteRepository.save(categoriaIncidente);
        return confirmarCategoriaIncidente != null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaIncidente> getByNombreLike(String nombre, Integer estado) {
        return categoriaIncidenteRepository.findByNombreContainingIgnoreCaseAndEstadoNot(nombre, estado);
    }
}
