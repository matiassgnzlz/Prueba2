package com.example.Vacunacion.repository;

import com.example.Vacunacion.model.Vacunacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VacunacionRepositoryTest {

    @Autowired
    private VacunacionRepository repository;

    @Test
    void debeGuardarVacunacion() {

        Vacunacion vacunacion = new Vacunacion();

        Vacunacion guardada =
                repository.save(vacunacion);

        assertNotNull(guardada.getId());
    }
}