package com.example.HistoriaClinica.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.HistoriaClinica.dto.MascotaResponse;
import com.example.HistoriaClinica.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MascotaClient {

    private final WebClient webClient;
    // Puerto 8083 (Mascotas
    private final String BASE_URL = "http://ms-mascota:8084/api/v1/mascota/";

    public MascotaResponse obtenerMascota(Long id, String token) {
        try {
            String tokenLimpio = token.startsWith("Bearer ") ? token : "Bearer " + token;

            ApiResponse<MascotaResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", tokenLimpio)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<MascotaResponse>>() {})
                    .block();

            if (response == null || response.getData() == null) {
                throw new RuntimeException("La mascota no existe en el micro de Mascotas");
            }
            return response.getData();
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con Mascotas: " + e.getMessage());
        }
    }
}