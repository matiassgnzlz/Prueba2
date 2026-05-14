package com.veterinaria.vet_farmacia.dto;
 
import com.veterinaria.vet_farmacia.model.Farmacia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmaciaDTO {
    private Long id;
    private String nombre_farmacia;
 
    public Farmacia toModel() {
        return new Farmacia(id, nombre_farmacia, null);
    }
 
    public static FarmaciaDTO fromModel(Farmacia farmacia) {
        if (farmacia == null) return null;
 
        return new FarmaciaDTO(
                farmacia.getId(),
                farmacia.getNombre_farmacia()
        );
    }

}
