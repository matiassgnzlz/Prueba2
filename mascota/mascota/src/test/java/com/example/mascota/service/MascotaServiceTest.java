package com.example.mascota.service;

import com.example.mascota.client.DuenoClient;
import com.example.mascota.dto.DuenoResponse;
import com.example.mascota.dto.MascotaDTO;
import com.example.mascota.dto.MascotaResponse;
import com.example.mascota.model.Mascota;
import com.example.mascota.repository.MascotaRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaRepository repo;

    @Mock
    private DuenoClient duenoClient; // Cliente externo que debemos mockear

    @InjectMocks
    private MascotaService service;

@Test
void deberiaRetornarMascotaCuandoExiste() {
    Mascota mascota = new Mascota(1L, "Chloe", "perro", "westie", 1L);

    DuenoResponse duenoSimulado = new DuenoResponse();
    duenoSimulado.setId(1L);
    duenoSimulado.setNombre("Alberto Hurtado");
    duenoSimulado.setContacto("+5Chile...");
    duenoSimulado.setRut("11.111.111-1");

    when(repo.findById(1L)).thenReturn(Optional.of(mascota));

    when(duenoClient.obtenerDueno(anyLong(), anyString())).thenReturn(duenoSimulado);

    MascotaResponse resultado = service.obtener(1L, "token-simulado-123");

    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertEquals(1L, resultado.getId());
    assertEquals("Chloe", resultado.getNombre());
    assertEquals("perro", resultado.getEspecie());
    assertEquals("westie", resultado.getRaza());

    assertNotNull(resultado.getDueno(), "El objeto dueño dentro de la mascota no debería ser nulo");
    assertEquals("Alberto Hurtado", resultado.getDueno().getNombre());

    verify(repo).findById(1L);
    verify(duenoClient).obtenerDueno(anyLong(), anyString());
}


    @Test
    void deberiaLanzarExcepcionCuandoMascotaNoExiste() {
        // 1. Arrange
        when(repo.findById(99L)).thenReturn(Optional.empty());
        
        // 2. Act + Assert
        EntityNotFoundException ex = assertThrows(
            EntityNotFoundException.class,
            () -> service.obtener(99L, "token-simulado-123")
        );
        
        // 3. Assert
        assertEquals("Mascota no encontrado", ex.getMessage());
        verify(repo).findById(99L);
    }

    @Test
    void deberiaRetornarListaMascota() {
        // 1. Arrange
        Mascota mascota = new Mascota(1L, "Luna", "Perro", "Quiltro", 1L);
        when(repo.findAll()).thenReturn(List.of(mascota));
        
        // 2. Act
        List<MascotaResponse> resultado = service.listar("token-simulado-123");
        
        // 3. Assert
        assertFalse(resultado.isEmpty(), "La lista no debería estar vacía");
        assertEquals(1, resultado.size());
        assertEquals("Luna", resultado.get(0).getNombre());
        
        verify(repo).findAll();
    }

@Test
void deberiaCrearMascotaCorrectamente() {
    // 1. Arrange: DTO con los datos que se envían desde el cliente/Postman
    MascotaDTO dto = new MascotaDTO();
    dto.setNombre("Luna");
    dto.setEspecie("Perro");
    dto.setRaza("Quiltro");
    dto.setDuenoId(1L);

    // Entidad simulada que el repositorio guardará y retornará con su ID autogenerado
    Mascota mascotaGuardada = new Mascota();
    mascotaGuardada.setId(1L);
    mascotaGuardada.setNombre("Luna");
    mascotaGuardada.setEspecie("Perro");
    mascotaGuardada.setRaza("Quiltro");
    mascotaGuardada.setDuenoId(1L);

    // Instanciamos el objeto Dueño simulado para que se inyecte en el mapToResponse
    DuenoResponse duenoSimulado = new DuenoResponse();
    duenoSimulado.setId(1L);
    duenoSimulado.setNombre("Alberto Hurtado");

    // Configuración de los Mocks
    // ⚠️ Esto cubrirá tanto la validación inicial del servicio como el llamado dentro de mapToResponse
    when(duenoClient.obtenerDueno(anyLong(), anyString())).thenReturn(duenoSimulado);
    when(repo.save(any(Mascota.class))).thenReturn(mascotaGuardada);

    // 2. Act: Ejecutamos el método crear del servicio
    MascotaResponse resultado = service.crear(dto, "token-simulado-123");

    // 3. Assert: Validamos que la respuesta mapeada final sea correcta
    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertEquals(1L, resultado.getId());
    assertEquals("Luna", resultado.getNombre());
    assertEquals("Perro", resultado.getEspecie());
    assertEquals("Quiltro", resultado.getRaza());
    
    // ⚠️ VALIDACIÓN DEL DUEÑO ANIDADO: 
    // Aseguramos que el Builder haya estructurado el objeto complejo correctamente
    assertNotNull(resultado.getDueno(), "El objeto dueño no debería ser nulo");
    assertEquals("Alberto Hurtado", resultado.getDueno().getNombre());

    // Verificaciones de comportamiento de los componentes mockeados
    verify(repo).save(any(Mascota.class));
    verify(duenoClient, times(2)).obtenerDueno(anyLong(), anyString()); // Se llama 2 veces en total en 'crear'
}

@Test
void deberiaActualizarMascotaCorrectamente() {
    // 1. Arrange: Datos viejos que simulan estar registrados en la BD
    Mascota existente = new Mascota();
    existente.setId(1L);
    existente.setNombre("Luna Vieja");
    existente.setEspecie("Perro");
    existente.setRaza("Quiltro");
    existente.setDuenoId(1L);

    // Nuevos datos para el PUT (lo que viene del DTO/Request)
    MascotaDTO dto = new MascotaDTO();
    dto.setNombre("Luna Clara");
    dto.setEspecie("Perro");
    dto.setRaza("Poodle"); // Modificamos la raza
    dto.setDuenoId(1L);

    // Instanciamos el objeto Dueño simulado para el mapeo final
    DuenoResponse duenoSimulado = new DuenoResponse();
    duenoSimulado.setId(1L);
    duenoSimulado.setNombre("Alberto Hurtado");

    // Configuración de los Mocks
    when(repo.findById(1L)).thenReturn(Optional.of(existente));
    when(repo.save(any(Mascota.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
    // ⚠️ LA CLAVE: Responde a las dos llamadas de obtenerDueno evitando la excepción "Dueno no existe"
    when(duenoClient.obtenerDueno(anyLong(), anyString())).thenReturn(duenoSimulado);

    // 2. Act: Ejecutamos la actualización en el servicio
    MascotaResponse resultado = service.actualizar(1L, dto, "token-simulado-123");

    // 3. Assert: Validamos los cambios esperados en la mascota
    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertEquals(1L, resultado.getId());
    assertEquals("Luna Clara", resultado.getNombre());
    assertEquals("Perro", resultado.getEspecie());
    assertEquals("Poodle", resultado.getRaza());
    
    // ⚠️ VALIDACIÓN DEL DUEÑO ANIDADO:
    // Comprobamos que el Builder del servicio inyectó correctamente el objeto complejo
    assertNotNull(resultado.getDueno(), "El objeto dueño no debería ser nulo");
    assertEquals("Alberto Hurtado", resultado.getDueno().getNombre());

    // Verificaciones de comportamiento
    verify(repo).findById(1L);
    verify(repo).save(existente);
    verify(duenoClient, times(2)).obtenerDueno(anyLong(), anyString()); // Se ejecuta en la validación inicial y en mapToResponse
}

    @Test
    void deberiaEliminarMascotaPorId() {
        // Arrange
        doNothing().when(repo).deleteById(1L);
        
        // Act
        service.eliminar(1L);
        
        // Assert
        verify(repo).deleteById(1L);
    }
}