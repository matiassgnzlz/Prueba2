package com.veterinaria.vet_receta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veterinaria.vet_receta.model.Receta;
import com.veterinaria.vet_receta.repository.RecetaRepository;

@Service
public class RecetaService {
    private final RecetaRepository recetaRepository;

    public RecetaService (RecetaRepository recetaRepository){
        this.recetaRepository = recetaRepository;
    }

    //buscar
    public Receta buscarRecetamodel (Long id){
        return recetaRepository.findById(id).get();
    }

    //listar
    public List<Receta> listar(){
        return recetaRepository.findAll();
    }

    //guardar
    public Receta guardar(Receta receta){
        return recetaRepository.save(receta);
    } 

    //modificar
    public Receta actualizar(Long id, Receta receta) {
        Receta a = buscarRecetamodel(id);
        a.setDueno_id(receta.getDueno_id());
        a.setDescripcion(receta.getDescripcion());
        a.setNom_mascota(receta.getNom_mascota());
        a.setFec_emision(receta.getFec_emision());
        a.setFirma_digital(receta.getFirma_digital());
        return recetaRepository.save(a);
    }

    //eliminar
    public void eliminar(Long id) {
        recetaRepository.deleteById(id);
    }

}
