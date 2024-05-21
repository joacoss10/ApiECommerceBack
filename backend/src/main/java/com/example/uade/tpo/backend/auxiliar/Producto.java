package com.example.uade.tpo.backend.auxiliar;

import lombok.Data;

import java.util.List;
@Data
public class Producto {
    private String nombre;
    private String descripcion;
    private String categoria;


    private double precio;


    private int stockDisponible;

    private List<String> imagenURL;

    private String username_vendedor;

}
