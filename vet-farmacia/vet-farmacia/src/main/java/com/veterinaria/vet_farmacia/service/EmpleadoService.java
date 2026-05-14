package com.veterinaria.vet_farmacia.service;

import java.util.List;
 
import org.springframework.stereotype.Service;

import com.veterinaria.vet_farmacia.model.Empleado;
import com.veterinaria.vet_farmacia.model.Farmacia;
import com.veterinaria.vet_farmacia.repository.EmpleadoRepository;
import com.veterinaria.vet_farmacia.repository.FarmaciaRepository;
 

 
@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final FarmaciaRepository farmaciaRepository;
 
    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           FarmaciaRepository farmaciaRepository) {
        this.empleadoRepository = empleadoRepository;
        this.farmaciaRepository = farmaciaRepository;
    }
 
   
    public Empleado buscarEmpleadoModel(Long id) {
        return empleadoRepository.findById(id).get();
    }
 
    
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }
 
    
    public List<Empleado> listarPorFarmacia(Long farmaciaId) {
        return empleadoRepository.findByFarmaciaId(farmaciaId);
    }
 
    
    public Empleado guardar(Empleado empleado, Long farmaciaId) {
        Farmacia farmacia = farmaciaRepository.findById(farmaciaId).get();
        empleado.setFarmacia(farmacia);
        return empleadoRepository.save(empleado);
    }
 
    
    public Empleado actualizar(Long id, Empleado empleado, Long farmaciaId) {
        Empleado e = buscarEmpleadoModel(id);
        Farmacia farmacia = farmaciaRepository.findById(farmaciaId).get();
        e.setNombre_empleado(empleado.getNombre_empleado());
        e.setCargo(empleado.getCargo());
        e.setFarmacia(farmacia);
        return empleadoRepository.save(e);
    }
 
    
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }

}
