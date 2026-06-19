package com.veterinaria.vet_laboratorio.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.veterinaria.vet_laboratorio.model.Laboratorio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class LaboratorioRepositoryTest {

    @Autowired
    private LaboratorioRepository repository;

    @Test
    void deberiaGuardarLaboratorio() {
        Laboratorio laboratorio = new Laboratorio(null, "Lab Central", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null);

        Laboratorio guardado = repository.save(laboratorio);

        assertNotNull(guardado.getId());
        assertEquals("Lab Central", guardado.getNombre_laboratorio());
        assertEquals("Ana", guardado.getPersona_a_cargo());
    }

    @Test
    void deberiaBuscarLaboratorioPorId() {
        Laboratorio laboratorio = new Laboratorio(null, "Lab Norte", "Luis", "+56922222222", "Concepción", LocalDate.of(2025, 2, 15), null);
        Laboratorio guardado = repository.save(laboratorio);

        Optional<Laboratorio> resultado = repository.findById(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Lab Norte", resultado.get().getNombre_laboratorio());
    }

    @Test
    void deberiaListarLaboratorios() {
        repository.save(new Laboratorio(null, "Lab A", "A", "+5691", "Santiago", LocalDate.of(2025, 1, 10), null));
        repository.save(new Laboratorio(null, "Lab B", "B", "+5692", "Valparaíso", LocalDate.of(2025, 2, 10), null));

        List<Laboratorio> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void deberiaBuscarPorPersonaACargo() {
        repository.save(new Laboratorio(null, "Lab A", "Ana", "+5691", "Santiago", LocalDate.of(2025, 1, 10), null));
        repository.save(new Laboratorio(null, "Lab B", "Luis", "+5692", "Valparaíso", LocalDate.of(2025, 2, 10), null));

        List<Laboratorio> resultado = repository.findByPersona_a_cargo("Ana");

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getPersona_a_cargo());
    }

    @Test
    void deberiaEliminarLaboratorio() {
        Laboratorio laboratorio = new Laboratorio(null, "Lab Eliminar", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null);
        Laboratorio guardado = repository.save(laboratorio);

        repository.deleteById(guardado.getId());

        Optional<Laboratorio> resultado = repository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}
