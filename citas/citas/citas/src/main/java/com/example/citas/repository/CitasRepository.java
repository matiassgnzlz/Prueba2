package com.example.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.citas.model.Citas;

@Repository
public interface CitasRepository extends JpaRepository<Citas, Long>{
}
