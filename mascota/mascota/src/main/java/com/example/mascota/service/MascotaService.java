package com.example.mascota.service;

import com.example.mascota.client.DuenoClient;
import com.example.mascota.dto.MascotaDTO;
import com.example.mascota.dto.MascotaResponse;
import com.example.mascota.model.Mascota;
import com.example.mascota.repository.MascotaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class MascotaService {

    private final MascotaRepository repository;
    private final DuenoClient duenoClient;

    public MascotaResponse  crear(MascotaDTO dto, String token) {

        log.info("Crear Mascota", keyValue("nombre", dto.getNombre()));

        var dueno = duenoClient.obtenerDueno(dto.getDuenoId(), token);

        if (dueno == null) {
            throw new RuntimeException("Dueno no existe");
        }

        Mascota mascota = repository.save(
                new Mascota(null, dto.getNombre(), dto.getEspecie(),dto.getRaza(), dto.getDuenoId())
        );

        return mapToResponse(mascota, token);
    }

    public List<MascotaResponse> listar(String token) {

        return repository.findAll()
                .stream()
                .map(l -> mapToResponse(l, token))
                .toList();
    }

    public MascotaResponse obtener(Long id, String token) {
        Mascota mascota = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrado"));
        
        // Usamos tu método para transformarla en MascotaResponse con su dueño correspondiente
        return mapToResponse(mascota, token);
}

    public MascotaResponse actualizar(Long id, MascotaDTO dto, String token) {

        var dueno = duenoClient.obtenerDueno(dto.getDuenoId(), token);

        if (dueno == null) {
            throw new RuntimeException("Dueno no existe");
        }

        Mascota m = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrado"));

        m.setNombre(dto.getNombre());
        m.setEspecie(dto.getEspecie());
        m.setRaza(dto.getRaza());
        m.setDuenoId(dto.getDuenoId());

        return mapToResponse(repository.save(m), token);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

private MascotaResponse mapToResponse(Mascota mascota, String token) {
    var dueno = duenoClient.obtenerDueno(mascota.getDuenoId(), token);

    return MascotaResponse.builder()
            .id(mascota.getId())
            .nombre(mascota.getNombre())
            .especie(mascota.getEspecie())
            .raza(mascota.getRaza())
            .dueno(dueno)
            .build();
}
}