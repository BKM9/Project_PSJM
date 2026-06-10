package com.parroquia.sjm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para la creación de eventos. 
 * Excluye ID y campos de auditoría para que Supabase los genere.
 */
public record HorarioCreateDTO(
    LocalDate fecha,
    LocalTime hora,
    @JsonProperty("tipo_evento")
    String tipoEvento,
    String celebrante,
    String detalles
) {
}