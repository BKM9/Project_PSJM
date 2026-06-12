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

    // URL completa con el endpoint de salud
    @Value("${RENDER_EXTERNAL_URL:https://project-psjm.onrender.com/api/v1/psjm/health}")
    private String selfUrl;

    public KeepAliveService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Scheduled(fixedRate = 600000)
    public void keepAlive() {
        log.info("KeepAlive: Enviando ping de salud para evitar suspensión...");
        
        webClient.get()
                .uri(selfUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                    success -> log.debug("KeepAlive: Respuesta del servidor: {}", success),
                    error -> log.warn("KeepAlive: El servidor aún no responde (posible inicio en curso o 502): {}", error.getMessage())
                );
    }
}