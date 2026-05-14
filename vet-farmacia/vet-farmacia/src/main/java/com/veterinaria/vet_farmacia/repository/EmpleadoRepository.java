package com.veterinaria.vet_farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.vet_farmacia.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository <Empleado, Long>{
    List<Empleado> findByFarmaciaId(Long farmaciaId);
    
}
