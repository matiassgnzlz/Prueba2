package com.example.Facturacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.service.FacturacionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/facturacion")
@RequiredArgsConstructor
public class FacturacionController {
    private final FacturacionService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<Facturacion>> crear(
            @Valid @RequestBody FacturacionDTO dto) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Facturacion>builder()
                        .success(true)
                        .message("Factura creada")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<List<Facturacion>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Facturacion>>builder()
                        .success(true)
                        .message("Facturas encontradas")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Facturacion>> obtener(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Facturacion>builder()
                        .success(true)
                        .message("Factura encontrada")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Facturacion>> actualizar(
            @PathVariable Long id,
            @RequestBody FacturacionDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.<Facturacion>builder()
                        .success(true)
                        .message("Factura actualizada")
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Factura eliminada")
                        .build()
        );
    }
}
