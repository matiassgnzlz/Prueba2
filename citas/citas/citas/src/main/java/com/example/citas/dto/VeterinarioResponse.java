package com.example.citas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VeterinarioResponse {
    private Long id;
    private String nombre;

    @JsonProperty("matricula")
    private String rut;

    @JsonProperty("especialidad")
    private String contacto;
}