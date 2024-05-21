package com.example.uade.tpo.backend.models;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_producto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stockDisponible;



    @OneToMany(mappedBy = "product")
    @Nullable
    private List<Images> imagenURL;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User vendedor;


    /*
    @OneToMany(mappedBy = "product")
    private List<CartElement> cartElementList;

    @OneToMany(mappedBy = "product")
    private List<OrdenElement> ordenElementList;

     */


}
