package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.service.incidente.service.IncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Incidente> listadoIncidentes = incidenteService.getAllIncidentes(0);
        mv.addObject("listadoIncidentes", listadoIncidentes);
        return mv;
    }
}
