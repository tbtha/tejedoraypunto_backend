package com.tbtha.tejedoraypunto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tbtha.tejedoraypunto.entities.Usuario;
import com.tbtha.tejedoraypunto.repositories.UsuarioRepositories;

@Service
public class UsuarioServicesImpl implements UsuarioServices {
    
    @Autowired
    private UsuarioRepositories usuarioRepositories;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario crear(Usuario usuario){
        // Encriptar la contraseña antes de guardar
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // El rol ya tiene valor por defecto "CLIENTE" en la entidad
        // No es necesario validar, setRol solo si quieres forzarlo siempre
        return usuarioRepositories.save(usuario);
    }
    @Override
    public Usuario obtenerId(Long id) {
        return usuarioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    @Override 
    public List<Usuario> listarTodas() {
        return (List<Usuario>) usuarioRepositories.findAll();
    }
    @Override
    public void eliminar(Long id) {
        if (!usuarioRepositories.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
       usuarioRepositories.deleteById(id);
    }   
    @Override
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario existente = obtenerId(id);
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setApellidos(usuarioActualizado.getApellidos());
        existente.setEmail(usuarioActualizado.getEmail());
        existente.setDireccion(usuarioActualizado.getDireccion());
        existente.setRegion(usuarioActualizado.getRegion());
        existente.setComuna(usuarioActualizado.getComuna());
        existente.setRol(usuarioActualizado.getRol());
        // No actualizar password aquí, debería tener un endpoint separado
        return usuarioRepositories.save(existente); 
    }   
    @Override
    public Usuario desactivar(Long id){
        Usuario usuario = obtenerId(id);
        usuario.setActivo(false);
        return usuarioRepositories.save(usuario);
    }
    
    @Override
    public Usuario activar(Long id){
        Usuario usuario = obtenerId(id);
        usuario.setActivo(true);
        return usuarioRepositories.save(usuario);
    }

}
