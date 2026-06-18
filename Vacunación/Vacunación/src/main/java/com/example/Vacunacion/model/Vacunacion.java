package com.example.Vacunacion.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "vacunacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacunacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mascotaId;
    @Column(nullable = false)
    private String vacuna;
    private LocalDate fechaAplicacion;
    private LocalDate proximaDosis;
}
