package com.veterinaria.vet_laboratorio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.vet_laboratorio.dto.MuestraDTO;
import com.veterinaria.vet_laboratorio.model.Muestra;
import com.veterinaria.vet_laboratorio.service.MuestraService;

@RestController
@RequestMapping("/api/muestras")
public class MuestraController {
    private final MuestraService muestraService;
 
    public MuestraController(MuestraService muestraService) {
        this.muestraService = muestraService;
    }
 
    // listar todos
    @GetMapping()
    public ResponseEntity<List<MuestraDTO>> getListar() {
        List<Muestra> muestras = muestraService.listar();
        List<MuestraDTO> dtos = muestras.stream().map(MuestraDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    // listar por laboratorio
    @GetMapping("/laboratorio/{laboratorioId}")
    public ResponseEntity<List<MuestraDTO>> getListarPorLaboratorio(@PathVariable Long laboratorioId) {
        List<Muestra> muestras = muestraService.listarPorLaboratorio(laboratorioId);
        List<MuestraDTO> dtos = muestras.stream().map(MuestraDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    // listar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MuestraDTO>> getListarPorEstado(@PathVariable String estado) {
        List<Muestra> muestras = muestraService.listarPorEstado(estado);
        List<MuestraDTO> dtos = muestras.stream().map(MuestraDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    // listar por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MuestraDTO>> getListarPorTipo(@PathVariable String tipo) {
        List<Muestra> muestras = muestraService.listarPorTipo(tipo);
        List<MuestraDTO> dtos = muestras.stream().map(MuestraDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<MuestraDTO> obtenerPorId(@PathVariable Long id) {
        Muestra muestra = muestraService.buscarMuestraModel(id);
        return ResponseEntity.ok(MuestraDTO.fromModel(muestra));
    }
 
    // guardar
    @PostMapping()
    public ResponseEntity<MuestraDTO> postSave(@RequestBody MuestraDTO muestraDTO) {
        Muestra muestra = new Muestra(null, muestraDTO.getTipo_muestra(), muestraDTO.getDescripcion(),
                muestraDTO.getFecha_toma(), muestraDTO.getEstado(),
                muestraDTO.getResultado(), muestraDTO.getFecha_resultado(), null);
        Muestra resultado = muestraService.guardar(muestra, muestraDTO.getLaboratorio_id());
        return ResponseEntity.ok(MuestraDTO.fromModel(resultado));
    }
 
    // modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<MuestraDTO> putModificar(@PathVariable Long id,
                                                    @RequestBody MuestraDTO muestraDTO) {
        Muestra muestra = new Muestra(null, muestraDTO.getTipo_muestra(), muestraDTO.getDescripcion(),
                muestraDTO.getFecha_toma(), muestraDTO.getEstado(),
                muestraDTO.getResultado(), muestraDTO.getFecha_resultado(), null);
        Muestra resultado = muestraService.actualizar(id, muestra, muestraDTO.getLaboratorio_id());
        if (resultado != null) {
            return ResponseEntity.ok(MuestraDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }
 
    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        muestraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
