package com.veterinaria.vet_laboratorio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.model.Muestra;
import com.veterinaria.vet_laboratorio.repository.LaboratorioRepository;
import com.veterinaria.vet_laboratorio.repository.MuestraRepository;

@Service
public class MuestraService {
    private final MuestraRepository muestraRepository;
    private final LaboratorioRepository laboratorioRepository;
 
    public MuestraService(MuestraRepository muestraRepository,
                          LaboratorioRepository laboratorioRepository) {
        this.muestraRepository = muestraRepository;
        this.laboratorioRepository = laboratorioRepository;
    }
 
    // buscar
    public Muestra buscarMuestraModel(Long id) {
        return muestraRepository.findById(id).get();
    }
 
    // listar todos
    public List<Muestra> listar() {
        return muestraRepository.findAll();
    }
 
    // listar por laboratorio
    public List<Muestra> listarPorLaboratorio(Long laboratorioId) {
        return muestraRepository.findByLaboratorioId(laboratorioId);
    }
 
    // listar por estado
    public List<Muestra> listarPorEstado(String estado) {
        return muestraRepository.findByEstado(estado);
    }
 
    // listar por tipo
public List<Muestra> listarPorTipo(String tipo) {
    return muestraRepository.findByTipoMuestra(tipo); // <--- CORRECTO
}
 
    // guardar
    public Muestra guardar(Muestra muestra, Long laboratorioId) {
        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId).get();
        muestra.setLaboratorio(laboratorio);
        return muestraRepository.save(muestra);
    }
 
    // modificar
public Muestra actualizar(Long id, Muestra muestra, Long laboratorioId) {
        Muestra m = buscarMuestraModel(id);
        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId).get();
        
        // CAMBIOS APLICADOS AQUÍ: getters y setters en camelCase
        m.setTipoMuestra(muestra.getTipoMuestra());
        m.setDescripcion(muestra.getDescripcion());
        m.setFechaToma(muestra.getFechaToma());
        m.setEstado(muestra.getEstado());
        m.setResultado(muestra.getResultado());
        m.setFechaResultado(muestra.getFechaResultado());
        m.setLaboratorio(laboratorio);
        
        return muestraRepository.save(m);
    }
 
    // eliminar
    public void eliminar(Long id) {
        muestraRepository.deleteById(id);
    }
}
