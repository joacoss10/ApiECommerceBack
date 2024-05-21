package com.example.uade.tpo.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Persona")
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column (nullable = false)
    private String nombre;

    @Column (unique = true)
    private String telefono;

}
