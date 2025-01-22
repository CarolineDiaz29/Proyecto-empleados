package com.profamilia.dto;

import javax.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    private long idempleado;

    @Column(nullable = false)
    private String nombre;
    private int edad;
    private long telefono;
    private String cargo;
    private double salario;

    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sede;

    public long getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(long idempleado) {
        this.idempleado = idempleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}
