package com.example.mascota.repository;

import com.example.mascota.model.Mascota;
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
class MascotaRepositoryTest {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Test
    void debeGuardarMascota() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Chloe");
        mascota.setEspecie("Perro");
        mascota.setRaza("Westie");
        mascota.setDuenoId(1L); // Campo asignado directamente como Long

        Mascota guardado = mascotaRepository.save(mascota);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Chloe");
    }

    @Test
    void debeBuscarMascotaPorId() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setEspecie("Perro");
        mascota.setRaza("Poodle");
        mascota.setDuenoId(1L);
        Mascota guardado = mascotaRepository.save(mascota);

        Optional<Mascota> encontrado = mascotaRepository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Luna");
    }

    @Test
    void debeListarTodasLasMascotas() {
        Mascota mascota1 = new Mascota();
        mascota1.setNombre("Mascota Uno");
        mascota1.setEspecie("Gato");
        mascota1.setDuenoId(1L);

        Mascota mascota2 = new Mascota();
        mascota2.setNombre("Mascota Dos");
        mascota2.setEspecie("Perro");
        mascota2.setDuenoId(2L);

        mascotaRepository.save(mascota1);
        mascotaRepository.save(mascota2);

        List<Mascota> lista = mascotaRepository.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void debeActualizarMascota() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Nombre Viejo");
        mascota.setEspecie("Gato");
        mascota.setDuenoId(1L);
        Mascota guardado = mascotaRepository.save(mascota);

        guardado.setNombre("Nombre Nuevo");
        Mascota actualizado = mascotaRepository.save(guardado);

        assertThat(actualizado.getNombre()).isEqualTo("Nombre Nuevo");
    }

    @Test
    void debeEliminarMascota() {
        Mascota mascota = new Mascota();
        mascota.setNombre("A Eliminar");
        mascota.setEspecie("Exotico");
        mascota.setDuenoId(1L);
        Mascota guardado = mascotaRepository.save(mascota);

        mascotaRepository.deleteById(guardado.getId());
        Optional<Mascota> eliminado = mascotaRepository.findById(guardado.getId());

        assertThat(eliminado).isEmpty();
    }
}