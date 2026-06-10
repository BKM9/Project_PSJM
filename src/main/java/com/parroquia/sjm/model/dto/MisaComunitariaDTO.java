package com.parroquia.sjm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.math.BigDecimal;

/**
 * Record DTO para Misas Comunitarias.
 * Representa una misa comunitaria con categoria específica.
 */
public record MisaComunitariaDTO(
    Long id,
    @JsonProperty("fecha_misa")
    LocalDate fechaMisa,
    @JsonProperty("hora_misa")
    LocalTime horaMisa,
    String categoria,
    String intencion,
    BigDecimal ofrenda,
    Boolean pagado,
    String anotaciones,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}

