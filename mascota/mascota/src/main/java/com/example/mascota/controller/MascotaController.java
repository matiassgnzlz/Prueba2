package com.example.mascota.controller;

import com.example.mascota.dto.ApiResponse;
import com.example.mascota.dto.MascotaDTO;
import com.example.mascota.dto.MascotaResponse;
import com.example.mascota.service.MascotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascota")
@RequiredArgsConstructor
public class MascotaController {

   private final MascotaService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<MascotaResponse>> crear(
            @Valid @RequestBody MascotaDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<MascotaResponse>builder()
                        .success(true)
                        .message("Mascota creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<MascotaResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<MascotaResponse>>builder()
                        .success(true)
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
        public ResponseEntity<ApiResponse<EntityModel<MascotaResponse>>> obtener(
        @PathVariable Long id,
        @RequestHeader("Authorization") String token) {
                MascotaResponse mascota = service.obtener(id, token);
                EntityModel<MascotaResponse> recurso = EntityModel.of(mascota);
                recurso.add(linkTo(methodOn(MascotaController.class).obtener(id, token)).withSelfRel());
                recurso.add(linkTo(methodOn(MascotaController.class).listar(token)).withRel("all"));
                recurso.add(linkTo(methodOn(MascotaController.class).actualizar(id, null, token)).withRel("update"));
                recurso.add(linkTo(methodOn(MascotaController.class).eliminar(id)).withRel("delete"));
                return ResponseEntity.ok(
                ApiResponse.<EntityModel<MascotaResponse>>builder()
                .success(true)
                .message("Mascota obtenido")
                .data(recurso)
                .build()
                );
        }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<MascotaResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<MascotaResponse>builder()
                        .success(true)
                        .message("Mascota actualizado")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Mascota eliminado")
                        .build()
        );
    } 
}
