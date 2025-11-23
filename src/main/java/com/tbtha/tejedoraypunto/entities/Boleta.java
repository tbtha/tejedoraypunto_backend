package com.tbtha.tejedoraypunto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    private Date fechaCompra = new Date();
    
    private Double total;
    
    private Double neto;
    
    private Double iva;
    
    private String estado = "PENDIENTE"; // PENDIENTE, CONFIRMADA, ENVIADA, ENTREGADA, CANCELADA
    
    private String metodoPago; // TRANSFERENCIA, TARJETA, EFECTIVO
    
    private String direccionEnvio;
    
    private String regionEnvio;
    
    private String comunaEnvio;
    
    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleBoleta> detalles;
}
