package com.example.uade.tpo.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id_cupon;

    @Column (nullable = false)
    private long descuento;           //aca va ponele 20 para el 20% de descuento 50 para el 50% y asi

    @Column (nullable = false, unique = true)
    private String name;
    
    private int uso;                  //se inicializa en 0 cada vez q se usa se suma 1
}
