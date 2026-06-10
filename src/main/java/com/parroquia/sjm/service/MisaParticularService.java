package com.parroquia.sjm.service;

import com.parroquia.sjm.model.dto.MisaParticularDTO;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class MisaParticularService {

    private final WebClient supabaseWebClient;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

    public MisaParticularService(WebClient supabaseWebClient) {
        this.supabaseWebClient = supabaseWebClient;
    }

    public Mono<byte[]> exportarMisasParticulares(String fecha) {
        return supabaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/v1/misas_particulares")
                        .queryParam("fecha_misa", "eq." + fecha)
                        .queryParam("order", "hora_misa.asc")
                        .build())
                .retrieve()
                .bodyToFlux(MisaParticularDTO.class)
                .collectList()
                .flatMap(this::generarWordDesdeLista);
    }

    private Mono<byte[]> generarWordDesdeLista(List<MisaParticularDTO> misas) {
        return Mono.fromCallable(() -> {
            if (misas.isEmpty()) {
                return crearDocumentoVacio("No hay misas para esta fecha");
            }

            try (XWPFDocument document = new XWPFDocument()) {
                // 1. Encabezado dinámico (Repetido en cada página)
                LocalDate fechaObj = misas.get(0).fechaMisa();
                String diaSemana = fechaObj.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);
                String encabezadoTexto = String.format("%s %02d", diaSemana, fechaObj.getDayOfMonth());

                XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                XWPFParagraph headerPara = header.createParagraph();
                headerPara.setAlignment(ParagraphAlignment.CENTER);
                headerPara.setBorderBottom(Borders.DOUBLE);
                
                XWPFRun headerRun = headerPara.createRun();
                headerRun.setText(encabezadoTexto);
                headerRun.setBold(true);
                headerRun.setFontSize(20);
                headerRun.setFontFamily("Arial");

                // 2. Cuerpo del documento: Usamos tablas para evitar que se corten las intenciones
                for (MisaParticularDTO misa : misas) {
                    // Creamos una tabla de 1x1 para contener la intención completa
                    XWPFTable table = document.createTable(1, 1);
                    table.removeBorders(); // Quitamos los bordes por defecto
                    
                    // Aseguramos que la tabla ocupe el ancho de la página
                    CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
                    width.setType(STTblWidth.DXA);
                    width.setW(BigInteger.valueOf(9072)); // Aprox ancho estándar A4 en twips

                    XWPFTableCell cell = table.getRow(0).getCell(0);
                    // Importante: No permitir que la fila se rompa entre páginas
                    table.getRow(0).setCantSplitRow(true);

                    // Contenido de la celda: Hora e Intención
                    XWPFParagraph p1 = cell.getParagraphs().get(0);
                    p1.setSpacingBefore(100);
                    XWPFRun r1 = p1.createRun();
                    r1.setText("Hora: " + misa.horaMisa().format(timeFormatter));
                    r1.setBold(true);
                    r1.setFontSize(13);
                    r1.setFontFamily("Arial");
                    r1.addBreak();

                    XWPFRun r2 = p1.createRun();
                    r2.setText(misa.intencion());
                    r2.setBold(true);
                    r2.setFontSize(12);
                    r2.setFontFamily("Arial");

                    // Contenido de la celda: Ofrece
                    XWPFParagraph p2 = cell.addParagraph();
                    XWPFRun rOfrece = p2.createRun();
                    rOfrece.setText("Ofrece: " + misa.ofrece());
                    rOfrece.setItalic(true);
                    rOfrece.setFontSize(11);
                    rOfrece.setFontFamily("Arial");

                    // Espacio en blanco Orden
                    XWPFParagraph p3 = cell.addParagraph();
                    XWPFRun salto = p3.createRun();
                    salto.setText("\n");
                    salto.setItalic(true);
                    salto.setFontSize(11);
                    salto.setFontFamily("Arial");

                    // Borde inferior para separar de la siguiente intención
                    p3.setBorderBottom(Borders.SINGLE);
                    p3.setSpacingAfter(200);

                    // Espacio en blanco fuera de la tabla para separar bloques
                    document.createParagraph();
                }

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                document.write(out);
                return out.toByteArray();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private byte[] crearDocumentoVacio(String mensaje) throws Exception {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XWPFParagraph p = document.createParagraph();
            p.createRun().setText(mensaje);
            document.write(out);
            return out.toByteArray();
        }
    }
}