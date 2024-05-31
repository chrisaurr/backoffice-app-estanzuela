package com.umg.backoffice.service.Notificacion.service;

import com.umg.backoffice.modelo.entity.Notificacion;
import com.umg.backoffice.repository.NotificacionRepository;
import com.umg.backoffice.service.Notificacion.interfaces.InterfaceForNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class NotificacionService implements InterfaceForNotificacion {

    @Autowired
    NotificacionRepository notificacionRepository;

    @Transactional
    @Override
    public Notificacion saveNewNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notificacion> getNotificacionesByIncidente(Long incidenteId, Integer estado) {
        return notificacionRepository.findByIdIncidente_IdAndEstado(incidenteId, estado);
    }

    @Transactional(readOnly = true)
    @Override
    public Notificacion getNotificacionById(Long id, Integer estado) {
        return notificacionRepository.findByIdAndEstadoNot(id, estado);
    }

    @Transactional
    @Override
    public Boolean deleteNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);
        if(notificacion == null)return true;
        notificacion.setEstado(0);
        String randomString = UUID.randomUUID().toString();
        randomString = randomString.substring(0,6);
        notificacion.setDescripcion(notificacion.getDescripcion()+randomString);
        Notificacion confirmarNotifcacion = notificacionRepository.save(notificacion);
        return confirmarNotifcacion != null;
    }

    @Override
    public List<Notificacion> getNotificacionesByEstado(Integer estado) {
        return notificacionRepository.findByEstadoNotOrderByIdDesc(estado);
    }

    @Override
    public List<Notificacion> getAllNotificacionesByIncidente(Long id) {
        return notificacionRepository.findByIdIncidente_Id(id);
    }
}
