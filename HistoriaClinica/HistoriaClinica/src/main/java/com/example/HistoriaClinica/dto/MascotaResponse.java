package com.example.HistoriaClinica.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MascotaResponse {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private DuenoResponse dueno;
}