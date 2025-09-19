package com.boletas.consulta_cesantes.service;
import com.boletas.consulta_cesantes.model.Cesante;
import com.boletas.consulta_cesantes.repository.boleta.CesanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CesanteImportService {

    private final CesanteRepository cesanteRepository;

    public CesanteImportService(CesanteRepository cesanteRepository) {
        this.cesanteRepository = cesanteRepository;
    }

    @Transactional
    public List<Cesante> saveCesantes(List<Cesante> cesantes) {
        return cesanteRepository.saveAll(cesantes);
    }
}