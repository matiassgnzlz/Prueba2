package com.example.HistoriaClinica.service;

import com.example.HistoriaClinica.client.MascotaClient;
import com.example.HistoriaClinica.dto.HistoriaClinicaDTO;
import com.example.HistoriaClinica.dto.MascotaResponse;
import com.example.HistoriaClinica.model.HistoriaClinica;
import com.example.HistoriaClinica.repository.HistoriaClinicaRepository;
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
public class HistoriaClinicaServiceTest {

    @Mock
    private HistoriaClinicaRepository repository;

    @Mock
    private MascotaClient mascotaClient;

    @InjectMocks
    private HistoriaClinicaService service; 

    private final String MOCK_TOKEN = "Bearer token-de-prueba";

    @Test
    void debeFiltrarHistoriasClinicasPorMascota() {
        // Arrange
        HistoriaClinica h1 = new HistoriaClinica();
        h1.setMascotaId(10L);
        h1.setDescripcion("Control general");

        HistoriaClinica h2 = new HistoriaClinica();
        h2.setMascotaId(20L);
        h2.setDescripcion("Cirugía");

        when(repository.findAll()).thenReturn(Arrays.asList(h1, h2));

        // Act
        List<HistoriaClinica> resultado = service.findByMascota(10L);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescripcion()).isEqualTo("Control general");
        verify(repository, times(1)).findAll();
    }

    @Test
    void debeGuardarHistoriaClinicaExitosamente() {
        // Arrange
        HistoriaClinica historia = new HistoriaClinica();
        historia.setMascotaId(10L);
        historia.setDescripcion("Vacunación Sextuple");

        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(new MascotaResponse());
        when(repository.save(any(HistoriaClinica.class))).thenReturn(historia);

        // Act
        HistoriaClinica resultado = service.guardar(historia, MOCK_TOKEN);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isEqualTo("Vacunación Sextuple");
        verify(mascotaClient, times(1)).obtenerMascota(10L, MOCK_TOKEN);
        verify(repository, times(1)).save(historia);
    }

    @Test
    void debeObtenerHistoriaCompletaConMapeoDTO() {
        // Arrange
        HistoriaClinica historia = new HistoriaClinica();
        historia.setId(1L);
        historia.setMascotaId(10L);
        historia.setDescripcion("Tratamiento digestivo");

        MascotaResponse mascotaMock = new MascotaResponse();
        mascotaMock.setId(10L);
        mascotaMock.setNombre("Firulais");

        when(repository.findById(1L)).thenReturn(Optional.of(historia));
        when(mascotaClient.obtenerMascota(10L, MOCK_TOKEN)).thenReturn(mascotaMock);

        // Act
        HistoriaClinicaDTO resultado = service.obtenerHistoriaCompleta(1L, MOCK_TOKEN);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getDescripcion()).isEqualTo("Tratamiento digestivo");
        assertThat(resultado.getMascota().getNombre()).isEqualTo("Firulais");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void debeActualizarHistoriaClinicaExitosamente() {
        // Arrange
        HistoriaClinica historiaExistente = new HistoriaClinica();
        historiaExistente.setId(1L);
        historiaExistente.setDescripcion("Antigua descripción");
        historiaExistente.setMascotaId(10L);

        HistoriaClinica nuevosDatos = new HistoriaClinica();
        nuevosDatos.setDescripcion("Nueva descripción actualizada");
        nuevosDatos.setMascotaId(10L);

        MascotaResponse mascotaMock = new MascotaResponse();
        mascotaMock.setId(10L);

        when(repository.findById(1L)).thenReturn(Optional.of(historiaExistente));
        when(repository.save(any(HistoriaClinica.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mascotaClient.obtenerMascota(10L, MOCK_TOKEN)).thenReturn(mascotaMock);

        // Act
        HistoriaClinicaDTO resultado = service.actualizar(1L, nuevosDatos, MOCK_TOKEN);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isEqualTo("Nueva descripción actualizada");
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(HistoriaClinica.class));
    }

    @Test
    void debeEliminarHistoriaClinicaCorrectamente() {
        // Arrange
        HistoriaClinica historia = new HistoriaClinica();
        historia.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(historia));
        doNothing().when(repository).delete(any(HistoriaClinica.class));

        // Act
        service.eliminar(1L, MOCK_TOKEN);

        // Assert
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(historia);
    }
}