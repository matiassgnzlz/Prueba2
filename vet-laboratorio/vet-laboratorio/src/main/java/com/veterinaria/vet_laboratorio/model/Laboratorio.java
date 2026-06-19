package com.veterinaria.vet_laboratorio.model;

import java.time.LocalDate;
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonManagedReference;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "laboratorio")

public class Laboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "nombre_laboratorio")
    private String nombre_laboratorio;
 
    @Column(name = "persona_a_cargo")
    private String personaACargo;
 
    @Column(name = "telefono")
    private String telefono;
 
    @Column(name = "direccion")
    private String direccion;
 
    @Column(name = "fecha_registro")
    private LocalDate fecha_registro;
 
    @JsonManagedReference
    @OneToMany(mappedBy = "laboratorio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Muestra> muestras;

}
