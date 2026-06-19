package com.veterinaria.vet_laboratorio.dto;

import java.time.LocalDate;
 
import com.veterinaria.vet_laboratorio.model.Laboratorio;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaboratorioDTO {
    private Long id;
    private String nombre_laboratorio;
    private String persona_a_cargo;
    private String telefono;
    private String direccion;
    private LocalDate fecha_registro;
 
    public Laboratorio toModel() {
        return new Laboratorio(id, nombre_laboratorio, persona_a_cargo,
                telefono, direccion, fecha_registro, null);
    }
 
    public static LaboratorioDTO fromModel(Laboratorio laboratorio) {
        if (laboratorio == null) return null;
 
return new LaboratorioDTO(
                laboratorio.getId(),
                laboratorio.getNombre_laboratorio(),
                laboratorio.getPersonaACargo(), 
                laboratorio.getTelefono(),
                laboratorio.getDireccion(),
                laboratorio.getFecha_registro()
        );
    }

}
