package com.parroquia.sjm.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * Record DTO para Bautizos.
 * JsonInclude evita enviar campos nulos (como el ID al guardar) a Supabase.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BautizosDTO(
    Long id,
    String apellidos,
    String nombres,
    String libro,
    String folio,
    @JsonProperty("n_de_registro")
    String nDeRegistro,
    @JsonProperty("anio_b")
    Integer anioBautizo,
    @JsonProperty("dia_b")
    Integer diaBautizo,
    @JsonProperty("mes_b")
    String mesBautizo,
    String ministro,
    @JsonProperty("lugar_nacimiento")
    String lugarNacimiento,
    @JsonProperty("dia_n")
    Integer diaNacimiento,
    @JsonProperty("mes_n")
    String mesNacimiento,
    @JsonProperty("anio_n")
    Integer anioNacimiento,
    @JsonProperty("nombre_y_apellido_padre")
    String nombrePadre,
    @JsonProperty("lugar_nacimiento_p")
    String lugarNacimientoPadre,
    @JsonProperty("nombre_y_apellido_madre")
    String nombreMadre,
    @JsonProperty("lugar_nacimiento_m")
    String lugarNacimientoMadre,
    @JsonProperty("nombre_padrino")
    String nombrePadrino,
    @JsonProperty("nombre_madrina")
    String nombreMadrina,
    String anotaciones,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}