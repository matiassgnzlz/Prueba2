package com.veterinaria.vet_laboratorio.model;

import java.time.LocalDate;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "muestra")
public class Muestra {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "tipo_muestra")
    private String tipo_muestra;
 
    @Column(name = "descripcion")
    private String descripcion;
 
    @Column(name = "fecha_toma")
    private LocalDate fecha_toma;
 
    @Column(name = "estado")
    private String estado;
 
    @Column(name = "resultado")
    private String resultado;
 
    @Column(name = "fecha_resultado")
    private LocalDate fecha_resultado;
 
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

}
