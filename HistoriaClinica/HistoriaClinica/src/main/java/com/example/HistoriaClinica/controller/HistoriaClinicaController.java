package com.example.HistoriaClinica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HistoriaClinica.dto.ApiResponse;
import com.example.HistoriaClinica.dto.HistoriaClinicaDTO;
import com.example.HistoriaClinica.model.HistoriaClinica;
import com.example.HistoriaClinica.service.HistoriaClinicaService;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;



import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "HistoriaClinica", description = "Operaciones relacionadas con HistoriaClinica")
@RestController
@RequestMapping("/api/v1/historiaclinica")
@RequiredArgsConstructor
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody HistoriaClinica historia, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.guardar(historia, token));
    }

@GetMapping("/{id}")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Operation(summary = "Obtener historia clínica completa con enlaces HATEOAS")
public ResponseEntity<ApiResponse<EntityModel<HistoriaClinicaDTO>>> obtenerCompleta(
        @PathVariable Long id, 
        @RequestHeader("Authorization") String token) {
    
    HistoriaClinicaDTO dto = service.obtenerHistoriaCompleta(id, token);

    EntityModel<HistoriaClinicaDTO> recurso = EntityModel.of(dto);
    
    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistoriaClinicaController.class).obtenerCompleta(id, token))
            .withSelfRel()
    );
    
    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistoriaClinicaController.class).listarPorMascota(dto.getMascota().getId()))
            .withRel("all-by-mascota")
    );
    
    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistoriaClinicaController.class).actualizar(id, null, token))
            .withRel("update")
    );
    
    recurso.add(
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistoriaClinicaController.class).eliminar(id, token))
            .withRel("delete")
    );
    
    return ResponseEntity.ok(
        ApiResponse.<EntityModel<HistoriaClinicaDTO>>builder()
            .success(true)
            .message("Historia clínica obtenida")
            .data(recurso)
            .build()
    );
}

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<?> listarPorMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(service.findByMascota(mascotaId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id, 
            @RequestBody HistoriaClinica historia, 
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.actualizar(id, historia, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long id, 
            @RequestHeader("Authorization") String token) {
        service.eliminar(id, token);
        return ResponseEntity.ok("Historia clínica eliminada correctamente");
    }
}