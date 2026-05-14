package com.veterinaria.vet_receta.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.vet_receta.model.Receta;


@Repository 
public interface RecetaRepository extends JpaRepository <Receta, Long>{

}
