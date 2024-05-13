package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.AreaTrabajo;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.service.area_de_trabajo.service.ServiceForAreaTrabajo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/area-trabajo")
public class AreaTrabajoController {

    @Autowired
    private ServiceForAreaTrabajo serviceForAreaTrabajo;

    @GetMapping("/all")
    public ModelAndView getAllAreaTrabajo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/area_de_trabajo/listar");
        Set<AreaTrabajo> areaTrabajos = serviceForAreaTrabajo.getAllAreaDeTrabajo();
        modelAndView.addObject("areaTrabajos", areaTrabajos);
        return modelAndView;
    }

    @PostMapping("/add")
    public String saveNewAreaTrabajo(@ModelAttribute AreaTrabajo areaTrabajo){
        areaTrabajo.setEstado(Constants.ESTADO_ACTIVO);
        serviceForAreaTrabajo.save(areaTrabajo);
        return "redirect:/area-trabajo/all";
    }

    @PostMapping("/eliminar/{id}")
    public String deleteAreaTrabajo(@PathVariable("id") Long id){
        serviceForAreaTrabajo.deleteAreaDeTrabajo(id);
        return "redirect:/area-trabajo/all";
    }
}
