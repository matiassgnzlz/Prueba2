package com.example.dueno.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.dueno.dto.ApiResponse;
import com.example.dueno.dto.DuenoDTO;
import com.example.dueno.model.Dueno;
import com.example.dueno.service.DuenoService;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Duenos", description = "Operaciones relacionadas con duenos")
@RestController
@RequestMapping("/api/v1/dueno")
@RequiredArgsConstructor
public class DuenoController {

    private final DuenoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<Dueno>> crear(@Valid @RequestBody DuenoDTO dto) {

        Dueno dueno = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Dueno>builder()
                        .success(true)
                        .message("Dueno creado")
                        .data(dueno)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Dueno>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Dueno>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EntityModel<Dueno>>> obtener(@PathVariable Long id) {

    Dueno dueno = service.obtener(id);
    EntityModel<Dueno> recurso = EntityModel.of(dueno);

        recurso.add(linkTo(methodOn(DuenoController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(DuenoController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(DuenoController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(DuenoController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
            ApiResponse.<EntityModel<Dueno>>builder()
                .success(true)
                .message("Dueno obtenido")
                .data(recurso)
                .build()
    );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Dueno>> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody DuenoDTO dto) {

        Dueno dueno = service.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Dueno>builder()
                        .success(true)
                        .message("Dueno actualizado")
                        .data(dueno)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Dueno eliminado")
                        .build()
        );
    }
}