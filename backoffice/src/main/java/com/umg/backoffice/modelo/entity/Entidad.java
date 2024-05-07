package com.umg.backoffice.modelo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "entidad", schema = "estanzuela")
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre_entidad", nullable = false, length = 100)
    private String nombreEntidad;

    @Size(max = 100)
    @NotNull
    @Column(name = "direccion_entidad", nullable = false, length = 100)
    private String direccionEntidad;

    @Size(max = 45)
    @Column(name = "telefono", length = 45)
    private String telefono;

    @Size(max = 255)
    @NotNull
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "key_mapas", nullable = false)
    private String keyMapas;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @OneToMany(mappedBy = "idEntidad")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getDireccionEntidad() {
        return direccionEntidad;
    }

    public void setDireccionEntidad(String direccionEntidad) {
        this.direccionEntidad = direccionEntidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getKeyMapas() {
        return keyMapas;
    }

    public void setKeyMapas(String keyMapas) {
        this.keyMapas = keyMapas;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}