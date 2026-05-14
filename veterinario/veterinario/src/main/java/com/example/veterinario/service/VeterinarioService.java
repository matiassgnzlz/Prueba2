package com.example.veterinario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import com.example.veterinario.dto.VeterinarioDTO;
import com.example.veterinario.dto.VeterinarioResponse;
import com.example.veterinario.model.Veterinario;
import com.example.veterinario.repository.VeterinarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository repository;

    public VeterinarioResponse crear(VeterinarioDTO dto, String token) {
        log.info("Crear Veterinario", keyValue("nombre", dto.getNombre()), keyValue("matricula", dto.getMatricula()));

        // Validación de matrícula usando findAll() para no tocar el Repository
        boolean existe = repository.findAll().stream()
                .anyMatch(v -> v.getMatricula().equals(dto.getMatricula()));

        if (existe) {
            throw new RuntimeException("La matrícula ya se encuentra registrada");
        }

        Veterinario v = repository.save(
                new Veterinario(null, dto.getNombre(), dto.getEspecialidad(), dto.getMatricula())
        );

        return mapToResponse(v, token);
    }

    public List<VeterinarioResponse> listar(String token) {
        log.info("Listar Veterinarios");
        return repository.findAll()
                .stream()
                .map(v -> mapToResponse(v, token))
                .toList();
    }

    public VeterinarioResponse obtener(Long id, String token) {
        log.info("Obtener Veterinario", keyValue("id", id));

        Veterinario v = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinario no encontrado"));

        return mapToResponse(v, token);
    }

    public VeterinarioResponse actualizar(Long id, VeterinarioDTO dto, String token) {
        log.info("Actualizar Veterinario", keyValue("id", id));

        Veterinario v = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinario no encontrado"));

        v.setNombre(dto.getNombre());
        v.setEspecialidad(dto.getEspecialidad());
        v.setMatricula(dto.getMatricula());

        return mapToResponse(repository.save(v), token);
    }

    public void eliminar(Long id) {
        log.info("Eliminar Veterinario", keyValue("id", id));
        
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Veterinario no encontrado");
        }
        
        repository.deleteById(id);
    }

    private VeterinarioResponse mapToResponse(Veterinario v, String token) {
        return VeterinarioResponse.builder()
                .id(v.getId())
                .nombre(v.getNombre())
                .especialidad(v.getEspecialidad())
                .matricula(v.getMatricula())
                .build();
    }
}
