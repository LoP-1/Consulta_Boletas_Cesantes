package com.boletas.consulta_cesantes.controller;
import com.boletas.consulta_cesantes.model.Cesante;
import com.boletas.consulta_cesantes.service.CesanteImportService;
import com.boletas.consulta_cesantes.service.CesanteTxtParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/import")
public class CesanteImportController {

    private final CesanteTxtParserService parserService;
    private final CesanteImportService importService;

    @Autowired
    public CesanteImportController(CesanteTxtParserService parserService, CesanteImportService importService) {
        this.parserService = parserService;
        this.importService = importService;
    }

    @PostMapping("/txt")
    public List<Cesante> importCesantesTxt(@RequestParam("file") MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("cesantes", ".txt");
        file.transferTo(tempFile);
        List<Cesante> cesantes = parserService.parseCesantesFromTxt(tempFile);
        return importService.saveCesantes(cesantes);
    }
}
