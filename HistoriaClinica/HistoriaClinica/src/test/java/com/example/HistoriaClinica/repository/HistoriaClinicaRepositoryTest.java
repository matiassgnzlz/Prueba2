package com.example.HistoriaClinica.repository;

import com.example.HistoriaClinica.model.HistoriaClinica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

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
class HistoriaClinicaRepositoryTest {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Test
    void debeGuardarHistoriaClinica() {
        HistoriaClinica historia = new HistoriaClinica();
        historia.setMascotaId(1L); 
        historia.setDescripcion("Control de vacunas anuales.");
        historia.setTratamiento("Sextuple aplicada.");
        historia.setVeterinario("Dr. Simi");

        HistoriaClinica guardada = historiaClinicaRepository.save(historia);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getFecha()).isNotNull();
    }

    @Test
    void debeBuscarHistoriaPorId() {
        HistoriaClinica historia = new HistoriaClinica();
        historia.setMascotaId(1L);
        historia.setDescripcion("Paciente estable");
        HistoriaClinica guardada = historiaClinicaRepository.save(historia);

        Optional<HistoriaClinica> encontrada = historiaClinicaRepository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getDescripcion()).isEqualTo("Paciente estable");
    }

    @Test
    void debeListarHistoriasClinicas() {
        HistoriaClinica h1 = new HistoriaClinica();
        h1.setMascotaId(1L);
        HistoriaClinica h2 = new HistoriaClinica();
        h2.setMascotaId(2L);

        historiaClinicaRepository.save(h1);
        historiaClinicaRepository.save(h2);

        List<HistoriaClinica> lista = historiaClinicaRepository.findAll();
        assertThat(lista).hasSize(2);
    }

    @Test
    void debeActualizarHistoriaClinica() {
        HistoriaClinica historia = new HistoriaClinica();
        historia.setMascotaId(1L);
        historia.setTratamiento("Ninguno");
        HistoriaClinica guardada = historiaClinicaRepository.save(historia);

        guardada.setTratamiento("Paracetamol 10mg");
        HistoriaClinica actualizada = historiaClinicaRepository.save(guardada);

        assertThat(actualizada.getTratamiento()).isEqualTo("Paracetamol 10mg");
    }

    @Test
    void debeEliminarHistoriaClinica() {
        HistoriaClinica historia = new HistoriaClinica();
        historia.setMascotaId(1L);
        HistoriaClinica guardada = historiaClinicaRepository.save(historia);

        historiaClinicaRepository.deleteById(guardada.getId());
        Optional<HistoriaClinica> eliminada = historiaClinicaRepository.findById(guardada.getId());

        assertThat(eliminada).isEmpty();
    }
}