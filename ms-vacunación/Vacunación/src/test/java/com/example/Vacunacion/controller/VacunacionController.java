package com.example.Vacunacion.controller;

import com.example.Vacunacion.model.Vacunacion;
import com.example.Vacunacion.service.VacunacionService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VacunacionControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VacunacionService service;

    @InjectMocks
    private VacunacionController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void debeGuardarVacunacion() throws Exception {

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setId(1L);

        when(service.guardar(any(Vacunacion.class))).thenReturn(vacunacion);

        mockMvc.perform(post("/api/vacunaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacunacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void debeListarVacunaciones() throws Exception {

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setId(1L);

        when(service.listar()).thenReturn(List.of(vacunacion));

        mockMvc.perform(get("/api/vacunaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void debeBuscarVacunacionPorId() throws Exception {

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setId(1L);

        when(service.buscarPorId(1L)).thenReturn(vacunacion);

        mockMvc.perform(get("/api/vacunaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}