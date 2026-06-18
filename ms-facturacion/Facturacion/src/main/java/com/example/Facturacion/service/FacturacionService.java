package com.example.Facturacion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.repository.FacturacionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturacionService {
    private static final String Facturacion = null;
    private final FacturacionRepository repository;
    
    public Facturacion crear(FacturacionDTO dto) {
        
        Facturacion factura = new Facturacion();

            factura.setMascotaId(dto.getMascotaId());
            factura.setCitaId(dto.getCitaId());
            factura.setMonto(dto.getMonto());
            factura.setEstado(dto.getEstado());
            factura.setFecha(dto.getFecha());

        return repository.save(factura);
    }

    public List<Facturacion> listar() {
        return repository.findAll();
    }

    public Facturacion obtener(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Factura no encontrada"));
    }

    public Facturacion actualizar(Long id, FacturacionDTO dto) {

        Facturacion factura = obtener(id);

        factura.setMascotaId(dto.getMascotaId());
        factura.setCitaId(dto.getCitaId());
        factura.setMonto(dto.getMonto());
        factura.setEstado(dto.getEstado());
        factura.setFecha(dto.getFecha());

        return repository.save(factura);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
