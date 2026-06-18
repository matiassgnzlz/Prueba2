package com.example.mascota.controller;

import com.example.mascota.dto.MascotaDTO;
import com.example.mascota.dto.MascotaResponse;
import com.example.mascota.service.MascotaService;
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
class MascotaControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String TOKEN_SIMULADO = "Bearer token-de-prueba";

    @Mock
    private MascotaService service;

    @InjectMocks
    private MascotaController mascotaController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
    }

    @Test
    void debeListarMascotas() throws Exception {
        MascotaResponse mascota = new MascotaResponse();
        mascota.setId(1L);
        mascota.setNombre("Chloe");
        mascota.setEspecie("Perro");
        mascota.setRaza("Westie");

        // Simulamos el comportamiento pasando cualquier String como token
        when(service.listar(any(String.class))).thenReturn(List.of(mascota));

        mockMvc.perform(get("/api/mascotas")
                .header("Authorization", TOKEN_SIMULADO)) // Enviamos el Header obligatorio
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Chloe"));
    }

    @Test
    void debeObtenerMascotaPorId() throws Exception {
        MascotaResponse mascota = new MascotaResponse();
        mascota.setId(1L);
        mascota.setNombre("Luna");

        when(service.obtener(eq(1L), any(String.class))).thenReturn(mascota);

        mockMvc.perform(get("/api/mascotas/1")
                .header("Authorization", TOKEN_SIMULADO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Mascota obtenido")) // Coincide con tu "Mascota obtenido"
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Luna"));
    }

@Test
    void debeCrearMascota() throws Exception {
        // LLENAMOS EL DTO CON TODOS LOS CAMPOS PARA EVITAR EL ERROR 400
        MascotaDTO dto = new MascotaDTO();
        dto.setNombre("Tom");
        dto.setEspecie("Gato");
        dto.setRaza("Siames");
        dto.setDuenoId(1L); // Asegúrate de que este método exista en tu MascotaDTO

        MascotaResponse creado = new MascotaResponse();
        creado.setId(1L);
        creado.setNombre("Tom");

        when(service.crear(any(MascotaDTO.class), any(String.class))).thenReturn(creado);

        mockMvc.perform(post("/api/mascotas")
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Mascota creado"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

@Test
    void debeActualizarMascota() throws Exception {
        // LLENAMOS EL DTO CON TODOS LOS CAMPOS PARA EVITAR EL ERROR 400
        MascotaDTO dto = new MascotaDTO();
        dto.setNombre("Mascota Actualizada");
        dto.setEspecie("Perro");
        dto.setRaza("Pug");
        dto.setDuenoId(1L);

        MascotaResponse actualizado = new MascotaResponse();
        actualizado.setId(1L);
        actualizado.setNombre("Mascota Actualizada");

        when(service.actualizar(any(), any(), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/mascotas/1")
                .header("Authorization", TOKEN_SIMULADO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Mascota actualizado"))
                .andExpect(jsonPath("$.data.nombre").value("Mascota Actualizada"));
    }

    @Test
    void debeEliminarMascota() throws Exception {
        doNothing().when(service).eliminar(eq(1L));

        // El método eliminar no recibe @RequestHeader en tu controlador, llamamos directo
        mockMvc.perform(delete("/api/mascotas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Mascota eliminado"));
    }
}