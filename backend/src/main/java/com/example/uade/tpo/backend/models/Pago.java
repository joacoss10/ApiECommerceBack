package com.example.uade.tpo.backend.models;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = false)
    private Date fechaPago;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = true)
    private String detalles;
}
