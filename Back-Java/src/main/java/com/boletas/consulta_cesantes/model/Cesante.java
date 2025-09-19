package com.boletas.consulta_cesantes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Cesante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos personales
    private String apellidos;
    private String nombres;
    private LocalDate fechaNacimiento;
    private String documentoIdentidad;
    private String cargo;

    // Datos de pensión y registro
    private String tipoPensionista; // Ej: CESANTE, VIUDEZ
    private String tipoPension;     // Ej: CESANTE DOC. NIVELABLE
    private String nivMagGrupoOcupHoras; // Ej: 5/0-0/40
    private String tiempoServicio;       // Ej: 28-07-11 ESSALUD : 4302261AIMIA001
    private String fechaRegistro;        // Ej: Cese :01/01/1991 Termino:01/01/1991
    private String cuentaTeleahorro;     // Ej: CTA- 4381413824
    private String leyendaPermanente;
    private String leyendaMensual;

    @OneToMany(mappedBy = "cesante", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BoletaCesante> boletas;

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

    public String getTipoPensionista() {
        return tipoPensionista;
    }

    public void setTipoPensionista(String tipoPensionista) {
        this.tipoPensionista = tipoPensionista;
    }

    public String getTipoPension() {
        return tipoPension;
    }

    public void setTipoPension(String tipoPension) {
        this.tipoPension = tipoPension;
    }

    public String getNivMagGrupoOcupHoras() {
        return nivMagGrupoOcupHoras;
    }

    public void setNivMagGrupoOcupHoras(String nivMagGrupoOcupHoras) {
        this.nivMagGrupoOcupHoras = nivMagGrupoOcupHoras;
    }

    public String getTiempoServicio() {
        return tiempoServicio;
    }

    public void setTiempoServicio(String tiempoServicio) {
        this.tiempoServicio = tiempoServicio;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public List<BoletaCesante> getBoletas() {
        return boletas;
    }

    public void setBoletas(List<BoletaCesante> boletas) {
        this.boletas = boletas;
    }
}
