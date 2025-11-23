package com.tbtha.tejedoraypunto.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tbtha.tejedoraypunto.entities.DetalleBoleta;
import java.util.List;

@Repository
public interface DetalleBoletaRepositories extends CrudRepository<DetalleBoleta, Long> {
    List<DetalleBoleta> findByBoletaId(Long boletaId);
}
