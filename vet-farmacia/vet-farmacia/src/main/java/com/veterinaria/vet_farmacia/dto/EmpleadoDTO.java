package com.veterinaria.vet_farmacia.dto;

 
import com.veterinaria.vet_farmacia.model.Empleado;
import com.veterinaria.vet_farmacia.model.Farmacia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoDTO {
    private Long id;
    private String nombre_empleado;
    private String cargo;
    private Long farmacia_id;
 
    public Empleado toModel(Farmacia farmacia) {
        return new Empleado(id, nombre_empleado, cargo, farmacia);
    }
 
    public static EmpleadoDTO fromModel(Empleado empleado) {
        if (empleado == null) return null;
 
        return new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre_empleado(),
                empleado.getCargo(),
                empleado.getFarmacia() != null ? empleado.getFarmacia().getId() : null
        );
    }
}
