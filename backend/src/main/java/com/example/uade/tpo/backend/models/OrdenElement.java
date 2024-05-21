package com.example.uade.tpo.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "OrdenElement")
@Data
public class OrdenElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    @JsonIgnore
    private Orden orden;
}
