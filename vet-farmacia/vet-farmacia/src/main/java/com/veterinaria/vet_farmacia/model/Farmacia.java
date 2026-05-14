package com.veterinaria.vet_farmacia.model;

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
@Table(name = "farmacia")
public class Farmacia {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "nombre_farmacia")
    private String nombre_farmacia;
 
    @JsonManagedReference
    @OneToMany(mappedBy = "farmacia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados;
}
