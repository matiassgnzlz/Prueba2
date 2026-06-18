package com.example.veterinario.controller;

import com.example.veterinario.dto.VeterinarioDTO;
import com.example.veterinario.dto.VeterinarioResponse;
import com.example.veterinario.service.VeterinarioService;
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
class VeterinarioControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String TOKEN_SIMULADO = "Bearer token-de-prueba";

    private final String BASE_URL = "/api/v1/veterinario"; 

    @Mock
    private VeterinarioService service;

    @InjectMocks
    private VeterinarioController veterinarioController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(veterinarioController).build();
    }

    @Test
    void debeListarVeterinarios() throws Exception {
        VeterinarioResponse vet = new VeterinarioResponse();
        vet.setId(1L);
        vet.setNombre("Dr. House");
        vet.setEspecialidad("Diagnostico");
        vet.setMatricula("MAT-111");

        when(service.listar(any(String.class))).thenReturn(List.of(vet));

        mockMvc.perform(get(BASE_URL)
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Dr. House"));
    }

@Test
void debeObtenerVeterinarioPorId() throws Exception {
    VeterinarioResponse vet = new VeterinarioResponse();
    vet.setId(1L);
    vet.setNombre("Dr. Simi");
    vet.setEspecialidad("General");
    vet.setMatricula("MAT-222");

    when(service.obtener(eq(1L), any(String.class))).thenReturn(vet);

    mockMvc.perform(get(BASE_URL + "/1")
            .header("Authorization", TOKEN_SIMULADO))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Veterinario obtenido"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.nombre").value("Dr. Simi"));
}

    @Test
    void debeCrearVeterinario() throws Exception {
        VeterinarioDTO dto = new VeterinarioDTO();
        dto.setNombre("Carlos");
        dto.setEspecialidad("Cirugia");
        dto.setMatricula("MAT-333");

        VeterinarioResponse creado = new VeterinarioResponse();
        creado.setId(1L);
        creado.setNombre("Carlos");

        when(service.crear(any(VeterinarioDTO.class), any(String.class))).thenReturn(creado);

        mockMvc.perform(post(BASE_URL)
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    void debeActualizarVeterinario() throws Exception {
        VeterinarioDTO dto = new VeterinarioDTO();
        dto.setNombre("Modificado");
        dto.setEspecialidad("Fisiatria");
        dto.setMatricula("MAT-444");

        VeterinarioResponse actualizado = new VeterinarioResponse();
        actualizado.setId(1L);
        actualizado.setNombre("Modificado");

        when(service.actualizar(eq(1L), any(VeterinarioDTO.class), any(String.class))).thenReturn(actualizado);

        mockMvc.perform(put(BASE_URL + "/1")
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Modificado"));
    }

    @Test
    void debeEliminarVeterinario() throws Exception {
        doNothing().when(service).eliminar(eq(1L));

        mockMvc.perform(delete(BASE_URL + "/1")
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isNoContent());
    }
}