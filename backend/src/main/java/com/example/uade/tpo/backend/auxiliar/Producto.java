package com.example.uade.tpo.backend.auxiliar;

import lombok.Data;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
@Data
public class Producto {
    private String nombre;
    private String descripcion;
    private String categoria;


    private double precio;


    private int stockDisponible;

    private List<String> imagenURL;

    private List <MultipartFile> files;

    private String username_vendedor;

}
