package com.example.dueno.controller;

import com.example.dueno.dto.DuenoDTO;
import com.example.dueno.model.Dueno;
import com.example.dueno.service.DuenoService;
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
class DuenoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private DuenoService service;

    @InjectMocks
    private DuenoController duenoController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(duenoController).build();
    }

    @Test
    void debeListarDuenos() throws Exception {
        Dueno dueno = new Dueno();
        dueno.setId(1L);
        dueno.setNombre("Alberto Hurtado");
        dueno.setContacto("+56912345678");
        dueno.setRut("11.111.111-1");

        List<Dueno> duenos = List.of(dueno);
        when(service.listar()).thenReturn(duenos);

        mockMvc.perform(get("/api/v1/dueno"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado obtenido"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Alberto Hurtado"));
    }

    @Test
    void debeObtenerDuenoPorId() throws Exception {
        Dueno dueno = new Dueno();
        dueno.setId(1L);
        dueno.setNombre("María Cifuentes");
        dueno.setContacto("+56987654321");
        dueno.setRut("22.222.222-2");

        when(service.obtener(eq(1L))).thenReturn(dueno);

        mockMvc.perform(get("/api/v1/dueno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Dueno obtenido"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("María Cifuentes"));
    }

    @Test
    void debeCrearDueno() throws Exception {
        DuenoDTO dto = new DuenoDTO();
        dto.setNombre("Carlos Prat");
        dto.setContacto("+56911112222");
        dto.setRut("33.333.333-3");

        Dueno creado = new Dueno();
        creado.setId(1L);
        creado.setNombre("Carlos Prat");

        when(service.crear(any(DuenoDTO.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/dueno")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Dueno creado"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

@Test
    void debeActualizarDueno() throws Exception {
        DuenoDTO dto = new DuenoDTO();
        dto.setNombre("Actualizado");
        dto.setContacto("+56999999999");
        dto.setRut("99.999.999-9");

        Dueno actualizado = new Dueno();
        actualizado.setId(1L);
        actualizado.setNombre("Actualizado");

        when(service.actualizar(any(), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/dueno/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Dueno actualizado"))
                .andExpect(jsonPath("$.data.nombre").value("Actualizado"));
    }

    @Test
    void debeEliminarDueno() throws Exception {
        doNothing().when(service).eliminar(eq(1L));

        mockMvc.perform(delete("/api/v1/dueno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Dueno eliminado"));
    }
}