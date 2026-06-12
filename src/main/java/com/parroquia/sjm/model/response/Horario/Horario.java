package com.parroquia.sjm.model.response.Horario;

/**
 * Representa una misa/horario que se expone al cliente.
 * Incluye el `id` para futuras operaciones (actualizar/eliminar).
 */
public record Horario (
        Long id,
        String hora,
        String dia,
        String tipoEvento,
        String celebrante,
        String detalles
){ }
