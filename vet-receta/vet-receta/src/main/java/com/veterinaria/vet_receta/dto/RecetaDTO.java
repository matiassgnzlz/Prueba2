package com.veterinaria.vet_receta.dto;

import java.time.LocalDateTime;

import com.veterinaria.vet_receta.model.Receta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecetaDTO {
    private Long id;
    private Long dueno_id;
    private String nom_mascota;
    private String descripcion;
    private LocalDateTime fec_emision;
    private String firma_digital;
 
    public Receta toModel() {
        return new Receta(id, dueno_id, nom_mascota, descripcion, fec_emision, firma_digital);
    }
 
    public static RecetaDTO fromModel(Receta receta) {
        if (receta == null) return null;

        return new RecetaDTO(
                receta.getId(),
                receta.getDueno_id(),
                receta.getNom_mascota(),
                receta.getDescripcion(),
                receta.getFec_emision(),
                receta.getFirma_digital()
        );
    }

}
