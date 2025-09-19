package com.boletas.consulta_cesantes.model;

import jakarta.persistence.*;

@Entity
public class BoletaDetalleCesante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boleta_id")
    private BoletaCesante boleta;

    private String concepto; // Ej: +basica, -ipss, +ds002-2024
    private Double monto;

    // getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BoletaCesante getBoleta() {
        return boleta;
    }

    public void setBoleta(BoletaCesante boleta) {
        this.boleta = boleta;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}