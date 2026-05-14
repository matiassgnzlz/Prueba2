package com.example.mascota.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class MascotaDTO {
    private Long id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotNull(message = "El ID del dueño es obligatorio")
    private Long duenoId;

}
