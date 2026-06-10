package com.parroquia.sjm.service;

import com.parroquia.sjm.model.dto.UsuarioDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioService {


    public Flux<UsuarioDTO> obtenerTodosLosUsuarios() {
        // TODO: Reemplazar con llamada a Supabase
        return Flux.empty();
    }

}

