package com.umg.backoffice.modelo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

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
    private Instant fecha;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Size(max = 255)
    @Column(name = "comentario")
    private String comentario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_incidente", nullable = false)
    private Incidente idIncidente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Incidente getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(Incidente idIncidente) {
        this.idIncidente = idIncidente;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

}