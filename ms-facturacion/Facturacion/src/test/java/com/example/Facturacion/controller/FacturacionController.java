package com.example.Facturacion.controller;

import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.service.FacturacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturacion")
@RequiredArgsConstructor
public class FacturacionController {

        private final FacturacionService service;

        @PostMapping
        public ResponseEntity<Facturacion> crear(
                @Valid @RequestBody FacturacionDTO dto) {

                return ResponseEntity.status(201)
                        .body(service.crear(dto));
        }

        @GetMapping
        public ResponseEntity<List<Facturacion>> listar() {

                return ResponseEntity.ok(service.listar());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Facturacion> obtener(
                @PathVariable Long id) {

                return ResponseEntity.ok(service.obtener(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Facturacion> actualizar(
                @PathVariable Long id,
                @RequestBody FacturacionDTO dto) {

                return ResponseEntity.ok(
                        service.actualizar(id, dto)
                );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminar(
                @PathVariable Long id) {

                service.eliminar(id);

                return ResponseEntity.ok("Factura eliminada correctamente");
        }
        

}