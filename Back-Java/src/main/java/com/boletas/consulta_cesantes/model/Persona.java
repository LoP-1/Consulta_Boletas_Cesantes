package com.boletas.consulta_cesantes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

//modelo padre de persona, de este heredaran activos y cesantes
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apellidos;
    private String nombres;
    private LocalDate fechaNacimiento;
    private String documentoIdentidad;
    private String cargo;
    private String cuentaTeleahorro;
    private String leyendaPermanente;
    private String leyendaMensual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCuentaTeleahorro() {
        return cuentaTeleahorro;
    }

    public void setCuentaTeleahorro(String cuentaTeleahorro) {
        this.cuentaTeleahorro = cuentaTeleahorro;
    }

    public String getLeyendaPermanente() {
        return leyendaPermanente;
    }

    public void setLeyendaPermanente(String leyendaPermanente) {
        this.leyendaPermanente = leyendaPermanente;
    }

    public String getLeyendaMensual() {
        return leyendaMensual;
    }

    public void setLeyendaMensual(String leyendaMensual) {
        this.leyendaMensual = leyendaMensual;
    }
}
