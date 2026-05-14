package com.example.veterinario.controller;

import com.example.veterinario.dto.VeterinarioDTO;
import com.example.veterinario.dto.VeterinarioResponse;
import com.example.veterinario.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    @Autowired
    private VeterinarioService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> listar(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.listar(token));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> obtener(@PathVariable Long id, 
                                                       @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.obtener(id, token));
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
