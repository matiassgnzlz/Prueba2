package com.veterinaria.vet_laboratorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.vet_laboratorio.model.Muestra;

@Repository
public interface MuestraRepository extends JpaRepository <Muestra, Long> {
    List<Muestra> findByLaboratorioId(Long laboratorioId);
 
    List<Muestra> findByEstado(String estado);
 
    List<Muestra> findByTipoMuestra(String tipoMuestra); 
}