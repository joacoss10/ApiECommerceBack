package com.example.uade.tpo.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CartElement")
@Data
public class CartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;


    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Product product;


    @Column(nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;
}
