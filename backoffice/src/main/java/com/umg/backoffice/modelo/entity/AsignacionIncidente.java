package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignacion_incidente", schema = "estanzuela", indexes = {
        @Index(name = "fk_historial_incidente_incidente1_idx", columnList = "id_incidente"),
        @Index(name = "fk_historial_incidente_usuarios1_idx", columnList = "id_usuario")
})
public class AsignacionIncidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historia_incidente", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Size(max = 255)
    @Column(name = "comentario")
    private String comentario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_incidente", nullable = false)
    @JsonBackReference("AsignacionIncidente")
    private Incidente idIncidente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference("AsignacionUsuario")
    private Usuario idUsuario;

    @PrePersist
    private void prePersist() {
        this.fecha = new Date();
        this.estado = Constants.ESTADO_ACTIVO;
    }

}