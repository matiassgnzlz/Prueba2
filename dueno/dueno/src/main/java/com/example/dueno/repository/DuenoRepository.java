package com.example.dueno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dueno.model.Dueno;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long>{

}
