package com.example.veterinario.service;

import com.example.veterinario.dto.VeterinarioDTO;
import com.example.veterinario.dto.VeterinarioResponse;
import com.example.veterinario.model.Veterinario;
import com.example.veterinario.repository.VeterinarioRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeterinarioServiceTest {
@Mock
private VeterinarioRepository repo;
@InjectMocks
private VeterinarioService service;
@Test
void deberiaRetornarVeterinarioCuandoExiste() {

    Veterinario veterinario = new Veterinario(1L, "felipe", "peluquero", "VT0804");
    

    when(repo.findById(1L)).thenReturn(Optional.of(veterinario));


    VeterinarioResponse resultado = service.obtener(1L, "token-simulado-123");


    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertEquals(1L, resultado.getId());
    assertEquals("felipe", resultado.getNombre());

    assertEquals("peluquero", resultado.getEspecialidad());
    assertEquals("VT0804", resultado.getMatricula());

    verify(repo).findById(1L);
}


    @Test
    void deberiaLanzarExcepcionCuandoVeterinarioNoExiste() {
    when(repo.findById(99L)).thenReturn(Optional.empty());
    EntityNotFoundException ex = assertThrows(
    EntityNotFoundException.class,
    () -> service.obtener(99L,"token-simulado-123")
    );
    assertEquals("Veterinario no encontrado", ex.getMessage());
    verify(repo).findById(99L);
    }

@Test
void deberiaRetornarListaVeterinario() {
    Veterinario veterinario = new Veterinario(1L, "María", "peluquera", "VT2222");
    
    when(repo.findAll()).thenReturn(List.of(veterinario));
    
    List<VeterinarioResponse> resultado = service.listar("token-simulado-123");
    
    assertFalse(resultado.isEmpty(), "La lista no debería estar vacía");
    assertEquals(1, resultado.size());
    assertEquals("María", resultado.get(0).getNombre());
    
    verify(repo).findAll();
}

    @Test
    void deberiaCrearVeterinarioCorrectamente() {

    VeterinarioDTO dto = new VeterinarioDTO();
    dto.setNombre("Juan");
    dto.setEspecialidad("promotor");
    dto.setMatricula("V4444");

    Veterinario veterinarioGuardado = new Veterinario();
    veterinarioGuardado.setId(1L); 
    veterinarioGuardado.setNombre("Juan");
    veterinarioGuardado.setEspecialidad("promotor");
    veterinarioGuardado.setMatricula("V4444");

    when(repo.save(any(Veterinario.class))).thenReturn(veterinarioGuardado);

    VeterinarioResponse resultado = service.crear(dto, "token-simulado-123");

    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Juan", resultado.getNombre());
    assertEquals("promotor", resultado.getEspecialidad());
    assertEquals("V4444", resultado.getMatricula());

    verify(repo).save(any(Veterinario.class));
}

@Test
void deberiaActualizarVeterinarioCorrectamente() {
    // Arrange
    Veterinario existente = new Veterinario();
    existente.setId(1L);
    existente.setNombre("Matias");
    existente.setEspecialidad("ayudante");
    existente.setMatricula("V8888");


    VeterinarioDTO dto = new VeterinarioDTO();
    dto.setNombre("Matias");
    dto.setEspecialidad("ayudante");
    dto.setMatricula("V8888");

    when(repo.findById(1L)).thenReturn(Optional.of(existente));

    when(repo.save(any(Veterinario.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // 2. Act
    VeterinarioResponse resultado = service.actualizar(1L, dto, "token-simulado-123");

    assertEquals(1L, resultado.getId());
    assertEquals("Matias", resultado.getNombre());
    assertEquals("ayudante", resultado.getEspecialidad());
    assertEquals("V8888", resultado.getMatricula());

    verify(repo).findById(1L);
    verify(repo).save(existente);
}

@Test
void deberiaEliminarVeterinarioPorId() {
    // 1. Arrange
    Long idEliminar = 1L;
    
    Veterinario veterinarioExistente = new Veterinario(1L, "Dr. Simi", "general", "VT1234");

    doReturn(Optional.of(veterinarioExistente))
        .when(repo)
        .findById(anyLong());
        
    // 2. Act
    service.eliminar(idEliminar); 
    
    // 3. Assert
    verify(repo).findById(anyLong());
}
}
