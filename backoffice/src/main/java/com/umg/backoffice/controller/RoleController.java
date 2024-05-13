package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Role;
import com.umg.backoffice.service.role.service.ServiceForRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private ServiceForRole serviceForRole;

    @GetMapping("/all")
    public ModelAndView allRoles(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/role/listar");
        Set<Role> roles = serviceForRole.getAllRoles();
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/add")
    public String addRole(@ModelAttribute Role role){
        role.setEstado(Constants.ESTADO_ACTIVO);
        serviceForRole.save(role);
        return "redirect:/role/all";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarRole(@PathVariable("id") Long id){
        serviceForRole.delete(id);
        return "redirect:/role/all";
    }
}
