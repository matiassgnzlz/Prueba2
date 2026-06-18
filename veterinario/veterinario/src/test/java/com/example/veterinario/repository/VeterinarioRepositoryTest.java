package com.example.veterinario.repository;

import com.example.veterinario.model.Veterinario;
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
class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Test
    void debeGuardarVeterinario() {
        Veterinario vet = new Veterinario();
        vet.setNombre("Dr. Simi");
        vet.setEspecialidad("Animales Exóticos");
        vet.setMatricula("MAT-12345");

        Veterinario guardado = veterinarioRepository.save(vet);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
    }

    @Test
    void debeBuscarVeterinarioPorId() {
        Veterinario vet = new Veterinario();
        vet.setNombre("Dra. Polo");
        vet.setEspecialidad("Cirugía");
        vet.setMatricula("MAT-54321");
        Veterinario guardado = veterinarioRepository.save(vet);

        Optional<Veterinario> encontrado = veterinarioRepository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
    }

    @Test
    void debeListarTodosLosVeterinarios() {
        Veterinario vet1 = new Veterinario();
        vet1.setNombre("Vet Uno");
        vet1.setEspecialidad("General");
        vet1.setMatricula("MAT-00001");
        
        Veterinario vet2 = new Veterinario();
        vet2.setNombre("Vet Dos");
        vet2.setEspecialidad("Urgencias");
        vet2.setMatricula("MAT-00002");

        veterinarioRepository.save(vet1);
        veterinarioRepository.save(vet2);

        List<Veterinario> lista = veterinarioRepository.findAll();
        assertThat(lista).isNotEmpty();
    }

    @Test
    void debeActualizarVeterinario() {
        Veterinario vet = new Veterinario();
        vet.setNombre("Original");
        vet.setEspecialidad("Fisiatría");
        vet.setMatricula("MAT-99999");
        Veterinario guardado = veterinarioRepository.save(vet);

        guardado.setNombre("Modificado");
        Veterinario actualizado = veterinarioRepository.save(guardado);

        assertThat(actualizado.getNombre()).isEqualTo("Modificado");
    }

    @Test
    void debeEliminarVeterinario() {
        Veterinario vet = new Veterinario();
        vet.setNombre("A Eliminar");
        vet.setEspecialidad("Odontología"); 
        vet.setMatricula("MAT-88888");
        Veterinario guardado = veterinarioRepository.save(vet);

        veterinarioRepository.deleteById(guardado.getId());
        Optional<Veterinario> eliminado = veterinarioRepository.findById(guardado.getId());

        assertThat(eliminado).isEmpty();
    }
}