package com.veterinaria.vet_receta.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "receta")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dueno_id")
    private Long dueno_id;

    @Column(name = "nom_mascota")
    private String nom_mascota;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fec_emision")
    private LocalDateTime fec_emision;

    
    @Column(name = "firma_digital")
    private String firma_digital;


}
