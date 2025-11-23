package com.tbtha.tejedoraypunto.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tbtha.tejedoraypunto.entities.Boleta;
import java.util.List;

@Repository
public interface BoletaRepositories extends CrudRepository<Boleta, Long> {
    List<Boleta> findByUsuarioId(Long usuarioId);
    List<Boleta> findByEstado(String estado);
}
