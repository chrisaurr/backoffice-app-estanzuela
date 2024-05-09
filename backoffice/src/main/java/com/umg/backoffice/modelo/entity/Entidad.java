package com.umg.backoffice.modelo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}