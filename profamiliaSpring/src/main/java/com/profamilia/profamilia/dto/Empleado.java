package com.profamilia.profamilia.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    private long idempleado;

    @Column(nullable = false)
    private String nombre;
    private Date fecha;
    private int edad;
    private long telefono;
    private String cargo;
    private double salario;

    @ManyToOne
    @JoinColumn(name = "idsede")
    @JsonBackReference
    private Sede sede;

}
