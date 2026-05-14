package com.example.mascota.dto;

import lombok.*;

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