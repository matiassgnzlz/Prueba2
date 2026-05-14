package com.example.HistoriaClinica.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistoriaClinicaDTO {
    private Long id;
    private LocalDateTime fecha;
    private String descripcion;
    private String tratamiento;
    private String veterinario;
    
    private MascotaDTO mascota; 
}
