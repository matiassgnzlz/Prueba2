package com.veterinaria.vet_laboratorio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veterinaria.vet_laboratorio.dto.LaboratorioDTO;
import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.service.LaboratorioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LaboratorioController.class)
@AutoConfigureMockMvc(addFilters = false)
class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LaboratorioService laboratorioService;

    @Test
    void deberiaListarLaboratorios() throws Exception {
        Laboratorio laboratorio = new Laboratorio(1L, "Lab Central", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null);
        when(laboratorioService.listar()).thenReturn(List.of(laboratorio));

        mockMvc.perform(get("/api/laboratorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Laboratorios obtenidos"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre_laboratorio").value("Lab Central"));
    }

    @Test
    void deberiaObtenerLaboratorioPorId() throws Exception {
        Laboratorio laboratorio = new Laboratorio(1L, "Lab Norte", "Luis", "+56922222222", "Concepción", LocalDate.of(2025, 2, 15), null);
        when(laboratorioService.buscarLaboratorioModel(1L)).thenReturn(laboratorio);

        mockMvc.perform(get("/api/laboratorios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Laboratorio encontrado"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre_laboratorio").value("Lab Norte"));
    }

    @Test
    void deberiaCrearLaboratorio() throws Exception {
        LaboratorioDTO dto = new LaboratorioDTO();
        dto.setNombre_laboratorio("Lab Sur");
        dto.setPersona_a_cargo("María");
        dto.setTelefono("+56933333333");
        dto.setDireccion("Valparaíso");
        dto.setFecha_registro(LocalDate.of(2025, 3, 20));

        Laboratorio guardado = new Laboratorio(1L, "Lab Sur", "María", "+56933333333", "Valparaíso", LocalDate.of(2025, 3, 20), null);
        when(laboratorioService.guardar(any(Laboratorio.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Laboratorio creado"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre_laboratorio").value("Lab Sur"));
    }

    @Test
    void deberiaActualizarLaboratorio() throws Exception {
        LaboratorioDTO dto = new LaboratorioDTO();
        dto.setNombre_laboratorio("Lab Actualizado");
        dto.setPersona_a_cargo("Camila");
        dto.setTelefono("+56944444444");
        dto.setDireccion("Temuco");
        dto.setFecha_registro(LocalDate.of(2025, 4, 5));

        Laboratorio actualizado = new Laboratorio(1L, "Lab Actualizado", "Camila", "+56944444444", "Temuco", LocalDate.of(2025, 4, 5), null);
        when(laboratorioService.actualizar(eq(1L), any(Laboratorio.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/laboratorios/modificar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Laboratorio actualizado"))
                .andExpect(jsonPath("$.data.nombre_laboratorio").value("Lab Actualizado"));
    }

    @Test
    void deberiaEliminarLaboratorio() throws Exception {
        doNothing().when(laboratorioService).eliminar(1L);

        mockMvc.perform(delete("/api/laboratorios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Laboratorio eliminado"));
    }
}
