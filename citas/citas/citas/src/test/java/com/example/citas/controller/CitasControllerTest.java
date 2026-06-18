package com.example.citas.controller;

import com.example.citas.dto.CitasDTO;
import com.example.citas.dto.CitasResponse;
import com.example.citas.service.CitasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CitasController.class)
public class CitasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CitasService service;

    @MockBean
    private com.example.citas.security.JwtUtil jwtUtil;

    private final String TOKEN_SIMULADO = "Bearer token-de-prueba";

    @Test
    @WithMockUser(roles = "USER")
    void debeObtenerCitaPorId() throws Exception {
        // Arrange
        CitasResponse responseDto = new CitasResponse();
        responseDto.setId(1L);
        responseDto.setMotivo("Vacunacion");

        when(service.obtenerPorId(eq(1L), any(String.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/citas/1")
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isOk())
                // Validaciones con la estructura exacta de tu ApiResponse
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cita encontrada"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.motivo").value("Vacunacion"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void debeAgendarCita() throws Exception {
        // Arrange
        CitasDTO dto = new CitasDTO();
        dto.setFechaHora(LocalDateTime.now());
        dto.setMotivo("Revision");
        dto.setTipo("General");
        dto.setMascotaId(1L);
        dto.setVeterinarioId(1L);
        dto.setDuenoId(1L);

        CitasResponse responseDto = new CitasResponse();
        responseDto.setId(1L);
        responseDto.setMotivo("Revision");

        when(service.agendarCitas(any(CitasDTO.class), any(String.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/citas/agendar")
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cita agendada con éxito"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.motivo").value("Revision"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void debeRetornarListaDeCitasVacia() throws Exception {
        // Arrange
        when(service.obtenerTodas(any(String.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/citas")
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Lista de citas obtenida"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }
}