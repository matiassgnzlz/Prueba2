package com.example.citas.service;

import com.example.citas.client.MascotaClient;
import com.example.citas.client.VeterinarioClient;
import com.example.citas.dto.CitasDTO;
import com.example.citas.dto.CitasResponse;
import com.example.citas.dto.MascotaResponse;
import com.example.citas.dto.VeterinarioResponse;
import com.example.citas.model.Citas;
import com.example.citas.repository.CitasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitasServiceTest {

    @Mock
    private CitasRepository citasRepository;

    @Mock
    private MascotaClient mascotaClient;

    @Mock
    private VeterinarioClient veterinarioClient;

    @InjectMocks
    private CitasService citasService; 

    @Test
    void debeRetornarTodasLasCitas() {
        // Arrange
        Citas cita1 = new Citas();
        cita1.setId(1L);
        cita1.setMotivo("Vacunación");
        cita1.setMascotaId(10L);
        cita1.setVeterinarioId(20L);

        when(citasRepository.findAll()).thenReturn(Arrays.asList(cita1));
        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(new MascotaResponse());
        when(veterinarioClient.obtenerVeterinario(anyLong(), anyString())).thenReturn(new VeterinarioResponse());

        // Act
        List<CitasResponse> resultado = citasService.obtenerTodas("mock-token"); 

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMotivo()).isEqualTo("Vacunación");
        verify(citasRepository, times(1)).findAll();
    }

    @Test
    void debeBuscarCitaPorIdExitosamente() {
        // Arrange
        Citas cita = new Citas();
        cita.setId(1L);
        cita.setMotivo("Revisión dental");
        cita.setMascotaId(10L);
        cita.setVeterinarioId(20L);

        when(citasRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(new MascotaResponse());
        when(veterinarioClient.obtenerVeterinario(anyLong(), anyString())).thenReturn(new VeterinarioResponse());

        // Act
        CitasResponse resultado = citasService.obtenerPorId(1L, "mock-token");

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getMotivo()).isEqualTo("Revisión dental");
        verify(citasRepository, times(1)).findById(1L);
    }

    @Test
    void debeAgendarUnaNuevaCitaExitosamente() {
        // Arrange
        CitasDTO dto = new CitasDTO();
        dto.setMotivo("Urgencia");
        dto.setMascotaId(10L);
        dto.setVeterinarioId(20L);
        dto.setDuenoId(30L);

        Citas citaGuardada = new Citas();
        citaGuardada.setId(100L); 
        citaGuardada.setMotivo("Urgencia");
        citaGuardada.setMascotaId(10L);
        citaGuardada.setVeterinarioId(20L);
        citaGuardada.setDuenoId(30L);

        when(citasRepository.save(any(Citas.class))).thenReturn(citaGuardada);
        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(new MascotaResponse());
        when(veterinarioClient.obtenerVeterinario(anyLong(), anyString())).thenReturn(new VeterinarioResponse());

        // Act
        CitasResponse resultado = citasService.agendarCitas(dto, "mock-token");

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(100L);
        assertThat(resultado.getMotivo()).isEqualTo("Urgencia");
        verify(citasRepository, times(1)).save(any(Citas.class));
    }
}