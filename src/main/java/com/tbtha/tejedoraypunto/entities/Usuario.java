package com.tbtha.tejedoraypunto.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Usuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String rut;
    private String nombre;
    private String apellidos;
    private String email;
    private String direccion;
    private String region;
    private String comuna;
    private String tipoUsuario = "CLIENTE";
    private String password;
    private Boolean activo = true;
    
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name="tipo_usuario_id")
    // private TipoUsuario tipoUsuario;
    
}
