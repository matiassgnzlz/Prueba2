package com.veterinaria.vet_receta.service;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.veterinaria.vet_receta.model.Receta;
import com.veterinaria.vet_receta.repository.RecetaRepository;

@ExtendWith(MockitoExtension.class)
class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private RecetaService service;

    @Test
    void deberiaRetornarRecetaCuandoExiste() {
        Receta receta = new Receta(1L, 1L, "Firulais", "Antibiótico", LocalDateTime.of(2026, 5, 1, 10, 0), "Firma A");
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));

        Receta resultado = service.buscarRecetamodel(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Firulais", resultado.getNom_mascota());
        verify(recetaRepository).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoRecetaNoExiste() {
        when(recetaRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.buscarRecetamodel(99L));

        assertEquals("Receta no encontrada", ex.getMessage());
        verify(recetaRepository).findById(99L);
    }

    @Test
    void deberiaRetornarListaRecetas() {
        Receta receta = new Receta(1L, 2L, "Luna", "Vacuna", LocalDateTime.of(2026, 5, 2, 11, 0), "Firma B");
        when(recetaRepository.findAll()).thenReturn(List.of(receta));

        List<Receta> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Luna", resultado.get(0).getNom_mascota());
        verify(recetaRepository).findAll();
    }

    @Test
    void deberiaGuardarRecetaCorrectamente() {
        Receta receta = new Receta(null, 1L, "Milo", "Reposo", LocalDateTime.of(2026, 5, 3, 12, 0), "Firma C");
        Receta guardada = new Receta(1L, 1L, "Milo", "Reposo", LocalDateTime.of(2026, 5, 3, 12, 0), "Firma C");
        when(recetaRepository.save(any(Receta.class))).thenReturn(guardada);

        Receta resultado = service.guardar(receta);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Milo", resultado.getNom_mascota());
        verify(recetaRepository).save(any(Receta.class));
    }

    @Test
    void deberiaActualizarRecetaCorrectamente() {
        Receta existente = new Receta(1L, 1L, "Vieja", "Viejo", LocalDateTime.of(2026, 5, 1, 8, 0), "Firma Vieja");
        Receta nueva = new Receta(null, 2L, "Nueva", "Nuevo", LocalDateTime.of(2026, 5, 5, 9, 0), "Firma Nueva");

        when(recetaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(recetaRepository.save(any(Receta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Receta resultado = service.actualizar(1L, nueva);

        assertEquals(1L, resultado.getId());
        assertEquals(2L, resultado.getDueno_id());
        assertEquals("Nueva", resultado.getNom_mascota());
        assertEquals("Nuevo", resultado.getDescripcion());
        verify(recetaRepository).findById(1L);
        verify(recetaRepository).save(existente);
    }

    @Test
    void deberiaEliminarRecetaCorrectamente() {
        when(recetaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(recetaRepository).deleteById(1L);

        service.eliminar(1L);

        verify(recetaRepository).existsById(1L);
        verify(recetaRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarRecetaInexistente() {
        when(recetaRepository.existsById(99L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.eliminar(99L));

        assertEquals("Receta no encontrada", ex.getMessage());
        verify(recetaRepository).existsById(99L);
        verify(recetaRepository, never()).deleteById(anyLong());
    }
}
