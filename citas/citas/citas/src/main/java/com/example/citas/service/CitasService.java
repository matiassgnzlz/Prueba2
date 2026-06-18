package com.example.citas.service;

import com.example.citas.client.MascotaClient;
import com.example.citas.client.VeterinarioClient;
import com.example.citas.dto.CitasDTO;
import com.example.citas.dto.CitasResponse;
import com.example.citas.dto.MascotaResponse;
import com.example.citas.dto.VeterinarioResponse;
import com.example.citas.model.Citas;
import com.example.citas.repository.CitasRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CitasService {

    private final CitasRepository repository;
    private final VeterinarioClient veterinarioClient;
    private final MascotaClient mascotaClient;

    public CitasResponse agendarCitas(CitasDTO dto, String token) {
    Citas citas = new Citas();
    citas.setFechaHora(dto.getFechaHora());
    citas.setMotivo(dto.getMotivo());
    citas.setTipo(dto.getTipo());
    citas.setEstado("PENDIENTE");
    
    citas.setMascotaId(dto.getMascotaId());
    citas.setVeterinarioId(dto.getVeterinarioId());
    citas.setDuenoId(dto.getDuenoId()); 

    MascotaResponse m = mascotaClient.obtenerMascota(dto.getMascotaId(), token);
    VeterinarioResponse v = veterinarioClient.obtenerVeterinario(dto.getVeterinarioId(), token);

    Citas guardada = repository.save(citas);

    return construirRespuesta(guardada, m, v);
}

    public CitasResponse obtenerPorId(Long id, String token) {
        Citas cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        MascotaResponse m = mascotaClient.obtenerMascota(cita.getMascotaId(), token);
        VeterinarioResponse v = veterinarioClient.obtenerVeterinario(cita.getVeterinarioId(), token);
        
        return construirRespuesta(cita, m, v);
    }

    public List<CitasResponse> obtenerTodas(String token) {
        return repository.findAll().stream()
                .map(c -> {
                    MascotaResponse m = mascotaClient.obtenerMascota(c.getMascotaId(), token);
                    VeterinarioResponse v = veterinarioClient.obtenerVeterinario(c.getVeterinarioId(), token);
                    return construirRespuesta(c, m, v);
                })
                .collect(Collectors.toList());
    }

    public CitasResponse cambiarEstado(Long id, String nuevoEstado, String token) {
        Citas cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        cita.setEstado(nuevoEstado);
        Citas actualizada = repository.save(cita);
        
        MascotaResponse m = mascotaClient.obtenerMascota(actualizada.getMascotaId(), token);
        VeterinarioResponse v = veterinarioClient.obtenerVeterinario(actualizada.getVeterinarioId(), token);
        
        return construirRespuesta(actualizada, m, v);
    }

    private CitasResponse construirRespuesta(Citas citas, MascotaResponse m, VeterinarioResponse v) {
        CitasResponse res = new CitasResponse();
        res.setId(citas.getId());
        res.setFechaHora(citas.getFechaHora());
        res.setMotivo(citas.getMotivo());
        res.setTipo(citas.getTipo());
        res.setEstado(citas.getEstado());
        
        res.setMascotaId(citas.getMascotaId());
        res.setVeterinarioId(citas.getVeterinarioId());
        res.setDuenoId(citas.getDuenoId());
            
        res.setVeterinario(v);  
        res.setMascota(m);
        return res;
    }

    public CitasResponse actualizarCita(Long id, CitasDTO dto, String token) {
        Citas cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada para actualizar"));

        // Actualizamos los datos generales de la cita
        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivo(dto.getMotivo());
        cita.setTipo(dto.getTipo());
        
        // Actualizamos las llaves foráneas/IDs relacionales si cambiaron
        cita.setMascotaId(dto.getMascotaId());
        cita.setVeterinarioId(dto.getVeterinarioId());
        cita.setDuenoId(dto.getDuenoId());

        // Guardamos los cambios en la base de datos
        Citas actualizada = repository.save(cita);

        // Volvemos a consumir los clientes Feign con la información actualizada
        MascotaResponse m = mascotaClient.obtenerMascota(actualizada.getMascotaId(), token);
        VeterinarioResponse v = veterinarioClient.obtenerVeterinario(actualizada.getVeterinarioId(), token);

        return construirRespuesta(actualizada, m, v);
    }
    public void eliminarCita(Long id, String token) {
        Citas cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada para eliminar"));
        
        repository.delete(cita);
    }



}
