package com.tbtha.tejedoraypunto.services;

import java.util.List;
import com.tbtha.tejedoraypunto.entities.DetalleBoleta;

public interface DetalleBoletaServices {
    DetalleBoleta crear(DetalleBoleta detalleBoleta);
    DetalleBoleta obtenerId(Long id);
    List<DetalleBoleta> listarTodas();
    List<DetalleBoleta> listarPorBoleta(Long boletaId);
    DetalleBoleta actualizar(Long id, DetalleBoleta detalleActualizado);
    void eliminar(Long id);
}
