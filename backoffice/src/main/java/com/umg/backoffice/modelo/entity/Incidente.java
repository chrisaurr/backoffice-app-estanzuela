package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference("CategoriaIncidente")
    private CategoriaIncidente idCategoria;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciudadano", nullable = false)
    @JsonBackReference("Ciudadano")
    private Ciudadano idCiudadano;

    @OneToMany(mappedBy = "idIncidente")
    private Set<AsignacionIncidente> asignacionIncidentes = new LinkedHashSet<>();

}