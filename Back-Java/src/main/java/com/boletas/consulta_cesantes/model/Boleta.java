package com.boletas.consulta_cesantes.model;
import jakarta.persistence.*;
import java.util.List;

import jakarta.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String periodo;
    private Double totalRemuneracion;
    private Double totalDescuento;
    private Double totalLiquido;
    private Double montoImponible;
    private String mensaje;
    private String numeroSecuencia;

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getTotalRemuneracion() {
        return totalRemuneracion;
    }

    public void setTotalRemuneracion(Double totalRemuneracion) {
        this.totalRemuneracion = totalRemuneracion;
    }

    public Double getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(Double totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public Double getTotalLiquido() {
        return totalLiquido;
    }

    public void setTotalLiquido(Double totalLiquido) {
        this.totalLiquido = totalLiquido;
    }

    public Double getMontoImponible() {
        return montoImponible;
    }

    public void setMontoImponible(Double montoImponible) {
        this.montoImponible = montoImponible;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumeroSecuencia() {
        return numeroSecuencia;
    }

    public void setNumeroSecuencia(String numeroSecuencia) {
        this.numeroSecuencia = numeroSecuencia;
    }
}