package com.example.mascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mascota.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long>{

}
