package com.tbtha.tejedoraypunto.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tbtha.tejedoraypunto.entities.DetalleBoleta;
import com.tbtha.tejedoraypunto.entities.Boleta;
import com.tbtha.tejedoraypunto.repositories.DetalleBoletaRepositories;
import com.tbtha.tejedoraypunto.repositories.BoletaRepositories;

@Service
public class DetalleBoletaServicesImpl implements DetalleBoletaServices {
    
    @Autowired
    private DetalleBoletaRepositories detalleBoletaRepositories;
    
    @Autowired
    private BoletaRepositories boletaRepositories;

    @Override
    public DetalleBoleta crear(DetalleBoleta detalleBoleta) {
        // Validar que la boleta exista y obtenerla de la BD
        if (detalleBoleta.getBoleta() == null || detalleBoleta.getBoleta().getId() == null) {
            throw new RuntimeException("Debe proporcionar una boleta válida");
        }
        Boleta boleta = boletaRepositories.findById(detalleBoleta.getBoleta().getId())
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
        detalleBoleta.setBoleta(boleta);
        
        // Calcular neto e IVA (precioUnitario incluye IVA del 19%)
        double netoUnitario = detalleBoleta.getPrecioUnitario() / 1.19;
        double ivaUnitario = detalleBoleta.getPrecioUnitario() - netoUnitario;
        
        detalleBoleta.setNeto(netoUnitario * detalleBoleta.getCantidad());
        detalleBoleta.setIva(ivaUnitario * detalleBoleta.getCantidad());
        detalleBoleta.setSubtotal(detalleBoleta.getCantidad() * detalleBoleta.getPrecioUnitario());
        
        return detalleBoletaRepositories.save(detalleBoleta);
    }

    @Override
    public DetalleBoleta obtenerId(Long id) {
        return detalleBoletaRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de boleta no encontrado"));
    }

    @Override
    public List<DetalleBoleta> listarTodas() {
        return (List<DetalleBoleta>) detalleBoletaRepositories.findAll();
    }

    @Override
    public List<DetalleBoleta> listarPorBoleta(Long boletaId) {
        return detalleBoletaRepositories.findByBoletaId(boletaId);
    }

    @Override
    public DetalleBoleta actualizar(Long id, DetalleBoleta detalleActualizado) {
        DetalleBoleta existente = obtenerId(id);
        existente.setCantidad(detalleActualizado.getCantidad());
        existente.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
        
        // Recalcular neto e IVA
        double netoUnitario = existente.getPrecioUnitario() / 1.19;
        double ivaUnitario = existente.getPrecioUnitario() - netoUnitario;
        
        existente.setNeto(netoUnitario * existente.getCantidad());
        existente.setIva(ivaUnitario * existente.getCantidad());
        existente.setSubtotal(existente.getCantidad() * existente.getPrecioUnitario());
        
        return detalleBoletaRepositories.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!detalleBoletaRepositories.existsById(id)) {
            throw new RuntimeException("Detalle de boleta no encontrado");
        }
        detalleBoletaRepositories.deleteById(id);
    }
}
