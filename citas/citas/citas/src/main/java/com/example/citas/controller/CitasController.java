package com.example.citas.controller;

import com.example.citas.dto.ApiResponse;
import com.example.citas.dto.CitasDTO;
import com.example.citas.dto.CitasResponse;
import com.example.citas.service.CitasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Citas", description = "Operaciones relacionadas con citas")
@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
public class CitasController {

    private final CitasService service;

@GetMapping("/{id}")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Operation(summary = "Obtener una cita por su ID con enlaces HATEOAS")
public ResponseEntity<ApiResponse<EntityModel<CitasResponse>>> obtenerPorId(
        @PathVariable Long id, 
        @RequestHeader("Authorization") String token) {

    CitasResponse data = service.obtenerPorId(id, token);

    EntityModel<CitasResponse> recurso = EntityModel.of(data);

    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CitasController.class).obtenerPorId(id, token))
            .withSelfRel()
    );

    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CitasController.class).listarTodas(token))
            .withRel("all")
    );
    
    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CitasController.class).actualizar(id, null, token))
            .withRel("update")
    );

    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CitasController.class).cambiarEstado(id, null, token))
            .withRel("change-status")
    );
    
    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CitasController.class).eliminar(id, token))
            .withRel("delete")
    );
    
    return ResponseEntity.ok(
        ApiResponse.<EntityModel<CitasResponse>>builder()
            .success(true)
            .message("Cita encontrada")
            .data(recurso)
            .build()
    );
}

    @PostMapping("/agendar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<CitasResponse>> agendar(
            @RequestBody CitasDTO dto, 
            @RequestHeader("Authorization") String token) {
        
        CitasResponse data = service.agendarCitas(dto, token);
        
        ApiResponse<CitasResponse> response = ApiResponse.<CitasResponse>builder()
                .success(true)
                .message("Cita agendada con éxito")
                .data(data)
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<List<CitasResponse>>> listarTodas(
            @RequestHeader("Authorization") String token) {
        
        List<CitasResponse> data = service.obtenerTodas(token);
        
        ApiResponse<List<CitasResponse>> response = ApiResponse.<List<CitasResponse>>builder()
                .success(true)
                .message("Lista de citas obtenida")
                .data(data)
                .build();
        
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CitasResponse>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado,
            @RequestHeader("Authorization") String token) {
        
        CitasResponse data = service.cambiarEstado(id, nuevoEstado, token);
        
        ApiResponse<CitasResponse> response = ApiResponse.<CitasResponse>builder()
                .success(true)
                .message("Estado de la cita actualizado")
                .data(data)
                .build();
        
        return ResponseEntity.ok(response);
            }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<CitasResponse>> actualizar(
            @PathVariable Long id,
            @RequestBody CitasDTO dto,
            @RequestHeader("Authorization") String token) {
        
        CitasResponse data = service.actualizarCita(id, dto, token);
        
        ApiResponse<CitasResponse> response = ApiResponse.<CitasResponse>builder()
                .success(true)
                .message("Cita actualizada con éxito")
                .data(data)
                .build();
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        service.eliminarCita(id, token);
        
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Cita eliminada correctamente")
                .data(null)
                .build();
        
        return ResponseEntity.ok(response);
    }
}