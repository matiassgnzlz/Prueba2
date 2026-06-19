package com.veterinaria.vet_laboratorio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.repository.LaboratorioRepository;

@ExtendWith(MockitoExtension.class)
class LaboratorioServiceTest {

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @InjectMocks
    private LaboratorioService service;

    @Test
    void deberiaRetornarLaboratorioCuandoExiste() {
        Laboratorio laboratorio = new Laboratorio(1L, "Lab Central", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null);
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));

        Laboratorio resultado = service.buscarLaboratorioModel(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Lab Central", resultado.getNombre_laboratorio());
        assertEquals("Ana", resultado.getPersona_a_cargo());
        verify(laboratorioRepository).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoLaboratorioNoExiste() {
        when(laboratorioRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.buscarLaboratorioModel(99L));

        assertEquals("Laboratorio no encontrado", ex.getMessage());
        verify(laboratorioRepository).findById(99L);
    }

    @Test
    void deberiaRetornarListaLaboratorios() {
        Laboratorio laboratorio = new Laboratorio(1L, "Lab Norte", "Luis", "+56922222222", "Concepción", LocalDate.of(2025, 2, 15), null);
        when(laboratorioRepository.findAll()).thenReturn(List.of(laboratorio));

        List<Laboratorio> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Lab Norte", resultado.get(0).getNombre_laboratorio());
        verify(laboratorioRepository).findAll();
    }

    @Test
    void deberiaGuardarLaboratorioCorrectamente() {
        Laboratorio laboratorio = new Laboratorio(null, "Lab Sur", "Clara", "+56933333333", "Valparaíso", LocalDate.of(2025, 3, 20), null);
        Laboratorio guardado = new Laboratorio(1L, "Lab Sur", "Clara", "+56933333333", "Valparaíso", LocalDate.of(2025, 3, 20), null);
        when(laboratorioRepository.save(any(Laboratorio.class))).thenReturn(guardado);

        Laboratorio resultado = service.guardar(laboratorio);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Lab Sur", resultado.getNombre_laboratorio());
        verify(laboratorioRepository).save(any(Laboratorio.class));
    }

    @Test
    void deberiaActualizarLaboratorioCorrectamente() {
        Laboratorio existente = new Laboratorio(1L, "Lab Viejo", "Pedro", "+56944444444", "Temuco", LocalDate.of(2025, 4, 5), null);
        Laboratorio dto = new Laboratorio(null, "Lab Nuevo", "María", "+56955555555", "La Serena", LocalDate.of(2025, 5, 1), null);

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(laboratorioRepository.save(any(Laboratorio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Laboratorio resultado = service.actualizar(1L, dto);

        assertEquals(1L, resultado.getId());
        assertEquals("Lab Nuevo", resultado.getNombre_laboratorio());
        assertEquals("María", resultado.getPersona_a_cargo());
        assertEquals("+56955555555", resultado.getTelefono());
        assertEquals("La Serena", resultado.getDireccion());
        assertEquals(LocalDate.of(2025, 5, 1), resultado.getFecha_registro());
        verify(laboratorioRepository).findById(1L);
        verify(laboratorioRepository).save(existente);
    }

    @Test
    void deberiaEliminarLaboratorioCorrectamente() {
        when(laboratorioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(laboratorioRepository).deleteById(1L);

        service.eliminar(1L);

        verify(laboratorioRepository).existsById(1L);
        verify(laboratorioRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarLaboratorioInexistente() {
        when(laboratorioRepository.existsById(99L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.eliminar(99L));

        assertEquals("Laboratorio no encontrado", ex.getMessage());
        verify(laboratorioRepository).existsById(99L);
        verify(laboratorioRepository, never()).deleteById(anyLong());
    }
}
