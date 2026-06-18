package com.example.dueno.repository;

import com.example.dueno.model.Dueno;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest 
@Transactional
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",

    "spring.flyway.enabled=false",
    "eureka.client.enabled=false"
})
class DuenoRepositoryTest {

    @Autowired
    private DuenoRepository duenoRepository;

    @Test
    void debeGuardarDueno() {
        Dueno dueno = new Dueno();
        dueno.setNombre("Alberto Hurtado");
        dueno.setContacto("+56912345678");
        dueno.setRut("11.111.111-1");

        Dueno guardado = duenoRepository.save(dueno);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Alberto Hurtado");
    }

    @Test
    void debeBuscarDuenoPorId() {
        Dueno dueno = new Dueno();
        dueno.setNombre("María Cifuentes");
        dueno.setContacto("+56987654321");
        dueno.setRut("22.222.222-2");
        Dueno guardado = duenoRepository.save(dueno);

        Optional<Dueno> encontrado = duenoRepository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("María Cifuentes");
    }

    @Test
    void debeListarTodosLosDuenos() {
        Dueno dueno1 = new Dueno();
        dueno1.setNombre("Dueno Uno");
        dueno1.setContacto("+56911111111");
        dueno1.setRut("1-1");

        Dueno dueno2 = new Dueno();
        dueno2.setNombre("Dueno Dos");
        dueno2.setContacto("+56922222222");
        dueno2.setRut("2-2");

        duenoRepository.save(dueno1);
        duenoRepository.save(dueno2);

        List<Dueno> lista = duenoRepository.findAll();

        assertThat(lista).hasSize(2);
    }

    @Test
    void debeActualizarDueno() {
        Dueno dueno = new Dueno();
        dueno.setNombre("Nombre Original");
        dueno.setContacto("+56900000000");
        dueno.setRut("3-3");
        Dueno guardado = duenoRepository.save(dueno);

        guardado.setNombre("Nombre Modificado");
        Dueno actualizado = duenoRepository.save(guardado);

        assertThat(actualizado.getNombre()).isEqualTo("Nombre Modificado");
    }

    @Test
    void debeEliminarDueno() {
        Dueno dueno = new Dueno();
        dueno.setNombre("A Eliminar");
        dueno.setContacto("+56933333333");
        dueno.setRut("4-4");
        Dueno guardado = duenoRepository.save(dueno);

        duenoRepository.deleteById(guardado.getId());
        Optional<Dueno> eliminado = duenoRepository.findById(guardado.getId());

        assertThat(eliminado).isEmpty();
    }
}