package com.tbtha.tejedoraypunto.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tbtha.tejedoraypunto.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepositories extends CrudRepository <Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
