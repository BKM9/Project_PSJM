package com.parroquia.sjm.model.response.Horario;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HorarioMisas(
        String dia,
        List<Horario> misas)
{}

