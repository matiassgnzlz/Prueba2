package com.example.dueno.service;

import com.example.dueno.dto.DuenoDTO;
import com.example.dueno.model.Dueno;
import com.example.dueno.repository.DuenoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DuenoServiceTest {
@Mock
private DuenoRepository repo;
@InjectMocks
private DuenoService service;

@Test
void deberiaRetornarDuenoCuandoExiste() {
    Dueno dueno = new Dueno(1L, "Alberto Hurtado", "+56911112222", "11.111.111-1");
    
    when(repo.findById(1L)).thenReturn(Optional.of(dueno));

    Dueno resultado = service.obtener(1L);

    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertEquals(1L, resultado.getId());
    assertEquals("Alberto Hurtado", resultado.getNombre());
    assertEquals("+56911112222", resultado.getContacto());
    assertEquals("11.111.111-1", resultado.getRut());

    verify(repo).findById(1L);
}


    @Test
    void deberiaLanzarExcepcionCuandoDuenoNoExiste() {
    when(repo.findById(99L)).thenReturn(Optional.empty());
    EntityNotFoundException ex = assertThrows(
    EntityNotFoundException.class,
    () -> service.obtener(99L)
    );
    assertEquals("Dueno no encontrado", ex.getMessage());
    verify(repo).findById(99L);
    }

    @Test
    void deberiaRetornarListaDueno() {
    Dueno Dueno = new Dueno(1L, "María Ignacia", "+56933334444", "22.222.222-2");
    when(repo.findAll()).thenReturn(List.of(Dueno));
    List<Dueno> resultado = service.listar();
    assertFalse(resultado.isEmpty());
    assertEquals(1, resultado.size());
    assertEquals("María Ignacia", resultado.get(0).getNombre());
    verify(repo).findAll();
    }

    @Test
    void deberiaCrearDuenoCorrectamente() {
    DuenoDTO dto = new DuenoDTO();
    dto.setNombre("Juan Pereira");
    dto.setRut("66.666.666-6");
    dto.setContacto("+56998765432");

    Dueno duenoGuardado = new Dueno();
    duenoGuardado.setId(1L); 
    duenoGuardado.setNombre("Juan Pereira");
    duenoGuardado.setRut("66.666.666-6");
    duenoGuardado.setContacto("+56998765432");

    when(repo.save(any(Dueno.class))).thenReturn(duenoGuardado);


    Dueno resultado = service.crear(dto);

    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Juan Pereira", resultado.getNombre());
    assertEquals("66.666.666-6", resultado.getRut());
    assertEquals("+56998765432", resultado.getContacto());

    verify(repo).save(any(Dueno.class));
}

@Test
void deberiaActualizarDuenoCorrectamente() {
    Dueno existente = new Dueno();
    existente.setId(1L);
    existente.setNombre("Dueño viejo");
    existente.setRut("11.111.111-1");
    existente.setContacto("+56911111111");



    DuenoDTO dto = new DuenoDTO();
    dto.setNombre("Dueño nuevo");
    dto.setRut("22.222.222-2");
    dto.setContacto("+56922222222");


    when(repo.findById(1L)).thenReturn(Optional.of(existente));

    when(repo.save(any(Dueno.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Dueno resultado = service.actualizar(1L, dto);

    assertEquals(1L, resultado.getId());
    assertEquals("Dueño nuevo", resultado.getNombre());
    assertEquals("22.222.222-2", resultado.getRut());
    assertEquals("+56922222222", resultado.getContacto());


    verify(repo).findById(1L);
    verify(repo).save(existente);
}

    @Test
    void deberiaEliminarDuenoPorId() {
    // Arrange
    doNothing().when(repo).deleteById(1L);
    // Act
    service.eliminar(1L);
    // Assert
    verify(repo).deleteById(1L);
    }
}
