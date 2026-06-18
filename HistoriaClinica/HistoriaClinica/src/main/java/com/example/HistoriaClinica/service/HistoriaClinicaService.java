package com.example.HistoriaClinica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HistoriaClinica.client.MascotaClient;
import com.example.HistoriaClinica.dto.HistoriaClinicaDTO;
import com.example.HistoriaClinica.dto.MascotaDTO;
import com.example.HistoriaClinica.dto.MascotaResponse;
import com.example.HistoriaClinica.model.HistoriaClinica;
import com.example.HistoriaClinica.repository.HistoriaClinicaRepository;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository repository;
    private final MascotaClient mascotaClient;

    HistoriaClinicaService(HistoriaClinicaRepository repository, MascotaClient mascotaClient) {
        this.repository = repository;
        this.mascotaClient = mascotaClient;
    }

    public List<HistoriaClinica> findByMascota(Long mascotaId) {
        return repository.findAll()
                .stream()
                .filter(h -> h.getMascotaId().equals(mascotaId))
                .toList();
    }

    public HistoriaClinica guardar(HistoriaClinica historia, String token) {
        mascotaClient.obtenerMascota(historia.getMascotaId(), token);
        return repository.save(historia);
    }

    public HistoriaClinicaDTO obtenerHistoriaCompleta(Long id, String token) {
        HistoriaClinica historia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historia no encontrada"));

        MascotaResponse mascota = mascotaClient.obtenerMascota(historia.getMascotaId(), token);

        return construirDTO(historia, mascota);
    }

    // --- NUEVOS MÉTODOS AGREGADOS ---

    /**
     * PUT: Busca la historia clínica, mapea los nuevos cambios recibidos en la entidad,
     * guarda en base de datos, valida/consulta con el cliente Feign de mascotas y retorna el DTO completo.
     */
    public HistoriaClinicaDTO actualizar(Long id, HistoriaClinica datosNuevos, String token) {
        HistoriaClinica historia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada para actualizar"));

        // Actualizamos los campos necesarios de la entidad
        historia.setFecha(datosNuevos.getFecha());
        historia.setDescripcion(datosNuevos.getDescripcion());
        historia.setTratamiento(datosNuevos.getTratamiento());
        historia.setVeterinario(datosNuevos.getVeterinario());
        historia.setMascotaId(datosNuevos.getMascotaId());

        // Guardamos los cambios
        HistoriaClinica actualizada = repository.save(historia);

        // Consultamos al microservicio de Mascotas con el ID (por si cambió de mascota en la edición)
        MascotaResponse mascota = mascotaClient.obtenerMascota(actualizada.getMascotaId(), token);

        // Mapeamos y retornamos el DTO estructurado
        return construirDTO(actualizada, mascota);
    }

    /**
     * DELETE: Busca la historia clínica por su ID y si existe la remueve físicamente.
     */
    public void eliminar(Long id, String token) {
        HistoriaClinica historia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada para eliminar"));
        
        repository.delete(historia);
    }

    /**
     * Método auxiliar privado para reutilizar el mapeo repetitivo a HistoriaClinicaDTO
     */
    private HistoriaClinicaDTO construirDTO(HistoriaClinica historia, MascotaResponse mascota) {
        HistoriaClinicaDTO dto = new HistoriaClinicaDTO();
        dto.setId(historia.getId());
        dto.setFecha(historia.getFecha());
        dto.setDescripcion(historia.getDescripcion());
        dto.setTratamiento(historia.getTratamiento());
        dto.setVeterinario(historia.getVeterinario());
        
        MascotaDTO mDto = new MascotaDTO();
        mDto.setId(mascota.getId());
        mDto.setNombre(mascota.getNombre());
        mDto.setEspecie(mascota.getEspecie());
        mDto.setRaza(mascota.getRaza());
        if (mascota.getDueno() != null) {
            mDto.setDuenoId(mascota.getDueno().getId()); 
        }
        dto.setMascota(mDto);

        return dto;
    }
}