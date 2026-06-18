package com.example.veterinario.controller;

import com.example.veterinario.dto.ApiResponse;
import com.example.veterinario.dto.VeterinarioDTO;
import com.example.veterinario.dto.VeterinarioResponse;
import com.example.veterinario.service.VeterinarioService;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/veterinario")
public class VeterinarioController {

    private final VeterinarioService service;

    VeterinarioController(VeterinarioService service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> listar(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.listar(token));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
        public ResponseEntity<ApiResponse<EntityModel<VeterinarioResponse>>> obtener(
        @PathVariable Long id,
        @RequestHeader("Authorization") String token) {
                VeterinarioResponse veterinario = service.obtener(id, token);
                EntityModel<VeterinarioResponse> recurso = EntityModel.of(veterinario);
                recurso.add(linkTo(methodOn(VeterinarioController.class).obtener(id, token)).withSelfRel());
                recurso.add(linkTo(methodOn(VeterinarioController.class).listar(token)).withRel("all"));
                recurso.add(linkTo(methodOn(VeterinarioController.class).actualizar(id, null, token)).withRel("update"));
                recurso.add(linkTo(methodOn(VeterinarioController.class).eliminar(id)).withRel("delete"));
                return ResponseEntity.ok(
                ApiResponse.<EntityModel<VeterinarioResponse>>builder()
                .success(true)
                .message("Veterinario obtenido")
                .data(recurso)
                .build()
                );
        }

    @PreAuthorize("isAuthenticated()") 
    @PostMapping
    public ResponseEntity<VeterinarioResponse> crear(@RequestBody VeterinarioDTO dto, 
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto, token));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> actualizar(@PathVariable Long id, 
                                                         @RequestBody VeterinarioDTO dto, 
                                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.actualizar(id, dto, token));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
