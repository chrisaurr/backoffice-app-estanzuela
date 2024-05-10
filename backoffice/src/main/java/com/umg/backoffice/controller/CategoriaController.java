package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.service.categoria_incidente.service.CategoriaIncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    CategoriaIncidenteService categoriaIncidenteService;

    @GetMapping("/all")
    public ModelAndView getAllCategorias(){
        ModelAndView modelAndView = new ModelAndView();
        List<CategoriaIncidente> categorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        modelAndView.addObject("categorias",categorias);
        //falta crear la vista
        return modelAndView;
    }
}
