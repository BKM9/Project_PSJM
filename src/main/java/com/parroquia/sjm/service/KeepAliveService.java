package com.parroquia.sjm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KeepAliveService {

    private static final Logger log = LoggerFactory.getLogger(KeepAliveService.class);
    private final WebClient webClient;

    @Value("${RENDER_EXTERNAL_URL:https://project-psjm.onrender.com/api/v1/psjm/health}")
    private String selfUrl;

    public KeepAliveService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // Se ejecuta cada 10 minutos (600,000 milisegundos)
    // El límite de Render Free suele ser 15 min de inactividad.
    @Scheduled(fixedRate = 600000)
    public void keepAlive() {
        log.info("KeepAlive: Enviando ping a {} para evitar que Render se duerma...", selfUrl);
        
        webClient.get()
                .uri(selfUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                    success -> log.debug("KeepAlive: Ping exitoso. Respuesta: {}", success),
                    error -> log.error("KeepAlive: Error en el ping: {}. Verifica que la URL sea correcta.", error.getMessage())
                );
    }
}