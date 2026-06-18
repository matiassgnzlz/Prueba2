package com.example.mascota.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.ParameterizedTypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.mascota.dto.DuenoResponse;
import com.example.mascota.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DuenoClient {

    private static final Logger log = LoggerFactory.getLogger(DuenoClient.class);
    private final WebClient webClient;

    private final String BASE_URL = "http://ms-dueno:8083/api/v1/dueno/";

    public DuenoResponse obtenerDueno(Long id, String token) {
        log.info("Iniciando llamada externa a ms-dueno para ID: {}", id);
        
        try {
            ApiResponse<DuenoResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<DuenoResponse>>() {})
                    .block();

            if (response != null && response.isSuccess()) {
                log.info("Datos del dueño obtenidos exitosamente para ID: {}", id);
                return response.getData();
            }
            
            log.warn("ms-dueno respondió, pero la operación no fue exitosa. Mensaje: {}", 
                     response != null ? response.getMessage() : "Respuesta nula");
            return null;

        } catch (WebClientResponseException e) {
            log.error("Error HTTP al comunicarse con ms-dueno. Código: {}, Cuerpo: {}", 
                      e.getStatusCode(), e.getResponseBodyAsString());
            return null;
                     
        } catch (Exception e) {
            log.error("Error crítico de infraestructura en la comunicación con ms-dueno: {}", e.getMessage());
            return null;
        }
    }
}