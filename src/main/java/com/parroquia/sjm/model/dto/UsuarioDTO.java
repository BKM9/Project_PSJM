package com.parroquia.sjm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * Record DTO para Usuarios de la Parroquia.
 * Representa un usuario/empleado del sistema.
 */
public record UsuarioDTO(
    Long id,
    String usuario,
    String clave,
    String nombres,
    String apellidos,
    String cargo,
    String telefono,
    Boolean activo,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}

