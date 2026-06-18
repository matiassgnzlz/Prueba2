package com.example.citas.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.citas.dto.VeterinarioResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VeterinarioClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8085/api/v1/veterinario";

    public VeterinarioResponse obtenerVeterinario(Long id, String token) {
        try {
            String tokenLimpio = token.startsWith("Bearer ") ? token : "Bearer " + token;

            return webClient.get()
                    .uri(BASE_URL + "/{id}", id)
                    .header("Authorization", tokenLimpio)
                    .retrieve()
                    .bodyToMono(VeterinarioResponse.class) // Mapeo directo al objeto
                    .block();
        } catch (Exception e) {
            System.err.println("Error en VeterinarioClient: " + e.getMessage());
            return null;
        }
    }
}