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
@Table(name = "categoria_incidente", schema = "estanzuela")
public class CategoriaIncidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Long id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @OneToMany(mappedBy = "idCategoria")
    @JsonManagedReference
    private Set<Incidente> incidentes = new LinkedHashSet<>();

}