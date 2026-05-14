package com.example.veterinario.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VeterinarioResponse {

    private Long id;
    private String nombre;
    private String especialidad;
    private String matricula;  
}