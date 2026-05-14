package com.example.citas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="citas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String tipo;
    private String estado;

    @Column(name = "veterinario_id")
    private Long veterinarioId; 

    @Column(name = "mascota_id")
    private Long mascotaId;

    @Column(name = "dueno_id")
    private Long duenoId;
}