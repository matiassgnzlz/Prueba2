package com.veterinaria.vet_farmacia.controller;


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

import com.veterinaria.vet_farmacia.dto.EmpleadoDTO;
import com.veterinaria.vet_farmacia.model.Empleado;
import com.veterinaria.vet_farmacia.service.EmpleadoService;
 

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;
 
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }
 
   
    @GetMapping()
    public ResponseEntity<List<EmpleadoDTO>> getListar() {
        List<Empleado> empleados = empleadoService.listar();
        List<EmpleadoDTO> dtos = empleados.stream().map(EmpleadoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    
    @GetMapping("/farmacia/{farmaciaId}")
    public ResponseEntity<List<EmpleadoDTO>> getListarPorFarmacia(@PathVariable Long farmaciaId) {
        List<Empleado> empleados = empleadoService.listarPorFarmacia(farmaciaId);
        List<EmpleadoDTO> dtos = empleados.stream().map(EmpleadoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerPorId(@PathVariable Long id) {
        Empleado empleado = empleadoService.buscarEmpleadoModel(id);
        return ResponseEntity.ok(EmpleadoDTO.fromModel(empleado));
    }
 
    
    @PostMapping()
    public ResponseEntity<EmpleadoDTO> postSave(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = new Empleado(null, empleadoDTO.getNombre_empleado(), empleadoDTO.getCargo(), null);
        Empleado resultado = empleadoService.guardar(empleado, empleadoDTO.getFarmacia_id());
        return ResponseEntity.ok(EmpleadoDTO.fromModel(resultado));
    }
 
    
    @PutMapping("/modificar/{id}")
    public ResponseEntity<EmpleadoDTO> putModificar(@PathVariable Long id,
                                                     @RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = new Empleado(null, empleadoDTO.getNombre_empleado(), empleadoDTO.getCargo(), null);
        Empleado resultado = empleadoService.actualizar(id, empleado, empleadoDTO.getFarmacia_id());
        if (resultado != null) {
            return ResponseEntity.ok(EmpleadoDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }
 
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
