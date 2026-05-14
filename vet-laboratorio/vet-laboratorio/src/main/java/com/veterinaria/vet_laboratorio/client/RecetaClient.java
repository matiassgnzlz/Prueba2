package com.veterinaria.vet_laboratorio.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.veterinaria.vet_laboratorio.dto.ApiResponse;
import com.veterinaria.vet_laboratorio.dto.RecetaResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecetaClient {
    private final WebClient webClient;
 
    private final String BASE_URL = "http://localhost:8082/api/recetas/";
 
    public RecetaResponse obtenerReceta(Long id, String token) {
 
        ApiResponse<RecetaResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<RecetaResponse>>() {})
                .block();
 
        return response != null ? response.getData() : null;
    }

}
