package com.boletas.consulta_cesantes.service;

import com.boletas.consulta_cesantes.model.BoletaCesante;
import com.boletas.consulta_cesantes.model.BoletaDetalleCesante;
import com.boletas.consulta_cesantes.model.Cesante;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

@Service
public class CesanteTxtParserService {

    public List<Cesante> parseCesantesFromTxt(File txtFile) throws Exception {
        List<Cesante> cesantes = new ArrayList<>();
        StringBuilder block = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(".")) {
                    if (block.length() > 0) {
                        Cesante cesante = parseBlock(block.toString());
                        if (cesante != null) cesantes.add(cesante);
                        block.setLength(0);
                    }
                } else {
                    block.append(line).append("\n");
                }
            }
        }
        return cesantes;
    }

    // Parse each block (one cesante and its boleta)
    private Cesante parseBlock(String block) {
        Cesante cesante = new Cesante();
        BoletaCesante boleta = new BoletaCesante();
        List<BoletaDetalleCesante> detalles = new ArrayList<>();

        // Simple regex helpers
        cesante.setApellidos(extract(block, "Apellidos\\s+:\\s*(.+)"));
        cesante.setNombres(extract(block, "Nombres\\s+:\\s*(.+)"));
        cesante.setFechaNacimiento(parseDate(extract(block, "Fecha de Nacimiento\\s+:\\s*(.+)")));
        cesante.setDocumentoIdentidad(extract(block, "Documento de Identidad\\s*:\\s*\\([^)]+\\)\\s*(.+)"));
        cesante.setCargo(extract(block, "Cargo\\s*:\\s*(.+)"));
        cesante.setTipoPensionista(extract(block, "Tipo de Pensionista\\s*:\\s*(.+)"));
        cesante.setTipoPension(extract(block, "Tipo de Pension\\s*:\\s*(.+)"));
        cesante.setNivMagGrupoOcupHoras(extract(block, "Niv.Mag./Grupo Ocup./Horas\\s*:?\\s*(.+)"));
        cesante.setTiempoServicio(extract(block, "Tiempo de Servicio \\(AA-MM-DD\\):\\s*(.+)"));
        cesante.setFechaRegistro(extract(block, "Fecha de Registro\\s*:\\s*(.+)"));
        cesante.setCuentaTeleahorro(extract(block, "Cta\\. TeleAhorro o Nro\\. Cheque:\\s*(.+)"));
        cesante.setLeyendaPermanente(extract(block, "Leyenda Permanente\\s*:\\s*(.+)"));
        cesante.setLeyendaMensual(extract(block, "Leyenda Mensual\\s*:\\s*(.+)"));

        // Boleta fields
        boleta.setPeriodo(extract(block, "(ENERO|FEBRERO|MARZO|ABRIL|MAYO|JUNIO|JULIO|AGOSTO|SETIEMBRE|OCTUBRE|NOVIEMBRE|DICIEMBRE)[^\\n]*"));
        boleta.setTipoBoleta(extract(block, "(CES/TIT|CES/SOB|ACT/CONT/TIT|ACT/NOMB/TIT|ACTIVO|CESANTE|VIUDEZ|PENSI[ÓO]N CESANTIA DOCENTE|CESANTE DOC. NIVELABLE|CESANTE DOC NO NIVELABLE)"));
        boleta.setCodigoCentroPago(extract(block, "CF[0-9A-Z\\-]+"));
        boleta.setRuc(extract(block, "RUC - (\\d+)"));
        boleta.setNumeroBoleta(extract(block, "RUC - \\d+\\s+([\\d\\-]+)"));
        boleta.setEstado(extract(block, "\\(\\d+\\)\\s*Habilitado"));
        boleta.setMensaje(extract(block, "Mensajes[\\s\\S]*?([\\s\\S]*?)\\n\\n"));

        // Totals
        boleta.setTotalRemuneracion(parseDouble(extract(block, "T-REMUN\\s+(\\d+[\\.,]?\\d*)")));
        boleta.setTotalDescuento(parseDouble(extract(block, "T-DSCTO\\s+(\\d+[\\.,]?\\d*)")));
        boleta.setTotalLiquido(parseDouble(extract(block, "T-LIQUI\\s+(\\d+[\\.,]?\\d*)")));
        boleta.setMontoImponible(parseDouble(extract(block, "MImponible\\s+(\\d+[\\.,]?\\d*)")));

        // Detalles (concepts)
        Pattern detallePattern = Pattern.compile("([\\+\\-][a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)?)[\\s]+([\\d\\.,]+)");
        Matcher matcher = detallePattern.matcher(block);
        while (matcher.find()) {
            BoletaDetalleCesante detalle = new BoletaDetalleCesante();
            detalle.setConcepto(matcher.group(1).trim());
            detalle.setMonto(parseDouble(matcher.group(2)));
            detalles.add(detalle);
        }

        boleta.setDetalles(detalles);
        boleta.setCesante(cesante);
        cesante.setBoletas(Collections.singletonList(boleta));
        return cesante;
    }

    private static String extract(String block, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(block);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    private static Double parseDouble(String s) {
        if (s == null) return null;
        s = s.replace(",", "."); // por si viene con coma decimal
        try { return Double.parseDouble(s); } catch (Exception e) { return null; }
    }

    private static LocalDate parseDate(String dateStr) {
        // formato dd/MM/yyyy
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr.trim(), dtf);
        } catch (Exception e) { return null; }
    }
}