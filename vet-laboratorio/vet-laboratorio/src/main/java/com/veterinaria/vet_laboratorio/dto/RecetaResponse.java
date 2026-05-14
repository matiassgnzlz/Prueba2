package com.veterinaria.vet_laboratorio.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecetaResponse {
    private Long id;
    private Long dueno_id;
    private String nom_mascota;
    private String descripcion;
    private LocalDate fec_emision;
    private String firma_digital;

}
