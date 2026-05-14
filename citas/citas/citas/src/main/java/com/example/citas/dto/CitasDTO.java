package com.example.citas.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CitasDTO {
    private LocalDateTime fechaHora;
    private String motivo;
    private String tipo;
    private String estado;
    private Long mascotaId;
    private Long veterinarioId;
    private Long duenoId;
}