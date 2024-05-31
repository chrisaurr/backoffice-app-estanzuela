package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ciudadano", schema = "estanzuela")
public class Ciudadano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "nombres", length = 100)
    private String nombres;

    @Size(max = 45)
    @Column(name = "dpi", length = 45)
    private String dpi;

    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 45)
    @Column(name = "telefono", length = 45)
    private String telefono;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "foto")
    private String foto;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Integer activo;

    @NotNull
    @Column(name = "eliminado", nullable = false)
    private Integer eliminado;

    @Lob
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "idCiudadano")
    @JsonManagedReference("Ciudadano")
    private Set<Incidente> incidentes = new LinkedHashSet<>();

}