package com.umg.backoffice.service.incidente.interfaces;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Ciudadano;
import com.umg.backoffice.modelo.entity.Incidente;

import java.time.Instant;
import java.util.List;

public interface InterfaceForIncidenteService {
    List<Incidente> getAllIncidentes();

    Boolean updateEstadoIncidente(Long id, Integer estado);

    Boolean updateCantidadAtendidos(Long id, Integer cantidadAtendidos);

    List<Incidente> getIncidentesByDescripcion(String descripcion);

    List<Incidente> getIncidentesByDireccion(String direccion);

    List<Incidente> getIncidentesByReferenciaDeDireccion(String referenciaDeDireccion);

    List<Incidente> getIncidentesByFechaBetween(Instant fechaInicio, Instant fechaFin);

    List<Incidente> getIncidentesByEstado(Integer estado);

    List<Incidente> getIncidentesByCategoria(CategoriaIncidente categoriaIncidente);

    List<Incidente> getIncidentesByCiudadano(Ciudadano ciudadano);
}
