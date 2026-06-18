package com.example.citas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitasResponse {
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String tipo;
    private String estado;
    
    private Long mascotaId;
    private Long veterinarioId;
    private Long duenoId;
    
    // Asegúrate de que existan estas dos propiedades como objetos complejos:
    private VeterinarioResponse veterinario;  
    private MascotaResponse mascota; 
}