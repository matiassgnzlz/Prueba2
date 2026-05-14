package com.veterinaria.vet_farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.vet_farmacia.model.Farmacia;

 
@Repository
public interface FarmaciaRepository extends JpaRepository <Farmacia, Long> {

}