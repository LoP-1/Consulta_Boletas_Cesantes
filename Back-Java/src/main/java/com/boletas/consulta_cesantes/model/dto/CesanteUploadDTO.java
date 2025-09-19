package com.boletas.consulta_cesantes.model.dto;

import com.boletas.consulta_cesantes.model.BoletaCesante;
import com.boletas.consulta_cesantes.model.Cesante;

import java.util.List;

public class CesanteUploadDTO {
    private Cesante cesante;
    private List<BoletaCesante> boletas;

    // getters, setters

    public Cesante getCesante() {
        return cesante;
    }

    public void setCesante(Cesante cesante) {
        this.cesante = cesante;
    }

    public List<BoletaCesante> getBoletas() {
        return boletas;
    }

    public void setBoletas(List<BoletaCesante> boletas) {
        this.boletas = boletas;
    }
}
