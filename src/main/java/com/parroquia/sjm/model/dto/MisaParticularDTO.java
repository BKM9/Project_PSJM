package com.parroquia.sjm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.math.BigDecimal;

/**
 * Record DTO para Misas Particulares.
 * Representa una misa particular ofrecida por una persona/familia.
 */
public record MisaParticularDTO(
    Long id,
    @JsonProperty("fecha_misa")
    LocalDate fechaMisa,
    @JsonProperty("hora_misa")
    LocalTime horaMisa,
    String intencion,
    String ofrece,
    BigDecimal ofrenda,
    Boolean pagado,
    String anotaciones,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}

