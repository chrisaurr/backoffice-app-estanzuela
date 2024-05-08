package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "incidente", schema = "estanzuela", indexes = {
        @Index(name = "fk_incidente_ciudadano1_idx", columnList = "id_ciudadano")
})
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidente", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Size(max = 150)
    @NotNull
    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Size(max = 150)
    @Column(name = "referencia_de_direccion", length = 150)
    private String referenciaDeDireccion;

    @Size(max = 255)
    @NotNull
    @Column(name = "latitud", nullable = false)
    private String latitud;

    @Size(max = 255)
    @NotNull
    @Column(name = "longitud", nullable = false)
    private String longitud;

    @Size(max = 255)
    @Column(name = "documento_a")
    private String documentoA;

    @Size(max = 255)
    @Column(name = "documento_b")
    private String documentoB;

    @Column(name = "fecha")
    private Instant fecha;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "cantidad_atendidos")
    private Integer cantidadAtendidos;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    @JsonBackReference
    private CategoriaIncidente idCategoria;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciudadano", nullable = false)
    @JsonBackReference
    private Ciudadano idCiudadano;

    @OneToMany(mappedBy = "idIncidente")
    private Set<AsignacionIncidente> asignacionIncidentes = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferenciaDeDireccion() {
        return referenciaDeDireccion;
    }

    public void setReferenciaDeDireccion(String referenciaDeDireccion) {
        this.referenciaDeDireccion = referenciaDeDireccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDocumentoA() {
        return documentoA;
    }

    public void setDocumentoA(String documentoA) {
        this.documentoA = documentoA;
    }

    public String getDocumentoB() {
        return documentoB;
    }

    public void setDocumentoB(String documentoB) {
        this.documentoB = documentoB;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getCantidadAtendidos() {
        return cantidadAtendidos;
    }

    public void setCantidadAtendidos(Integer cantidadAtendidos) {
        this.cantidadAtendidos = cantidadAtendidos;
    }

    public CategoriaIncidente getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(CategoriaIncidente idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Ciudadano getIdCiudadano() {
        return idCiudadano;
    }

    public void setIdCiudadano(Ciudadano idCiudadano) {
        this.idCiudadano = idCiudadano;
    }

    public Set<AsignacionIncidente> getAsignacionIncidentes() {
        return asignacionIncidentes;
    }

    public void setAsignacionIncidentes(Set<AsignacionIncidente> asignacionIncidentes) {
        this.asignacionIncidentes = asignacionIncidentes;
    }

}