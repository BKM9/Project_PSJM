package com.parroquia.sjm.service;

import com.parroquia.sjm.model.dto.HorarioDTO;
import com.parroquia.sjm.model.response.Horario.Horario;
import com.parroquia.sjm.model.response.Horario.HorarioMisas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final WebClient supabaseWebClient;
    private static final Logger log = LoggerFactory.getLogger(HorarioService.class);

    public HorarioService(WebClient supabaseWebClient) {
        this.supabaseWebClient = supabaseWebClient;
    }

    public Flux<HorarioMisas> obtenerHorarios(String fechaInicio, String fechaFin) {
        LocalDate inicio;
        LocalDate fin;
        try {
            inicio = LocalDate.parse(fechaInicio);
            fin = LocalDate.parse(fechaFin);
        } catch (Exception e) {
            return Flux.error(new IllegalArgumentException("Fechas inválidas. Usa formato YYYY-MM-DD"));
        }

        String uri = String.format("/rest/v1/horario?fecha=gte.%s&fecha=lte.%s&order=hora.asc",
                inicio.toString(), fin.toString());

        return supabaseWebClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(HorarioDTO.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(500))
                        .filter(throwable -> throwable instanceof IOException || throwable instanceof WebClientRequestException))
                .collectList()
                .flatMapMany(list -> {
                    Map<LocalDate, java.util.List<HorarioDTO>> grouped = list.stream()
                            .collect(Collectors.groupingBy(HorarioDTO::fecha));

                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

                    java.util.List<HorarioMisas> result = grouped.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .map(entry -> {
                                LocalDate date = entry.getKey();
                                String dia = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                                dia = dia.substring(0,1).toUpperCase() + dia.substring(1);

                                java.util.List<Horario> misas = entry.getValue().stream()
                                        .map(h -> new Horario(
                                                h.id(),
                                                h.hora().format(timeFormatter),
                                                h.fecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                                h.tipoEvento(),
                                                h.celebrante()
                                        ))
                                        .collect(Collectors.toList());

                                return new HorarioMisas(dia, misas);
                            })
                            .collect(Collectors.toList());

                    return Flux.fromIterable(result);
                })
                .onErrorResume(throwable -> {
                    log.error("Error al consultar horarios en Supabase: {}", throwable.toString());
                    return Flux.error(new RuntimeException("Error al consultar servicio externo"));
                });
    }

    /**
     * Guarda un nuevo evento usando el DTO unificado.
     * Gracias a @JsonInclude en el Record, el ID nulo no se envía.
     */
    public Mono<Void> guardarEvento(HorarioDTO nuevoEvento) {
        return supabaseWebClient.post()
                .uri("/rest/v1/horario")
                .bodyValue(nuevoEvento)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                    response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error de Supabase al guardar: {}", errorBody);
                                return Mono.error(new RuntimeException("Error al guardar en Supabase"));
                            })
                )
                .bodyToMono(Void.class);
    }

    public Mono<Void> actualizarEvento(Long id, Map<String, Object> cambios) {
        return supabaseWebClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/v1/horario")
                        .queryParam("id", "eq." + id)
                        .build())
                .bodyValue(cambios)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                    response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error de Supabase al actualizar ID {}: {}", id, errorBody);
                                return Mono.error(new RuntimeException("Error al actualizar en Supabase"));
                            })
                )
                .bodyToMono(Void.class);
    }
}