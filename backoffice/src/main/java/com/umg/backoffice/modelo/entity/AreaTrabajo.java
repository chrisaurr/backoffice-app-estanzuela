package com.umg.backoffice.modelo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "area_trabajo", schema = "estanzuela")
public class AreaTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area_trabajo", nullable = false)
    private Long id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @OneToMany(mappedBy = "idAreaTrabajo")
    @JsonManagedReference("AreaDeTrabajo")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

}