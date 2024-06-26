package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.service.categoria_incidente.service.CategoriaIncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    CategoriaIncidenteService categoriaIncidenteService;

    @GetMapping("/all")
    public ModelAndView getAllCategorias() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/categoria/listar");
        List<CategoriaIncidente> categorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        modelAndView.addObject("categorias", categorias);
        return modelAndView;
    }

    @PostMapping("/add")
    public String addCategoria(@ModelAttribute CategoriaIncidente categoriaIncidente, Model model) {
        categoriaIncidente.setEstado(Constants.ESTADO_ACTIVO);
        categoriaIncidenteService.saveNewCategoriaIncidente(categoriaIncidente);
        return "redirect:/categoria/all";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public CategoriaIncidente mostrarModalEditar(@PathVariable Long id) {
        return categoriaIncidenteService.getCategoriaIncidenteById(id, Constants.ESTADO_ELIMINADO);
    }

    @PostMapping("/eliminar/{id}")
    public ModelAndView eliminarCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/categoria/all");
        categoriaIncidenteService.deleteCategoriaIncidenteById(id);
        return mv;
    }

}
