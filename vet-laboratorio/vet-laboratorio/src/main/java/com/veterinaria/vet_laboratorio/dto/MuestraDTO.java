package com.veterinaria.vet_laboratorio.dto;


import java.time.LocalDate;
 
import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.model.Muestra;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MuestraDTO {
    private Long id;
    private String tipo_muestra;
    private String descripcion;
    private LocalDate fecha_toma;
    private String estado;
    private String resultado;
    private LocalDate fecha_resultado;
    private Long laboratorio_id;
 
    public Muestra toModel(Laboratorio laboratorio) {
        return new Muestra(id, tipo_muestra, descripcion, fecha_toma,
                estado, resultado, fecha_resultado, laboratorio);
    }
 
    public static MuestraDTO fromModel(Muestra muestra) {
        if (muestra == null) return null;
 
        return new MuestraDTO(
                muestra.getId(),
                muestra.getTipo_muestra(),
                muestra.getDescripcion(),
                muestra.getFecha_toma(),
                muestra.getEstado(),
                muestra.getResultado(),
                muestra.getFecha_resultado(),
                muestra.getLaboratorio() != null ? muestra.getLaboratorio().getId() : null
        );
    }

}
