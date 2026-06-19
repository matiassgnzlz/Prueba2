package com.example.Vacunacion.service;

import com.example.Vacunacion.model.Vacunacion;
import com.example.Vacunacion.repository.VacunacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacunacionServiceTest {

    @Mock
    private VacunacionRepository repository;

    @InjectMocks
    private VacunacionService service;

    @Test
    void debeGuardarVacunacion() {

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setId(1L);

        when(repository.save(any(Vacunacion.class)))
                .thenReturn(vacunacion);

        Vacunacion resultado =
                service.guardar(vacunacion);

        assertNotNull(resultado);
    }

    @Test
    void debeListarVacunaciones() {

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setId(1L);

        when(repository.findAll())
                .thenReturn(List.of(vacunacion));

        List<Vacunacion> resultado =
                service.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void debeBuscarVacunacionPorId() {

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(vacunacion));

        Vacunacion resultado =
                service.buscarPorId(1L);

        assertNotNull(resultado);
    }
}