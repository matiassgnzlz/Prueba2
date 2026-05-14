package com.example.HistoriaClinica.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HistoriaClinica.model.HistoriaClinica;
import com.example.HistoriaClinica.service.HistoriaClinicaService;

@RestController
@RequestMapping("/api/historias")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService service;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody HistoriaClinica historia, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.guardar(historia, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCompleta(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.obtenerHistoriaCompleta(id, token));
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<?> listarPorMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(service.findByMascota(mascotaId));
    }
}

