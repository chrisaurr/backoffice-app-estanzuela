package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios", schema = "estanzuela", indexes = {
        @Index(name = "usuarios_roles_FK", columnList = "id_rol"),
        @Index(name = "usuarios_departamento_FK", columnList = "id_area_trabajo"),
        @Index(name = "fk_usuarios_entidad1_idx", columnList = "id_entidad")
})
public class Usuario{

    @Transient
    private final int ACTIVO = 1;
    @Transient
    private final int ELIMINADO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long id;

    @Size(max = 45)
    @Column(name = "nombre", length = 45)
    private String nombre;

    @Size(max = 45)
    @Column(name = "apellidos", length = 45)
    private String apellidos;

    @Size(max = 45)
    @Column(name = "direccion", length = 45)
    private String direccion;

    @Size(max = 45)
    @Column(name = "telefono", length = 45)
    private String telefono;

    @Size(max = 200)
    @Column(name = "email", length = 200)
    private String email;

    @Size(max = 45)
    @NotNull
    @Column(name = "username", nullable = false, length = 45, unique = true)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    @JsonBackReference("Role")
    private Role idRol;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_area_trabajo", nullable = false)
    @JsonBackReference("AreaDeTrabajo")
    private AreaTrabajo idAreaTrabajo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_entidad", nullable = false)
    @JsonBackReference("Entidad")
    private Entidad idEntidad;

    @OneToMany(mappedBy = "idUsuario")
    @JsonManagedReference("AsignacionUsuario")
    private Set<AsignacionIncidente> asignacionIncidentes = new LinkedHashSet<>();

    @PrePersist
    private void prePersist() {
        this.estado = ACTIVO;
        this.fecha = Instant.now();
        Entidad entidad = new Entidad();
        entidad.setId(1L);
        this.idEntidad = entidad;
    }
}