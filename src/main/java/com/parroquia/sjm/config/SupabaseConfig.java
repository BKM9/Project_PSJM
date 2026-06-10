package com.parroquia.sjm.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class SupabaseConfig {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Bean
    public WebClient supabaseWebClient(WebClient.Builder builder) {
        // Configure a small connection pool and sensible timeouts to avoid resource exhaustion
        ConnectionProvider provider = ConnectionProvider.builder("supabasePool")
                .maxConnections(10)
                .pendingAcquireTimeout(Duration.ofSeconds(2))
                .build();

        HttpClient httpClient = HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(15))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(15))
                        .addHandlerLast(new WriteTimeoutHandler(15)));

        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(supabaseUrl)
                .defaultHeader("apikey", supabaseKey)
                .defaultHeader("Authorization", "Bearer " + supabaseKey)
                .build();
    }
}

