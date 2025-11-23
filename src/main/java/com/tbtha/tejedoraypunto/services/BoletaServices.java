package com.tbtha.tejedoraypunto.services;

import java.util.List;
import com.tbtha.tejedoraypunto.entities.Boleta;

public interface BoletaServices {
    Boleta crear(Boleta boleta);
    Boleta obtenerId(Long id);
    List<Boleta> listarTodas();
    List<Boleta> listarPorUsuario(Long usuarioId);
    List<Boleta> listarPorEstado(String estado);
    Boleta actualizar(Long id, Boleta boletaActualizada);
    Boleta cambiarEstado(Long id, String nuevoEstado);
    void eliminar(Long id);
}
