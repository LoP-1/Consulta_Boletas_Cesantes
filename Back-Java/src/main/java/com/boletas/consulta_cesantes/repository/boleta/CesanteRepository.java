package com.boletas.consulta_cesantes.repository.boleta;

import com.boletas.consulta_cesantes.model.Cesante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CesanteRepository extends JpaRepository<Cesante, Long> {
    Cesante findByDocumentoIdentidad(String documentoIdentidad);
}