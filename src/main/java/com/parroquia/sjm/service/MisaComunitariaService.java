package com.parroquia.sjm.service;

import com.parroquia.sjm.model.dto.MisaComunitariaDTO;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MisaComunitariaService {

    private final WebClient supabaseWebClient;
    private final List<String> CATEGORIAS_ORDENADAS = List.of("ACCIÓN DE GRACIAS", "SALUD", "DIFUNTOS");

    public MisaComunitariaService(WebClient supabaseWebClient) {
        this.supabaseWebClient = supabaseWebClient;
    }

    public Mono<byte[]> exportarMisaComunitaria(String fecha, String hora) {
        return supabaseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/v1/misas_comunitarias")
                        .queryParam("fecha_misa", "eq." + fecha)
                        .queryParam("hora_misa", "eq." + hora)
                        .queryParam("order", "categoria.asc")
                        .build())
                .retrieve()
                .bodyToFlux(MisaComunitariaDTO.class)
                .collectList()
                .flatMap(misas -> generarWordComunitario(misas, fecha, hora));
    }

    private Mono<byte[]> generarWordComunitario(List<MisaComunitariaDTO> misas, String fechaStr, String horaStr) {
        return Mono.fromCallable(() -> {
            try (XWPFDocument document = new XWPFDocument()) {
                // --- AJUSTE DE MÁRGENES (25% del estándar) ---
                CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
                CTPageMar pageMar = sectPr.addNewPgMar();
                pageMar.setTop(BigInteger.valueOf(460));    // 1440 * 0.25
                pageMar.setBottom(BigInteger.valueOf(460)); // 1440 * 0.25
                pageMar.setLeft(BigInteger.valueOf(720));   // Margen lateral un poco más amplio para lectura
                pageMar.setRight(BigInteger.valueOf(720));

                // 1. Título en el Cuerpo
                LocalDate date = LocalDate.parse(fechaStr);
                LocalTime time = LocalTime.parse(horaStr);
                String diaSemana = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();
                String tituloTexto = String.format("%s %02d - %s", diaSemana, date.getDayOfMonth(), 
                        time.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)).toUpperCase());

                XWPFParagraph titlePara = document.createParagraph();
                titlePara.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titlePara.createRun();
                titleRun.setText(tituloTexto);
                titleRun.setBold(true);
                titleRun.setFontSize(18);
                titleRun.setFontFamily("Arial");
                titlePara.setSpacingAfter(400);

                // 2. Agrupar datos
                Map<String, List<MisaComunitariaDTO>> agrupados = misas.stream()
                        .collect(Collectors.groupingBy(m -> m.categoria().toUpperCase()));

                // 3. Generar secciones
                for (String cat : CATEGORIAS_ORDENADAS) {
                    XWPFParagraph catPara = document.createParagraph();
                    XWPFRun catRun = catPara.createRun();
                    catRun.setText(cat);
                    catRun.setBold(true);
                    catRun.setFontSize(14);
                    catRun.setFontFamily("Arial");

                    List<MisaComunitariaDTO> intenciones = agrupados.getOrDefault(cat, List.of());
                    
                    if (intenciones.isEmpty()) {
                        for (int i = 0; i < 4; i++) {
                            document.createParagraph();
                        }
                    } else {
                        for (MisaComunitariaDTO m : intenciones) {
                            XWPFParagraph p = document.createParagraph();
                            XWPFRun r = p.createRun();
                            r.setText("• " + m.intencion().toUpperCase());
                            r.setFontSize(13);
                            r.setFontFamily("Arial");
                        }
                        document.createParagraph();
                    }
                }

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                document.write(out);
                return out.toByteArray();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}