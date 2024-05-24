package com.umg.backoffice.service.Notificacion.interfaces;

import com.umg.backoffice.modelo.entity.Notificacion;

import java.util.List;

public interface InterfaceForNotificacion {

    Notificacion saveNewNotificacion(Notificacion notificacion);

    List<Notificacion> getNotificacionesByIncidente(Long id, Integer estado);

    Notificacion getNotificacionById(Long id, Integer estado);

    Boolean deleteNotificacion(Long id);

    List<Notificacion> getNotificacionesByEstado(Integer estado);

    List<Notificacion> getAllNotificacionesByIncidente(Long id);

}
