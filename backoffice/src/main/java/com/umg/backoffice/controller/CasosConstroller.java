package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.service.incidente.service.IncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/casos")
public class CasosConstroller {

    @Autowired
    private IncidenteService incidenteService;

    @GetMapping("/all")
    public ModelAndView allCases(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/listar");
        List<Incidente> listadoIncidentes = incidenteService.getAllIncidentes(Constants.ESTADO_ELIMINADO);
        mv.addObject("listadoIncidentes", listadoIncidentes);
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalleCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/detalle");
        Incidente incidente = incidenteService.getIncidenteById(id, Constants.ESTADO_ELIMINADO);
        mv.addObject("incidente", incidente);
        return mv;
    }
}
