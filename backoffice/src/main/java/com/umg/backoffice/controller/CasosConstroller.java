package com.umg.backoffice.controller;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.service.categoria_incidente.service.CategoriaIncidenteService;
import com.umg.backoffice.service.incidente.service.IncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
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

@Controller
@RequestMapping("/casos")
public class CasosConstroller {

    @Autowired
    private IncidenteService incidenteService;

    @Autowired
    private CategoriaIncidenteService categoriaIncidenteService;

    private Integer resultado = 0;

    @GetMapping("/all")
    public ModelAndView allCases() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/listar");
        List<Incidente> listadoIncidentes = incidenteService.getAllIncidentes(Constants.ESTADO_ELIMINADO);
        List<CategoriaIncidente> allCategorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        mv.addObject("listadoIncidentes", listadoIncidentes);
        mv.addObject("actualizado", resultado);
        mv.addObject("allCategorias", allCategorias);
        resultado = 0;
        System.out.println("TAMANIO: " + listadoIncidentes.size());
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalleCase(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/detalle");
        Incidente incidente = incidenteService.getIncidenteById(id, Constants.ESTADO_ELIMINADO);
        mv.addObject("incidente", incidente);

        if(incidente.getDocumentoA() != null){
            try{
                String uploadDir = incidente.getDocumentoA();
                Path directorio = Paths.get(uploadDir);
                String extension = getFileExtension(directorio);
                byte[] resource = Files.readAllBytes(directorio);
                String documentoA = Base64.getEncoder().encodeToString(resource);
                documentoA = "data:image/" + extension + ";base64," + documentoA;
                mv.addObject("documentoA", documentoA);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        if(incidente.getDocumentoB() != null){
            try {
                String uploadDir = incidente.getDocumentoB();
                Path directorio = Paths.get(uploadDir);
                String extension = getFileExtension(directorio);
                byte[] resourceB = Files.readAllBytes(directorio);
                String documentoB = Base64.getEncoder().encodeToString(resourceB);
                documentoB = "data:image/" + extension + ";base64," + documentoB;
                mv.addObject("documentoB", documentoB);
            }catch (Exception e){
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
            @RequestParam("filtro_fecha_final") String fechaFin)
    {
        Instant fechaInicial = null;
        Instant fechaFinal = null;
        if(!fechaInicio.isBlank()){
            LocalDateTime localDateTimeInicio = LocalDateTime.parse(fechaInicio + "T00:00:00");
            fechaInicial = localDateTimeInicio.toInstant(ZoneOffset.UTC);
        }
        if(!fechaFin.isBlank()){
            LocalDateTime localDateTimeFinal = LocalDateTime.parse(fechaFin + "T00:00:00");
            fechaFinal = localDateTimeFinal.toInstant(ZoneOffset.UTC);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/casos/listar");
        CategoriaIncidente categoriaIncidente = new CategoriaIncidente();
        categoriaIncidente.setId(categoria);
        List<Incidente> listadoIncidentes = incidenteService.busquedaCompuesta(
                descripcion,
                direccion,
                estado,
                categoriaIncidente,
                fechaInicial,
                fechaFinal
        );
        List<CategoriaIncidente> allCategorias = categoriaIncidenteService.getAllCategoriaIncidente(Constants.ESTADO_ELIMINADO);
        mv.addObject("listadoIncidentes", listadoIncidentes);
        mv.addObject("actualizado", resultado);
        mv.addObject("allCategorias", allCategorias);
        resultado = 0;
        System.out.println("TAMANIO: " + listadoIncidentes.size());
        return mv;
    }
}
