package com.example.HistoriaClinica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HistoriaClinica.client.MascotaClient;
import com.example.HistoriaClinica.dto.HistoriaClinicaDTO;
import com.example.HistoriaClinica.dto.MascotaDTO;
import com.example.HistoriaClinica.dto.MascotaResponse;
import com.example.HistoriaClinica.model.HistoriaClinica;
import com.example.HistoriaClinica.repository.HistoriaClinicaRepository;

@Service
public class HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository repository;

    @Autowired
    private MascotaClient mascotaClient;

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

