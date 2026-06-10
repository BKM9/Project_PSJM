package com.parroquia.sjm.controller;

import com.parroquia.sjm.model.dto.BautizosDTO;
import com.parroquia.sjm.model.dto.HorarioDTO;
import com.parroquia.sjm.model.response.Horario.HorarioMisas;
import com.parroquia.sjm.service.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/psjm")
@CrossOrigin(origins = "*")
public class ParroquiaSJMController {

    private final MisaParticularService misaParticularService;
    private final MisaComunitariaService misaComunitariaService;
    private final BautizosService bautizosService;
    private final HorarioService horarioService;
    private final UsuarioService usuarioService;

    private static final String MS_WORD_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public ParroquiaSJMController(MisaParticularService misaParticularService, 
                                  MisaComunitariaService misaComunitariaService, 
                                  BautizosService bautizosService, 
                                  HorarioService horarioService, 
                                  UsuarioService usuarioService) {
        this.misaParticularService = misaParticularService;
        this.misaComunitariaService = misaComunitariaService;
        this.bautizosService = bautizosService;
        this.horarioService = horarioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/consultar/horario")
    public Flux<HorarioMisas> consultarHorarioMisas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        return horarioService.obtenerHorarios(fechaInicio, fechaFin);
    }

    @PostMapping("/guardar/evento")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> guardarEvento(@RequestBody HorarioDTO evento) {
        return horarioService.guardarEvento(evento);
    }

    @PatchMapping("/actualizar/evento")
    public Mono<Void> actualizarEvento(@RequestParam Long id, @RequestBody Map<String, Object> cambios) {
        return horarioService.actualizarEvento(id, cambios);
    }

    @PostMapping("/guardar/bautizo")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> guardarBautizo(@RequestBody BautizosDTO bautizo) {
        return bautizosService.guardarBautizo(bautizo);
    }

    @GetMapping("/exportar/constancia/bautizo")
    public Mono<ResponseEntity<byte[]>> exportarBautizo(@RequestParam Long id) {
        return bautizosService.exportarCertificadoBautizo(id)
                .map(reporte -> {
                    String nombreArchivo = "Constancia_" + reporte.nombrePersona().replace(" ", "_") + ".docx";
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                            .contentType(MediaType.parseMediaType(MS_WORD_TYPE))
                            .body(reporte.contenido());
                });
    }

    @GetMapping("/exportar/misaparticular")
    public Mono<ResponseEntity<byte[]>> exportarMisasParticulares(@RequestParam String fecha) {
        return misaParticularService.exportarMisasParticulares(fecha)
                .map(bytes -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Misas_Particulares_" + fecha + ".docx")
                        .contentType(MediaType.parseMediaType(MS_WORD_TYPE))
                        .body(bytes));
    }

    @GetMapping("/exportar/misacomunitaria")
    public Mono<ResponseEntity<byte[]>> exportarMisaComunitaria(
            @RequestParam String fecha,
            @RequestParam String hora) {
        
        String horaCorta = hora.contains(":") ? hora.split(":")[0] : hora;

        return misaComunitariaService.exportarMisaComunitaria(fecha, hora)
                .map(bytes -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Misa_Comunitaria_" + fecha + "_" + horaCorta + ".docx")
                        .contentType(MediaType.parseMediaType(MS_WORD_TYPE))
                        .body(bytes));
    }
}