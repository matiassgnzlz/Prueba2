package com.example.citas.repository;

import com.example.citas.model.Citas;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL;DATABASE_TO_LOWER=TRUE",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.flyway.enabled=false"
})
class CitasRepositoryTest {

    @Autowired
    private CitasRepository citasRepository;

    @Test
    void debeGuardarCita() {
        Citas cita = new Citas();
        cita.setFechaHora(LocalDateTime.now().plusDays(1));
        cita.setMotivo("Control General");
        cita.setTipo("Consulta");
        cita.setEstado("PENDIENTE");
        cita.setVeterinarioId(1L);
        cita.setMascotaId(1L);
        cita.setDuenoId(1L);

        Citas guardado = citasRepository.save(cita);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getMotivo()).isEqualTo("Control General");
    }

    @Test
    void debeBuscarCitaPorId() {
        Citas cita = new Citas();
        cita.setFechaHora(LocalDateTime.now().plusDays(2));
        cita.setMotivo("Cirugia");
        cita.setTipo("Urgencia");
        cita.setEstado("CONFIRMADA");
        cita.setVeterinarioId(1L);
        cita.setMascotaId(1L);
        cita.setDuenoId(1L);
        Citas guardado = citasRepository.save(cita);

        Optional<Citas> encontrado = citasRepository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getMotivo()).isEqualTo("Cirugia");
    }

    @Test
    void debeListarTodasLasCitas() {
        Citas cita1 = new Citas();
        cita1.setFechaHora(LocalDateTime.now());
        cita1.setMotivo("Consulta 1");
        cita1.setVeterinarioId(1L);
        cita1.setMascotaId(1L);
        cita1.setDuenoId(1L);

        Citas cita2 = new Citas();
        cita2.setFechaHora(LocalDateTime.now().plusHours(1));
        cita2.setMotivo("Consulta 2");
        cita2.setVeterinarioId(2L);
        cita2.setMascotaId(2L);
        cita2.setDuenoId(1L);

        citasRepository.save(cita1);
        citasRepository.save(cita2);

        List<Citas> lista = citasRepository.findAll();
        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void debeActualizarCita() {
        Citas cita = new Citas();
        cita.setFechaHora(LocalDateTime.now());
        cita.setMotivo("Motivo Viejo");
        cita.setVeterinarioId(1L);
        cita.setMascotaId(1L);
        cita.setDuenoId(1L);
        Citas guardado = citasRepository.save(cita);

        guardado.setMotivo("Motivo Nuevo");
        Citas actualizado = citasRepository.save(guardado);

        assertThat(actualizado.getMotivo()).isEqualTo("Motivo Nuevo");
    }

    @Test
    void debeEliminarCita() {
        Citas cita = new Citas();
        cita.setFechaHora(LocalDateTime.now());
        cita.setMotivo("A Eliminar");
        cita.setVeterinarioId(1L);
        cita.setMascotaId(1L);
        cita.setDuenoId(1L);
        Citas guardado = citasRepository.save(cita);

        citasRepository.deleteById(guardado.getId());
        Optional<Citas> eliminado = citasRepository.findById(guardado.getId());

        assertThat(eliminado).isEmpty();
    }
}