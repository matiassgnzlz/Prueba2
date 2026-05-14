package com.veterinaria.vet_laboratorio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.repository.LaboratorioRepository;

@Service
public class LaboratorioService {
    private final LaboratorioRepository laboratorioRepository;
 
    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }
 
    // buscar
    public Laboratorio buscarLaboratorioModel(Long id) {
        return laboratorioRepository.findById(id).get();
    }
 
    // listar todos
    public List<Laboratorio> listar() {
        return laboratorioRepository.findAll();
    }
 
    // listar por persona a cargo
    public List<Laboratorio> listarPorPersona(String persona) {
        return laboratorioRepository.findByPersona_a_cargo(persona);
    }
 
    // guardar
    public Laboratorio guardar(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }
 
    // modificar
    public Laboratorio actualizar(Long id, Laboratorio laboratorio) {
        Laboratorio l = buscarLaboratorioModel(id);
        l.setNombre_laboratorio(laboratorio.getNombre_laboratorio());
        l.setPersona_a_cargo(laboratorio.getPersona_a_cargo());
        l.setTelefono(laboratorio.getTelefono());
        l.setDireccion(laboratorio.getDireccion());
        l.setFecha_registro(laboratorio.getFecha_registro());
        return laboratorioRepository.save(l);
    }
 
    // eliminar
    public void eliminar(Long id) {
        laboratorioRepository.deleteById(id);
    }

}
