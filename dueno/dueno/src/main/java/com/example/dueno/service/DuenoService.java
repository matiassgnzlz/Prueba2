package com.example.dueno.service;


import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dueno.dto.DuenoDTO;
import com.example.dueno.model.Dueno;
import com.example.dueno.repository.DuenoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class DuenoService {
    private final DuenoRepository repository;

    public Dueno crear(DuenoDTO dto) {
        log.info("Crear Dueño", keyValue("nombre", dto.getNombre()));

    Dueno d = Dueno.builder()
                .rut(dto.getRut())
                .nombre(dto.getNombre())
                .contacto(dto.getContacto())
                .build();
            return repository.save(d);
    }

    public List<Dueno> listar() {
        log.info("Listar Dueño");
        return repository.findAll();
    }

    public Dueno obtener(Long id) {
        log.info("Obtener Dueño", keyValue("id", id));

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dueno no encontrado"));
    }

    public Dueno actualizar(Long id, DuenoDTO dto) {
        log.info("Actualizar autor", keyValue("id", id));

        Dueno d = obtener(id);
        d.setRut(dto.getRut());
        d.setNombre(dto.getNombre());
        d.setContacto(dto.getContacto());

        return repository.save(d);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar dueno", keyValue("id", id));
        repository.deleteById(id);
    }
}
