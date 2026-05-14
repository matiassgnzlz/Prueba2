package com.example.HistoriaClinica.dto;

import lombok.Data;

@Data
public class MascotaDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Long duenoId;
}
