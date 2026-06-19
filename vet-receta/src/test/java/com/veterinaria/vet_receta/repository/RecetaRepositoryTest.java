package com.veterinaria.vet_receta.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.veterinaria.vet_receta.model.Receta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class RecetaRepositoryTest {

    @Autowired
    private RecetaRepository repository;

    @Test
    void deberiaGuardarReceta() {
        Receta receta = new Receta(null, 1L, "Firulais", "Antibiótico", LocalDateTime.of(2026, 5, 1, 10, 0), "Firma A");

        Receta guardada = repository.save(receta);

        assertNotNull(guardada.getId());
        assertEquals("Firulais", guardada.getNom_mascota());
    }

    @Test
    void deberiaBuscarRecetaPorId() {
        Receta receta = new Receta(null, 2L, "Luna", "Vacuna", LocalDateTime.of(2026, 5, 2, 11, 0), "Firma B");
        Receta guardada = repository.save(receta);

        Optional<Receta> resultado = repository.findById(guardada.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Luna", resultado.get().getNom_mascota());
    }

    @Test
    void deberiaListarRecetas() {
        repository.save(new Receta(null, 1L, "Milo", "Reposo", LocalDateTime.of(2026, 5, 3, 12, 0), "Firma C"));
        repository.save(new Receta(null, 2L, "Nala", "Control", LocalDateTime.of(2026, 5, 4, 13, 0), "Firma D"));

        List<Receta> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void deberiaEliminarReceta() {
        Receta receta = repository.save(new Receta(null, 3L, "Toby", "Dolor", LocalDateTime.of(2026, 5, 5, 14, 0), "Firma E"));

        repository.deleteById(receta.getId());

        Optional<Receta> resultado = repository.findById(receta.getId());
        assertFalse(resultado.isPresent());
    }
}
