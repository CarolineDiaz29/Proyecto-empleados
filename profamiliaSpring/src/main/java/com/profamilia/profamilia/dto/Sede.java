package com.profamilia.profamilia.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sede")
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsede;

    @Column(nullable = false)
    private String nombre;
    private String ciudad;
    private String direccion;
    private long telefono;
    private int capacidad;

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Empleado> empleados;
}
