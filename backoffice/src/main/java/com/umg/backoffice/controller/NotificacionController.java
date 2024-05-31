package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.modelo.entity.Notificacion;
import com.umg.backoffice.service.Notificacion.service.NotificacionService;
import com.umg.backoffice.service.incidente.service.IncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/notificacion")
public class NotificacionController {

    @Autowired
    NotificacionService notificacionService;

    @Autowired
    private IncidenteService incidenteService;

    @GetMapping("/all")
    public ModelAndView getAllNotificaciones() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/notificacion/listar");
        List<Notificacion> notificaciones = notificacionService.getNotificacionesByEstado(Constants.ESTADO_ELIMINADO);
        modelAndView.addObject("notificaciones", notificaciones);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addNotificacion(@ModelAttribute Notificacion notificacion, @RequestParam Long incidenteId) {
        ModelAndView mv = new ModelAndView();
        try {
            Incidente incidente = incidenteService.getIncidenteById(incidenteId, Constants.ESTADO_ELIMINADO);
            notificacion.setIdIncidente(incidente);
            notificacion.setEstado(Constants.ESTADO_ACTIVO);
            notificacion.setFecha(Instant.now());
            notificacion.setIdCiudadano(0L);
            notificacion.setIsIncident(0L);
            notificacionService.saveNewNotificacion(notificacion);

            mv.setViewName("redirect:/casos/detalle/" + incidenteId);
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @PostMapping("/usuarios/add")
    public ModelAndView addNotificacionUsuarios(@ModelAttribute Notificacion notificacion, @RequestParam Long incidenteId) {
        ModelAndView mv = new ModelAndView();
        try {
            Incidente incidente = incidenteService.getIncidenteById(incidenteId, Constants.ESTADO_ELIMINADO);
            notificacion.setIdIncidente(incidente);
            notificacion.setEstado(Constants.ESTADO_ACTIVO);
            notificacion.setFecha(Instant.now());
            notificacion.setIdCiudadano(0L);
            notificacion.setIsIncident(0L);
            notificacionService.saveNewNotificacion(notificacion);

            mv.setViewName("redirect:/casos/usuarios/detalle/" + incidenteId);
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Notificacion mostrarModalEditar(@PathVariable Long id) {
        return notificacionService.getNotificacionById(id, Constants.ESTADO_ELIMINADO);
    }

    @PostMapping("/editar")
    public ModelAndView editarDescripcionNotificacion(@RequestParam Long id, @RequestParam String descripcion) {
        ModelAndView mv = new ModelAndView();
        try {
            Notificacion notificacion = notificacionService.getNotificacionById(id, Constants.ESTADO_ELIMINADO);
            notificacion.setDescripcion(descripcion);
            notificacionService.saveNewNotificacion(notificacion);
            mv.setViewName("redirect:/casos/detalle/" + notificacion.getIdIncidente().getId());
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @PostMapping("/usuarios/editar")
    public ModelAndView editarDescripcionNotificacionUsuarios(@RequestParam Long id, @RequestParam String descripcion) {
        ModelAndView mv = new ModelAndView();
        try {
            Notificacion notificacion = notificacionService.getNotificacionById(id, Constants.ESTADO_ELIMINADO);
            notificacion.setDescripcion(descripcion);
            notificacionService.saveNewNotificacion(notificacion);
            mv.setViewName("redirect:/casos/usuarios/detalle/" + notificacion.getIdIncidente().getId());
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @PostMapping("/eliminar/{id}")
    public ModelAndView eliminarNotificacion(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        try {
            Notificacion notificacion = notificacionService.getNotificacionById(id, Constants.ESTADO_ELIMINADO);
            notificacion.setEstado(Constants.ESTADO_ELIMINADO);
            notificacionService.saveNewNotificacion(notificacion);
            mv.setViewName("redirect:/casos/detalle/" + notificacion.getIdIncidente().getId());
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @PostMapping("/eliminar/usuarios/{id}")
    public ModelAndView eliminarNotificacionUsuarios(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        try {
            Notificacion notificacion = notificacionService.getNotificacionById(id, Constants.ESTADO_ELIMINADO);
            notificacion.setEstado(Constants.ESTADO_ELIMINADO);
            notificacionService.saveNewNotificacion(notificacion);
            mv.setViewName("redirect:/casos/usuarios/detalle/" + notificacion.getIdIncidente().getId());
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/");
        }
        return mv;
    }

}
