package com.example.citas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) 
public class MascotaResponse {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
}