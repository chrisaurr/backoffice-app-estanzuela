package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notificacion", schema = "estanzuela")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 250)
    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_incidente", nullable = false)
    @JsonBackReference("Incidente")
    private Incidente idIncidente;

    @NotNull
    @Column(name = "id_ciudadano", nullable = false)
    private Long idCiudadano = 0L;

    @NotNull
    @Column(name = "is_incident", nullable = false)
    private Long isIncident = 0L;

}
