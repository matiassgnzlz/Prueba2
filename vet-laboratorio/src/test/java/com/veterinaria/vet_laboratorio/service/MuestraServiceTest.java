package com.veterinaria.vet_laboratorio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.model.Muestra;
import com.veterinaria.vet_laboratorio.repository.LaboratorioRepository;
import com.veterinaria.vet_laboratorio.repository.MuestraRepository;

@ExtendWith(MockitoExtension.class)
class MuestraServiceTest {

    @Mock
    private MuestraRepository muestraRepository;

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @InjectMocks
    private MuestraService service;

    @Test
    void deberiaRetornarMuestraCuandoExiste() {
        Muestra muestra = new Muestra(1L, "Sangre", "Hemograma", LocalDate.of(2026, 5, 1), "PROCESADA", "OK", LocalDate.of(2026, 5, 3), null);
        when(muestraRepository.findById(1L)).thenReturn(Optional.of(muestra));

        Muestra resultado = service.buscarMuestraModel(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sangre", resultado.getTipo_muestra());
        verify(muestraRepository).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoMuestraNoExiste() {
        when(muestraRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.buscarMuestraModel(99L));

        verify(muestraRepository).findById(99L);
    }

    @Test
    void deberiaRetornarListaDeMuestras() {
        Muestra muestra = new Muestra(1L, "Orina", "Urianálisis", LocalDate.of(2026, 5, 2), "PROCESADA", "Normal", LocalDate.of(2026, 5, 4), null);
        when(muestraRepository.findAll()).thenReturn(List.of(muestra));

        List<Muestra> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Orina", resultado.get(0).getTipo_muestra());
        verify(muestraRepository).findAll();
    }

    @Test
    void deberiaRetornarMuestrasPorLaboratorio() {
        when(muestraRepository.findByLaboratorioId(1L)).thenReturn(List.of(new Muestra()));

        List<Muestra> resultado = service.listarPorLaboratorio(1L);

        assertEquals(1, resultado.size());
        verify(muestraRepository).findByLaboratorioId(1L);
    }

    @Test
    void deberiaRetornarMuestrasPorEstado() {
        when(muestraRepository.findByEstado("PROCESADA")).thenReturn(List.of(new Muestra()));

        List<Muestra> resultado = service.listarPorEstado("PROCESADA");

        assertEquals(1, resultado.size());
        verify(muestraRepository).findByEstado("PROCESADA");
    }

    @Test
    void deberiaRetornarMuestrasPorTipo() {
        when(muestraRepository.findByTipo_muestra("Sangre")).thenReturn(List.of(new Muestra()));

        List<Muestra> resultado = service.listarPorTipo("Sangre");

        assertEquals(1, resultado.size());
        verify(muestraRepository).findByTipo_muestra("Sangre");
    }

    @Test
    void deberiaGuardarMuestraCorrectamente() {
        Laboratorio laboratorio = new Laboratorio(1L, "Lab Central", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null);
        Muestra muestra = new Muestra(null, "Sangre", "Hemograma", LocalDate.of(2026, 5, 1), "PENDIENTE", null, null, null);

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(muestraRepository.save(any(Muestra.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Muestra resultado = service.guardar(muestra, 1L);

        assertNotNull(resultado);
        assertEquals(laboratorio, resultado.getLaboratorio());
        assertEquals("Sangre", resultado.getTipo_muestra());
        verify(laboratorioRepository).findById(1L);
        verify(muestraRepository).save(muestra);
    }

    @Test
    void deberiaActualizarMuestraCorrectamente() {
        Laboratorio laboratorio = new Laboratorio(1L, "Lab Central", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null);
        Muestra existente = new Muestra(1L, "Orina", "Texto viejo", LocalDate.of(2026, 5, 1), "PENDIENTE", null, null, null);
        Muestra nuevaInfo = new Muestra(null, "Piel", "Nuevo texto", LocalDate.of(2026, 5, 2), "PROCESADA", "Resultado", LocalDate.of(2026, 5, 4), null);

        when(muestraRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(muestraRepository.save(any(Muestra.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Muestra resultado = service.actualizar(1L, nuevaInfo, 1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Piel", resultado.getTipo_muestra());
        assertEquals("Nuevo texto", resultado.getDescripcion());
        assertEquals(laboratorio, resultado.getLaboratorio());
        verify(muestraRepository).findById(1L);
        verify(laboratorioRepository).findById(1L);
        verify(muestraRepository).save(existente);
    }

    @Test
    void deberiaEliminarMuestraCorrectamente() {
        doNothing().when(muestraRepository).deleteById(1L);

        service.eliminar(1L);

        verify(muestraRepository).deleteById(1L);
    }
}
