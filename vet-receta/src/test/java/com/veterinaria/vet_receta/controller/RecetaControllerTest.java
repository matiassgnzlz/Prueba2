package com.veterinaria.vet_receta.controller;

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

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veterinaria.vet_receta.dto.RecetaDTO;
import com.veterinaria.vet_receta.model.Receta;
import com.veterinaria.vet_receta.service.RecetaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RecetaController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecetaService recetaService;

    @Test
    void deberiaListarRecetas() throws Exception {
        Receta receta = new Receta(1L, 1L, "Firulais", "Antibiótico", LocalDateTime.of(2026, 5, 1, 10, 0), "Firma A");
        when(recetaService.listar()).thenReturn(List.of(receta));

        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nom_mascota").value("Firulais"));
    }

    @Test
    void deberiaObtenerRecetaPorId() throws Exception {
        Receta receta = new Receta(1L, 2L, "Luna", "Vacuna", LocalDateTime.of(2026, 5, 2, 11, 0), "Firma B");
        when(recetaService.buscarRecetamodel(1L)).thenReturn(receta);

        mockMvc.perform(get("/api/recetas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom_mascota").value("Luna"));
    }

    @Test
    void deberiaCrearReceta() throws Exception {
        Receta guardada = new Receta(1L, 1L, "Milo", "Reposo", LocalDateTime.of(2026, 5, 3, 12, 0), "Firma C");
        when(recetaService.guardar(any(Receta.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/recetas")
                        .param("dueno_id", "1")
                        .param("nom_mascota", "Milo")
                        .param("descripcion", "Reposo")
                        .param("fec_emision", "2026-05-03T12:00:00")
                        .param("firma_digital", "Firma C"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom_mascota").value("Milo"));
    }

    @Test
    void deberiaActualizarReceta() throws Exception {
        Receta actualizada = new Receta(1L, 2L, "Nueva", "Nuevo", LocalDateTime.of(2026, 5, 5, 9, 0), "Firma Nueva");
        when(recetaService.actualizar(eq(1L), any(Receta.class))).thenReturn(actualizada);

        RecetaDTO dto = new RecetaDTO(1L, 2L, "Nueva", "Nuevo", LocalDateTime.of(2026, 5, 5, 9, 0), "Firma Nueva");

        mockMvc.perform(put("/api/recetas/modificar/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom_mascota").value("Nueva"));
    }

    @Test
    void deberiaEliminarReceta() throws Exception {
        doNothing().when(recetaService).eliminar(1L);

        mockMvc.perform(delete("/api/recetas/1"))
                .andExpect(status().isNoContent());
    }
}
