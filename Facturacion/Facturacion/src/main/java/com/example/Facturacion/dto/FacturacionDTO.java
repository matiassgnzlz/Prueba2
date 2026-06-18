package com.example.Facturacion.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FacturacionDTO {
    private Long mascotaId;
    private Long citaId;
    private Double monto;
    private String estado;
    private LocalDate fecha;
}
