package com.example.uade.tpo.backend.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Pago")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id_pago;

    @JsonIgnore
    @OneToOne(mappedBy = "pago")
    private Orden orden;

    @Column(nullable = false)
    private String medioDePago;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPago;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = true)
    private String detalles;
}
