package com.tbtha.tejedoraypunto.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tbtha.tejedoraypunto.entities.Boleta;
import com.tbtha.tejedoraypunto.entities.DetalleBoleta;
import com.tbtha.tejedoraypunto.repositories.BoletaRepositories;
import com.tbtha.tejedoraypunto.repositories.ProductoRepositories;

@Service
public class BoletaServicesImpl implements BoletaServices {
    
    @Autowired
    private BoletaRepositories boletaRepositories;
    
    @Autowired
    private ProductoRepositories productoRepositories;

    @Override
    public Boleta crear(Boleta boleta) {
        // Calcular el total basado en los detalles
        double total = 0.0;
        double totalNeto = 0.0;
        double totalIva = 0.0;
        
        if (boleta.getDetalles() != null) {
            for (DetalleBoleta detalle : boleta.getDetalles()) {
                detalle.setBoleta(boleta);
                
                // Calcular neto e IVA por detalle (precioUnitario incluye IVA)
                double netoUnitario = detalle.getPrecioUnitario() / 1.19;
                double ivaUnitario = detalle.getPrecioUnitario() - netoUnitario;
                
                detalle.setNeto(netoUnitario * detalle.getCantidad());
                detalle.setIva(ivaUnitario * detalle.getCantidad());
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                
                total += detalle.getSubtotal();
                totalNeto += detalle.getNeto();
                totalIva += detalle.getIva();
                
                // Actualizar stock del producto
                var producto = productoRepositories.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoRepositories.save(producto);
            }
        }
        
        boleta.setTotal(total);
        boleta.setNeto(totalNeto);
        boleta.setIva(totalIva);
        return boletaRepositories.save(boleta);
    }

    @Override
    public Boleta obtenerId(Long id) {
        return boletaRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
    }

    @Override
    public List<Boleta> listarTodas() {
        return (List<Boleta>) boletaRepositories.findAll();
    }

    @Override
    public List<Boleta> listarPorUsuario(Long usuarioId) {
        return boletaRepositories.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Boleta> listarPorEstado(String estado) {
        return boletaRepositories.findByEstado(estado);
    }

    @Override
    public Boleta actualizar(Long id, Boleta boletaActualizada) {
        Boleta existente = obtenerId(id);
        existente.setEstado(boletaActualizada.getEstado());
        existente.setMetodoPago(boletaActualizada.getMetodoPago());
        existente.setDireccionEnvio(boletaActualizada.getDireccionEnvio());
        existente.setRegionEnvio(boletaActualizada.getRegionEnvio());
        existente.setComunaEnvio(boletaActualizada.getComunaEnvio());
        return boletaRepositories.save(existente);
    }

    @Override
    public Boleta cambiarEstado(Long id, String nuevoEstado) {
        Boleta boleta = obtenerId(id);
        boleta.setEstado(nuevoEstado);
        return boletaRepositories.save(boleta);
    }

    @Override
    public void eliminar(Long id) {
        if (!boletaRepositories.existsById(id)) {
            throw new RuntimeException("Boleta no encontrada");
        }
        boletaRepositories.deleteById(id);
    }
}
