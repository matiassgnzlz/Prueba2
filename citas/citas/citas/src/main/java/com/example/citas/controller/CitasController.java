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

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitasController {

    private final CitasService service;

    @GetMapping("/{id}") 
        public ResponseEntity<ApiResponse<CitasResponse>> obtenerPorId(
        @PathVariable Long id, 
        @RequestHeader("Authorization") String token
        ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Cita encontrada", service.obtenerPorId(id, token), null));
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
}