package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.AreaTrabajo;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Role;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.service.area_de_trabajo.service.ServiceForAreaTrabajo;
import com.umg.backoffice.service.role.service.ServiceForRole;
import com.umg.backoffice.service.usuario.service.ServiceForUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private ServiceForUsuario serviceForUsuario;

    @Autowired
    private ServiceForRole serviceForRole;

    @Autowired
    private ServiceForAreaTrabajo serviceForAreaTrabajo;

    @GetMapping("/all")
    public ModelAndView findAll(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usuario/listar");
        Set<Usuario> usuarios = serviceForUsuario.findAll();
        modelAndView.addObject("usuarios", usuarios);
        return modelAndView;
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usuario/crear");
        Set<AreaTrabajo> listadoAreaTrabajo = serviceForAreaTrabajo.getAllAreaDeTrabajo();
        modelAndView.addObject("listadoAreaTrabajo", listadoAreaTrabajo);
        Set<Role> listadoRoles = serviceForRole.getAllRoles();
        modelAndView.addObject("listadoRoles", listadoRoles);
        modelAndView.addObject("isEditar", false);
        return modelAndView;
    }

    @PostMapping("/crear")
    public String crear(
            @RequestParam("nombre")String nombre,
            @RequestParam("apellidos")String apellidos,
            @RequestParam("direccion")String direccion,
            @RequestParam("telefono")String telefono,
            @RequestParam("email")String email,
            @RequestParam("username")String username,
            @RequestParam("areaTrabajo")Long idAreaTrabajo,
            @RequestParam("idRol")Long idRol,
            @RequestParam("password")String password
    ){
        Usuario usuario = new Usuario();
        AreaTrabajo areaTrabajo = new AreaTrabajo();
        areaTrabajo.setId(idAreaTrabajo);

        Role role = new Role();
        role.setId(idRol);

        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setIdAreaTrabajo(areaTrabajo);
        usuario.setIdRol(role);
        Usuario user = serviceForUsuario.save(usuario);

        if(user != null){return "redirect:/usuario/all";}
        else{return "redirect:/usuario/all?usuarioexiste";}
    }

    @PostMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usuario/crear");
        Usuario usuario = serviceForUsuario.findById(id);
        modelAndView.addObject("usuario", usuario);
        Set<AreaTrabajo> listadoAreaTrabajo = serviceForAreaTrabajo.getAllAreaDeTrabajo();
        modelAndView.addObject("listadoAreaTrabajo", listadoAreaTrabajo);
        Set<Role> listadoRoles = serviceForRole.getAllRoles();
        modelAndView.addObject("listadoRoles", listadoRoles);
        modelAndView.addObject("isEditar", true);
        return modelAndView;
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @RequestParam("nombre")String nombre,
            @RequestParam("apellidos")String apellidos,
            @RequestParam("direccion")String direccion,
            @RequestParam("telefono")String telefono,
            @RequestParam("email")String email,
            @RequestParam("username")String username,
            @RequestParam("areaTrabajo")Long idAreaTrabajo,
            @RequestParam("idRol")Long idRol,
            @RequestParam("password")String password,
            @PathVariable("id")Long id
    ){
        Usuario usuario = serviceForUsuario.findById(id);

        AreaTrabajo areaTrabajo = new AreaTrabajo();
        areaTrabajo.setId(idAreaTrabajo);

        Role role = new Role();
        role.setId(idRol);

        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setIdAreaTrabajo(areaTrabajo);
        usuario.setIdRol(role);
        serviceForUsuario.save(usuario);
        return "redirect:/usuario/all";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id")Long id){
        Usuario usuario = serviceForUsuario.findById(id);
        usuario.setEstado(Constants.ESTADO_ELIMINADO);
        serviceForUsuario.save(usuario);
        return "redirect:/usuario/all";
    }

}
