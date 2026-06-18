package com.example.Vacunacion.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class VacunacionDTO {
    private Long mascotaId;
    private String vacuna;
    private LocalDate fechaAplicacion;
    private LocalDate proximaDosis;
}
