package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.modelo.entity.Notificacion;
import com.umg.backoffice.service.Notificacion.service.NotificacionService;
import com.umg.backoffice.service.categoria_incidente.service.CategoriaIncidenteService;
import com.umg.backoffice.service.incidente.service.IncidenteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;
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

    @GetMapping("/detalle/{id}")
    public ModelAndView detalleCase(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/detalle");
        Incidente incidente = incidenteService.getIncidenteById(id, Constants.ESTADO_ELIMINADO);
        mv.addObject("incidente", incidente);

        List<Notificacion> notificaciones = notificacionService.getNotificacionesByIncidente(id, Constants.ESTADO_ACTIVO);
        mv.addObject("notificaciones", notificaciones);

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
        if(result)resultado = 1;
        else resultado = 0;
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

    @PostMapping("/eliminar/{id}")
    public ModelAndView eliminarCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/casos/all");
        Boolean result = incidenteService.updateEstadoIncidente(id, Constants.ESTADO_ELIMINADO);
        if(result)resultado = 1;
        else resultado = 0;
        return mv;
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
}
