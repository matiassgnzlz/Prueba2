package com.example.vacunacion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Vacunacion.dto.VacunacionDTO;
import com.example.Vacunacion.model.Vacunacion;
import com.example.Vacunacion.repository.VacunacionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacunacionService {
    private final VacunacionRepository repository;

    public Vacunacion crear(VacunacionDTO dto) {

        Vacunacion vacuna = new Vacunacion();

        vacuna.setMascotaId(dto.getMascotaId());
        vacuna.setVacuna(dto.getVacuna());
        vacuna.setFechaAplicacion(dto.getFechaAplicacion());
        vacuna.setProximaDosis(dto.getProximaDosis());

        return repository.save(vacuna);
    }

    public List<Vacunacion> listar() {
        return repository.findAll();
    }

    public Vacunacion obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Vacuna no encontrada"));
    }

    public Vacunacion actualizar(Long id, VacunacionDTO dto) {

        Vacunacion vacuna = obtener(id);

        vacuna.setMascotaId(dto.getMascotaId());
        vacuna.setVacuna(dto.getVacuna());
        vacuna.setFechaAplicacion(dto.getFechaAplicacion());
        vacuna.setProximaDosis(dto.getProximaDosis());

        return repository.save(vacuna);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
