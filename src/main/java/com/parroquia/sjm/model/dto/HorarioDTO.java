package com.parroquia.sjm.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

/**
 * Record DTO para Horarios de Eventos.
 * JsonInclude evita enviar campos nulos (como el ID al guardar) a Supabase.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HorarioDTO(
    Long id,
    LocalDate fecha,
    LocalTime hora,
    @JsonProperty("tipo_evento")
    String tipoEvento,
    String celebrante,
    String detalles,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}