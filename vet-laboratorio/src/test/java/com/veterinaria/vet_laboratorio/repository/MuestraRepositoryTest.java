package com.veterinaria.vet_laboratorio.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.model.Muestra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class MuestraRepositoryTest {

    @Autowired
    private MuestraRepository repository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Test
    void deberiaGuardarMuestra() {
        Laboratorio laboratorio = laboratorioRepository.save(new Laboratorio(null, "Lab Central", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null));
        Muestra muestra = new Muestra(null, "Sangre", "Hemograma", LocalDate.of(2026, 5, 1), "PROCESADA", "Normal", LocalDate.of(2026, 5, 3), laboratorio);

        Muestra guardada = repository.save(muestra);

        assertNotNull(guardada.getId());
        assertEquals("Sangre", guardada.getTipo_muestra());
    }

    @Test
    void deberiaBuscarMuestraPorId() {
        Laboratorio laboratorio = laboratorioRepository.save(new Laboratorio(null, "Lab Norte", "Luis", "+56922222222", "Concepción", LocalDate.of(2025, 2, 15), null));
        Muestra muestra = repository.save(new Muestra(null, "Orina", "Urianálisis", LocalDate.of(2026, 5, 2), "PROCESADA", "Normal", LocalDate.of(2026, 5, 4), laboratorio));

        Optional<Muestra> resultado = repository.findById(muestra.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Orina", resultado.get().getTipo_muestra());
    }

    @Test
    void deberiaListarMuestras() {
        Laboratorio laboratorio = laboratorioRepository.save(new Laboratorio(null, "Lab A", "Ana", "+5691", "Santiago", LocalDate.of(2025, 1, 10), null));
        repository.save(new Muestra(null, "Sangre", "A", LocalDate.of(2026, 5, 1), "PROCESADA", "Normal", LocalDate.of(2026, 5, 3), laboratorio));
        repository.save(new Muestra(null, "Orina", "B", LocalDate.of(2026, 5, 2), "PENDIENTE", null, null, laboratorio));

        List<Muestra> resultado = repository.findAll();

        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void deberiaFiltrarPorLaboratorio() {
        Laboratorio lab1 = laboratorioRepository.save(new Laboratorio(null, "Lab A", "Ana", "+5691", "Santiago", LocalDate.of(2025, 1, 10), null));
        Laboratorio lab2 = laboratorioRepository.save(new Laboratorio(null, "Lab B", "Luis", "+5692", "Valparaíso", LocalDate.of(2025, 2, 10), null));
        repository.save(new Muestra(null, "Sangre", "A", LocalDate.of(2026, 5, 1), "PROCESADA", "Normal", LocalDate.of(2026, 5, 3), lab1));
        repository.save(new Muestra(null, "Orina", "B", LocalDate.of(2026, 5, 2), "PENDIENTE", null, null, lab2));

        List<Muestra> resultado = repository.findByLaboratorioId(lab1.getId());

        assertEquals(1, resultado.size());
        assertEquals(lab1.getId(), resultado.get(0).getLaboratorio().getId());
    }

    @Test
    void deberiaFiltrarPorEstado() {
        Laboratorio laboratorio = laboratorioRepository.save(new Laboratorio(null, "Lab A", "Ana", "+5691", "Santiago", LocalDate.of(2025, 1, 10), null));
        repository.save(new Muestra(null, "Sangre", "A", LocalDate.of(2026, 5, 1), "PROCESADA", "Normal", LocalDate.of(2026, 5, 3), laboratorio));
        repository.save(new Muestra(null, "Orina", "B", LocalDate.of(2026, 5, 2), "PENDIENTE", null, null, laboratorio));

        List<Muestra> resultado = repository.findByEstado("PROCESADA");

        assertEquals(1, resultado.size());
        assertEquals("PROCESADA", resultado.get(0).getEstado());
    }

    @Test
    void deberiaFiltrarPorTipo() {
        Laboratorio laboratorio = laboratorioRepository.save(new Laboratorio(null, "Lab A", "Ana", "+5691", "Santiago", LocalDate.of(2025, 1, 10), null));
        repository.save(new Muestra(null, "Sangre", "A", LocalDate.of(2026, 5, 1), "PROCESADA", "Normal", LocalDate.of(2026, 5, 3), laboratorio));
        repository.save(new Muestra(null, "Orina", "B", LocalDate.of(2026, 5, 2), "PENDIENTE", null, null, laboratorio));

        List<Muestra> resultado = repository.findByTipo_muestra("Sangre");

        assertEquals(1, resultado.size());
        assertEquals("Sangre", resultado.get(0).getTipo_muestra());
    }

    @Test
    void deberiaEliminarMuestra() {
        Laboratorio laboratorio = laboratorioRepository.save(new Laboratorio(null, "Lab Eliminar", "Ana", "+56911111111", "Santiago", LocalDate.of(2025, 1, 10), null));
        Muestra muestra = repository.save(new Muestra(null, "Piel", "Cultura", LocalDate.of(2026, 5, 4), "PENDIENTE", null, null, laboratorio));

        repository.deleteById(muestra.getId());

        Optional<Muestra> resultado = repository.findById(muestra.getId());
        assertFalse(resultado.isPresent());
    }
}
