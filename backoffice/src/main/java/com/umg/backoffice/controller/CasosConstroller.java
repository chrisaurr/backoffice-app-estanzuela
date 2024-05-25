package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.*;
import com.umg.backoffice.service.Notificacion.service.NotificacionService;
import com.umg.backoffice.service.asignacion_incidente.service.ServiceForAsignacionIncidente;
import com.umg.backoffice.service.categoria_incidente.service.CategoriaIncidenteService;
import com.umg.backoffice.service.incidente.service.IncidenteService;
import com.umg.backoffice.service.usuario.service.ServiceForUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/casos")
public class CasosConstroller {

    @Autowired
    private IncidenteService incidenteService;

    @Autowired
    private CategoriaIncidenteService categoriaIncidenteService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private ServiceForUsuario serviceForUsuario;

    @Autowired
    private ServiceForAsignacionIncidente serviceForAsignacionIncidente;

    private Integer resultado = 0;

    @GetMapping("/all")
    public ModelAndView allCases(@RequestParam(name = "page", defaultValue = "0")int page) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/listar");
        Pageable pageable = PageRequest.of(page, 10);
        Page<Incidente> listadoIncidentes = incidenteService.getAllIncidentes(Constants.ESTADO_ELIMINADO, pageable);
        int totalPages = listadoIncidentes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }
        List<CategoriaIncidente> allCategorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        mv.addObject("listadoIncidentes", listadoIncidentes);
        mv.addObject("actualizado", resultado);
        mv.addObject("allCategorias", allCategorias);
        mv.addObject("url", "all");
        resultado = 0;
        return mv;
    }

    @GetMapping("/usuarios/all")
    public ModelAndView allCasesByUser(
            @RequestParam(name = "page",
                    defaultValue = "0")int page,
            Authentication authentication
    ) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos_asignados/listar");
        Pageable pageable = PageRequest.of(page, 10);

        Page<AsignacionIncidente> listadoDeAsignaciones = incidenteService.getAsignacionesByUserName(authentication.getName(), pageable);

        int totalPages = listadoDeAsignaciones.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }
        List<CategoriaIncidente> allCategorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        mv.addObject("listadoDeAsignaciones", listadoDeAsignaciones);
        mv.addObject("actualizado", resultado);
        mv.addObject("allCategorias", allCategorias);
        mv.addObject("url", "all");
        resultado = 0;
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalleCase(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/detalle");
        Incidente incidente = incidenteService.getIncidenteById(id, Constants.ESTADO_ELIMINADO);
        mv.addObject("incidente", incidente);

        List<Notificacion> notificaciones = notificacionService.getAllNotificacionesByIncidente(id);
        mv.addObject("notificaciones", notificaciones);

        Set<Usuario> usuarios = serviceForUsuario.findAll();
        mv.addObject("usuarios", usuarios);

        Set<AsignacionIncidente> listadoAsignaciones = serviceForAsignacionIncidente.findAllAsignacionIncidentesByIdIncidente(id);
        mv.addObject("listadoAsignaciones", listadoAsignaciones);

        if (incidente.getDocumentoA() != null) {
            try {
                String uploadDir = incidente.getDocumentoA();
                Path directorio = Paths.get(uploadDir);
                String extension = getFileExtension(directorio);
                byte[] resource = Files.readAllBytes(directorio);
                String documentoA = Base64.getEncoder().encodeToString(resource);
                documentoA = "data:image/" + extension + ";base64," + documentoA;
                mv.addObject("documentoA", documentoA);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (incidente.getDocumentoB() != null) {
            try {
                String uploadDir = incidente.getDocumentoB();
                Path directorio = Paths.get(uploadDir);
                String extension = getFileExtension(directorio);
                byte[] resourceB = Files.readAllBytes(directorio);
                String documentoB = Base64.getEncoder().encodeToString(resourceB);
                documentoB = "data:image/" + extension + ";base64," + documentoB;
                mv.addObject("documentoB", documentoB);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return mv;
    }

    @GetMapping("/usuarios/detalle/{id}")
    public ModelAndView usuariosDetalleCase(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos_asignados/detalle");
        Incidente incidente = incidenteService.getIncidenteById(id, Constants.ESTADO_ELIMINADO);
        mv.addObject("incidente", incidente);

        List<Notificacion> notificaciones = notificacionService.getNotificacionesByIncidente(id, Constants.ESTADO_ACTIVO);
        mv.addObject("notificaciones", notificaciones);

        Set<Usuario> usuarios = serviceForUsuario.findAll();
        mv.addObject("usuarios", usuarios);

        Set<AsignacionIncidente> listadoAsignaciones = serviceForAsignacionIncidente.findAllAsignacionIncidentesByIdIncidente(id);
        mv.addObject("listadoAsignaciones", listadoAsignaciones);

        if (incidente.getDocumentoA() != null) {
            try {
                String uploadDir = incidente.getDocumentoA();
                Path directorio = Paths.get(uploadDir);
                String extension = getFileExtension(directorio);
                byte[] resource = Files.readAllBytes(directorio);
                String documentoA = Base64.getEncoder().encodeToString(resource);
                documentoA = "data:image/" + extension + ";base64," + documentoA;
                mv.addObject("documentoA", documentoA);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (incidente.getDocumentoB() != null) {
            try {
                String uploadDir = incidente.getDocumentoB();
                Path directorio = Paths.get(uploadDir);
                String extension = getFileExtension(directorio);
                byte[] resourceB = Files.readAllBytes(directorio);
                String documentoB = Base64.getEncoder().encodeToString(resourceB);
                documentoB = "data:image/" + extension + ";base64," + documentoB;
                mv.addObject("documentoB", documentoB);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return mv;
    }

    @PostMapping("/solucionado/{id}")
    public ModelAndView solucionadoCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/all");

        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_SOLUCIONADO);

        if(result) {
            try {
                Incidente incidente = incidenteService.getIncidenteById(id, Constants.ESTADO_ELIMINADO);
                Notificacion notificacion = new Notificacion();
                notificacion.setIdIncidente(incidente);
                notificacion.setDescripcion("Incidente ha sido solucionado.");
                notificacion.setEstado(Constants.ESTADO_ACTIVO);
                notificacion.setFecha(Instant.now());
                notificacion.setIdCiudadano(0L);
                notificacion.setIsIncident(0L);
                notificacionService.saveNewNotificacion(notificacion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(result) resultado = 1;
        else resultado = 0;

        return mv;
    }

    @PostMapping("/solucionado/detalle/{id}")
    public ModelAndView solucionadoDetalle(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/detalle/" + id);
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_SOLUCIONADO);
        return mv;
    }

    @PostMapping("/solucionado/usuarios/detalle/{id}")
    public ModelAndView solucionadoUsuariosDetalle(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/usuarios/detalle/" + id);
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_SOLUCIONADO);
        return mv;
    }

    private String getFileExtension(Path rutaArchivo) {
        String nombreArchivo = rutaArchivo.getFileName().toString();
        int index = nombreArchivo.lastIndexOf('.');
        if (index > 0 && index < nombreArchivo.length() - 1) {
            return nombreArchivo.substring(index + 1);
        }
        return "";
    }

    @PostMapping("/no-aplica/{id}")
    public ModelAndView noAplicaCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/all");
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_NO_APLICA);
        if(result)resultado = 1;
        else resultado = 0;
        return mv;
    }

    @PostMapping("/no-aplica/usuarios/detalle/{id}")
    public ModelAndView noAplicaUsuariosDetalle(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/usuarios/detalle/" + id);
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_NO_APLICA);
        return mv;
    }

    @PostMapping("/no-aplica/detalle/{id}")
    public ModelAndView noAplicaDetalle(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/detalle/" + id);
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_NO_APLICA);
        return mv;
    }

    @PostMapping("/eliminar/{id}")
    public ModelAndView eliminarCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/all");
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_ELIMINADO);
        if(result)resultado = 1;
        else resultado = 0;
        return mv;
    }

    @PostMapping("/eliminar/detalle/{id}")
    public String eliminarDesdeDetalle(@PathVariable("id") Long id){
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_ELIMINADO);
        if(result)resultado = 1;
        else resultado = 0;
        serviceForAsignacionIncidente.eliminarAsignacionesIncidente(id);
        return "redirect:/casos/all";
    }

    @PostMapping("/eliminar/usuarios/detalle/{id}")
    public String eliminarUsuariosDesdeDetalle(@PathVariable("id") Long id){
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_ELIMINADO);
        if(result)resultado = 1;
        else resultado = 0;
        serviceForAsignacionIncidente.eliminarAsignacionesIncidente(id);
        return "redirect:/casos/usuarios/all";
    }

    @GetMapping("/buscar")
    public ModelAndView buscarCase(
            @RequestParam("filtro_descripcion") String descripcion,
            @RequestParam("filtro_direccion") String direccion,
            @RequestParam("filtro_estado") Integer estado,
            @RequestParam("filtro_categoria") Long categoria,
            @RequestParam("filtro_fecha_inicio") String fechaInicio,
            @RequestParam("filtro_fecha_final") String fechaFin,
            @RequestParam(name = "page", defaultValue = "0")int page,
            HttpServletRequest request
    )
    {
        String dataActual = "?filtro_descripcion="+descripcion+
                "&filtro_direccion="+direccion+
                "&filtro_estado="+estado+
                "&filtro_categoria="+categoria+
                "&filtro_fecha_inicio="+fechaInicio+"" +
                "&filtro_fecha_final="+fechaFin;
        Instant fechaInicial = null;
        Instant fechaFinal = null;
        CategoriaIncidente categoriaIncidente = null;
        if(!fechaInicio.isBlank()){
            LocalDateTime localDateTimeInicio = LocalDateTime.parse(fechaInicio + "T00:00:00");
            fechaInicial = localDateTimeInicio.toInstant(ZoneOffset.UTC);
        }
        if(!fechaFin.isBlank()){
            LocalDateTime localDateTimeFinal = LocalDateTime.parse(fechaFin + "T00:00:00");
            fechaFinal = localDateTimeFinal.toInstant(ZoneOffset.UTC);
        }
        if(estado == 0)estado = null;
        if(estado != null){
            if(estado == 4)estado = 0;
        }
        if(categoria == 0) categoria = null;
        if(categoria != null){
            categoriaIncidente = new CategoriaIncidente();
            categoriaIncidente.setId(categoria);
        }
        Pageable pageable = PageRequest.of(page, 10);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/listar");
        Page<Incidente> listadoIncidentes = incidenteService.busquedaCompuesta(
                descripcion,
                direccion,
                estado,
                categoriaIncidente,
                fechaInicial,
                fechaFinal,
                pageable
        );
        int totalPages = listadoIncidentes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }
        List<CategoriaIncidente> allCategorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        mv.addObject("listadoIncidentes", listadoIncidentes);
        mv.addObject("actualizado", resultado);
        mv.addObject("allCategorias", allCategorias);
        mv.addObject("filtroDescripcion", descripcion);
        mv.addObject("filtroDireccion", direccion);
        if(estado != null){
            if(estado == 0)estado = 4;
        }
        mv.addObject("filtroEstado", estado);
        mv.addObject("filtroCategoria", categoria);
        mv.addObject("filtroFechaInicio", fechaInicio);
        mv.addObject("filtroFechaFinal", fechaFin);
        mv.addObject("url", "buscar");
        mv.addObject("dataActual", dataActual);
        resultado = 0;
        return mv;
    }


    @PostMapping("/asignar/{id}")
    public String asignarCaso(@PathVariable("id") Long idIncidente,
                                    @RequestParam("id_usuario")Long idUsuario,
                                    @RequestParam("comentario")String comentario
    ){

        AsignacionIncidente asignacionIncidente = new AsignacionIncidente();
        asignacionIncidente.setComentario(comentario);
        Incidente incidente = new Incidente();
        incidente.setId(idIncidente);
        asignacionIncidente.setIdIncidente(incidente);
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        asignacionIncidente.setIdUsuario(usuario);

        serviceForAsignacionIncidente.save(asignacionIncidente);

        return "redirect:/casos/detalle/" + idIncidente;
    }

    @PostMapping("/marcar-completado/{id}/{idIncidente}")
    public String marcarTareaCompletada(@PathVariable("id")Long id, @PathVariable("idIncidente")Long idIncidente)
    {
        AsignacionIncidente asignacionIncidente = serviceForAsignacionIncidente.findOneAsignacion(id);
        if(asignacionIncidente == null){ return "redirect:/casos/detalle/" + idIncidente; }

        asignacionIncidente.setEstado(Constants.ESTADO_ELIMINADO);
        serviceForAsignacionIncidente.save(asignacionIncidente);
        return "redirect:/casos/detalle/" + idIncidente;
    }

    @PostMapping("/marcar-completado/usuarios/{id}/{idIncidente}")
    public String marcarTareaCompletadaUsuarios(@PathVariable("id")Long id, @PathVariable("idIncidente")Long idIncidente)
    {
        AsignacionIncidente asignacionIncidente = serviceForAsignacionIncidente.findOneAsignacion(id);
        if(asignacionIncidente == null){ return "redirect:/casos/usuarios/detalle/" + idIncidente; }

        asignacionIncidente.setEstado(Constants.ESTADO_ELIMINADO);
        serviceForAsignacionIncidente.save(asignacionIncidente);
        return "redirect:/casos/usuarios/detalle/" + idIncidente;
    }
}
