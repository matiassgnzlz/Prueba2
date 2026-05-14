package com.example.citas.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.citas.dto.DuenoResponse;
import com.example.citas.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DuenoClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8083/api/dueno/";

    public DuenoResponse obtenerDueno(Long id, String token) {

        ApiResponse<DuenoResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<DuenoResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}