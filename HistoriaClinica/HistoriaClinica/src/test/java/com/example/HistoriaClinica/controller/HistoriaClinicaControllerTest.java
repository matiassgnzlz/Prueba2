package com.example.HistoriaClinica.controller;

import com.example.HistoriaClinica.dto.HistoriaClinicaDTO;
import com.example.HistoriaClinica.dto.MascotaDTO;
import com.example.HistoriaClinica.model.HistoriaClinica;
import com.example.HistoriaClinica.service.HistoriaClinicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HistoriaClinicaControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String TOKEN_SIMULADO = "Bearer token-de-prueba";

    @Mock
    private HistoriaClinicaService service;

    @InjectMocks
    private HistoriaClinicaController historiaClinicaController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(historiaClinicaController).build();
    }

    @Test
    void debeGuardarHistoriaClinica() throws Exception {
        // Arrange
        HistoriaClinica historia = new HistoriaClinica();
        historia.setMascotaId(1L);
        historia.setDescripcion("Nueva historia");

        when(service.guardar(any(HistoriaClinica.class), any(String.class))).thenReturn(historia);

        // Act & Assert
        mockMvc.perform(post("/api/v1/historiaclinica")
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mascotaId").value(1))
                .andExpect(jsonPath("$.descripcion").value("Nueva historia"));
    }

    @Test
    void debeObtenerHistoriaCompleta() throws Exception {
        // Arrange
        HistoriaClinicaDTO dto = new HistoriaClinicaDTO();
        dto.setId(1L);
        dto.setDescripcion("Historia Clinica Completa");

        MascotaDTO mascotaMock = new MascotaDTO();
        mascotaMock.setId(5L);
        dto.setMascota(mascotaMock);

        when(service.obtenerHistoriaCompleta(eq(1L), any(String.class))).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/historiaclinica/1")
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Historia clínica obtenida"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.descripcion").value("Historia Clinica Completa"));
    }

    @Test
    void debeListarPorMascota() throws Exception {
        // Arrange
        HistoriaClinica historia = new HistoriaClinica();
        historia.setId(1L);
        historia.setMascotaId(5L);

        when(service.findByMascota(eq(5L))).thenReturn(List.of(historia));

        // Act & Assert
        mockMvc.perform(get("/api/v1/historiaclinica/mascota/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mascotaId").value(5));
    }

    @Test
    void debeActualizarHistoriaClinica() throws Exception {
        // Arrange
        HistoriaClinica historia = new HistoriaClinica();
        historia.setId(1L);
        historia.setMascotaId(1L);
        historia.setDescripcion("Cambio de diagnostico");

        HistoriaClinicaDTO dtoRespuesta = new HistoriaClinicaDTO();
        dtoRespuesta.setId(1L);
        dtoRespuesta.setDescripcion("Cambio de diagnostico");

        when(service.actualizar(any(), any(), any())).thenReturn(dtoRespuesta);

        // Act & Assert
        mockMvc.perform(put("/api/v1/historiaclinica/1")
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Cambio de diagnostico"));
    }

    @Test
    void debeEliminarHistoriaClinica() throws Exception {
        // Arrange
        doNothing().when(service).eliminar(eq(1L), any(String.class));

        // Act & Assert
        mockMvc.perform(delete("/api/v1/historiaclinica/1")
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isOk())
                .andExpect(content().string("Historia clínica eliminada correctamente"));
    }
}