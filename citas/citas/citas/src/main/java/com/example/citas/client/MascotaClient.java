package com.example.citas.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.citas.dto.MascotaResponse;
import com.example.citas.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MascotaClient {

    private final WebClient webClient;

    public MascotaResponse obtenerMascota(Long id, String token) {
        try {
            String tokenLimpio = (token != null && token.startsWith("Bearer ")) ? token : "Bearer " + token;

            ParameterizedTypeReference<ApiResponse<MascotaResponse>> typeRef = 
                new ParameterizedTypeReference<ApiResponse<MascotaResponse>>() {};

            ApiResponse<MascotaResponse> response = webClient.get()
                    .uri("http://localhost:8084/api/v1/mascota/{id}", id)
                    .header("Authorization", tokenLimpio)
                    .retrieve()
                    .bodyToMono(typeRef) 
                    .block();

            if (response != null && response.isSuccess() && response.getData() != null) {
                return response.getData();
            }
            
            return null;

        } catch (Exception e) {
    System.out.println("LOG CRÍTICO: Error al llamar a Mascotas -> " + e.getMessage());
    e.printStackTrace(); 
    return null;
}
    }
}