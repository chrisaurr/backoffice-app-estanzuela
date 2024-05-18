package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.AreaTrabajo;
import com.umg.backoffice.modelo.entity.Role;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.service.area_de_trabajo.service.ServiceForAreaTrabajo;
import com.umg.backoffice.service.role.service.ServiceForRole;
import com.umg.backoffice.service.usuario.service.ServiceForUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class WebPageController {
    @Autowired
    ServiceForUsuario serviceForUsuario;

    @Autowired
    private ServiceForRole serviceForRole;

    @Autowired
    private ServiceForAreaTrabajo serviceForAreaTrabajo;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        Set<AreaTrabajo> listadoAreaTrabajo = serviceForAreaTrabajo.getAllAreaDeTrabajo();
        modelAndView.addObject("listadoAreaTrabajo", listadoAreaTrabajo);
        Set<Role> listadoRoles = serviceForRole.getAllRoles();
        modelAndView.addObject("listadoRoles", listadoRoles);

        return modelAndView;
    }

    @PostMapping("/signup")
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
        serviceForUsuario.save(usuario);
        return "redirect:/login";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

}
