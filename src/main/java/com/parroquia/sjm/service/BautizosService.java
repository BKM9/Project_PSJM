package com.parroquia.sjm.service;

import com.parroquia.sjm.model.dto.BautizosDTO;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BautizosService {

    private final WebClient supabaseWebClient;
    private static final Logger log = LoggerFactory.getLogger(BautizosService.class);

    public record BautizoReporte(byte[] contenido, String nombrePersona) {}

    public BautizosService(WebClient supabaseWebClient) {
        this.supabaseWebClient = supabaseWebClient;
    }

    public Mono<Void> guardarBautizo(BautizosDTO bautizo) {
        return supabaseWebClient.post()
                .uri("/rest/v1/bautizos")
                .bodyValue(bautizo)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                    response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error de Supabase al guardar bautizo: {}", errorBody);
                                return Mono.error(new RuntimeException("Error al guardar bautizo en Supabase"));
                            })
                )
                .bodyToMono(Void.class);
    }

    public Mono<BautizoReporte> exportarCertificadoBautizo(Long id) {
        return supabaseWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/rest/v1/bautizos").queryParam("id", "eq." + id).build())
                .retrieve()
                .bodyToFlux(BautizosDTO.class)
                .collectList()
                .flatMap(list -> {
                    if (list.isEmpty()) return Mono.error(new RuntimeException("Bautizo no encontrado"));
                    BautizosDTO data = list.get(0);
                    return procesarMaquetaWord(data)
                            .map(bytes -> new BautizoReporte(bytes, data.nombres()));
                });
    }

    private Mono<byte[]> procesarMaquetaWord(BautizosDTO data) {
        return Mono.fromCallable(() -> {
            ClassPathResource res = new ClassPathResource("templates/maqueta_constancia_bautizo.docx");
            LocalDate hoy = LocalDate.now();

            try (InputStream is = res.getInputStream();
                 XWPFDocument doc = new XWPFDocument(is);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                Map<String, String> replacements = new HashMap<>();
                replacements.put("{{APELLIDOS}}", nvl(data.apellidos()).toUpperCase());
                replacements.put("{{NOMBRES}}", nvl(data.nombres()).toUpperCase());
                replacements.put("{{LIBRO}}", nvl(data.libro()));
                replacements.put("{{FOL}}", nvl(data.folio()));
                replacements.put("{{REG}}", nvl(data.nDeRegistro()));
                replacements.put("{{ANIO_B}}", String.valueOf(data.anioBautizo()));
                replacements.put("{{DIA_B}}", String.valueOf(data.diaBautizo()));
                replacements.put("{{MES_B}}", nvl(data.mesBautizo()).toUpperCase());
                replacements.put("{{LUGAR_N}}", nvl(data.lugarNacimiento()).toUpperCase());
                replacements.put("{{DIA_N}}", String.valueOf(data.diaNacimiento()));
                replacements.put("{{MES_N}}", nvl(data.mesNacimiento()).toUpperCase());
                replacements.put("{{ANIO_N}}", String.valueOf(data.anioNacimiento()));
                replacements.put("{{PADRE}}", nvl(data.nombrePadre()).toUpperCase());
                replacements.put("{{MADRE}}", nvl(data.nombreMadre()).toUpperCase());
                replacements.put("{{PADRINO}}", nvl(data.nombrePadrino()).toUpperCase());
                replacements.put("{{MADRINA}}", nvl(data.nombreMadrina()).toUpperCase());
                replacements.put("{{ANOTACIONES}}", nvl(data.anotaciones()).toUpperCase());

                // Fecha de impresión (D, MES, A)
                replacements.put("{{D}}", String.format("%02d", hoy.getDayOfMonth()));
                replacements.put("{{MES}}", hoy.getMonth().getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "ES")).toUpperCase());
                replacements.put("{{A}}", String.valueOf(hoy.getYear()));

                doc.getParagraphs().forEach(p -> replacePlaceholdersInParagraph(p, replacements));
                for (XWPFTable tbl : doc.getTables()) {
                    for (XWPFTableRow row : tbl.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            cell.getParagraphs().forEach(p -> replacePlaceholdersInParagraph(p, replacements));
                        }
                    }
                }

                doc.write(out);
                return out.toByteArray();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private String nvl(String val) { return val == null ? "" : val; }

    private void replacePlaceholdersInParagraph(XWPFParagraph p, Map<String, String> replacements) {
        for (XWPFRun r : p.getRuns()) {
            String text = r.getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    if (text.contains(entry.getKey())) {
                        text = text.replace(entry.getKey(), entry.getValue());
                        r.setText(text, 0);
                    }
                }
            }
        }
    }
}