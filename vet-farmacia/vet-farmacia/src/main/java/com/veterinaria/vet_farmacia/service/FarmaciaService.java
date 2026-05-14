package com.veterinaria.vet_farmacia.service;

import java.util.List;
 
import org.springframework.stereotype.Service;

import com.veterinaria.vet_farmacia.model.Farmacia;
import com.veterinaria.vet_farmacia.repository.FarmaciaRepository;
 

@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;
 
    public FarmaciaService(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }
 
    // buscar
    public Farmacia buscarFarmaciaModel(Long id) {
        return farmaciaRepository.findById(id).get();
    }
 
    // listar
    public List<Farmacia> listar() {
        return farmaciaRepository.findAll();
    }
 
    
    public Farmacia guardar(Farmacia farmacia) {
        return farmaciaRepository.save(farmacia);
    }
 
    
    public Farmacia actualizar(Long id, Farmacia farmacia) {
        Farmacia f = buscarFarmaciaModel(id);
        f.setNombre_farmacia(farmacia.getNombre_farmacia());
        return farmaciaRepository.save(f);
    }
 
    // eliminar
    public void eliminar(Long id) {
        farmaciaRepository.deleteById(id);
    }
}
