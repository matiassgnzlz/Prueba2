package com.veterinaria.vet_laboratorio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.vet_laboratorio.model.Laboratorio;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long>{
    List<Laboratorio> findByPersonaACargo(String personaACargo);
}
