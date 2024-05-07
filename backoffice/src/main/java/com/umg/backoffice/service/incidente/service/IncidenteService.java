package com.umg.backoffice.service.incidente.service;

import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.repository.IncidenteRepository;
import com.umg.backoffice.service.incidente.interfaces.InterfaceForIncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncidenteService implements InterfaceForIncidenteService {

    @Autowired
    private IncidenteRepository incidenteRepository;

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getAllIncidentes() {
        return incidenteRepository.findAll();
    }
}
