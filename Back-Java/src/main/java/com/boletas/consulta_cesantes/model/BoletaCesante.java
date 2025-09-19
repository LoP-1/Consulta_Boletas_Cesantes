package com.boletas.consulta_cesantes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class BoletaCesante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cesante_id")
    private Cesante cesante;

    // Datos de la boleta
    private String dre;              // Ej: DRE JUNIN
    private String codigoCentroPago; // Ej: CP10B000-
    private String ruc;              // Ej: 20188468706
    private String numeroBoleta;     // Ej: 1019914539-325001
    private String periodo;          // Ej: ABRIL - 2025
    private String tipoBoleta;       // Ej: CES/TIT
    private String estado;           // Ej: (4) Habilitado

    // Totales y montos
    private Double totalRemuneracion;
    private Double totalDescuento;
    private Double totalLiquido;
    private Double montoImponible;

    // Mensajes y otros
    @Column(columnDefinition="TEXT")
    private String mensaje;
    private String numeroSecuencia; // Ej: 00000001

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BoletaDetalleCesante> detalles;

    // getters, setters

    public List<BoletaDetalleCesante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<BoletaDetalleCesante> detalles) {
        this.detalles = detalles;
    }

    public String getNumeroSecuencia() {
        return numeroSecuencia;
    }

    public void setNumeroSecuencia(String numeroSecuencia) {
        this.numeroSecuencia = numeroSecuencia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Double getMontoImponible() {
        return montoImponible;
    }

    public void setMontoImponible(Double montoImponible) {
        this.montoImponible = montoImponible;
    }

    public Double getTotalLiquido() {
        return totalLiquido;
    }

    public void setTotalLiquido(Double totalLiquido) {
        this.totalLiquido = totalLiquido;
    }

    public Double getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(Double totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public Double getTotalRemuneracion() {
        return totalRemuneracion;
    }

    public void setTotalRemuneracion(Double totalRemuneracion) {
        this.totalRemuneracion = totalRemuneracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoBoleta() {
        return tipoBoleta;
    }

    public void setTipoBoleta(String tipoBoleta) {
        this.tipoBoleta = tipoBoleta;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(String numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCodigoCentroPago() {
        return codigoCentroPago;
    }

    public void setCodigoCentroPago(String codigoCentroPago) {
        this.codigoCentroPago = codigoCentroPago;
    }

    public String getDre() {
        return dre;
    }

    public void setDre(String dre) {
        this.dre = dre;
    }

    public Cesante getCesante() {
        return cesante;
    }

    public void setCesante(Cesante cesante) {
        this.cesante = cesante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}